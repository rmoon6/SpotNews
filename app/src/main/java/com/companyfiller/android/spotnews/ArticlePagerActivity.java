package com.companyfiller.android.spotnews;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;

/**
 * Created by Chase on 7/29/2017.
 *
 * This will run a background thread to get the data for the articles
 * and the use the output list to populate the pager
 */

public class ArticlePagerActivity extends FragmentActivity {

    private static final String TAG = "ArticlePagerActivity";
    private static final String INTENT_SOURCE_KEY = "id";
    private static final String INTENT_TITLE_KEY = "title";
    private static final String USES_COLOR_ONE_SWITCH = "hasColor";

    private String newsSourceId;
    private String newsSourcetitle;

    boolean usesColor1Flag;

    private ViewPager articleViewPager;

    private List<NewsArticle> articleList;

    private AsyncTask fetchNewsArticlesTask;

    public static Intent newIntent(Context context, String id, String title, boolean usesColor1) {
        Intent i = new Intent(context, ArticlePagerActivity.class);
        i.putExtra(INTENT_SOURCE_KEY, id);
        i.putExtra(INTENT_TITLE_KEY, title);
        i.putExtra(USES_COLOR_ONE_SWITCH, usesColor1);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_pager);

        articleViewPager = (ViewPager) findViewById(R.id.article_view_pager);

        newsSourceId = getIntent().getStringExtra(INTENT_SOURCE_KEY);
        newsSourcetitle = getIntent().getStringExtra(INTENT_TITLE_KEY);
        usesColor1Flag = getIntent().getBooleanExtra(USES_COLOR_ONE_SWITCH, false);
        Log.i(TAG, "Here is the source id passed in: " + newsSourceId);
        new FetchNewsArticlesTask().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "The onDestroy() method was called");
    }

    private class FetchNewsArticlesTask extends AsyncTask<String, Void, List<NewsArticle>> {

        @Override
        protected List<NewsArticle> doInBackground(String...args) {
            return new NewsFetcher().getNewsArticles(newsSourceId);
        }

        @Override
        protected void onPostExecute(List<NewsArticle> newsArticles) {
            super.onPostExecute(newsArticles);
            ArticlesHolder.getInstance().setArticlesList(newsArticles);
            Log.i(TAG, ArticlesHolder.getInstance().toString());
            articleList = ArticlesHolder.getInstance().getArticlesList();

            setupPager();
        }
    }

    private void setupPager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        articleViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                NewsArticle art = ArticlesHolder.getInstance().getArticlesList().get(position);
                return ArticleFragment.getInstance(art.getTitle(), newsSourcetitle,
                        usesColor1Flag);
            }

            @Override
            public int getCount() {
                return articleList.size();
            }
        });
    }
}




























