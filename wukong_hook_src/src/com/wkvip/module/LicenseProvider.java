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

/**
 * 跨进程授权桥：模块进程读 AuthActivity 写的 wklic.dat，悟空分身进程
 * 通过 ContentResolver.query(content://com.wkvip.lic/license) 拿到卡密+过期。
 *
 * 关键：provider 运行在模块自己的进程，getContext().getFilesDir() = 模块内部
 * 存储，与桌面 AuthActivity 写入路径一致；悟空分身进程（不同 UID）经此 provider
 * 即可读到，不受 scoped storage / UID 隔离影响。
 */
public class LicenseProvider extends ContentProvider {

    private static final String TAG = "[LicenseProvider]";
    public static final String AUTHORITY = "com.wkvip.lic";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/license");

    private static final String FILE_NAME = "wklic.dat";
    private static final String SEP = "::";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // 固定两列：card, expire(秒)
        MatrixCursor cursor = new MatrixCursor(new String[]{"card", "expire"});
        String card = "";
        long expire = 0L;
        try {
            File f = new File(getContext().getFilesDir(), FILE_NAME);
            if (f.exists()) {
                BufferedReader r = new BufferedReader(new FileReader(f));
                String line = r.readLine();
                r.close();
                if (line != null && !line.isEmpty()) {
                    String[] parts = line.split(SEP);
                    if (parts.length >= 1) card = parts[0].trim();
                    if (parts.length >= 2) {
                        try { expire = Long.parseLong(parts[1].trim()); }
                        catch (Throwable ignore) { expire = 0L; }
                    }
                }
            }
        } catch (Throwable e) {
            Log.d(TAG, "query 异常: " + e);
        }
        Log.d(TAG, "查询授权: card=" + card + " expire=" + expire);
        cursor.addRow(new Object[]{card, expire});
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.item/vnd.wkvip.license";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) { return null; }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) { return 0; }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) { return 0; }
}
