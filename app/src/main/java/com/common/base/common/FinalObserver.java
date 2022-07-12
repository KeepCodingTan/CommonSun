package com.common.base.common;

import android.os.Handler;

import com.blankj.utilcode.util.ToastUtils;
import com.budiyev.android.codescanner.CodeScanner;
import com.common.libscan.ScanObserver;
import com.google.zxing.Result;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/9
 * @Description: java类作用描述
 */
public class FinalObserver implements ScanObserver {

    private static Handler handler = new Handler();

    @Override
    public boolean match(CodeScanner scanner, Result result) {
        return true;
    }

    @Override
    public void handle(CodeScanner scanner, Result result) {
        ToastUtils.showShort("无法识别");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scanner.startPreview();
            }
        },2500);
    }

}
