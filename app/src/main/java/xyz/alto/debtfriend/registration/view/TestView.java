package xyz.alto.debtfriend.registration.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import se.dromt.papper.activity.OnCreateOptionsMenuListener;
import se.dromt.papper.PapperView;
import xyz.alto.debtfriend.R;

/**
 * Created by isak on 2015-10-10.
 */
public class TestView extends PapperView implements OnCreateOptionsMenuListener {

    TextView mTextView;

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnCreateOptionsMenuListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) findViewById(R.id.bundled);
    }


    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_test, menu);
    }
}
