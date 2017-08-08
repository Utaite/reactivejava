package com.pandora.rxandroid.activities;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.pandora.rxandroid.R;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class HelloRxAppActivity extends RxAppCompatActivity {
    public static final String TAG = HelloRxAppActivity.class.getSimpleName();

    @BindView(R.id.tv_hello) TextView mTextView;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        mUnbinder = ButterKnife.bind(this);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "file.txt");


        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                e.onNext(file);
                e.onComplete();
            }})
                //.compose(bindToLifecycle())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .map(f-> new BufferedReader(new FileReader(f)))
                .map(BufferedReader::readLine)
                .doOnEach(l -> Log.d(TAG, l.toString()))
                .subscribe(mTextView::setText, Throwable::printStackTrace);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}


