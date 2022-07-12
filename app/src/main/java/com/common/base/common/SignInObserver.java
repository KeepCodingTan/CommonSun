package com.common.base.common;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.budiyev.android.codescanner.CodeScanner;
import com.common.base.act.SignInActvity;
import com.common.libbase.App;
import com.common.libscan.ScanObserver;
import com.google.zxing.Result;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/9
 * @Description: java类作用描述
 */
public class SignInObserver implements ScanObserver {

    @Override
    public boolean match(CodeScanner scanner, Result result) {
        String content = result.getText();
        if(content.startsWith("http://") || content.startsWith("https://")){
            Uri uri = Uri.parse(content);
            String classId = uri.getQueryParameter("classId");
            if(!TextUtils.isEmpty(classId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void handle(CodeScanner scanner, Result result) {
        Intent intent = new Intent(ActivityUtils.getTopActivity(), SignInActvity.class);
        ActivityUtils.getTopActivity().startActivity(intent);
    }

}
