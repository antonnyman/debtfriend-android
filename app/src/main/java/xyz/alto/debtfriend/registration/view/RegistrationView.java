package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;

import se.dromt.papper.PapperActivity;
import se.dromt.papper.PapperView;
import xyz.alto.debtfriend.R;

/**
 * Created by isak on 2015-10-10.
 */
public class RegistrationView extends PapperView implements PapperActivity.OnCreateOptionsMenuListener {

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

        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("hej");
    }

    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_main, menu);
    }
}
