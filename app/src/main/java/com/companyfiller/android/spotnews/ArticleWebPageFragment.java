package com.companyfiller.android.spotnews;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.net.URI;

/**
 * Created by Chase on 7/30/2017.
 *
 * Fragment for diaplaying the web view
 */

public class ArticleWebPageFragment extends Fragment {

    private static final String TAG = "ArticleWebPageFragment";
    private static final String URI_KEY = "uri";

    private Uri webPageUri;

    private WebView webView;

    public static ArticleWebPageFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(URI_KEY, uri);

        ArticleWebPageFragment articleWebPageFragment = new ArticleWebPageFragment();
        articleWebPageFragment.setArguments(args);
        return articleWebPageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webPageUri = getArguments().getParcelable(URI_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article_web_page, container, false);

        webView = v.findViewById(R.id.article_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.loadUrl(webPageUri.toString());

        return v;
    }
}
























