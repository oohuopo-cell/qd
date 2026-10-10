package com.wkvip.module;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/* loaded from: classes.dex */
public class LicenseProvider extends ContentProvider {
    public static final String AUTHORITY = "com.wkvip.lic";
    public static final Uri CONTENT_URI = Uri.parse("content://com.wkvip.lic/license");
    private static final String FILE_NAME = "wklic.dat";
    private static final String SEP = "::";
    private static final String TAG = "[LicenseProvider]";

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return "vnd.android.cursor.item/vnd.wkvip.license";
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String str3;
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"card", "expire", "type"});
        str3 = "";
        String str4 = "yearly";
        long j = 0;
        try {
            File file = new File(getContext().getFilesDir(), FILE_NAME);
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readLine = bufferedReader.readLine();
                bufferedReader.close();
                if (readLine != null && !readLine.isEmpty()) {
                    String[] split = readLine.split(SEP);
                    str3 = split.length >= 1 ? split[0].trim() : "";
                    if (split.length >= 2) {
                        try {
                            j = Long.parseLong(split[1].trim());
                        } catch (Throwable th) {
                        }
                    }
                    if (j - (System.currentTimeMillis() / 1000) > 315360000) {
                        str4 = "permanent";
                    }
                }
            }
        } catch (Throwable th2) {
            Log.d(TAG, "query 异常: " + th2);
        }
        Log.d(TAG, "查询授权: card=" + str3 + " expire=" + j + " type=" + str4);
        matrixCursor.addRow(new Object[]{str3, Long.valueOf(j), str4});
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
