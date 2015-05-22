package com.julia.bookshelf.ui.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.julia.bookshelf.model.pref.PreferencesManager;

public class BaseFragment extends Fragment {
    private PreferencesManager preferencesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesManager = new PreferencesManager(getActivity().getApplicationContext());
    }

    @NonNull
    public PreferencesManager getPreferences() {
        return preferencesManager;
    }

    public Context getContext() {
        return getActivity().getApplicationContext();
    }
}
