package com.companyfiller.android.spotnews;

import java.util.List;

/**
 * Created by Chase on 7/27/2017.
 *
 * This will be a singleton class that will hold the current set of news articles being considered.
 *
 * Purpose is to have it so that there will be only one set of articles out there at once
 * and so that it can be accessible from anywhere
 */

public class ArticlesHolder {

    public static ArticlesHolder articlesHolder;

    public List<NewsArticle> articlesList;

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
}






























