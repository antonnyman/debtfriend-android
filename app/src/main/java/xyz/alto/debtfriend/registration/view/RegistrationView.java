package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.AttributeSet;

import se.dromt.papper.PapperView;

/**
 * Created by isak on 2015-10-10.
 */
public class RegistrationView extends PapperView {

    public RegistrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Gör inget här
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // Initera saker här
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /* Om PapperView.Builder har skickats med argument (bundle) eller presenter (object),
        kan de hämtas här med getArguments() och getPresenter() */
    }
}
