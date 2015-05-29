package com.julia.bookshelf.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.data.FavouriteBook;
import com.julia.bookshelf.model.http.InternetAccess;
import com.julia.bookshelf.model.tasks.LoadFavouriteBooksTask;
import com.julia.bookshelf.ui.adapters.DrawerMenuItem;
import com.julia.bookshelf.ui.adapters.NavigationDrawerAdapter;
import com.julia.bookshelf.ui.fragments.AboutFragment;
import com.julia.bookshelf.ui.fragments.BookListFragment;
import com.julia.bookshelf.ui.fragments.FavouriteBooksFragment;

import java.util.List;

public class HomeActivity extends BaseActivity implements BookListFragment.OnListItemClickedListener, FavouriteBooksFragment.OnListItemClickedListener {
    public static final int EXPLORER = 0;
    public static final int FAVOURITE = 1;
    public static final int ABOUT = 2;
    public static final int LOG_OUT = 3;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private android.support.v7.app.ActionBarDrawerToggle drawerToggle;

    @Override
    public void onListItemClicked(Book book) {
        BookDetailsActivity.start(this, book);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showFragment(BookListFragment.newInstance());
        if (InternetAccess.isInternetConnection(getApplicationContext())) {
            LoadFavouriteBooksTask loadFavouriteBooksTask = new LoadFavouriteBooksTask(getPreferences().loadUser()) {
                @Override
                protected void onPostExecute(List<FavouriteBook> favouriteBooks) {
                    getPreferences().clearFavouriteBooks();
                    getPreferences().saveFavouriteBooks(favouriteBooks);
                }
            };
            loadFavouriteBooksTask.execute();
        } else {
            InternetAccess.showNoInternetConnection(getApplicationContext());
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new NavigationDrawerAdapter(this, getMenuItemsArray()));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed);
        drawerLayout.setDrawerListener(drawerToggle);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        drawerToggle.syncState();
    }

    private DrawerMenuItem[] getMenuItemsArray() {
        return new DrawerMenuItem[]{new DrawerMenuItem("Explorer", R.drawable.ic_drawer_books),
                new DrawerMenuItem("Favourite", R.drawable.ic_drawer_star),
                new DrawerMenuItem("About", R.drawable.ic_drawer_about),
                new DrawerMenuItem("Log out", R.drawable.ic_action_logout)};
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            showMenuItemContent(position);
        }
    }

    private void showMenuItemContent(int position) {
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
        switch (position) {
            case EXPLORER:
                showFragment(BookListFragment.newInstance());
                break;
            case FAVOURITE:
                showFragment(FavouriteBooksFragment.newInstance());
                break;
            case ABOUT:
                showFragment(AboutFragment.newInstance());
                break;
            case LOG_OUT:
                getPreferences().clearUser();
                startLoginActivity();
                finish();
                break;
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
