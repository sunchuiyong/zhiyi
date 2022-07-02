package com.yinggu.utils;

public class ResultObject<T> {
    private boolean result = true;
    private int code = 200;
    private T dt;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getDt() {
        return dt;
    }

    public void setDt(T dt) {
        this.dt = dt;
    }

    public static ResultObject obj(Object dt){
        ResultObject rs = new ResultObject();
        rs.setDt(dt);
        return rs;
    }
}
