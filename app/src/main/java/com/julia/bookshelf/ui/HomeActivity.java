package com.julia.bookshelf.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.ui.adapters.BookAdapter;

/**
 * Created by Julia on 26.01.2015.
 */
public class HomeActivity extends Activity implements BookListFragment.OnListItemClickedListener {
    @Override
    public void onListItemClicked(int position, BookAdapter adapter) {
        Book book = adapter.getBook(position);
        BookDetailsActivity.start(this, book);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFragment(BookListFragment.newInstance());

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

}
