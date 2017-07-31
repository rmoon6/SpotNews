package com.companyfiller.android.spotnews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by Chase on 7/30/2017.
 *
 * This class will be used to open up an article's link in a web view
 */

public class ArticleWebPageActivity extends SingleFragmentActivity {

    private static final String TAG = "ArticleWebPageActivity";

    public static Intent newIntent(Context context, Uri webPageUri) {
        Intent i = new Intent(context, ArticleWebPageActivity.class);
        i.setData(webPageUri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return ArticleWebPageFragment.newInstance(getIntent().getData());
    }
}





























