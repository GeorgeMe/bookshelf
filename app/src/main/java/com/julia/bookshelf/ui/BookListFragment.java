package com.julia.bookshelf.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.http.InternetAccess;
import com.julia.bookshelf.model.tasks.LoadBooksTask;
import com.julia.bookshelf.ui.adapters.BookAdapter;

import java.util.List;

/**
 * Created by Julia on 26.01.2015.
 */
public class BookListFragment extends Fragment {
    public interface OnListItemClickedListener {
        public void onListItemClicked(Book book);
    }

    private BookAdapter rvAdapter;

    public static Fragment newInstance() {
        return new BookListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
        if (InternetAccess.isInternetConnection(getActivity().getApplicationContext())) {
            LoadBooksTask loadBooksTask = new LoadBooksTask() {
                @Override
                protected void onPostExecute(List<Book> books) {
                    updateView(books);
                }
            };
            loadBooksTask.execute();
        } else {
            InternetAccess.showNoInternetConnection(getActivity().getApplicationContext());
        }
    }

    private void updateView(List<Book> bookList) {
        rvAdapter.updateData(bookList);
        rvAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        RecyclerView rvBookList = (RecyclerView) view.findViewById(R.id.rv_book_list);
        rvBookList.setHasFixedSize(true);
        Context context = getActivity().getApplicationContext();
        RecyclerView.LayoutManager rvManager = new GridLayoutManager(context, 2);
        rvBookList.setLayoutManager(rvManager);
        rvAdapter = new BookAdapter(context);
        rvAdapter.setOnItemCleckListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                getListener().onListItemClicked(rvAdapter.getBook(position));
            }
        });
        rvBookList.setAdapter(rvAdapter);
    }

    private OnListItemClickedListener getListener() {
        return (OnListItemClickedListener) getActivity();
    }
}
