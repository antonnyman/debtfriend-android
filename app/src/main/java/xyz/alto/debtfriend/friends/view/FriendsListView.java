package xyz.alto.debtfriend.friends.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import xyz.alto.debtfriend.friends.adapter.FriendsListAdapter;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by antonnyman on 21/10/15.
 */
public class FriendsListView extends LinearLayout implements OnOptionsMenuListener {

    @Bind(R.id.view_friends_list) RecyclerView mFriendsList;
    @Bind(R.id.view_friends_list_fab) FloatingActionButton mFab;
    FriendsListAdapter mFriendsListAdapter;

    public FriendsListView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_friends_list, this, true);
        ButterKnife.bind(this);
    }

    @Override
    public void onOptionsMenuCreated(Menu menu) {


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

        mFriendsListAdapter = new FriendsListAdapter(getMyFriends());
        mFriendsList.setAdapter(mFriendsListAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFriendsList.setLayoutManager(linearLayoutManager);

    }

    @OnClick(R.id.view_friends_list_fab) void clickFab() {
        getViewManager(getContext()).addView(new AddFriendView.Builder());
    }


    List<Friend> getMyFriends() {
        final List<Friend> friendList = new ArrayList<>();
        RestClient restClient = new RestClient();

        Call<FriendsResult> call = restClient.getAltoService().getFriends(Helper.getKey(getContext()));
        call.enqueue(new Callback<FriendsResult>() {
            @Override
            public void onResponse(Response<FriendsResult> response, Retrofit retrofit) {

                for (Friend f : response.body().getResult()) {
                    Log.d("Ma bro", f.getUsername());
                    friendList.add(new Friend(f.getId(), f.getUsername(), f.getEmail(), f.getLastLoggedIn(), f.getRegisteredOn(), f.getConfirmedOn(), f.isAdmin(), f.isConfirmed()));
                }

                mFriendsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        return friendList;
    }
}
