package com.companyfiller.android.spotnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chase on 7/26/2017.
 *
 * This is a class that is used for pulling data from a url connection
 *
 * I think that I will use this class to download the article images as well
 */

public class NewsFetcher {

    private static final String TAG = "NewsFetcher";
    private static final String API_KEY = "ce2a5ecf53384b83848060a4f84c9286";
    private static final String SOURCES_URL_BASE = "https://newsapi.org/v1/sources?language=en";
    private static final String ARTICLES_URL_BASE = "https://newsapi.org/v1/articles";

    public byte[] getUrlBytes(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        "for url: " +
                        urlString);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private String getJsonString(String url) throws IOException {
        return new String(getUrlBytes(url));
    }

    //eventually, I will try to modify this so that it can
    //handle parameters
    public List<NewsSource> getNewsSources() {

        List<NewsSource> sourcesList = new ArrayList<>();

        try {
            String url = Uri.parse(SOURCES_URL_BASE).toString();

            String jsonString = getJsonString(url);
            Log.i(TAG, "Here is the json received: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);

            populateNewsSourcesList(sourcesList, jsonBody);

        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse the json", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to access sources API", ioe);
        }

        return sourcesList;
    }

    private void populateNewsSourcesList(List<NewsSource> list, JSONObject jsonBody)
        throws JSONException {

        if (!jsonBody.getString("status").equalsIgnoreCase("ok")) {
            Log.i(TAG, "There is something wrong with the JSON body");
            return;
        }

        JSONArray sourcesJsonArray = jsonBody.getJSONArray("sources");
        JSONObject sourceJsonObject = null;
        NewsSource currentNewsSource = null;

        for (int i = 0; i < sourcesJsonArray.length(); i++) {
            sourceJsonObject = sourcesJsonArray.getJSONObject(i);

            currentNewsSource = new NewsSource();
            currentNewsSource.setId(sourceJsonObject.getString("id"));
            currentNewsSource.setDescription(sourceJsonObject.getString("description"));
            currentNewsSource.setLanguage(sourceJsonObject.getString("language"));
            currentNewsSource.setName(sourceJsonObject.getString("name"));
            currentNewsSource.setUrl(sourceJsonObject.getString("url"));

            list.add(currentNewsSource);
        }

    }

    public List<NewsArticle> getNewsArticles(String sourceId) {

        List<NewsArticle> newsArticleList = new ArrayList<>();

        String url = Uri.parse(ARTICLES_URL_BASE)
                .buildUpon()
                .appendQueryParameter("apiKey", API_KEY)
                .appendQueryParameter("source", sourceId)
                .build()
                .toString();

        try {
            String jsonString = getJsonString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            populateNewsArticlesList(newsArticleList, jsonBody);
            Log.i(TAG, "Here is the json string from the articles " + jsonString);
        } catch (IOException ioe) {
            Log.i(TAG, "There was an issue getting the json from " + sourceId, ioe);
        } catch (JSONException je) {
            Log.i(TAG, "There was an issue parsing the json from " + sourceId, je);
        }

        return newsArticleList;

    }

    private void populateNewsArticlesList(List<NewsArticle> list, JSONObject jsonBody)
        throws JSONException {

        if (!jsonBody.getString("status").equalsIgnoreCase("ok")) {
            Log.i(TAG, "There is something wrong with the JSON body");
            return;
        }

        JSONArray articlesJsonArray = jsonBody.getJSONArray("articles");
        JSONObject articleJsonObject = null;
        NewsArticle currentArticle = null;

        for (int i = 0; i < articlesJsonArray.length(); i++) {
            articleJsonObject = articlesJsonArray.getJSONObject(i);

            currentArticle = new NewsArticle();
            currentArticle.setUrl(articleJsonObject.getString("url"));
            currentArticle.setAuthor(articleJsonObject.getString("author"));
            currentArticle.setDescription(articleJsonObject.getString("description"));
            currentArticle.setPublishedAt(articleJsonObject.getString("publishedAt"));
            currentArticle.setTitle(articleJsonObject.getString("title"));
            currentArticle.setUrlToImage(articleJsonObject.getString("urlToImage"));

            list.add(currentArticle);
        }
    }

    public Bitmap getBitmapFromUri(Uri imageUri) {

        Bitmap bitmap = null;

        try {
            byte[] bitmapBytes = getUrlBytes(imageUri.toString());
            bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        } catch (IOException ioe) {
            Log.i(TAG, "There was an issue getting the bitmap from the image uri", ioe);
        }

        return bitmap;

    }
}






























