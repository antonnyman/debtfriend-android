package xyz.alto.debtfriend.friends.view;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.OnOptionsMenuListener;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.ViewBuilder;
import se.dromt.papper.ViewManager;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.FriendsResult;
import xyz.alto.debtfriend.api.model.LoginResult;
import xyz.alto.debtfriend.friends.adapter.FriendsListAdapter;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.main.MainActivity;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by antonnyman on 21/10/15.
 */
public class FriendsListView extends LinearLayout implements OnOptionsMenuListener {

    @Bind(R.id.view_friends_list) RecyclerView mFriendsList;
    FriendsListAdapter mFriendsListAdapter;

    public FriendsListView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_friends_list, this, true);
        ButterKnife.bind(this);
        //Helper.hideToolbar(context);
    }

    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_search_friends, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new FriendsListView(context);
        }
    }

    public ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        List<Friend> mFriends = getFriends();

        mFriendsListAdapter = new FriendsListAdapter(mFriends);
        mFriendsList.setAdapter(mFriendsListAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFriendsList.setLayoutManager(linearLayoutManager);
        //mFriendsList.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration()
        //mFriendsList.setItemAnimator(new DefaultItemAnimator());

    }

    public List<Friend> getFriends() {
        final List<Friend> friends = new ArrayList<>();
        RestClient restClient = new RestClient();

        Call<FriendsResult> call = restClient.getAltoService().getFriends(Helper.getKey(getContext()));
        call.enqueue(new Callback<FriendsResult>() {
            @Override
            public void onResponse(Response<FriendsResult> response, Retrofit retrofit) {
                Log.d("FriendsResult", response.body().getResult() + response.message() + response.code());

                for(Friend f : response.body().getResult()) {
                    Log.d("Ma bro", f.getUsername());
                    friends.add(new Friend(f.getId(), f.getUsername(), f.getEmail(), f.getLastLoggedIn(), f.getRegistredOn(), f.isAdmin(), f.isConfirmed()));
                }

                mFriendsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        return friends;
    }
}
