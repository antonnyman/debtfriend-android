package xyz.alto.debtfriend.start.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import se.dromt.papper.OnOptionsMenuListener;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.ViewBuilder;
import se.dromt.papper.ViewManager;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.login.view.LoginView;
import xyz.alto.debtfriend.registration.view.RegistrationView;

/**
 * Created by isak on 2015-10-27.
 */
public class StartView extends LinearLayout implements OnOptionsMenuListener {

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

    private ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }

    @Nullable
    @OnClick(R.id.view_start_default_button_register) void clickRegister() {
        Log.d(getClass().getName(), "click!");
        getViewManager(getContext())
                .addView(new RegistrationView.Builder());
    }

    @Nullable
    @OnClick (R.id.view_start_default_button_login) void clickLogin() {
        Log.d(getClass().getName(), "click!");
        getViewManager(getContext())
                .addView(new LoginView.Builder());
    }

    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_start, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
