package com.julia.bookshelf.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;

/**
 * Created by Julia on 26.01.2015.
 */
public class HomeActivity extends Activity implements BookListFragment.OnListItemClickedListener {
    private String[] actionsArray;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence title;
    private CharSequence drawerTitle;

    @Override
    public void onListItemClicked(Book book) {
        BookDetailsActivity.start(this, book);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showFragment(BookListFragment.newInstance());

        actionsArray = getResources().getStringArray(R.array.actions);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        title = drawerTitle = getTitle();

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_launcher, R.string.drawer_opened, R.string.drawer_closed) {
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
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_item, actionsArray));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout.setDrawerListener(drawerToggle);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        drawerList.setItemChecked(position, true);
        setTitle(actionsArray[position]);
        drawerLayout.closeDrawer(drawerList);
        Log.i("BOOKSHELF", "!!!!!!!!!!!!!" + position);

        // if(position==0){showFragment(InfoFragment.newInstance());}
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
        fragmentTransaction.commit();
    }

}
