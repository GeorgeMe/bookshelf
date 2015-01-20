package com.julia.bookshelf.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia on 30.12.2014.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> books;
    private OnItemClickListener listener;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cover;
        TextView title;
        TextView author;
        ImageView isFavourite;


        public ViewHolder(CardView cvBook) {
            super(cvBook);
            cover = (ImageView) cvBook.findViewById(R.id.img_cover);
            title = (TextView) cvBook.findViewById(R.id.txt_title);
            author = (TextView) cvBook.findViewById(R.id.txt_author);
            isFavourite = (ImageView) cvBook.findViewById(R.id.img_favourite);
            cvBook.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemCleckListener(final OnItemClickListener listener) {
        this.listener = listener;
    }

    public BookAdapter(Context context) {
        books = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.book_list_item, viewGroup, false);
        return new ViewHolder((CardView) v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Book book = books.get(i);
        viewHolder.title.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthor());
        if (book.isFavourite()) {
            viewHolder.isFavourite.setImageResource(R.drawable.ic_action_important);
        } else {
            viewHolder.isFavourite.setImageResource(R.drawable.ic_action_not_important);
        }
        Picasso.with(context).load(book.getCover()).into(viewHolder.cover);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public Book getBook(int position) {
        return books.get(position);
    }

    public void updateData(List<Book> bookList) {
        if (bookList != null) {
            this.books = bookList;
        }
    }
}
