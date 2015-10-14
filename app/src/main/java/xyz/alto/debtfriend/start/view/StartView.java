package xyz.alto.debtfriend.start.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.dromt.papper.ViewManager;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.login.view.LoginView;
import xyz.alto.debtfriend.registration.view.RegistrationView;

/**
 * Created by Anton on 2015-10-10.
 */
public class StartView extends LinearLayout {


    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new StartView(context);
        }
    }

    public StartView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_start, this, true);
        ButterKnife.bind(this);
    }


    public ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }

    @OnClick (R.id.view_start_button_register) void clickRegister() {
        Log.d(getClass().getName(), "click!");
        getViewManager(getContext())
                .addView(new RegistrationView.Builder());
    }

    @OnClick (R.id.view_start_button_login) void clickLogin() {
        Log.d(getClass().getName(), "click!");
        getViewManager(getContext())
                .addView(new LoginView.Builder());
    }
}
