package fengyu.aoshen.topeakdemo.controller;

/**
 * Created by David on 2017/3/30.
 */
public interface IAPPCallback<T> {
    void onFinish(T object);
}
