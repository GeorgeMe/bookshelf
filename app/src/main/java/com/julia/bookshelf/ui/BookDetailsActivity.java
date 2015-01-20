package com.julia.bookshelf.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.squareup.picasso.Picasso;


/**
 * Created by Julia on 10.11.2014.
 */
public class BookDetailsActivity extends Activity {
    private static final String EXTRAS_BOOK = "EXTRAS_BOOK";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);

        initView();
    }

    private void initView() {
        Book book = getIntent().getParcelableExtra(EXTRAS_BOOK);

        ImageView imgCover = (ImageView) findViewById(R.id.img_cover);
        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        TextView txtAuthor = (TextView) findViewById(R.id.txt_author);
        TextView txtGeneralRating = (TextView) findViewById(R.id.txt_general_rating);
        RatingBar rbRating = (RatingBar) findViewById(R.id.rb_rating);
        TextView txtGenre = (TextView) findViewById(R.id.txt_genre);
        TextView txtAnnotation = (TextView) findViewById(R.id.txt_annotation);

        Picasso.with(this).load(book.getCover()).into(imgCover);
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        txtGeneralRating.setText(String.format(getString(R.string.Rating_by), book.getGeneralRating(), book.getPeople()));
        rbRating.setRating(book.getUserRating());
        String htmlText = String.format(getString(R.string.Genre), book.getGenre());
        txtGenre.setText(Html.fromHtml(htmlText));
        txtAnnotation.setText(book.getAnnotation());
    }

    public static void start(Context context, Book book) {
        Intent intent = new Intent(context, BookDetailsActivity.class);
        intent.putExtra(EXTRAS_BOOK, book);
        context.startActivity(intent);
    }

}
