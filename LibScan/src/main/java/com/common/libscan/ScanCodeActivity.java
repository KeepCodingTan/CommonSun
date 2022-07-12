package com.common.libscan;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import java.util.Vector;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/9
 * @Description: java类作用描述
 */
public class ScanCodeActivity extends AppCompatActivity implements DecodeCallback {
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private Vector<ScanObserver> mObservers = new Vector<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        setNoActionBar();
        scannerView = findViewById(R.id.scanner_view);
        queryObservers();
        initScan();
    }

    private void setNoActionBar() {
        Window window = getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.INVISIBLE;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initScan() {
        mCodeScanner = new CodeScanner(this,scannerView);
        mCodeScanner.setDecodeCallback(this);
    }

    private void queryObservers() {
        Vector<Class<? extends ScanObserver>> classVector = ScanManager.getInstance().queryObservers();
        for (Class<? extends ScanObserver> cls : classVector){
            try {
                mObservers.add(cls.newInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onDecoded(@NonNull Result result) {
        if(mObservers != null){
            for(ScanObserver so : mObservers){
                if(so.match(mCodeScanner,result)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            so.handle(mCodeScanner,result);
                        }
                    });
                    break;
                }
            }
        }
    }
}
