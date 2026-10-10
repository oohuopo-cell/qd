#!/usr/bin/env bash
set -e

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export ANDROID_HOME=/workspace/android-sdk
export PATH=$JAVA_HOME/bin:$ANDROID_HOME/build-tools/34.0.0:$ANDROID_HOME/platform-tools:$PATH

BT=$ANDROID_HOME/build-tools/34.0.0
PLATFORM=$ANDROID_HOME/platforms/android-34/android.jar
PROJ=/workspace/project
BUILD=$PROJ/build
LIB=$PROJ/libs

rm -rf $BUILD
mkdir -p $BUILD/gen $BUILD/obj $BUILD/apk

echo "=== 1. aapt2 compile resources ==="
$BT/aapt2 compile --dir $PROJ/res -o $BUILD/compiled_res.zip

echo "=== 2. aapt2 link (manifest + resources) -> base APK + R.java ==="
$BT/aapt2 link \
  -o $BUILD/base.apk \
  -I $PLATFORM \
  --manifest $PROJ/AndroidManifest.xml \
  --java $BUILD/gen \
  --min-sdk-version 21 \
  --target-sdk-version 34 \
  --version-code 15 \
  --version-name 15.0 \
  --auto-add-overlay \
  $BUILD/compiled_res.zip

echo "=== 3. javac compile ==="
find $BUILD/gen $PROJ/src -name "*.java" > $BUILD/sources.txt
javac -source 1.8 -target 1.8 \
  -bootclasspath $PLATFORM \
  -classpath "$PLATFORM:$LIB/api-82.jar" \
  -d $BUILD/obj \
  @$BUILD/sources.txt

echo "=== 4. d8 -> classes.dex ==="
find $BUILD/obj -name "*.class" > $BUILD/classes.txt
$BT/d8 --lib $PLATFORM --output $BUILD/apk @$BUILD/classes.txt

echo "=== 5. add assets (xposed_init) into base.apk ==="
cp $BUILD/base.apk $BUILD/wkvip-unsigned.apk
cd $PROJ
# aapt add needs the APK path; add assets/ keeping path
$BT/aapt add $BUILD/wkvip-unsigned.apk assets/xposed_init

echo "=== 6. add classes.dex into APK ==="
cd $BUILD/apk
$BT/aapt add $BUILD/wkvip-unsigned.apk classes.dex

echo "=== 7. zipalign ==="
$BT/zipalign -f -p 4 $BUILD/wkvip-unsigned.apk $BUILD/wkvip-aligned.apk

echo "=== 8. generate keystore & sign ==="
if [ ! -f $BUILD/debug.keystore ]; then
  keytool -genkeypair -v -keystore $BUILD/debug.keystore -alias wkvip \
    -keyalg RSA -keysize 2048 -validity 10000 \
    -storepass wkvip123 -keypass wkvip123 \
    -dname "CN=WKVip, OU=Dev, O=WKVip, L=NA, ST=NA, C=CN" 2>/dev/null
fi
$BT/apksigner sign --ks $BUILD/debug.keystore --ks-key-alias wkvip \
  --ks-pass pass:wkvip123 --key-pass pass:wkvip123 \
  --out $BUILD/wkvip-v10.apk $BUILD/wkvip-aligned.apk

echo "=== 9. verify ==="
$BT/apksigner verify --verbose $BUILD/wkvip-v10.apk

echo ""
echo "=== DONE: $BUILD/wkvip-v10.apk ==="
ls -la $BUILD/wkvip-v10.apk
