package android.app;

import android.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.PerformanceCollector;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.TestLooperManager;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.ViewConfiguration;
import android.view.WindowManagerGlobal;
import com.android.internal.content.ReferrerIntent;
import com.ep.DP;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/* loaded from: /workspace/wfp_patch/classes.dex */
public class Instrumentation {
    private static final long CONNECT_TIMEOUT_MILLIS = 5000;
    public static final String REPORT_KEY_IDENTIFIER = "id";
    public static final String REPORT_KEY_STREAMRESULT = "stream";
    private static final String TAG = "Instrumentation";
    private List<ActivityMonitor> mActivityMonitors;
    private Context mAppContext;
    private ComponentName mComponent;
    private Context mInstrContext;
    private PerformanceCollector mPerformanceCollector;
    private Thread mRunner;
    private UiAutomation mUiAutomation;
    private IUiAutomationConnection mUiAutomationConnection;
    private List<ActivityWaiter> mWaitingActivities;
    private IInstrumentationWatcher mWatcher;
    private final Object mSync = new Object();
    private ActivityThread mThread = null;
    private MessageQueue mMessageQueue = null;
    private boolean mAutomaticPerformanceSnapshots = false;
    private Bundle mPerfMetrics = new Bundle();
    private final Object mAnimationCompleteLock = new Object();

    /* renamed from: android.app.Instrumentation$1ContextMenuRunnable, reason: invalid class name */
    /* loaded from: /workspace/wfp_patch/classes.dex */
    class C1ContextMenuRunnable implements Runnable {
        private final Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public C1ContextMenuRunnable(Activity activity, int i, int i2) {
            this.activity = activity;
            this.identifier = i;
            this.flags = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.returnValue = this.activity.getWindow().performContextMenuIdentifierAction(this.identifier, this.flags);
        }
    }

    /* renamed from: android.app.Instrumentation$1MenuRunnable, reason: invalid class name */
    /* loaded from: /workspace/wfp_patch/classes.dex */
    class C1MenuRunnable implements Runnable {
        private final Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public C1MenuRunnable(Activity activity, int i, int i2) {
            this.activity = activity;
            this.identifier = i;
            this.flags = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.returnValue = this.activity.getWindow().performPanelIdentifierAction(0, this.identifier, this.flags);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: /workspace/wfp_patch/classes.dex */
    public final class ActivityGoing implements MessageQueue.IdleHandler {
        private final ActivityWaiter mWaiter;

        public ActivityGoing(ActivityWaiter activityWaiter) {
            this.mWaiter = activityWaiter;
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            synchronized (Instrumentation.this.mSync) {
                Instrumentation.this.mWaitingActivities.remove(this.mWaiter);
                Instrumentation.this.mSync.notifyAll();
            }
            return false;
        }
    }

    /* loaded from: /workspace/wfp_patch/classes.dex */
    public static class ActivityMonitor {
        private final boolean mBlock;
        private final String mClass;
        int mHits;
        private final boolean mIgnoreMatchingSpecificIntents;
        Activity mLastActivity;
        private final ActivityResult mResult;
        private final IntentFilter mWhich;

        public ActivityMonitor() {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = null;
            this.mClass = null;
            this.mResult = null;
            this.mBlock = false;
            this.mIgnoreMatchingSpecificIntents = true;
        }

        public ActivityMonitor(IntentFilter intentFilter, ActivityResult activityResult, boolean z) {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = intentFilter;
            this.mClass = null;
            this.mResult = activityResult;
            this.mBlock = z;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public ActivityMonitor(String str, ActivityResult activityResult, boolean z) {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = null;
            this.mClass = str;
            this.mResult = activityResult;
            this.mBlock = z;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public final IntentFilter getFilter() {
            return this.mWhich;
        }

        public final int getHits() {
            return this.mHits;
        }

        public final Activity getLastActivity() {
            return this.mLastActivity;
        }

        public final ActivityResult getResult() {
            return this.mResult;
        }

        final boolean ignoreMatchingSpecificIntents() {
            return this.mIgnoreMatchingSpecificIntents;
        }

        public final boolean isBlocking() {
            return this.mBlock;
        }

        final boolean match(Context context, Activity activity, Intent intent) {
            if (this.mIgnoreMatchingSpecificIntents) {
                return false;
            }
            synchronized (this) {
                IntentFilter intentFilter = this.mWhich;
                if (intentFilter != null && intentFilter.match(context.getContentResolver(), intent, true, Instrumentation.TAG) < 0) {
                    return false;
                }
                if (this.mClass != null) {
                    String str = null;
                    if (activity != null) {
                        str = activity.getClass().getName();
                    } else if (intent.getComponent() != null) {
                        str = intent.getComponent().getClassName();
                    }
                    if (str == null || !this.mClass.equals(str)) {
                        return false;
                    }
                }
                if (activity != null) {
                    this.mLastActivity = activity;
                    notifyAll();
                }
                return true;
            }
        }

        public ActivityResult onStartActivity(Context context, Intent intent, Bundle bundle) {
            return onStartActivity(intent);
        }

        public ActivityResult onStartActivity(Intent intent) {
            return null;
        }

        public final Activity waitForActivity() {
            Activity activity;
            synchronized (this) {
                while (true) {
                    activity = this.mLastActivity;
                    if (activity == null) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    } else {
                        this.mLastActivity = null;
                    }
                }
            }
            return activity;
        }

        public final Activity waitForActivityWithTimeout(long j) {
            synchronized (this) {
                if (this.mLastActivity == null) {
                    try {
                        wait(j);
                    } catch (InterruptedException e) {
                    }
                }
                Activity activity = this.mLastActivity;
                if (activity == null) {
                    return null;
                }
                this.mLastActivity = null;
                return activity;
            }
        }
    }

    /* loaded from: /workspace/wfp_patch/classes.dex */
    public static final class ActivityResult {
        private final int mResultCode;
        private final Intent mResultData;

        public ActivityResult(int i, Intent intent) {
            this.mResultCode = i;
            this.mResultData = intent;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public Intent getResultData() {
            return this.mResultData;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: /workspace/wfp_patch/classes.dex */
    public static final class ActivityWaiter {
        public Activity activity;
        public final Intent intent;

        public ActivityWaiter(Intent intent) {
            this.intent = intent;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: /workspace/wfp_patch/classes.dex */
    public static final class EmptyRunnable implements Runnable {
        private EmptyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: /workspace/wfp_patch/classes.dex */
    public static final class Idler implements MessageQueue.IdleHandler {
        private final Runnable mCallback;
        private boolean mIdle = false;

        public Idler(Runnable runnable) {
            this.mCallback = runnable;
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            Runnable runnable = this.mCallback;
            if (runnable != null) {
                runnable.run();
            }
            synchronized (this) {
                this.mIdle = true;
                notifyAll();
            }
            return false;
        }

        public void waitForIdle() {
            synchronized (this) {
                while (!this.mIdle) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    /* loaded from: /workspace/wfp_patch/classes.dex */
    private final class InstrumentationThread extends Thread {
        public InstrumentationThread(String str) {
            super(str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Process.setThreadPriority(-8);
            } catch (RuntimeException e) {
                Log.w(Instrumentation.TAG, "Exception setting priority of instrumentation thread " + Process.myTid(), e);
            }
            if (Instrumentation.this.mAutomaticPerformanceSnapshots) {
                Instrumentation.this.startPerformanceSnapshot();
            }
            Instrumentation.this.onStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: /workspace/wfp_patch/classes.dex */
    public static final class SyncRunnable implements Runnable {
        private boolean mComplete;
        private final Runnable mTarget;

        public SyncRunnable(Runnable runnable) {
            this.mTarget = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mTarget.run();
            synchronized (this) {
                this.mComplete = true;
                notifyAll();
            }
        }

        public void waitForComplete() {
            synchronized (this) {
                while (!this.mComplete) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: /workspace/wfp_patch/classes.dex */
    public @interface UiAutomationFlags {
    }

    private void addValue(String str, int i, Bundle bundle) {
        if (!bundle.containsKey(str)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(Integer.valueOf(i));
            bundle.putIntegerArrayList(str, arrayList);
        } else {
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(str);
            if (integerArrayList != null) {
                integerArrayList.add(Integer.valueOf(i));
            }
        }
    }

    private void checkInstrumenting(String str) {
        if (this.mInstrContext == null) {
            throw new RuntimeException(str + " cannot be called outside of instrumented processes");
        }
    }

    public static void checkStartActivityResult(int i, Object obj) {
        if (ActivityManager.isStartResultFatalError(i)) {
            switch (i) {
                case -100:
                    throw new IllegalStateException("Cannot start voice activity on a hidden session");
                case -99:
                    throw new IllegalStateException("Session calling startVoiceActivity does not match active session");
                case -98:
                default:
                    throw new AndroidRuntimeException("Unknown error code " + i + " when starting " + obj);
                case -97:
                    throw new SecurityException("Starting under voice control not allowed for: " + obj);
                case -96:
                    throw new AndroidRuntimeException("Activity could not be started for " + obj);
                case -95:
                    throw new IllegalArgumentException("PendingIntent is not an activity");
                case -94:
                    throw new SecurityException("Not allowed to start activity " + obj);
                case -93:
                    throw new AndroidRuntimeException("FORWARD_RESULT_FLAG used while also requesting a result");
                case -92:
                case -91:
                    if ((obj instanceof Intent) && ((Intent) obj).getComponent() != null) {
                        throw new ActivityNotFoundException("Unable to find explicit activity class " + ((Intent) obj).getComponent().toShortString() + "; have you declared this activity in your AndroidManifest.xml, or does your intent not match its declared <intent-filter>?");
                    }
                    throw new ActivityNotFoundException("No Activity found to handle " + obj);
                case -90:
                    throw new IllegalStateException("Cannot start assistant activity on a hidden session");
                case -89:
                    throw new IllegalStateException("Session calling startAssistantActivity does not match active session");
            }
        }
    }

    private AppComponentFactory getFactory(String str) {
        if (str == null) {
            Log.e(TAG, "No pkg specified, disabling AppComponentFactory");
            return AppComponentFactory.DEFAULT;
        }
        ActivityThread activityThread = this.mThread;
        if (activityThread == null) {
            Log.e(TAG, "Uninitialized ActivityThread, likely app-created Instrumentation, disabling AppComponentFactory", new Throwable());
            return AppComponentFactory.DEFAULT;
        }
        LoadedApk peekPackageInfo = activityThread.peekPackageInfo(str, true);
        if (peekPackageInfo == null) {
            peekPackageInfo = this.mThread.getSystemContext().mPackageInfo;
        }
        return peekPackageInfo.getAppFactory();
    }

    public static Application newApplication(Class<?> cls, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Application application = (Application) cls.newInstance();
        application.attach(context);
        DP.entry(application);
        return application;
    }

    private void postPerformCreate(Activity activity) {
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                for (int i = 0; i < size; i++) {
                    this.mActivityMonitors.get(i).match(activity, activity, activity.getIntent());
                }
            }
        }
    }

    private void prePerformCreate(Activity activity) {
        if (this.mWaitingActivities != null) {
            synchronized (this.mSync) {
                int size = this.mWaitingActivities.size();
                for (int i = 0; i < size; i++) {
                    ActivityWaiter activityWaiter = this.mWaitingActivities.get(i);
                    if (activityWaiter.intent.filterEquals(activity.getIntent())) {
                        activityWaiter.activity = activity;
                        this.mMessageQueue.addIdleHandler(new ActivityGoing(activityWaiter));
                    }
                }
            }
        }
    }

    private void syncInputTransactionsAndInjectEventIntoSelf(MotionEvent motionEvent) {
        boolean z = motionEvent.getAction() == 0 || motionEvent.isFromSource(8194);
        boolean z2 = motionEvent.getAction() == 1;
        if (z) {
            try {
                WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        InputManager.getInstance().injectInputEvent(motionEvent, 2, Process.myUid());
        if (z2) {
            WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
        }
    }

    private final void validateNotAppThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("This method can not be called from the main application thread");
        }
    }

    private void waitForEnterAnimationComplete(Activity activity) {
        synchronized (this.mAnimationCompleteLock) {
            long j = CONNECT_TIMEOUT_MILLIS;
            while (j > 0) {
                try {
                    if (activity.mEnterAnimationComplete) {
                        break;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    this.mAnimationCompleteLock.wait(j);
                    j -= System.currentTimeMillis() - currentTimeMillis;
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public TestLooperManager acquireLooperManager(Looper looper) {
        checkInstrumenting("acquireLooperManager");
        return new TestLooperManager(looper);
    }

    public ActivityMonitor addMonitor(IntentFilter intentFilter, ActivityResult activityResult, boolean z) {
        ActivityMonitor activityMonitor = new ActivityMonitor(intentFilter, activityResult, z);
        addMonitor(activityMonitor);
        return activityMonitor;
    }

    public ActivityMonitor addMonitor(String str, ActivityResult activityResult, boolean z) {
        ActivityMonitor activityMonitor = new ActivityMonitor(str, activityResult, z);
        addMonitor(activityMonitor);
        return activityMonitor;
    }

    public void addMonitor(ActivityMonitor activityMonitor) {
        synchronized (this.mSync) {
            if (this.mActivityMonitors == null) {
                this.mActivityMonitors = new ArrayList();
            }
            this.mActivityMonitors.add(activityMonitor);
        }
    }

    public void addResults(Bundle bundle) {
        try {
            ActivityManager.getService().addInstrumentationResults(this.mThread.getApplicationThread(), bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    final void basicInit(ActivityThread activityThread) {
        this.mThread = activityThread;
    }

    public void callActivityOnCreate(Activity activity, Bundle bundle) {
        prePerformCreate(activity);
        activity.performCreate(bundle);
        postPerformCreate(activity);
    }

    public void callActivityOnCreate(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        prePerformCreate(activity);
        activity.performCreate(bundle, persistableBundle);
        postPerformCreate(activity);
    }

    public void callActivityOnDestroy(Activity activity) {
        activity.performDestroy();
    }

    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        activity.performNewIntent(intent);
    }

    public void callActivityOnNewIntent(Activity activity, ReferrerIntent referrerIntent) {
        String str = activity.mReferrer;
        if (referrerIntent != null) {
            try {
                activity.mReferrer = referrerIntent.mReferrer;
            } catch (Throwable th) {
                activity.mReferrer = str;
                throw th;
            }
        }
        callActivityOnNewIntent(activity, referrerIntent != null ? new Intent((Intent) referrerIntent) : null);
        activity.mReferrer = str;
    }

    public void callActivityOnPause(Activity activity) {
        activity.performPause();
    }

    public void callActivityOnPictureInPictureRequested(Activity activity) {
        activity.onPictureInPictureRequested();
    }

    public void callActivityOnPostCreate(Activity activity, Bundle bundle) {
        activity.onPostCreate(bundle);
    }

    public void callActivityOnPostCreate(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.onPostCreate(bundle, persistableBundle);
    }

    public void callActivityOnRestart(Activity activity) {
        activity.onRestart();
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle bundle) {
        activity.performRestoreInstanceState(bundle);
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.performRestoreInstanceState(bundle, persistableBundle);
    }

    public void callActivityOnResume(Activity activity) {
        activity.mResumed = true;
        activity.onResume();
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                for (int i = 0; i < size; i++) {
                    this.mActivityMonitors.get(i).match(activity, activity, activity.getIntent());
                }
            }
        }
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle bundle) {
        activity.performSaveInstanceState(bundle);
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.performSaveInstanceState(bundle, persistableBundle);
    }

    public void callActivityOnStart(Activity activity) {
        activity.onStart();
    }

    public void callActivityOnStop(Activity activity) {
        activity.onStop();
    }

    public void callActivityOnUserLeaving(Activity activity) {
        activity.performUserLeaving();
    }

    public void callApplicationOnCreate(Application application) {
        application.onCreate();
    }

    public boolean checkMonitorHit(ActivityMonitor activityMonitor, int i) {
        waitForIdleSync();
        synchronized (this.mSync) {
            if (activityMonitor.getHits() < i) {
                return false;
            }
            this.mActivityMonitors.remove(activityMonitor);
            return true;
        }
    }

    public void endPerformanceSnapshot() {
        if (isProfiling()) {
            return;
        }
        this.mPerfMetrics = this.mPerformanceCollector.endSnapshot();
    }

    public void execStartActivities(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent[] intentArr, Bundle bundle) {
        execStartActivitiesAsUser(context, iBinder, iBinder2, activity, intentArr, bundle, context.getUserId());
    }

    public int execStartActivitiesAsUser(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent[] intentArr, Bundle bundle, int i) {
        Bundle bundle2;
        IApplicationThread iApplicationThread = (IApplicationThread) iBinder;
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                try {
                    try {
                        int size = this.mActivityMonitors.size();
                        int i2 = 0;
                        Bundle bundle3 = bundle;
                        while (true) {
                            if (i2 >= size) {
                                break;
                            }
                            ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                            ActivityResult activityResult = null;
                            if (activityMonitor.ignoreMatchingSpecificIntents()) {
                                if (bundle3 == null) {
                                    bundle3 = ActivityOptions.makeBasic().toBundle();
                                }
                                activityResult = activityMonitor.onStartActivity(context, intentArr[0], bundle3);
                            }
                            if (activityResult != null) {
                                activityMonitor.mHits++;
                                return -96;
                            }
                            if (activityMonitor.match(context, null, intentArr[0])) {
                                activityMonitor.mHits++;
                                if (activityMonitor.isBlocking()) {
                                    return -96;
                                }
                            } else {
                                i2++;
                            }
                        }
                        bundle2 = bundle3;
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } else {
            bundle2 = bundle;
        }
        try {
            String[] strArr = new String[intentArr.length];
            for (int i3 = 0; i3 < intentArr.length; i3++) {
                intentArr[i3].migrateExtraStreamToClipData(context);
                intentArr[i3].prepareToLeaveProcess(context);
                strArr[i3] = intentArr[i3].resolveTypeIfNeeded(context.getContentResolver());
            }
            int startActivities = ActivityTaskManager.getService().startActivities(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intentArr, strArr, iBinder2, bundle2, i);
            checkStartActivityResult(startActivities, intentArr[0]);
            return startActivities;
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0077, code lost:
    
        r17 = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(android.content.Context r21, android.os.IBinder r22, android.os.IBinder r23, android.app.Activity r24, android.content.Intent r25, int r26, android.os.Bundle r27) {
        /*
            Method dump skipped, instructions count: 210
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.Instrumentation.execStartActivity(android.content.Context, android.os.IBinder, android.os.IBinder, android.app.Activity, android.content.Intent, int, android.os.Bundle):android.app.Instrumentation$ActivityResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
    
        r17 = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(android.content.Context r19, android.os.IBinder r20, android.os.IBinder r21, java.lang.String r22, android.content.Intent r23, int r24, android.os.Bundle r25) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r15 = r23
            r16 = r20
            android.app.IApplicationThread r16 = (android.app.IApplicationThread) r16
            java.util.List<android.app.Instrumentation$ActivityMonitor> r0 = r1.mActivityMonitors
            r14 = 0
            if (r0 == 0) goto L6f
            java.lang.Object r3 = r1.mSync
            monitor-enter(r3)
            java.util.List<android.app.Instrumentation$ActivityMonitor> r0 = r1.mActivityMonitors     // Catch: java.lang.Throwable -> L68
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L68
            r4 = 0
            r5 = r4
            r4 = r25
        L1c:
            if (r5 >= r0) goto L64
            java.util.List<android.app.Instrumentation$ActivityMonitor> r6 = r1.mActivityMonitors     // Catch: java.lang.Throwable -> L6d
            java.lang.Object r6 = r6.get(r5)     // Catch: java.lang.Throwable -> L6d
            android.app.Instrumentation$ActivityMonitor r6 = (android.app.Instrumentation.ActivityMonitor) r6     // Catch: java.lang.Throwable -> L6d
            r7 = 0
            boolean r8 = r6.ignoreMatchingSpecificIntents()     // Catch: java.lang.Throwable -> L6d
            if (r8 == 0) goto L3d
            if (r4 != 0) goto L38
            android.app.ActivityOptions r8 = android.app.ActivityOptions.makeBasic()     // Catch: java.lang.Throwable -> L6d
            android.os.Bundle r8 = r8.toBundle()     // Catch: java.lang.Throwable -> L6d
            r4 = r8
        L38:
            android.app.Instrumentation$ActivityResult r8 = r6.onStartActivity(r2, r15, r4)     // Catch: java.lang.Throwable -> L6d
            r7 = r8
        L3d:
            if (r7 == 0) goto L47
            int r8 = r6.mHits     // Catch: java.lang.Throwable -> L6d
            int r8 = r8 + 1
            r6.mHits = r8     // Catch: java.lang.Throwable -> L6d
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L6d
            return r7
        L47:
            boolean r8 = r6.match(r2, r14, r15)     // Catch: java.lang.Throwable -> L6d
            if (r8 == 0) goto L61
            int r8 = r6.mHits     // Catch: java.lang.Throwable -> L6d
            int r8 = r8 + 1
            r6.mHits = r8     // Catch: java.lang.Throwable -> L6d
            boolean r8 = r6.isBlocking()     // Catch: java.lang.Throwable -> L6d
            if (r8 == 0) goto L64
            if (r24 < 0) goto L5f
            android.app.Instrumentation$ActivityResult r14 = r6.getResult()     // Catch: java.lang.Throwable -> L6d
        L5f:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L6d
            return r14
        L61:
            int r5 = r5 + 1
            goto L1c
        L64:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L6d
            r17 = r4
            goto L71
        L68:
            r0 = move-exception
            r4 = r25
        L6b:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L6d
            throw r0
        L6d:
            r0 = move-exception
            goto L6b
        L6f:
            r17 = r25
        L71:
            r15.migrateExtraStreamToClipData(r2)     // Catch: android.os.RemoteException -> La3
            r15.prepareToLeaveProcess(r2)     // Catch: android.os.RemoteException -> La3
            android.app.IActivityTaskManager r3 = android.app.ActivityTaskManager.getService()     // Catch: android.os.RemoteException -> La3
            java.lang.String r5 = r19.getOpPackageName()     // Catch: android.os.RemoteException -> La3
            java.lang.String r6 = r19.getAttributionTag()     // Catch: android.os.RemoteException -> La3
            android.content.ContentResolver r0 = r19.getContentResolver()     // Catch: android.os.RemoteException -> La3
            java.lang.String r8 = r15.resolveTypeIfNeeded(r0)     // Catch: android.os.RemoteException -> La3
            r12 = 0
            r13 = 0
            r4 = r16
            r7 = r23
            r9 = r21
            r10 = r22
            r11 = r24
            r0 = r14
            r14 = r17
            int r3 = r3.startActivity(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)     // Catch: android.os.RemoteException -> La3
            checkStartActivityResult(r3, r15)     // Catch: android.os.RemoteException -> La3
            return r0
        La3:
            r0 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.String r4 = "Failure from system"
            r3.<init>(r4, r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.Instrumentation.execStartActivity(android.content.Context, android.os.IBinder, android.os.IBinder, java.lang.String, android.content.Intent, int, android.os.Bundle):android.app.Instrumentation$ActivityResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
    
        r17 = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(android.content.Context r20, android.os.IBinder r21, android.os.IBinder r22, java.lang.String r23, android.content.Intent r24, int r25, android.os.Bundle r26, android.os.UserHandle r27) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r15 = r24
            r16 = r21
            android.app.IApplicationThread r16 = (android.app.IApplicationThread) r16
            java.util.List<android.app.Instrumentation$ActivityMonitor> r0 = r1.mActivityMonitors
            r14 = 0
            if (r0 == 0) goto L6f
            java.lang.Object r3 = r1.mSync
            monitor-enter(r3)
            java.util.List<android.app.Instrumentation$ActivityMonitor> r0 = r1.mActivityMonitors     // Catch: java.lang.Throwable -> L68
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L68
            r4 = 0
            r5 = r4
            r4 = r26
        L1c:
            if (r5 >= r0) goto L64
            java.util.List<android.app.Instrumentation$ActivityMonitor> r6 = r1.mActivityMonitors     // Catch: java.lang.Throwable -> L6d
            java.lang.Object r6 = r6.get(r5)     // Catch: java.lang.Throwable -> L6d
            android.app.Instrumentation$ActivityMonitor r6 = (android.app.Instrumentation.ActivityMonitor) r6     // Catch: java.lang.Throwable -> L6d
            r7 = 0
            boolean r8 = r6.ignoreMatchingSpecificIntents()     // Catch: java.lang.Throwable -> L6d
            if (r8 == 0) goto L3d
            if (r4 != 0) goto L38
            android.app.ActivityOptions r8 = android.app.ActivityOptions.makeBasic()     // Catch: java.lang.Throwable -> L6d
            android.os.Bundle r8 = r8.toBundle()     // Catch: java.lang.Throwable -> L6d
            r4 = r8
        L38:
            android.app.Instrumentation$ActivityResult r8 = r6.onStartActivity(r2, r15, r4)     // Catch: java.lang.Throwable -> L6d
            r7 = r8
        L3d:
            if (r7 == 0) goto L47
            int r8 = r6.mHits     // Catch: java.lang.Throwable -> L6d
            int r8 = r8 + 1
            r6.mHits = r8     // Catch: java.lang.Throwable -> L6d
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L6d
            return r7
        L47:
            boolean r8 = r6.match(r2, r14, r15)     // Catch: java.lang.Throwable -> L6d
            if (r8 == 0) goto L61
            int r8 = r6.mHits     // Catch: java.lang.Throwable -> L6d
            int r8 = r8 + 1
            r6.mHits = r8     // Catch: java.lang.Throwable -> L6d
            boolean r8 = r6.isBlocking()     // Catch: java.lang.Throwable -> L6d
            if (r8 == 0) goto L64
            if (r25 < 0) goto L5f
            android.app.Instrumentation$ActivityResult r14 = r6.getResult()     // Catch: java.lang.Throwable -> L6d
        L5f:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L6d
            return r14
        L61:
            int r5 = r5 + 1
            goto L1c
        L64:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L6d
            r17 = r4
            goto L71
        L68:
            r0 = move-exception
            r4 = r26
        L6b:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L6d
            throw r0
        L6d:
            r0 = move-exception
            goto L6b
        L6f:
            r17 = r26
        L71:
            r15.migrateExtraStreamToClipData(r2)     // Catch: android.os.RemoteException -> Lac
            r15.prepareToLeaveProcess(r2)     // Catch: android.os.RemoteException -> Lac
            android.app.IActivityTaskManager r3 = android.app.ActivityTaskManager.getService()     // Catch: android.os.RemoteException -> Lac
            java.lang.String r5 = r20.getOpPackageName()     // Catch: android.os.RemoteException -> Lac
            java.lang.String r6 = r20.getAttributionTag()     // Catch: android.os.RemoteException -> Lac
            android.content.ContentResolver r0 = r20.getContentResolver()     // Catch: android.os.RemoteException -> Lac
            java.lang.String r8 = r15.resolveTypeIfNeeded(r0)     // Catch: android.os.RemoteException -> Lac
            r12 = 0
            r13 = 0
            int r0 = r27.getIdentifier()     // Catch: android.os.RemoteException -> Lac
            r4 = r16
            r7 = r24
            r9 = r22
            r10 = r23
            r11 = r25
            r18 = r14
            r14 = r17
            r1 = r15
            r15 = r0
            int r0 = r3.startActivityAsUser(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch: android.os.RemoteException -> Laa
            checkStartActivityResult(r0, r1)     // Catch: android.os.RemoteException -> Laa
            return r18
        Laa:
            r0 = move-exception
            goto Lae
        Lac:
            r0 = move-exception
            r1 = r15
        Lae:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.String r4 = "Failure from system"
            r3.<init>(r4, r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.Instrumentation.execStartActivity(android.content.Context, android.os.IBinder, android.os.IBinder, java.lang.String, android.content.Intent, int, android.os.Bundle, android.os.UserHandle):android.app.Instrumentation$ActivityResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0067, code lost:
    
        r18 = r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.app.Instrumentation.ActivityResult execStartActivityAsCaller(android.content.Context r20, android.os.IBinder r21, android.os.IBinder r22, android.app.Activity r23, android.content.Intent r24, int r25, android.os.Bundle r26, boolean r27, int r28) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r3 = r23
            r15 = r24
            r17 = r21
            android.app.IApplicationThread r17 = (android.app.IApplicationThread) r17
            java.util.List<android.app.Instrumentation$ActivityMonitor> r0 = r1.mActivityMonitors
            r14 = 0
            if (r0 == 0) goto L71
            java.lang.Object r4 = r1.mSync
            monitor-enter(r4)
            java.util.List<android.app.Instrumentation$ActivityMonitor> r0 = r1.mActivityMonitors     // Catch: java.lang.Throwable -> L6a
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L6a
            r5 = 0
            r6 = r5
            r5 = r26
        L1e:
            if (r6 >= r0) goto L66
            java.util.List<android.app.Instrumentation$ActivityMonitor> r7 = r1.mActivityMonitors     // Catch: java.lang.Throwable -> L6f
            java.lang.Object r7 = r7.get(r6)     // Catch: java.lang.Throwable -> L6f
            android.app.Instrumentation$ActivityMonitor r7 = (android.app.Instrumentation.ActivityMonitor) r7     // Catch: java.lang.Throwable -> L6f
            r8 = 0
            boolean r9 = r7.ignoreMatchingSpecificIntents()     // Catch: java.lang.Throwable -> L6f
            if (r9 == 0) goto L3f
            if (r5 != 0) goto L3a
            android.app.ActivityOptions r9 = android.app.ActivityOptions.makeBasic()     // Catch: java.lang.Throwable -> L6f
            android.os.Bundle r9 = r9.toBundle()     // Catch: java.lang.Throwable -> L6f
            r5 = r9
        L3a:
            android.app.Instrumentation$ActivityResult r9 = r7.onStartActivity(r2, r15, r5)     // Catch: java.lang.Throwable -> L6f
            r8 = r9
        L3f:
            if (r8 == 0) goto L49
            int r9 = r7.mHits     // Catch: java.lang.Throwable -> L6f
            int r9 = r9 + 1
            r7.mHits = r9     // Catch: java.lang.Throwable -> L6f
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6f
            return r8
        L49:
            boolean r9 = r7.match(r2, r14, r15)     // Catch: java.lang.Throwable -> L6f
            if (r9 == 0) goto L63
            int r9 = r7.mHits     // Catch: java.lang.Throwable -> L6f
            int r9 = r9 + 1
            r7.mHits = r9     // Catch: java.lang.Throwable -> L6f
            boolean r9 = r7.isBlocking()     // Catch: java.lang.Throwable -> L6f
            if (r9 == 0) goto L66
            if (r25 < 0) goto L61
            android.app.Instrumentation$ActivityResult r14 = r7.getResult()     // Catch: java.lang.Throwable -> L6f
        L61:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6f
            return r14
        L63:
            int r6 = r6 + 1
            goto L1e
        L66:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6f
            r18 = r5
            goto L73
        L6a:
            r0 = move-exception
            r5 = r26
        L6d:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6f
            throw r0
        L6f:
            r0 = move-exception
            goto L6d
        L71:
            r18 = r26
        L73:
            r15.migrateExtraStreamToClipData(r2)     // Catch: android.os.RemoteException -> Lb2
            r15.prepareToLeaveProcess(r2)     // Catch: android.os.RemoteException -> Lb2
            android.app.IActivityTaskManager r4 = android.app.ActivityTaskManager.getService()     // Catch: android.os.RemoteException -> Lb2
            java.lang.String r6 = r20.getOpPackageName()     // Catch: android.os.RemoteException -> Lb2
            android.content.ContentResolver r0 = r20.getContentResolver()     // Catch: android.os.RemoteException -> Lb2
            java.lang.String r8 = r15.resolveTypeIfNeeded(r0)     // Catch: android.os.RemoteException -> Lb2
            if (r3 == 0) goto L8f
            java.lang.String r0 = r3.mEmbeddedID     // Catch: android.os.RemoteException -> Lb2
            r10 = r0
            goto L90
        L8f:
            r10 = r14
        L90:
            r12 = 0
            r13 = 0
            r5 = r17
            r7 = r24
            r9 = r22
            r11 = r25
            r0 = r14
            r14 = r18
            r15 = r27
            r16 = r28
            int r4 = r4.startActivityAsCaller(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch: android.os.RemoteException -> Lae
            r5 = r24
            checkStartActivityResult(r4, r5)     // Catch: android.os.RemoteException -> Lac
            return r0
        Lac:
            r0 = move-exception
            goto Lb4
        Lae:
            r0 = move-exception
            r5 = r24
            goto Lb4
        Lb2:
            r0 = move-exception
            r5 = r15
        Lb4:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.String r6 = "Failure from system"
            r4.<init>(r6, r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.Instrumentation.execStartActivityAsCaller(android.content.Context, android.os.IBinder, android.os.IBinder, android.app.Activity, android.content.Intent, int, android.os.Bundle, boolean, int):android.app.Instrumentation$ActivityResult");
    }

    public void execStartActivityFromAppTask(Context context, IBinder iBinder, IAppTask iAppTask, Intent intent, Bundle bundle) {
        IApplicationThread iApplicationThread = (IApplicationThread) iBinder;
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i);
                    ActivityResult activityResult = null;
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        if (bundle == null) {
                            bundle = ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle);
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return;
                    } else if (activityMonitor.match(context, null, intent)) {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return;
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            checkStartActivityResult(iAppTask.startActivity(iApplicationThread.asBinder(), context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), bundle), intent);
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    public void finish(int i, Bundle bundle) {
        if (this.mAutomaticPerformanceSnapshots) {
            endPerformanceSnapshot();
        }
        if (this.mPerfMetrics != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putAll(this.mPerfMetrics);
        }
        UiAutomation uiAutomation = this.mUiAutomation;
        if (uiAutomation != null && !uiAutomation.isDestroyed()) {
            this.mUiAutomation.disconnect();
            this.mUiAutomation = null;
        }
        this.mThread.finishInstrumentation(i, bundle);
    }

    public Bundle getAllocCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong("global_alloc_count", Debug.getGlobalAllocCount());
        bundle.putLong("global_alloc_size", Debug.getGlobalAllocSize());
        bundle.putLong("global_freed_count", Debug.getGlobalFreedCount());
        bundle.putLong("global_freed_size", Debug.getGlobalFreedSize());
        bundle.putLong("gc_invocation_count", Debug.getGlobalGcInvocationCount());
        return bundle;
    }

    public Bundle getBinderCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong("sent_transactions", Debug.getBinderSentTransactions());
        bundle.putLong("received_transactions", Debug.getBinderReceivedTransactions());
        return bundle;
    }

    public ComponentName getComponentName() {
        return this.mComponent;
    }

    public Context getContext() {
        return this.mInstrContext;
    }

    public String getProcessName() {
        return this.mThread.getProcessName();
    }

    public Context getTargetContext() {
        return this.mAppContext;
    }

    public UiAutomation getUiAutomation() {
        return getUiAutomation(0);
    }

    public UiAutomation getUiAutomation(int i) {
        UiAutomation uiAutomation = this.mUiAutomation;
        boolean z = uiAutomation == null || uiAutomation.isDestroyed();
        if (this.mUiAutomationConnection != null) {
            if (!z && this.mUiAutomation.getFlags() == i) {
                return this.mUiAutomation;
            }
            if (z) {
                this.mUiAutomation = new UiAutomation(getTargetContext().getMainLooper(), this.mUiAutomationConnection);
            } else {
                this.mUiAutomation.disconnect();
            }
            if (getTargetContext().getApplicationInfo().targetSdkVersion <= 30) {
                this.mUiAutomation.connect(i);
                return this.mUiAutomation;
            }
            try {
                this.mUiAutomation.connectWithTimeout(i, CONNECT_TIMEOUT_MILLIS);
                return this.mUiAutomation;
            } catch (TimeoutException e) {
                this.mUiAutomation.destroy();
                this.mUiAutomation = null;
            }
        }
        return null;
    }

    final void init(ActivityThread activityThread, Context context, Context context2, ComponentName componentName, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection) {
        this.mThread = activityThread;
        activityThread.getLooper();
        this.mMessageQueue = Looper.myQueue();
        this.mInstrContext = context;
        this.mAppContext = context2;
        this.mComponent = componentName;
        this.mWatcher = iInstrumentationWatcher;
        this.mUiAutomationConnection = iUiAutomationConnection;
    }

    public boolean invokeContextMenuAction(Activity activity, int i, int i2) {
        validateNotAppThread();
        sendKeySync(new KeyEvent(0, 23));
        waitForIdleSync();
        try {
            Thread.sleep(ViewConfiguration.getLongPressTimeout());
            sendKeySync(new KeyEvent(1, 23));
            waitForIdleSync();
            C1ContextMenuRunnable c1ContextMenuRunnable = new C1ContextMenuRunnable(activity, i, i2);
            runOnMainSync(c1ContextMenuRunnable);
            return c1ContextMenuRunnable.returnValue;
        } catch (InterruptedException e) {
            Log.e(TAG, "Could not sleep for long press timeout", e);
            return false;
        }
    }

    public boolean invokeMenuActionSync(Activity activity, int i, int i2) {
        C1MenuRunnable c1MenuRunnable = new C1MenuRunnable(activity, i, i2);
        runOnMainSync(c1MenuRunnable);
        return c1MenuRunnable.returnValue;
    }

    public boolean isInstrumenting() {
        return this.mInstrContext != null;
    }

    public boolean isProfiling() {
        return this.mThread.isProfiling();
    }

    public Activity newActivity(Class<?> cls, Context context, IBinder iBinder, Application application, Intent intent, ActivityInfo activityInfo, CharSequence charSequence, Activity activity, String str, Object obj) throws InstantiationException, IllegalAccessException {
        Activity activity2 = (Activity) cls.newInstance();
        activity2.attach(context, null, this, iBinder, 0, application == null ? new Application() : application, intent, activityInfo, charSequence, activity, str, (Activity.NonConfigurationInstances) obj, new Configuration(), null, null, null, null, null, null);
        return activity2;
    }

    public Activity newActivity(ClassLoader classLoader, String str, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return getFactory((intent == null || intent.getComponent() == null) ? null : intent.getComponent().getPackageName()).instantiateActivity(classLoader, str, intent);
    }

    public Application newApplication(ClassLoader classLoader, String str, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Application instantiateApplication = getFactory(context.getPackageName()).instantiateApplication(classLoader, str);
        instantiateApplication.attach(context);
        DP.entry(instantiateApplication);
        return instantiateApplication;
    }

    public void onCreate(Bundle bundle) {
    }

    public void onDestroy() {
    }

    public void onEnterAnimationComplete() {
        synchronized (this.mAnimationCompleteLock) {
            this.mAnimationCompleteLock.notifyAll();
        }
    }

    public boolean onException(Object obj, Throwable th) {
        return false;
    }

    public void onStart() {
    }

    public void removeMonitor(ActivityMonitor activityMonitor) {
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
    }

    public void resetInTouchMode() {
        setInTouchMode(getContext().getResources().getBoolean(R.bool.config_volumeHushGestureEnabled));
    }

    public void runOnMainSync(Runnable runnable) {
        validateNotAppThread();
        SyncRunnable syncRunnable = new SyncRunnable(runnable);
        this.mThread.getHandler().post(syncRunnable);
        syncRunnable.waitForComplete();
    }

    public void sendCharacterSync(int i) {
        sendKeyDownUpSync(i);
    }

    public void sendKeyDownUpSync(int i) {
        sendKeySync(new KeyEvent(0, i));
        sendKeySync(new KeyEvent(1, i));
    }

    public void sendKeySync(KeyEvent keyEvent) {
        validateNotAppThread();
        long downTime = keyEvent.getDownTime();
        long eventTime = keyEvent.getEventTime();
        int source = keyEvent.getSource();
        if (source == 0) {
            source = 257;
        }
        if (eventTime == 0) {
            eventTime = SystemClock.uptimeMillis();
        }
        if (downTime == 0) {
            downTime = eventTime;
        }
        KeyEvent keyEvent2 = new KeyEvent(keyEvent);
        keyEvent2.setTime(downTime, eventTime);
        keyEvent2.setSource(source);
        keyEvent2.setFlags(keyEvent.getFlags() | 8);
        InputManager.getInstance().injectInputEvent(keyEvent2, 2);
    }

    public void sendPointerSync(MotionEvent motionEvent) {
        validateNotAppThread();
        if ((motionEvent.getSource() & 2) == 0) {
            motionEvent.setSource(4098);
        }
        syncInputTransactionsAndInjectEventIntoSelf(motionEvent);
    }

    public void sendStatus(int i, Bundle bundle) {
        IInstrumentationWatcher iInstrumentationWatcher = this.mWatcher;
        if (iInstrumentationWatcher != null) {
            try {
                iInstrumentationWatcher.instrumentationStatus(this.mComponent, i, bundle);
            } catch (RemoteException e) {
                this.mWatcher = null;
            }
        }
    }

    public void sendStringSync(String str) {
        KeyEvent[] events;
        if (str == null || (events = KeyCharacterMap.load(-1).getEvents(str.toCharArray())) == null) {
            return;
        }
        for (KeyEvent keyEvent : events) {
            sendKeySync(KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), 0));
        }
    }

    public void sendTrackballEventSync(MotionEvent motionEvent) {
        validateNotAppThread();
        if (!motionEvent.isFromSource(4)) {
            motionEvent.setSource(65540);
        }
        InputManager.getInstance().injectInputEvent(motionEvent, 2);
    }

    public void setAutomaticPerformanceSnapshots() {
        this.mAutomaticPerformanceSnapshots = true;
        this.mPerformanceCollector = new PerformanceCollector();
    }

    public void setInTouchMode(boolean z) {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).setInTouchMode(z);
        } catch (RemoteException e) {
        }
    }

    public void start() {
        if (this.mRunner != null) {
            throw new RuntimeException("Instrumentation already started");
        }
        InstrumentationThread instrumentationThread = new InstrumentationThread("Instr: " + getClass().getName());
        this.mRunner = instrumentationThread;
        instrumentationThread.start();
    }

    public Activity startActivitySync(Intent intent) {
        return startActivitySync(intent, null);
    }

    public Activity startActivitySync(Intent intent, Bundle bundle) {
        Activity activity;
        validateNotAppThread();
        synchronized (this.mSync) {
            Intent intent2 = new Intent(intent);
            ActivityInfo resolveActivityInfo = intent2.resolveActivityInfo(getTargetContext().getPackageManager(), 0);
            if (resolveActivityInfo == null) {
                throw new RuntimeException("Unable to resolve activity for: " + intent2);
            }
            String processName = this.mThread.getProcessName();
            if (!resolveActivityInfo.processName.equals(processName)) {
                throw new RuntimeException("Intent in process " + processName + " resolved to different process " + resolveActivityInfo.processName + ": " + intent2);
            }
            intent2.setComponent(new ComponentName(resolveActivityInfo.applicationInfo.packageName, resolveActivityInfo.name));
            ActivityWaiter activityWaiter = new ActivityWaiter(intent2);
            if (this.mWaitingActivities == null) {
                this.mWaitingActivities = new ArrayList();
            }
            this.mWaitingActivities.add(activityWaiter);
            getTargetContext().startActivity(intent2, bundle);
            do {
                try {
                    this.mSync.wait();
                } catch (InterruptedException e) {
                }
            } while (this.mWaitingActivities.contains(activityWaiter));
            activity = activityWaiter.activity;
        }
        waitForEnterAnimationComplete(activity);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        try {
            transaction.apply(true);
            transaction.close();
            return activity;
        } catch (Throwable th) {
            try {
                transaction.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Deprecated
    public void startAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.resetAllCounts();
        Debug.startAllocCounting();
    }

    public void startPerformanceSnapshot() {
        if (isProfiling()) {
            return;
        }
        this.mPerformanceCollector.beginSnapshot((String) null);
    }

    public void startProfiling() {
        if (this.mThread.isProfiling()) {
            File file = new File(this.mThread.getProfileFilePath());
            file.getParentFile().mkdirs();
            Debug.startMethodTracing(file.toString(), 8388608);
        }
    }

    @Deprecated
    public void stopAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.stopAllocCounting();
    }

    public void stopProfiling() {
        if (this.mThread.isProfiling()) {
            Debug.stopMethodTracing();
        }
    }

    public void waitForIdle(Runnable runnable) {
        this.mMessageQueue.addIdleHandler(new Idler(runnable));
        this.mThread.getHandler().post(new EmptyRunnable());
    }

    public void waitForIdleSync() {
        validateNotAppThread();
        Idler idler = new Idler(null);
        this.mMessageQueue.addIdleHandler(idler);
        this.mThread.getHandler().post(new EmptyRunnable());
        idler.waitForIdle();
    }

    public Activity waitForMonitor(ActivityMonitor activityMonitor) {
        Activity waitForActivity = activityMonitor.waitForActivity();
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
        return waitForActivity;
    }

    public Activity waitForMonitorWithTimeout(ActivityMonitor activityMonitor, long j) {
        Activity waitForActivityWithTimeout = activityMonitor.waitForActivityWithTimeout(j);
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
        return waitForActivityWithTimeout;
    }
}
