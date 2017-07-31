package com.companyfiller.android.spotnews;

/**
 * Created by Chase on 7/26/2017.
 *
 * Class to hold data for all of the news sources
 */

public class NewsSource {

    private String id;
    private String name;
    private String description;
    private String url;
    private String language;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("News Outlet Name: " + name + "\t");
        sb.append("Description: " + description + "\t");
        sb.append("Language: " + language);

        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
