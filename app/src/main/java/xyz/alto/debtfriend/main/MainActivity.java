package xyz.alto.debtfriend.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import se.dromt.papper.PapperView;
import se.dromt.papper.ViewManager;
import se.dromt.papper.activity.PapperActivity;
import xyz.alto.debtfriend.R;

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
            mViewManager.addView(new PapperView.Builder(R.layout.view_start));
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
