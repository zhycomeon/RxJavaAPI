package org.icegeneral.rxjavaapi.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class SkipLastActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("忽略后N项数据，只保留前面的数据。与TakeLast一样有2个重载方法skipLast(long,TimeUnit)和skipLast(long,TimeUnit,Scheduler))\n\n" +
                "observable = Observable.range(0, 10).skipLast(4);");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        observable = Observable.range(0, 10).skipLast(4);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        sb.append("onCompleted()");
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }

                    @Override
                    public void onError(Throwable e) {
                        sb.append("onError() ");
                        sb.append(e.getMessage());
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }

                    @Override
                    public void onNext(Integer o) {
                        sb.append("onNext() ");
                        sb.append(o);
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }
                });
            }
        });

    }
}
