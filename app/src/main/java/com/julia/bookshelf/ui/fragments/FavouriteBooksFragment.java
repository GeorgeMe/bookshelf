package com.julia.bookshelf.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.database.tasks.LoadBooksFromDatabaseTask;
import com.julia.bookshelf.model.http.InternetAccess;
import com.julia.bookshelf.model.http.URLCreator;
import com.julia.bookshelf.model.tasks.LoadBooksTask;
import com.julia.bookshelf.ui.adapters.BookAdapter;

import java.util.*;

public class FavouriteBooksFragment extends BaseFragment {

    private List<String> mFavouriteBookId;

    public static Fragment newInstance() {
        return new FavouriteBooksFragment();
    }

    public interface OnListItemClickedListener {
        void onListItemClicked(Book book);
    }

    private BookAdapter rvAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mFavouriteBookId = getPreferences().getFavouriteBooksId();
        initView(view);
        loadDataFromDatabase();
        if (InternetAccess.isInternetConnection(getActivity().getApplicationContext())) {
            loadFavouriteBooks(mFavouriteBookId);
        } else {
            InternetAccess.showNoInternetConnection(getActivity().getApplicationContext());
        }
    }

    private void loadDataFromDatabase() {
        LoadBooksFromDatabaseTask task = new LoadBooksFromDatabaseTask() {
            @Override
            protected void onPostExecute(List<Book> books) {
                List<Book> favoriteBooks = new ArrayList<>();
                for (Book book : books) {
                    if(isFavorite(book.getId())) {
                        favoriteBooks.add(book);
                    }
                }
                updateView(favoriteBooks);
            }
        };
        task.execute();
    }

    private boolean isFavorite(@NonNull String id) {
        for (String favId : mFavouriteBookId) {
            if (favId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void loadFavouriteBooks(@NonNull List<String> favouriteBookId) {
        if (!favouriteBookId.isEmpty()) {
            LoadBooksTask loadBooksTask = new LoadBooksTask(URLCreator.getFavouriteBooks(favouriteBookId)) {
                @Override
                protected void onPostExecute(List<Book> books) {
                    updateView(books);
                }
            };
            loadBooksTask.execute();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No favourite books", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateView(@NonNull List<Book> bookList) {
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book book1, Book book2) {
                return book1.getTitle().compareTo(book2.getTitle());
            }
        });
        rvAdapter.updateData(bookList);
        rvAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        RecyclerView rvBookList = (RecyclerView) view.findViewById(R.id.rv_book_list);
        rvBookList.setHasFixedSize(true);
        Context context = getActivity().getApplicationContext();
        RecyclerView.LayoutManager rvManager = new GridLayoutManager(context, 3);
        rvBookList.setLayoutManager(rvManager);
        rvAdapter = new BookAdapter(context);
        rvAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
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
