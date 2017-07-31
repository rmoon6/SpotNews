package com.companyfiller.android.spotnews;

import android.net.Uri;

/**
 * Created by Chase on 7/27/2017.
 *
 * Class to hold the information for each particular news article
 */

public class NewsArticle {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Author: " + author + "\t");
        sb.append("Title: " + title + "\t");
        sb.append("Description: " + description);

        return sb.toString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Uri getArticleUri() {
        return Uri.parse(getUrl());
    }

    public Uri getImageUri() {
        return Uri.parse(getUrlToImage());
    }
}
























