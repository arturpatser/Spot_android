package com.gridyn.potspot.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.FriendsAdapter;
import com.gridyn.potspot.databinding.FragmentSelectFriendsBinding;
import com.gridyn.potspot.interfaces.SelectFriendsInterface;
import com.gridyn.potspot.model.FriendModel;
import com.gridyn.potspot.query.FriendByMailQuery;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.response.friendResponse.FriendsResponse;
import com.gridyn.potspot.response.friendResponse.Message;
import com.gridyn.potspot.utils.FragmentUtils;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.gridyn.potspot.Constant.CONNECTION_ERROR;

public class SelectFriendsFragment extends Fragment implements SelectFriendsInterface {

    public static final String TAG = SelectFriendsFragment.class.getName();
    private static String ARG_PARTY_SIZE = "partysize";
    private static String ARG_FRIENDS_SELECTED = "friends";
    private static String ARG_REQUEST_ID = "reqId";
    private OnSelectFriendsListener mListener;
    private int partySize;

    @BindView(R.id.friends_list_recycler)
    RecyclerView friendsRecycler;

    @BindView(R.id.friend_email)
    EditText friendEmail;

    FriendsAdapter adapter;

    FragmentSelectFriendsBinding binding;
    private ArrayList<FriendModel> friendsArr;
    private String requestId;

    public SelectFriendsFragment() {
        // Required empty public constructor
    }

    public static SelectFriendsFragment newInstance(int partySize, ArrayList<FriendModel> arrayList,
                                                    String requestId) {
        SelectFriendsFragment fragment = new SelectFriendsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARTY_SIZE, partySize);
        args.putParcelableArrayList(ARG_FRIENDS_SELECTED, arrayList);
        args.putString(ARG_REQUEST_ID, requestId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            partySize = getArguments().getInt(ARG_PARTY_SIZE);
            friendsArr = getArguments().getParcelableArrayList(ARG_FRIENDS_SELECTED);
            requestId = getArguments().getString(ARG_REQUEST_ID);
        }

        adapter = new FriendsAdapter(getContext(), this, partySize, requestId);

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

                if (friendEmail.getText().length() > 0) {

                    FriendByMailQuery friendByMailQuery = new FriendByMailQuery();

                    friendByMailQuery.setEmail(friendEmail.getText().toString().trim());
                    friendByMailQuery.setToken(Person.getToken());

                    Call<SuccessResponse> call = ServerApiUtil.initUser().addFriendByMail(requestId,
                            friendByMailQuery);

                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                            SuccessResponse successResponse = response.body();

                            if (successResponse == null) {

                            } else {

                                if (successResponse.isSuccess()) {

                                    Snackbar.make(getActivity().findViewById(android.R.id.content),
                                            R.string.successfull_add_friend, Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Log.e(TAG, "onFailure: error while add friend by mail = " + Log.getStackTraceString(t));

                            Snackbar.make(getActivity().findViewById(android.R.id.content), CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
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

                FriendsResponse friendsResponse = response.body();

                Log.d(TAG, "onResponse: " + friendsResponse);

                if (friendsResponse.getMessage().size() > 0) {

                    ArrayList<FriendModel> friendModels = new ArrayList<>();

                    for (Message message : friendsResponse.getMessage().get(0)) {

                        FriendModel friendModel = message.getData();

                        friendModel.setId(message.getId().get$id());

                        friendModels.add(friendModel);
                    }

                    adapter.addAll(friendModels);

                    //checking selected items
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
