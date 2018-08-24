package com.nestef.room.messaging;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.FixedPreloadSizeProvider;
import com.nestef.room.R;
import com.nestef.room.data.MessageManager;
import com.nestef.room.data.PrefManager;
import com.nestef.room.model.Event;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;
import com.nestef.room.preferences.ThemeChanger;
import com.nestef.room.util.Constants;
import com.nestef.room.widget.RoomWidgetProvider;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Noah Steffes on 4/13/18.
 */
public class MessagingFragment extends Fragment implements MessagingContract.MessagingView, MessageAdapter.MentionClickHandler {

    private static final String TAG = "MessagingFragment";
    private static final String ROOM_KEY = "room";
    private static final String RECYCLER_STATE = "list_state";

    Unbinder mUnbinder;
    MessagingPresenter mPresenter;
    MessageAdapter mMessageAdapter;
    LinearLayoutManager mLayoutManager;
    Room mRoom;

    Parcelable listSaveState;
    boolean loading;
    boolean isInputable = false;

    @BindView(R.id.message_list)
    RecyclerView mMessageList;
    @BindView(R.id.message_input)
    EditText mInputField;
    @BindView(R.id.message_send_iv)
    ImageView mSend;
    @BindView(R.id.message_layout_ll)
    LinearLayout mInputLayout;
    @BindView(R.id.room_join_layout)
    LinearLayout mJoinLayout;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @BindView(R.id.message_loading_pb)
    ProgressBar mProgress;
    @Nullable
    @BindView(R.id.message_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.join_button)
    Button mJoinButton;
    @BindView(R.id.markdown_info_iv)
    ImageView mMdButton;
    @BindView(R.id.message_empty_text)
    TextView mEmptyText;

    public MessagingFragment() {
    }

    public static MessagingFragment newInstance(Room room) {
        Bundle args = new Bundle();
        args.putParcelable(ROOM_KEY, Parcels.wrap(room));
        MessagingFragment fragment = new MessagingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MessagingPresenter(MessageManager.getInstance(PrefManager.getInstance(getContext().getSharedPreferences(Constants.AUTH_SHARED_PREF, Context.MODE_PRIVATE))));
        if (getArguments() != null) {
            mRoom = Parcels.unwrap(getArguments().getParcelable(ROOM_KEY));
            mPresenter.setRoomId(mRoom.id);
            mPresenter.setUserId(PrefManager.getInstance(getContext().getSharedPreferences(Constants.AUTH_SHARED_PREF, Context.MODE_PRIVATE)).getUserId());
        }
        if (savedInstanceState != null) {
            listSaveState = savedInstanceState.getParcelable(RECYCLER_STATE);
        }
    }

    @Override
    public void updateRoom(Room room) {
        mRoom = room;
        mPresenter.setRoomId(room.id);
        updateMenu();
    }

    @Override
    public void networkError() {
        Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean checkForConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&

                activeNetwork.isConnectedOrConnecting();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messaging_fragment, container, false);
        setHasOptionsMenu(true);
        mUnbinder = ButterKnife.bind(this, view);
        if (!isTablet()) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (mRoom != null) {
            if (isTablet()) {
                mToolbar.setTitle(mRoom.name);
            } else {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mRoom.name);
            }
            mPresenter.setView(this);
            mPresenter.fetchMessages();
            mPresenter.fetchUnreadIds();
            mPresenter.checkRoomMembership(mRoom);
        }
        mMdButton.setOnClickListener(v -> {
            MarkdownBottomSheetFragment bottomSheetFragment = new MarkdownBottomSheetFragment();
            bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, ThemeChanger.getThemeIdForDialog(getContext()));
            bottomSheetFragment.show(getFragmentManager(), "");
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMessageList.getLayoutManager() != null) {
            outState.putParcelable(RECYCLER_STATE, mMessageList.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.room_fragment_items, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.leave_room_item);
        item.setVisible(mRoom.roomMember);
        super.onPrepareOptionsMenu(menu);
    }

    private void inflateMenu() {
        mToolbar.inflateMenu(R.menu.room_fragment_items);
        MenuItem item = mToolbar.getMenu().findItem(R.id.leave_room_item);
        item.setVisible(mRoom.roomMember);
        mToolbar.setOnMenuItemClickListener(this::handleMenuClick);
    }

    private void updateMenu() {
        if (!isTablet()) {
            getActivity().invalidateOptionsMenu();
        } else {
            mToolbar.getMenu().clear();
            inflateMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleMenuClick(item);
    }

    private boolean handleMenuClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.leave_room_item:
                mPresenter.leaveRoom();
                updateWidget();
                mPresenter.checkRoomMembership(mRoom);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void showMessages(List<Message> messages) {
        mEmptyText.setVisibility(View.INVISIBLE);
        mLayoutManager = new LinearLayoutManager(getContext());
        mMessageList.setLayoutManager(mLayoutManager);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mMessageAdapter = new MessageAdapter(messages, getContext(), this);
        mMessageList.setAdapter(mMessageAdapter);
        if (listSaveState != null) {
            //restore list state
            //TODO if the saved state is in older data, state will not restore correctly
            mLayoutManager.onRestoreInstanceState(listSaveState);
        } else {
            mMessageList.scrollToPosition(mMessageAdapter.getItemCount() - 1);
        }
        FixedPreloadSizeProvider<Message> sizeProvider = new FixedPreloadSizeProvider<>(40, 40);
        RecyclerViewPreloader<Message> preloader = new RecyclerViewPreloader<>(this, mMessageAdapter, sizeProvider, 5);
        mMessageList.addOnScrollListener(preloader);

        //if scrolled to top of page, load older messages
        mMessageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItem == 5 && !loading) {
                    mPresenter.fetchOlderMessages(mMessageAdapter.getMessageIdByPosition(0));
                    loading = true;
                }
            }
        });
    }

    @Override
    public void addNewMessages(List<Message> newMessages) {
        mMessageAdapter.addItems(newMessages);
    }

    @Override
    public void addMessage(Message message) {
        if (mMessageAdapter != null) {
            mMessageAdapter.addItem(message);
            if (mLayoutManager.findLastVisibleItemPosition() == mMessageAdapter.getItemCount() - 2) {
                mMessageList.scrollToPosition(mMessageAdapter.getItemCount() - 1);
            }
        } else {
            List<Message> newList = new ArrayList<>();
            newList.add(message);
            showMessages(newList);
        }
    }

    @Override
    public void addEvent(Event event) {

    }

    @Override
    public void showOlderMessages(List<Message> oldMessages) {
        mMessageAdapter.addItems(oldMessages);
        loading = false;
    }

    @Override
    public void showJoinUi() {
        mInputLayout.setVisibility(View.GONE);
        mJoinLayout.setVisibility(View.VISIBLE);
        mJoinButton.setOnClickListener(v -> {
            mPresenter.joinRoom();
            updateWidget();
            mPresenter.checkRoomMembership(mRoom);
        });
        isInputable = false;
    }

    private void updateWidget() {
        ComponentName thisWidget = new ComponentName(getContext(), RoomWidgetProvider.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] ids = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widget_list);
    }

    @Override
    public void showInputUi() {
        mJoinLayout.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mSend.setOnClickListener(v -> {
            if (mInputField != null) {
                //ensure text is not empty
                if (!mInputField.getText().toString().equals("")) {
                    mPresenter.sendMessage(mInputField.getText().toString());
                    mInputField.setText("");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mInputField.getWindowToken(), 0);
                }
            }
        });
        isInputable = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsetView();
    }


    @Override
    public void showLoadingIndicator() {
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mEmptyText.setVisibility(View.VISIBLE);
    }

    private boolean isTablet() {
        return mIsTablet == 1;
    }

    @Override
    public void onMentionClick(String mention) {
        if (isInputable) {
            mInputField.setText(mention);
        }
    }
}
