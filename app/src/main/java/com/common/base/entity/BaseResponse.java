package com.common.base.entity;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/8
 * @Description: java类作用描述
 */
public class BaseResponse<T> {

    public String message;

    public Boolean success;

    public T entity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
