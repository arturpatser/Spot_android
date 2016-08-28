package com.gridyn.potspot.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.FriendsAdapter;
import com.gridyn.potspot.databinding.FragmentSelectFriendsBinding;
import com.gridyn.potspot.model.FriendModel;
import com.gridyn.potspot.response.FriendsResponse;
import com.gridyn.potspot.utils.FragmentUtils;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SelectFriendsFragment extends Fragment {

    public static final String TAG = SelectFriendsFragment.class.getName();
    private static String ARG_PARTY_SIZE = "partysize";
    private OnSelectFriendsListener mListener;
    private int partySize;

    @BindView(R.id.friends_list_recycler)
    RecyclerView friendsRecycler;

    FriendsAdapter adapter;

    public SelectFriendsFragment() {
        // Required empty public constructor
    }

    public static SelectFriendsFragment newInstance(int partySize) {
        SelectFriendsFragment fragment = new SelectFriendsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARTY_SIZE, partySize);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            partySize = getArguments().getInt(ARG_PARTY_SIZE);
        }

        adapter = new FriendsAdapter(getContext());

        loadFriends();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_friends, container, false);

        ButterKnife.bind(this, rootView);
        FragmentSelectFriendsBinding binding = DataBindingUtil.bind(rootView);

        binding.setShareText(getString(R.string.share_with, String.valueOf(partySize - 1)));

        binding.setAddFriendsListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: clicked share with friends");

                FragmentUtils.closeFragment(getContext(), SelectFriendsFragment.TAG);

                mListener.friendsSelected(adapter.getSelectedItems());
            }
        });

        binding.setAddByEmail(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: clicked add by email ");
            }
        });

        friendsRecycler.setAdapter(adapter);
        friendsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    private void loadFriends() {

        Call<FriendsResponse> friendsResponseCall = ServerApiUtil.initUser().getFriends(Person.getTokenMap());

        friendsResponseCall.enqueue(new Callback<FriendsResponse>() {
            @Override
            public void onResponse(Response<FriendsResponse> response, Retrofit retrofit) {

                Log.d(TAG, "onResponse: ");

                //TODO uncomment, add friends to adapter here
                //adapter.addAll();
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e(TAG, "onFailure: error while load friends = " + Log.getStackTraceString(t));
            }
        });

        ArrayList<FriendModel> arr = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            arr.add(new FriendModel(""+i, false, "topkek"+i));
        }

        adapter.addAll(arr);
    }

    public void setOnSelectFriendsListener(OnSelectFriendsListener onSelectFriendsListener) {

        this.mListener = onSelectFriendsListener;
    }

    public interface OnSelectFriendsListener {

        void friendsSelected(ArrayList<FriendModel> selectedItems);
    }
}
