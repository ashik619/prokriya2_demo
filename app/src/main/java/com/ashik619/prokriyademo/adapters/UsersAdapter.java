package com.ashik619.prokriyademo.adapters;

import android.util.Log;
import android.view.View;

import com.ashik619.prokriyademo.MainActivity;
import com.ashik619.prokriyademo.Models.User;
import com.ashik619.prokriyademo.R;
import com.ashik619.prokriyademo.custum_view.CustomTextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by ashik619 on 07-06-2017.
 */
public class UsersAdapter extends FirebaseListAdapter<User> {
     MainActivity activity;

    public UsersAdapter(MainActivity activity, Class<User> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, User model, int position) {
        CustomTextView nameText = (CustomTextView) v.findViewById(R.id.nameText);
        //Log.e("user",model.getName());
        nameText.setText(model.getName());

    }
}
