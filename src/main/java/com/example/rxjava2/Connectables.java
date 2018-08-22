package com.example.rxjava2;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class Connectables {

    public static void main(String[] args) throws InterruptedException {
        connectAndPublish();
        refCount();
        replay();
    }

    private static void connectAndPublish() throws InterruptedException {

        ConnectableObservable<Long> connectableObservable = Observable.interval(1000, TimeUnit.MILLISECONDS).publish();


        Disposable disposable = connectableObservable.connect();

        Thread.sleep(2000);
        connectableObservable.subscribe(integer -> System.out.println("Before calling connect " + integer));

        Thread.sleep(2000);
        connectableObservable.subscribe(integer -> System.out.println("After calling connect " + integer));
        Thread.sleep(3000);

        disposable.dispose();
    }

    /**
     * Starts the subscription from beginning if any observer subscribers after all previous subscribers has unsubscribed.
     * @throws InterruptedException
     */
    private static void refCount() throws InterruptedException {
        System.out.println();
        System.out.println("calling refCount");

        ConnectableObservable<Long> connectableObservable = Observable.interval(1000, TimeUnit.MILLISECONDS).publish();


        Observable<Long> longObservable = connectableObservable.refCount();

        Thread.sleep(2000);
        Disposable disposable1 = longObservable.subscribe(integer -> System.out.println("Observer#1:" + integer));

        Thread.sleep(2000);
        Disposable disposable2 = longObservable.subscribe(integer -> System.out.println("Observer#2:" + integer));
        Thread.sleep(3000);

        disposable1.dispose();
        disposable2.dispose();


        Disposable disposable3 = longObservable.subscribe(integer -> System.out.println("Observer#3:" + integer));

        Thread.sleep(3000);
        disposable3.dispose();
    }

    private static void replay() throws InterruptedException {
        System.out.println();
        System.out.println("calling replay");

        ConnectableObservable<Long> connectableObservable = Observable.interval(1000, TimeUnit.MILLISECONDS).replay();


        Disposable disposable = connectableObservable.connect();

        Thread.sleep(2000);
        connectableObservable.subscribe(integer -> System.out.println("Observer#1:  " + integer));

        Thread.sleep(2000);
        connectableObservable.subscribe(integer -> System.out.println("Observer#2:  " + integer));
        Thread.sleep(3000);

        disposable.dispose();
    }

}