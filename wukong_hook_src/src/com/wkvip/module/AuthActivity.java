package com.wkvip.module;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 桌面授权 Activity — 打开 App 输入卡密，联网验证后写入 system property / 文件
 * 悟空分身模块只读授权状态，不弹卡密界面
 */
public class AuthActivity extends Activity {

    private EditText etCard;
    private TextView tvStatus;
    private TextView tvCountdown;
    private Button btnActivate;
    private Handler handler = new Handler();
    private Runnable countdownRunnable;
    private long expireAt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        etCard = (EditText) findViewById(R.id.etCard);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvCountdown = (TextView) findViewById(R.id.tvCountdown);
        btnActivate = (Button) findViewById(R.id.btnActivate);

        // 检查已有授权
        checkExistingLicense();

        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String card = etCard.getText().toString().trim();
                if (card.isEmpty()) {
                    tvStatus.setText("请输入卡密");
                    return;
                }
                // 反调试检测
                if (KeytLicense.isDebuggerConnected()) {
                    tvStatus.setText("检测到调试环境，无法激活");
                    return;
                }
                tvStatus.setText("正在联网验证...");
                btnActivate.setEnabled(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 二次反调试检测
                        if (KeytLicense.isDebuggerConnected()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnActivate.setEnabled(true);
                                    tvStatus.setText("检测到调试环境，无法激活");
                                }
                            });
                            return;
                        }
                        final String err = KeytLicense.activate(AuthActivity.this, card);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnActivate.setEnabled(true);
                                if (err == null) {
                                    tvStatus.setText("授权成功！\n悟空分身已解锁。");
                                    Toast.makeText(AuthActivity.this, "激活成功", Toast.LENGTH_SHORT).show();
                                    startCountdown();
                                } else {
                                    tvStatus.setText("激活失败：" + err);
                                }
                            }
                        });
                    }
                }).start();
            }
        });

        Button btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeytLicense.clear(AuthActivity.this);
                tvStatus.setText("授权已清除，请重新输入卡密");
                stopCountdown();
                Toast.makeText(AuthActivity.this, "已清除", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startCountdown() {
        try {
            expireAt = KeytLicense.expireAt(AuthActivity.this);
            if (expireAt <= 0) {
                tvCountdown.setVisibility(View.VISIBLE);
                tvCountdown.setText("到期时间未知");
                tvCountdown.setTextColor(0xFF999999);
                return;
            }
            long now = System.currentTimeMillis() / 1000;
            if (expireAt - now > 36500 * 86400L) {
                tvCountdown.setVisibility(View.VISIBLE);
                tvCountdown.setText("永久授权");
                tvCountdown.setTextColor(0xFF4CAF50);
                return;
            }

            stopCountdown();
            countdownRunnable = new Runnable() {
                @Override
                public void run() {
                    long now = System.currentTimeMillis() / 1000;
                    long diff = expireAt - now;
                    if (diff <= 0) {
                        tvCountdown.setVisibility(View.VISIBLE);
                        tvCountdown.setText("卡密已过期");
                        tvCountdown.setTextColor(0xFFFF0000);
                        return;
                    }
                    long days = diff / 86400;
                    long hours = (diff % 86400) / 3600;
                    long mins = (diff % 3600) / 60;
                    long secs = diff % 60;
                    tvCountdown.setVisibility(View.VISIBLE);
                    tvCountdown.setTextColor(0xFF6666AA);
                    tvCountdown.setText(String.format("剩余 %d天 %02d:%02d:%02d", days, hours, mins, secs));
                    handler.postDelayed(this, 1000);
                }
            };
            handler.post(countdownRunnable);
        } catch (Throwable e) {}
    }

    private void stopCountdown() {
        if (countdownRunnable != null) {
            handler.removeCallbacks(countdownRunnable);
            countdownRunnable = null;
        }
        tvCountdown.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCountdown();
    }

    private void checkExistingLicense() {
        try {
            if (KeytLicense.isValid(AuthActivity.this)) {
                String card = KeytLicense.storedCard(AuthActivity.this);
                etCard.setText(card);
                tvStatus.setText("已授权\n卡密: " + card);
                startCountdown();
            } else {
                tvStatus.setText("请输入卡密");
            }
        } catch (Throwable e) {
            tvStatus.setText("请输入卡密");
        }
    }
}
