package com.ashik619.prokriyademo.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ashik619.prokriyademo.R;
import com.ashik619.prokriyademo.custum_view.CustomTextView;

/**
 * Created by ashik619 on 08-06-2017.
 */
public class ChatViewHolder extends RecyclerView.ViewHolder {

    private final CustomTextView mTextField;

    public ChatViewHolder(View itemView) {
        super(itemView);
        mTextField = (CustomTextView) itemView.findViewById(R.id.messageText);
    }

    public void setText(String text) {
        mTextField.setText(text);
    }
}