package com.julia.bookshelf.model.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String id;
    private String cover;
    private String title;
    private String author;
    private String genre;
    private String annotation;
    private float userRating;
    private float generalRating;
    private int people;

    public Book(Parcel parcel) {
        this.id=parcel.readString();
        this.cover = parcel.readString();
        this.title = parcel.readString();
        this.author = parcel.readString();
        this.genre = parcel.readString();
        this.annotation = parcel.readString();
        this.userRating = parcel.readFloat();
        this.generalRating = parcel.readFloat();
        this.people = parcel.readInt();
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public float getUserRating() {
        return userRating;
    }

    public float getGeneralRating() {
        return generalRating;
    }

    public int getPeople() {
        return people;
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

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public void setGeneralRating(float generalRating) {
        this.generalRating = generalRating;
    }

    public void setPeople(int people) {
        this.people = people;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cover);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(genre);
        dest.writeString(annotation);
        dest.writeFloat(userRating);
        dest.writeFloat(generalRating);
        dest.writeInt(people);
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
