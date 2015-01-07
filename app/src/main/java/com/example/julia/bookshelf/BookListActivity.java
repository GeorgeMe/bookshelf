package com.example.julia.bookshelf;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia on 29.12.2014.
 */
public class BookListActivity extends Activity {
    private RecyclerView rvBookList;
    private BookAdapter rvAdapter;
    private RecyclerView.LayoutManager rvManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        initView();
        // loadData();
        LoadDataTask loadDataTask = new LoadDataTask();
        loadDataTask.execute();

    }

    class LoadDataTask extends AsyncTask<Void, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(Void... params) {
            String json = loadJSONFromAsset("json/books.json");

            return JSONParser.parseBooks(json);
        }

        @Override
        protected void onPostExecute(List<Book> bookList) {
            updateView(bookList);
        }
    }

    private void updateView(List<Book> bookList) {
        rvAdapter.updateData(bookList);
        rvAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = loadJSONFromAsset("json/books.json");
                List<Book> bookList = JSONParser.parseBooks(json);
                updateViewWithThread(bookList);
            }
        }).start();
    }

    private void updateViewWithThread(final List<Book> bookList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvAdapter.updateData(bookList);
                rvAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        rvBookList = (RecyclerView) findViewById(R.id.rv_book_list);
        rvBookList.setHasFixedSize(true);
        rvManager = new GridLayoutManager(this, 2);
        rvBookList.setLayoutManager(rvManager);
        rvAdapter = new BookAdapter(getApplicationContext());
        rvAdapter.setOnItemCleckListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                startBookDetailsActivity(position);
            }
        });
        rvBookList.setAdapter(rvAdapter);
    }

    private void startBookDetailsActivity(int position) {
        Book book = rvAdapter.getBook(position);
        BookDetailsActivity.start(this, book);
    }


    private ArrayList<Book> generateTestData() {// TODO test data
        ArrayList<Book> books = new ArrayList<>();
        books.add(generateTestBook());
        books.add(generateTestBook());
        books.add(generateTestBook());
        books.add(generateTestBook());
        books.add(generateTestBook());
        books.add(generateTestBook());
        books.add(generateTestBook());
        books.add(generateTestBook());
        books.add(generateTestBook());
        return books;
    }

    private Book generateTestBook() { // TODO test data
        Book book = new Book();
        book.setCover("https://pp.vk.me/c540102/v540102340/ecb3/TSlDUYFp1Ss.jpg");
        book.setCover("http://a.wattpad.com/cover/10282042-256-k765626.jpg");
        book.setTitle("Heart of Three");
        book.setAuthor("Jack London");
        book.setGenre("Fiction");
        book.setAnnotation("Francis Morgan, a wealthy heir of industrialist and Wall Street maven Richard Henry Morgan, is a jaded young New Yorker. ");
        book.setUserRating(4);
        book.setGeneralRating(5);
        book.setPeople(10);
        book.setFavourite(true);
        return book;
    }

    public String loadJSONFromAsset(String path) {
        String json;
        try {
            InputStream is = getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
