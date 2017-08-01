package com.companyfiller.android.spotnews;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Chase on 7/27/2017.
 *
 * This will be a singleton class that will hold the current set of news articles being considered.
 *
 * Purpose is to have it so that there will be only one set of articles out there at once
 * and so that it can be accessible from anywhere
 *
 * Just added the bitmap hash map so that the images can simply be held after download
 * within the scope of the pager activity
 *
 * This way, they can be simply pulled from here instead of being re-downloaded
 */

public class ArticlesHolder {

    private static final String TAG = "ArticlesHolder";

    private static ArticlesHolder articlesHolder;

    private List<NewsArticle> articlesList;
    private HashMap<String, Bitmap> bitmapHashMap;

    public static ArticlesHolder getInstance() {
        if (articlesHolder == null) {
            articlesHolder = new ArticlesHolder();
        }

        return articlesHolder;
    }

    public void setArticlesList(List<NewsArticle> list) {
        articlesList = list;
    }

    public List<NewsArticle> getArticlesList() {
        return articlesList;
    }

    @Override
    public String toString() {
        String outputString = "";

        for (NewsArticle na : articlesList) {
            outputString += na.toString() + "\n";
        }

        return outputString;
    }

    public NewsArticle getArticleFromTitle(String title) {

        if (articlesList.isEmpty()) {
            return null;
        }

        for (NewsArticle na : articlesList) {
            if (na.getTitle().equals(title)) {
                return na;
            }
        }

        return articlesList.get(0);

    }

    public Bitmap getBitmapFromtitle(String title) {

        if (bitmapHashMap == null) {
            return null;
        }

        if (bitmapHashMap.get(title) == null) {
            return null;
        }

        return bitmapHashMap.get(title);
    }

    public void addBitmap(String title, Bitmap bitmap) {
        if (bitmapHashMap == null) {
            bitmapHashMap = new HashMap<>();
        }

        bitmapHashMap.put(title, bitmap);
    }

    public void clearBitmaps() {
        bitmapHashMap = null;
        Log.i(TAG, "The bitmap map was cleared");
    }
}






























