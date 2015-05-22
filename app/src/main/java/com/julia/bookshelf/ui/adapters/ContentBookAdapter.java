package com.julia.bookshelf.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContentBookAdapter extends RecyclerView.Adapter<ContentBookAdapter.ViewHolder> {
    private List<Book> books;
    private OnItemClickListener listener;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cover;
        TextView title;
        TextView annotation;

        public ViewHolder(LinearLayout llBook) {
            super(llBook);
            cover = (ImageView) llBook.findViewById(R.id.img_cover);
            title = (TextView) llBook.findViewById(R.id.txt_title);
            annotation = (TextView) llBook.findViewById(R.id.txt_annotation);
            llBook.setOnClickListener(this);
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

    public void setOnItemClickListener(final OnItemClickListener listener) {
        this.listener = listener;
    }

    public ContentBookAdapter(Context context) {
        books = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_booklist_item, viewGroup, false);
        return new ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Book book = books.get(i);
        Picasso.with(context).load(book.getCover()).into(viewHolder.cover);
        viewHolder.title.setText(book.getTitle());
        viewHolder.annotation.setText(book.getAnnotation());
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
