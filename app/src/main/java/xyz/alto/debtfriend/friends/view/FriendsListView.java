package xyz.alto.debtfriend.friends.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
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
import xyz.alto.debtfriend.api.model.AddRemoveFriendResult;
import xyz.alto.debtfriend.api.model.FriendsResult;
import xyz.alto.debtfriend.friends.adapter.FriendsListAdapter;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.utils.Helper;
import xyz.alto.debtfriend.utils.ItemClickSupport;

/**
 * Created by antonnyman on 21/10/15.
 */
public class FriendsListView extends LinearLayout implements OnOptionsMenuListener {

    @Bind(R.id.view_friends_list) RecyclerView mFriendsList;
    @Bind(R.id.view_friends_list_fab) FloatingActionButton mFab;
    @BindString(R.string.yes) String yes;
    @BindString(R.string.no) String no;
    FriendsListAdapter mFriendsListAdapter;
    List<Friend> myFriends;
    RestClient mRestClient = new RestClient();

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

        myFriends = getMyFriends();
        mFriendsListAdapter = new FriendsListAdapter(myFriends);
        mFriendsList.setAdapter(mFriendsListAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFriendsList.setLayoutManager(linearLayoutManager);

        ItemClickSupport.addTo(mFriendsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("friend", Parcels.wrap(myFriends.get(position)));
                getViewManager(getContext()).addView(new FriendDetailView.Builder().arguments(bundle));
            }
        });

        ItemClickSupport.addTo(mFriendsList).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Remove friend")
                        .setMessage("Remove friend from your friends list?")
                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeFriend(myFriends.get(position));
                            }
                        })
                        .setNegativeButton(no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                builder.show();
                return true;
            }
        });

    }


    @OnClick(R.id.view_friends_list_fab) void clickFab() {
        getViewManager(getContext()).addView(new AddFriendView.Builder());
    }



    List<Friend> getMyFriends() {
        final List<Friend> friendList = new ArrayList<>();


        Call<FriendsResult> call = mRestClient.getAltoService().getFriends(Helper.getKey(getContext()));
        call.enqueue(new Callback<FriendsResult>() {
            @Override
            public void onResponse(Response<FriendsResult> response, Retrofit retrofit) {

                if (response.body() == null) {
                    Helper.toast(getContext(), "API not running.");
                } else {
                    for (Friend f : response.body().getResult()) {

                        Log.d("Ma bro", f.getUsername());
                        friendList.add(new Friend(f.getId(), f.getUsername(), f.getEmail(), f.getLastLoggedIn(), f.getRegisteredOn(), f.getConfirmedOn(), f.isAdmin(), f.isConfirmed()));
                    }
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

    void removeFriend(Friend f) {
        Call<AddRemoveFriendResult> call = mRestClient.getAltoService().removeFriend(f.getId(), Helper.getKey(getContext()));
        call.enqueue(new Callback<AddRemoveFriendResult>() {
            @Override
            public void onResponse(Response<AddRemoveFriendResult> response, Retrofit retrofit) {
                Helper.snackbar(FriendsListView.this, response.body().getResult(), "OK", 1);
                getMyFriends();
                mFriendsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
