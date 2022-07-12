package com.common.libscan;

import com.budiyev.android.codescanner.CodeScanner;
import com.google.zxing.Result;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/9
 * @Description: java类作用描述
 */
public interface ScanObserver {

    boolean match(CodeScanner scanner,Result result);


    void handle(CodeScanner scanner,Result result);

}
