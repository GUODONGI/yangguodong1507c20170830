package rikao.bwiei.com.yangguodong1507c20170830;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 0514 on 2017/8/30.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
    }
}
