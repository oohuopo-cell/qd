package com.wkvip.module;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/* loaded from: classes.dex */
public class AuthActivity extends Activity {
    private Button btnActivate;
    private Runnable countdownRunnable;
    private EditText etCard;
    private TextView tvCountdown;
    private TextView tvStatus;
    private Handler handler = new Handler();
    private long expireAt = 0;

    /* renamed from: com.wkvip.module.AuthActivity$1, reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements View.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            final String trim = AuthActivity.this.etCard.getText().toString().trim();
            if (trim.isEmpty()) {
                AuthActivity.this.tvStatus.setText("请输入卡密");
            } else {
                if (KeytLicense.isDebuggerConnected()) {
                    AuthActivity.this.tvStatus.setText("检测到调试环境，无法激活");
                    return;
                }
                AuthActivity.this.tvStatus.setText("正在联网验证...");
                AuthActivity.this.btnActivate.setEnabled(false);
                new Thread(new Runnable() { // from class: com.wkvip.module.AuthActivity.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (KeytLicense.isDebuggerConnected()) {
                            AuthActivity.this.runOnUiThread(new Runnable() { // from class: com.wkvip.module.AuthActivity.1.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AuthActivity.this.btnActivate.setEnabled(true);
                                    AuthActivity.this.tvStatus.setText("检测到调试环境，无法激活");
                                }
                            });
                        } else {
                            final String activate = KeytLicense.activate(AuthActivity.this, trim);
                            AuthActivity.this.runOnUiThread(new Runnable() { // from class: com.wkvip.module.AuthActivity.1.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    AuthActivity.this.btnActivate.setEnabled(true);
                                    if (activate != null) {
                                        AuthActivity.this.tvStatus.setText("激活失败：" + activate);
                                        return;
                                    }
                                    AuthActivity.this.tvStatus.setText("授权成功！\n悟空分身已解锁。");
                                    Toast.makeText(AuthActivity.this, "激活成功", 0).show();
                                    AuthActivity.this.startCountdown();
                                }
                            });
                        }
                    }
                }).start();
            }
        }
    }

    private void checkExistingLicense() {
        try {
            if (KeytLicense.isValid(this)) {
                String storedCard = KeytLicense.storedCard(this);
                this.etCard.setText(storedCard);
                this.tvStatus.setText("已授权\n卡密: " + storedCard);
                startCountdown();
            } else {
                this.tvStatus.setText("请输入卡密");
            }
        } catch (Throwable th) {
            this.tvStatus.setText("请输入卡密");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCountdown() {
        try {
            long expireAt = KeytLicense.expireAt(this);
            this.expireAt = expireAt;
            if (expireAt <= 0) {
                this.tvCountdown.setVisibility(0);
                this.tvCountdown.setText("到期时间未知");
                this.tvCountdown.setTextColor(-6710887);
                return;
            }
            if (this.expireAt - (System.currentTimeMillis() / 1000) > 3153600000L) {
                this.tvCountdown.setVisibility(0);
                this.tvCountdown.setText("永久授权");
                this.tvCountdown.setTextColor(-11751600);
            } else {
                stopCountdown();
                Runnable runnable = new Runnable() { // from class: com.wkvip.module.AuthActivity.3
                    @Override // java.lang.Runnable
                    public void run() {
                        long currentTimeMillis = AuthActivity.this.expireAt - (System.currentTimeMillis() / 1000);
                        if (currentTimeMillis <= 0) {
                            AuthActivity.this.tvCountdown.setVisibility(0);
                            AuthActivity.this.tvCountdown.setText("卡密已过期");
                            AuthActivity.this.tvCountdown.setTextColor(-65536);
                        } else {
                            AuthActivity.this.tvCountdown.setVisibility(0);
                            AuthActivity.this.tvCountdown.setTextColor(-10066262);
                            AuthActivity.this.tvCountdown.setText(String.format("剩余 %d天 %02d:%02d:%02d", Long.valueOf(currentTimeMillis / 86400), Long.valueOf((currentTimeMillis % 86400) / 3600), Long.valueOf((currentTimeMillis % 3600) / 60), Long.valueOf(currentTimeMillis % 60)));
                            AuthActivity.this.handler.postDelayed(this, 1000L);
                        }
                    }
                };
                this.countdownRunnable = runnable;
                this.handler.post(runnable);
            }
        } catch (Throwable th) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopCountdown() {
        Runnable runnable = this.countdownRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
            this.countdownRunnable = null;
        }
        this.tvCountdown.setVisibility(8);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        this.etCard = (EditText) findViewById(R.id.etCard);
        this.tvStatus = (TextView) findViewById(R.id.tvStatus);
        this.tvCountdown = (TextView) findViewById(R.id.tvCountdown);
        this.btnActivate = (Button) findViewById(R.id.btnActivate);
        checkExistingLicense();
        this.btnActivate.setOnClickListener(new AnonymousClass1());
        ((Button) findViewById(R.id.btnClear)).setOnClickListener(new View.OnClickListener() { // from class: com.wkvip.module.AuthActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KeytLicense.clear(AuthActivity.this);
                AuthActivity.this.tvStatus.setText("授权已清除，请重新输入卡密");
                AuthActivity.this.stopCountdown();
                Toast.makeText(AuthActivity.this, "已清除", 0).show();
            }
        });
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        stopCountdown();
    }
}
