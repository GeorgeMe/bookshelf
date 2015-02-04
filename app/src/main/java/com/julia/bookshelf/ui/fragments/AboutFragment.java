package com.julia.bookshelf.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.julia.bookshelf.R;

/**
 * Created by Julia on 30.01.2015.
 */
public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_us, container, false);
    }

    public static Fragment newInstance(){
        return new AboutFragment();
    }
}
