package xyz.alto.debtfriend.friends.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.dromt.papper.PapperActivity;
import se.dromt.papper.ViewBuilder;
import se.dromt.papper.ViewManager;
import xyz.alto.debtfriend.R;
import xyz.alto.debtfriend.api.RestClient;
import xyz.alto.debtfriend.api.model.FriendsResult;
import xyz.alto.debtfriend.api.model.LoginResult;
import xyz.alto.debtfriend.friends.adapter.FriendsListAdapter;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.utils.Helper;

/**
 * Created by antonnyman on 21/10/15.
 */
public class FriendsListView extends LinearLayout {

    @Bind(R.id.view_friends_list) RecyclerView mFriendsList;

    public FriendsListView(Context context) {
        super(context);
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

        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(mFriends);
        mFriendsList.setAdapter(friendsListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFriendsList.setLayoutManager(linearLayoutManager);

    }

    public List<Friend> getFriends() {
        List<Friend> friends = new ArrayList<>();
        RestClient restClient = new RestClient();

        Call<FriendsResult> call = restClient.getAltoService().getFriends(Helper.getKey(getContext()));
        call.enqueue(new Callback<FriendsResult>() {
            @Override
            public void onResponse(Response<FriendsResult> response, Retrofit retrofit) {
                Log.d("FriendsResult", response.body() + response.message() + response.code());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        return friends;
    }
}
