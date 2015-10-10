package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import se.dromt.papper.PapperView;
import se.dromt.papper.activity.OnOptionsMenuListener;
import xyz.alto.debtfriend.R;

/**
 * Created by isak on 2015-10-10.
 */
public class RegistrationView extends PapperView implements OnOptionsMenuListener {

    public RegistrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnCreateOptionsMenuListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_test, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about) {
            return true;
        }
        return false;
    }
}
