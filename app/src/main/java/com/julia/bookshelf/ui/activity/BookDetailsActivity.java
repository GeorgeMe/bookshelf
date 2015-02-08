package com.julia.bookshelf.ui.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.data.FavouriteBook;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.tasks.AddFavouriteBookTask;
import com.julia.bookshelf.model.tasks.DeleteFavouriteBookTask;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Julia on 10.11.2014.
 */
public class BookDetailsActivity extends BaseActivity {
    private static final String EXTRAS_BOOK = "EXTRAS_BOOK";
    private boolean isFavorite;
    private boolean hasChange;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Book details");
        initView();
        initFavouriteIcon();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_favorite:
                hasChange = !hasChange;
                isFavorite = !isFavorite;
                invalidateOptionsMenu();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        if (hasChange) {
            Book book = getIntent().getParcelableExtra(EXTRAS_BOOK);
            if (isFavorite) {
                getPreferences().addFavouriteBook(book);
                sendFavouriteBook();
            } else {
                //todo: deleting favourite book
                // from server
                DeleteFavouriteBookTask deleteFavouriteBookTask = new DeleteFavouriteBookTask(getPreferences().getFavouriteBook(book));
                deleteFavouriteBookTask.execute();
                // from prefs
                getPreferences().deleteFavouriteBook(book);


            }
        }
        super.onDestroy();
    }

    private void sendFavouriteBook() {
        Book book = getIntent().getParcelableExtra(EXTRAS_BOOK);
        User user = getPreferences().loadUser();
        AddFavouriteBookTask addFavouriteBookTask = new AddFavouriteBookTask(user, book) {
            @Override
            protected void onPostExecute(FavouriteBook favouriteBook) {
                super.onPostExecute(favouriteBook);
                if (favouriteBook != null) {
                    getPreferences().updateFavouriteBook(favouriteBook);
                }
            }
        };
        addFavouriteBookTask.execute();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isFavorite) {
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_action_favourite);
        } else {
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_action_not_favourite);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void initFavouriteIcon() {
        List<FavouriteBook> favouriteBookList = getPreferences().loadFavouriteBooks();
        Book book = getIntent().getParcelableExtra(EXTRAS_BOOK);
        if (favouriteBookList != null) {
            String id;
            for (int i = 0; i < favouriteBookList.size(); i++) {
                id = favouriteBookList.get(i).getBookId();
                if (book.getId().equals(id)) {
                    isFavorite = true;
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.book_details, menu);
        return super.onCreateOptionsMenu(menu);
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
