package xyz.alto.debtfriend.login.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;

/**
 * Created by Anton on 2015-10-10.
 */
public class LoginView extends LinearLayout {

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new LoginView(context);
        }
    }

    public LoginView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_login, this, true);
    }
}
