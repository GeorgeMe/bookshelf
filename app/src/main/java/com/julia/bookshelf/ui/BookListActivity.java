package com.julia.bookshelf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.tasks.LoadBooksTask;
import com.julia.bookshelf.ui.adapters.BookAdapter;

import java.util.List;

/**
 * Created by Julia on 29.12.2014.
 */
public class BookListActivity extends Activity {
    private BookAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);

        initView();
        LoadBooksTask loadBooksTask = new LoadBooksTask(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Book> books) {
                updateView(books);

            }
        };
        loadBooksTask.execute();
    }

    private void updateView(List<Book> bookList) {
        rvAdapter.updateData(bookList);
        rvAdapter.notifyDataSetChanged();
    }

    private void initView() {
        RecyclerView rvBookList = (RecyclerView) findViewById(R.id.rv_book_list);
        rvBookList.setHasFixedSize(true);
        RecyclerView.LayoutManager rvManager = new GridLayoutManager(this, 2);
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


}
