package com.example.julia.bookshelf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Julia on 18.11.2014.
 */
public class Book implements Parcelable {
    private String cover;
    private String title;
    private String author;
    private String genre;
    private String annotation;
    private int userRating;
    private int generalRating;
    private int people;
    private boolean isFavourite;

    public Book(Parcel parcel) {
        this.cover = parcel.readString();
        this.title = parcel.readString();
        this.author = parcel.readString();
        this.genre = parcel.readString();
        this.annotation = parcel.readString();
        this.userRating = parcel.readInt();
        this.generalRating = parcel.readInt();
        this.people = parcel.readInt();
        this.isFavourite = parcel.readByte() != 0;
    }

    public Book() {
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getAnnotation() {
        return annotation;
    }

    public int getUserRating() {
        return userRating;
    }

    public int getGeneralRating() {
        return generalRating;
    }

    public int getPeople() {
        return people;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public void setGeneralRating(int generalRating) {
        this.generalRating = generalRating;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cover);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(genre);
        dest.writeString(annotation);
        dest.writeInt(userRating);
        dest.writeInt(generalRating);
        dest.writeInt(people);
        dest.writeByte((byte) (isFavourite ? 1 : 0));
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
