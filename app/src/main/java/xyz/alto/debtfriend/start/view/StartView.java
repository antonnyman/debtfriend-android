package xyz.alto.debtfriend.start.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.dromt.papper.PapperView;
import xyz.alto.debtfriend.R;

/**
 * Created by Anton on 2015-10-10.
 */
public class StartView extends PapperView {

    @Bind(R.id.view_start_button_register)
    Button mRegistrationButton;

    @Bind(R.id.view_start_button_login)
    Button mLoginButton;

    public StartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Gör inget här
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // Initera saker här

        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /* Om PapperView.Builder har skickats med argument (bundle) eller presenter (object),
        kan de hämtas här med getArguments() och getPresenter() */
    }

    @OnClick(R.id.view_start_button_register) void clickRegister() {
        getViewManager(getContext())
                .addView(new PapperView.Builder(R.layout.view_registration));
    }

    @OnClick(R.id.view_start_button_login) void clickLogin() {
        getViewManager(getContext())
                .addView(new PapperView.Builder(R.layout.view_login));
    }
}
