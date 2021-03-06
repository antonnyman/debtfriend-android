package xyz.alto.debtfriend.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import se.dromt.papper.ViewManager;
import se.dromt.papper.PapperActivity;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.home.view.HomeView;
import xyz.alto.debtfriend.start.view.StartView;
import xyz.alto.debtfriend.utils.Helper;

public class MainActivity extends AppCompatActivity implements PapperActivity {

    private ViewManager mViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewManager = ViewManager.create((ViewGroup) findViewById(R.id.main_container), this);

        if(getLastCustomNonConfigurationInstance() == null) {
            if(Helper.isLoggedIn(this)) {
                mViewManager.addView(new HomeView.Builder());
            } else {
                mViewManager.addView(new StartView.Builder());
            }
        } else {
            mViewManager.rebuildViewStack(getLastCustomNonConfigurationInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mViewManager.setMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mViewManager.menuItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mViewManager.saveViewStack();
    }

    @Override
    public void onBackPressed() {
        mViewManager.goBack();
    }

    @Override
    public ViewManager getViewManager() {
        return mViewManager;
    }

    @Override
    public void close() {
        finish();
    }
}
