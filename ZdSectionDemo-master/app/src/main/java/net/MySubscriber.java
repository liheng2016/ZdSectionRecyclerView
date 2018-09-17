package net;


import rx.Subscriber;

/**
 * Created by niudong on 2017/5/1.
 */
public abstract class MySubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
