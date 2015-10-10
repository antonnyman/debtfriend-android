package xyz.alto.debtfriend.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import se.dromt.papper.PapperActivity;
import se.dromt.papper.PapperView;
import se.dromt.papper.ViewManager;
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
            mViewManager.addViewBuilder(new PapperView.Builder(R.layout.view_start))
                    .createView();
        } else {
            mViewManager.rebuildViewStack(getLastCustomNonConfigurationInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
