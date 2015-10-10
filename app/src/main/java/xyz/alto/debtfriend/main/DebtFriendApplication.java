package xyz.alto.debtfriend.main;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by isak on 2015-10-10.
 */
public class DebtFriendApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
