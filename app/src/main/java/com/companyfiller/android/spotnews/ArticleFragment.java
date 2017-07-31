package com.companyfiller.android.spotnews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Chase on 7/29/2017.
 *
 * Fragment to show information about the current article that is in the forefront of the pager
 */

public class ArticleFragment extends Fragment {

    private static final String TAG = "ArticleFragment";
    private static final String ARTICLE_TITLE_TAG = "title";
    private static final String SOURCE_TITLE_TAG = "source";
    private static final String COLOR_SWITCH_TAG = "colorSwitch";

    /*
        This app has code to use either an implicit intent or a webview for displaying
        the web page for an article's url.  The switch below controls which method is used.
     */
    private static final boolean useWebView = false;

    private String sourceTitle;

    private boolean usesColor1Flag;

    private TextView sourceTitleTextView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView articleImageView;
    private Button linkButton;

    private NewsArticle newsArticle;

    private AsyncTask fetchPhotoTask;

    public static ArticleFragment getInstance(String articleTitle, String sourceTitle,
                                              boolean usesColor1) {
        Bundle args = new Bundle();
        args.putString(ARTICLE_TITLE_TAG, articleTitle);
        args.putString(SOURCE_TITLE_TAG, sourceTitle);
        args.putBoolean(COLOR_SWITCH_TAG, usesColor1);

        ArticleFragment articleFragment = new ArticleFragment();
        articleFragment.setArguments(args);

        return articleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sourceTitle = getArguments().getString(SOURCE_TITLE_TAG);

        String title = getArguments().getString(ARTICLE_TITLE_TAG);
        newsArticle = ArticlesHolder.getInstance().getArticleFromTitle(title);

        usesColor1Flag = getArguments().getBoolean(COLOR_SWITCH_TAG);

        fetchPhotoTask = new FetchPhotoTask().execute();

        Log.i(TAG, "The onCreateMethod was called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_article_page, container, false);

        sourceTitleTextView = v.findViewById(R.id.source_title_text_view);
        sourceTitleTextView.setText("Source: " + sourceTitle);

        titleTextView = v.findViewById(R.id.article_title_text_view);
        titleTextView.setText(newsArticle.getTitle());

        if (newsArticle.getDescription() != null &&
                !newsArticle.getDescription().equalsIgnoreCase("null")) {
            Log.i(TAG, "Here is the description: " + newsArticle.getDescription());
            descriptionTextView = v.findViewById(R.id.article_description_text_view);
            descriptionTextView.setText(newsArticle.getDescription());
        }

        articleImageView = v.findViewById(R.id.article_image);

        linkButton = v.findViewById(R.id.article_viewer_button);
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (useWebView) {
                    Intent i = ArticleWebPageActivity.newIntent(getActivity(),
                            newsArticle.getArticleUri());
                    startActivity(i);
                } else {
                    Log.i(TAG, "Uri used for the actionView intent: " + newsArticle.getArticleUri().toString());
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(newsArticle.getArticleUri());

                    startActivity(browserIntent);
                }
            }
        });

        if (usesColor1Flag) {
            v.setBackground(getResources().getDrawable(R.color.sourceListFirstHalf));
            linkButton.setBackground(getResources().getDrawable(R.color.sourcesListSecondHalf));
        } else {
            v.setBackground(getResources().getDrawable(R.color.sourcesListSecondHalf));
            linkButton.setBackground(getResources().getDrawable(R.color.sourceListFirstHalf));

        }

        return v;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "The onDestroy method was called");
    }

    public class FetchPhotoTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return new NewsFetcher().getBitmapFromUri(newsArticle.getImageUri());
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            articleImageView.setImageBitmap(bitmap);
        }
    }
}


























