package xyz.alto.debtfriend.home.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import se.dromt.papper.OnOptionsMenuListener;
import se.dromt.papper.ViewManager;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.ViewBuilder;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.friends.view.FriendsListView;
import xyz.alto.debtfriend.login.view.LoginView;
import xyz.alto.debtfriend.registration.view.RegistrationView;

/**
 * Created by Anton on 2015-10-10.
 */
public class HomeView extends LinearLayout implements OnOptionsMenuListener {

    public HomeView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_home, this, true);
        ButterKnife.bind(this);
    }

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new HomeView(context);
        }
    }

    private ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }



    @Nullable
    @OnClick(R.id.view_start_button_friends) void clickFriends() {
        getViewManager(getContext()).addView(new FriendsListView.Builder());
    }

    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_search_friends, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
