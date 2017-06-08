package com.ashik619.prokriyademo.adapters;

import android.content.Context;
import android.view.View;

import com.ashik619.prokriyademo.MainActivity;
import com.ashik619.prokriyademo.Models.Message;
import com.ashik619.prokriyademo.Models.User;
import com.ashik619.prokriyademo.R;
import com.ashik619.prokriyademo.custum_view.CustomTextView;
import com.ashik619.prokriyademo.holder.ChatViewHolder;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by ashik619 on 07-06-2017.
 */
public class MessageAdapter extends FirebaseRecyclerAdapter<Message, ChatViewHolder> {
    private static final String TAG = MessageAdapter.class.getSimpleName();
    private Context context;
    public MessageAdapter(Class<Message> modelClass, int modelLayout, Class<ChatViewHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }
    @Override
    protected void populateViewHolder(ChatViewHolder viewHolder, Message model, int position) {
        viewHolder.setText(model.getText());
    }
}
