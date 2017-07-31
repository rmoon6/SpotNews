package com.companyfiller.android.spotnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chase on 7/26/2017.
 */

public class OutletListFragment extends Fragment {

    private static final String TAG = "OutletListFragment";
    private static final String SOURCES_GET = "sources";
    private static final String ARTICLES_GET = "articles";
    private static final String INTENT_SOURCE_KEY = "id";

    private List<NewsSource> sourcesList = new ArrayList<>();

    private RecyclerView sourcesRecyclerView;

    public static OutletListFragment newInstance() {
        return new OutletListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchNewsSourcesTask().execute(SOURCES_GET);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_outlet_list, container, false);

        sourcesRecyclerView = (RecyclerView) v.findViewById(R.id.outlet_list_recycler_view);
        sourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            sourcesRecyclerView.setAdapter(new NewsSourceAdapter(sourcesList));
        }
    }

    public class NewsSourceViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private View sourceView;
        private RelativeLayout sourceLayout;
        private TextView sourceNameTextView;
        private TextView sourceDescriptionTextView;
        private NewsSource newsSource;

        private boolean usesColor1Flag;

        public NewsSourceViewHolder(View textView) {
            super(textView);
            sourceView = textView;

            sourceLayout = sourceView.findViewById(R.id.recycler_relative_layout);
            sourceNameTextView = sourceView.findViewById(R.id.source_name);
            sourceDescriptionTextView = sourceView.findViewById(R.id.source_description);

            sourceLayout.setOnClickListener(this);
        }

        public void bindSource(NewsSource source, boolean usesColor1) {
            newsSource = source;
            usesColor1Flag = usesColor1;

            if (usesColor1Flag) {
                sourceLayout.setBackground(getResources().getDrawable(R.color.sourceListFirstHalf));
            } else {
                sourceLayout.setBackground(getResources().getDrawable(R.color.sourcesListSecondHalf));
            }

            sourceNameTextView.setText(source.getName());
            sourceDescriptionTextView.setText(source.getDescription());
        }

        @Override
        public void onClick(View view) {
            Log.i(TAG, "We've made it into the onClick listener for " + newsSource.getName());
            Intent i = ArticlePagerActivity.newIntent(getActivity(),
                    newsSource.getId(), newsSource.getName(),
                    usesColor1Flag);
            startActivity(i);
        }
    }

    public class NewsSourceAdapter extends RecyclerView.Adapter<NewsSourceViewHolder> {

        private List<NewsSource> newsSources;

        public NewsSourceAdapter(List<NewsSource> sources) {
            newsSources = sources;
        }

        @Override
        public NewsSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View v = inflater.inflate(R.layout.recycler_view_component, parent, false);
            return new NewsSourceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(NewsSourceViewHolder holder, int position) {
            holder.bindSource(newsSources.get(position), position % 2 == 0);
        }

        @Override
        public int getItemCount() {
            return newsSources.size();
        }
    }

    private class FetchNewsSourcesTask extends AsyncTask<String, Void, List<NewsSource>> {

        @Override
        protected List<NewsSource> doInBackground(String...args) {
            if (args[0].equals(SOURCES_GET)) {
                return new NewsFetcher().getNewsSources();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<NewsSource> newsSources) {
            super.onPostExecute(newsSources);
            sourcesList = newsSources;

            for (NewsSource ns : sourcesList) {
                Log.i(TAG, ns.toString());
            }

            setupAdapter();
        }
    }
}






























