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
import com.gridyn.potspot.interfaces.SelectFriendsInterface;
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

public class SelectFriendsFragment extends Fragment implements SelectFriendsInterface {

    public static final String TAG = SelectFriendsFragment.class.getName();
    private static String ARG_PARTY_SIZE = "partysize";
    private static String ARG_FRIENDS_SELECTED = "friends";
    private OnSelectFriendsListener mListener;
    private int partySize;

    @BindView(R.id.friends_list_recycler)
    RecyclerView friendsRecycler;

    FriendsAdapter adapter;

    FragmentSelectFriendsBinding binding;
    private ArrayList<FriendModel> friendsArr;

    public SelectFriendsFragment() {
        // Required empty public constructor
    }

    public static SelectFriendsFragment newInstance(int partySize, ArrayList<FriendModel> arrayList) {
        SelectFriendsFragment fragment = new SelectFriendsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARTY_SIZE, partySize);
        args.putParcelableArrayList(ARG_FRIENDS_SELECTED, arrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            partySize = getArguments().getInt(ARG_PARTY_SIZE);
            friendsArr = getArguments().getParcelableArrayList(ARG_FRIENDS_SELECTED);
        }

        adapter = new FriendsAdapter(getContext(), this, partySize);

        loadFriends();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_friends, container, false);

        ButterKnife.bind(this, rootView);
        binding = DataBindingUtil.bind(rootView);

        binding.setShareText(getString(R.string.share_with, ""));

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

                ArrayList<FriendModel> arr = new ArrayList<>();

                for (int i = 0; i < 10; i++) {

                    arr.add(new FriendModel(""+i, false, "topkek"+i));
                }

                adapter.addAll(arr);

                for (int j = 0; j < adapter.getItems().size(); j++) {

                    FriendModel adapItem = adapter.getItem(j);

                    for (int i = 0; i < friendsArr.size(); i++) {

                        FriendModel selectedItem = friendsArr.get(i);

                        if (adapItem.getId().equals(selectedItem.getId())) {
                            adapItem.setSelected(true);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e(TAG, "onFailure: error while load friends = " + Log.getStackTraceString(t));
            }
        });
    }

    public void setOnSelectFriendsListener(OnSelectFriendsListener onSelectFriendsListener) {

        this.mListener = onSelectFriendsListener;
    }

    @Override
    public void selectedFriends(int selected) {

        binding.setShareText(getString(R.string.share_with, selected != 0 ? String.valueOf(selected) : ""));
    }

    public interface OnSelectFriendsListener {

        void friendsSelected(ArrayList<FriendModel> selectedItems);
    }
}
