package com.ashik619.prokriyademo;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.apradanas.simplelinkabletext.Link;
import com.apradanas.simplelinkabletext.LinkableEditText;
import com.ashik619.prokriyademo.Models.Message;
import com.ashik619.prokriyademo.Models.User;
import com.ashik619.prokriyademo.adapters.MessageAdapter;
import com.ashik619.prokriyademo.adapters.UsersAdapter;
import com.ashik619.prokriyademo.custum_view.CustomTextView;
import com.ashik619.prokriyademo.holder.ChatViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    Link linkUsername;
    @BindView(R.id.posterView)
    ImageView posterView;
    @BindView(R.id.movieName)
    CustomTextView movieName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.msgEditText)
    LinkableEditText msgEditText;
    @BindView(R.id.sendButton)
    ImageButton sendButton;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @BindView(R.id.userListview)
    ListView userListview;
    UsersAdapter usersAdapter;
    MessageAdapter messageAdapter;
    DatabaseReference mDatabase;
    DatabaseReference userReference;
    DatabaseReference messageRef;
    DatabaseReference userMessageRef;
    private static final String TAG = "main";
    @BindView(R.id.listViewChat)
    RecyclerView listViewChat;
    String username;
    ArrayList<User> userArrayList;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initiate();
    }

    void initiate() {
        userArrayList = new ArrayList<>();
        username = ProkriyaApplication.getLocalPrefInstance().getName();
        movieName.setText(username);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userReference = mDatabase.child("Users");
        messageRef = mDatabase.child("Messages");
        userMessageRef = messageRef.child(username);
        linkUsername = new Link(Pattern.compile("(@\\w+)"))
                .setUnderlined(false)
                .setTextColor(Color.parseColor("#D00000"))
                .setTextStyle(Link.TextStyle.BOLD)
                .setClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String text) {
                        // do something
                    }
                });
        msgEditText.addLink(linkUsername);
        linearLayoutManager = new LinearLayoutManager(this);
        usersAdapter = new UsersAdapter(MainActivity.this, User.class, R.layout.users_list_item, userReference);
        listViewChat.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(Message.class,R.layout.message_list_item, ChatViewHolder.class,userMessageRef,MainActivity.this);
        listViewChat.setAdapter(messageAdapter);
        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = messageAdapter.getItemCount();
                int lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    listViewChat.scrollToPosition(positionStart);
                }
            }
        });



        msgEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                //System.out.println("before" + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
               // Log.e(TAG, s.toString() + count);
                String str = msgEditText.getText().toString();
                if (str.length() > 0) {
                    if (Character.toString(str.charAt(str.length() - 1)).equals("@")) {
                      //  Log.e(TAG, "showing");
                        showUserList();
                    } else hideUserlist();
                }

            }
        });
        userListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = (User) userListview.getItemAtPosition(i);
                msgEditText.append(selectedUser.getName());
                userArrayList.add(selectedUser);
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = msgEditText.getText().toString();
                if (!msg.equals("")){
                    if(userArrayList.size()>0) {
                        pushMessage(msg);
                        msgEditText.setText("");
                    }else Toast.makeText(MainActivity.this, "Add at least one Receiver",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    void showUserList() {
        userListview.setAdapter(usersAdapter);
        userListview.setVisibility(View.VISIBLE);
        setListViewHeightBasedOnChildren(userListview);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(msgEditText, InputMethodManager.SHOW_IMPLICIT);

    }

    void hideUserlist() {
        userListview.setVisibility(View.INVISIBLE);
    }

    void pushMessage(String msg) {
        Message message = new Message();
        message.setText(msg);
        messageRef.child(username).push().setValue(message);
        for (User user : userArrayList){
            messageRef.child(user.getName()).push().setValue(message);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {

            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        params.height += listView.getDividerHeight() * listAdapter.getCount() + 10;

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
