package xyz.alto.debtfriend.friends.view;

import android.content.Context;
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
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
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
import xyz.alto.debtfriend.api.model.AddRemoveFriendResult;
import xyz.alto.debtfriend.api.model.FriendsResult;
import xyz.alto.debtfriend.friends.adapter.AddFriendAdapter;
import xyz.alto.debtfriend.friends.model.Friend;
import xyz.alto.debtfriend.utils.Helper;
import xyz.alto.debtfriend.utils.ItemClickSupport;

/**
 * Created by antonnyman on 02/11/15.
 */
public class AddFriendView extends LinearLayout implements OnOptionsMenuListener {

    @Bind(R.id.view_add_friends_list) RecyclerView mFriendsList;
    @BindString(R.string.ok) String ok;
    AddFriendAdapter mAddFriendAdapter;
    List<Friend> mSearchFriends;
    RestClient mRestClient = new RestClient();

    public AddFriendView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_add_friend_list, this, true);
        ButterKnife.bind(this);
    }

    public ViewManager getViewManager(Context context) {
        return ((PapperActivity) context).getViewManager();
    }


    @Override
    public void onOptionsMenuCreated(Menu menu) {
        new MenuInflater(getContext()).inflate(R.menu.menu_search_friends, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search_friends_search_item);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.requestFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFriend(query);
                searchView.clearFocus();
                Helper.hideKeyboard(AddFriendView.this, getContext());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchFriend(query);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public static class Builder extends ViewBuilder {
        @Override
        protected View build(Context context, ViewGroup container) {
            return new AddFriendView(context);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mSearchFriends = new ArrayList<>();
        mAddFriendAdapter = new AddFriendAdapter(mSearchFriends);
        mFriendsList.setAdapter(mAddFriendAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFriendsList.setLayoutManager(linearLayoutManager);


        ItemClickSupport.addTo(mFriendsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                addFriendToFriendslist(mSearchFriends.get(position));
                Helper.hideKeyboard(AddFriendView.this, getContext());
            }
        });
    }

    public void searchFriend(String s) {
        mSearchFriends.clear();

        Call<FriendsResult> call = mRestClient.getAltoService().findUser(s);
        call.enqueue(new Callback<FriendsResult>() {
            @Override
            public void onResponse(Response<FriendsResult> response, Retrofit retrofit) {
                if (response.body() != null) {
                    for (Friend f : response.body().getResult()) {
                        mSearchFriends.add(new Friend(
                                f.getId(),
                                f.getUsername(),
                                f.getEmail(),
                                f.getLastLoggedIn(),
                                f.getRegisteredOn(),
                                f.getConfirmedOn(),
                                f.isAdmin(),
                                f.isConfirmed()));
                        Log.d("This friend", f.getUsername());
                    }
                }
                mAddFriendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void addFriendToFriendslist(final Friend f) {

        Call<AddRemoveFriendResult> call = mRestClient.getAltoService().addFriend(f.getId(), Helper.getKey(getContext()));
        call.enqueue(new Callback<AddRemoveFriendResult>() {
            @Override
            public void onResponse(Response<AddRemoveFriendResult> response, Retrofit retrofit) {
                Log.d("Response", response.body() + " " + response.code() + " " + response.isSuccess());
                Helper.snackbar(AddFriendView.this, "Added " + f.getUsername() + " to your friends list", ok, 1);
                getViewManager(getContext()).addView(new FriendsListView.Builder());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
