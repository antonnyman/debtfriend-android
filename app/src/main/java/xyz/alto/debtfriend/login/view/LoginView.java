package xyz.alto.debtfriend.login.view;

import android.content.Context;
import android.util.AttributeSet;

import butterknife.ButterKnife;
import se.dromt.papper.PapperView;

/**
 * Created by Anton on 2015-10-10.
 */
public class LoginView extends PapperView {
    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
}
