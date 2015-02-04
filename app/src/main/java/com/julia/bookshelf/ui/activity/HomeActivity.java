package com.julia.bookshelf.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.ui.adapters.DrawerMenuItem;
import com.julia.bookshelf.ui.adapters.NavigationDrawerAdapter;
import com.julia.bookshelf.ui.fragments.AboutFragment;
import com.julia.bookshelf.ui.fragments.BookListFragment;
import com.julia.bookshelf.ui.fragments.FavouriteBooksFragment;

/**
 * Created by Julia on 26.01.2015.
 */
public class HomeActivity extends BaseActivity implements BookListFragment.OnListItemClickedListener,FavouriteBooksFragment.OnListItemClickedListener {
    public static final int EXPLORER = 0;
    public static final int FAVOURITE = 1;
    public static final int ABOUT = 2;
    public static final int LOG_OUT = 3;


    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence title;
    private CharSequence drawerTitle;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onListItemClicked(Book book) {
        BookDetailsActivity.start(this, book);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showFragment(BookListFragment.newInstance());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        title = drawerTitle = getTitle();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_opened, R.string.drawer_closed) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerList.setAdapter(new NavigationDrawerAdapter(this, getMenuItemsArray()));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    private DrawerMenuItem[] getMenuItemsArray() {
        return new DrawerMenuItem[]{new DrawerMenuItem("Explorer", R.drawable.ic_action_explorer),
                new DrawerMenuItem("Favourite", R.drawable.ic_action_favourite),
                new DrawerMenuItem("About", R.drawable.ic_action_about),
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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            showMenuItemContent(position);
        }
    }

    private void showMenuItemContent(int position) {
        drawerList.setItemChecked(position, true);
        //setTitle(actionsArray[position]);
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

//    @Override
//    public void setTitle(CharSequence title) {
//        this.title= title;
//        getActionBar().setTitle(title);
//    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
