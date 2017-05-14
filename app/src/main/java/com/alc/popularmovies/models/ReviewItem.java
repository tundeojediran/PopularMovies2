package com.alc.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dannytee on 14/05/2017.
 */
public class ReviewItem implements Parcelable {

    private String content;

    private String id;

    private String author;

    private String url;

    public ReviewItem(String content, String id, String author, String url) {
        this.content = content;
        this.id = id;
        this.author = author;
        this.url = url;
    }

    protected ReviewItem(Parcel in) {
        content = in.readString();
        id = in.readString();
        author = in.readString();
        url = in.readString();
    }

    public static final Creator<ReviewItem> CREATOR = new Creator<ReviewItem>() {
        @Override
        public ReviewItem createFromParcel(Parcel in) {
            return new ReviewItem(in);
        }

        @Override
        public ReviewItem[] newArray(int size) {
            return new ReviewItem[size];
        }
    };

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(url);
    }
}
