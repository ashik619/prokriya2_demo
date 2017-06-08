package com.ashik619.prokriyademo;

import android.app.Application;

import com.ashik619.prokriyademo.helper.PrefHandler;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ashik619 on 08-06-2017.
 */
public class ProkriyaApplication extends Application {
    public static PrefHandler localStorageHandler;
    public static PrefHandler getLocalPrefInstance() {
        return localStorageHandler;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        if (localStorageHandler == null) {
            localStorageHandler = new PrefHandler(getApplicationContext());
        }
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
