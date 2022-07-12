package com.common.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.common.base.entity.SignIn;
import com.common.base.repository.HttpRepository;
import com.common.libbase.config.LoadState;
import com.common.libbase.vm.BaseViewModel;
import com.common.libnet.data.HttpDisposable;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/8
 * @Description: java类作用描述
 */
public class SignInViewModel extends BaseViewModel {

    private HttpRepository repository = HttpRepository.getInstance();

    public MutableLiveData<SignIn> signInLiveData = new MutableLiveData<>();


    public void signIn(String key,String sign,String timestamps,String userId,String gradeId){
        loadState.postValue(LoadState.LOADING);
        repository.signIn(key,sign,timestamps,userId,gradeId).subscribe(new HttpDisposable<SignIn>() {
            @Override
            public void success(SignIn signIn) {
                loadState.postValue(LoadState.SUCCESS);
                signInLiveData.postValue(signIn);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

}
