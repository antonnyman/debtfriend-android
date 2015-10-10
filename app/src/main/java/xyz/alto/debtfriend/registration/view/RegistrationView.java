package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import se.dromt.papper.PapperActivity;
import se.dromt.papper.PapperView;
import se.dromt.papper.ViewManager;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.main.MainActivity;

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
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(getClass().getName(), "OnAttached");
        /* Om PapperView.Builder har skickats med argument (bundle) eller presenter (object),
        kan de hämtas här med getArguments() och getPresenter() */
    }
}
