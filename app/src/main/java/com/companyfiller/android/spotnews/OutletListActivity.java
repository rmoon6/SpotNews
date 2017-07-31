package com.companyfiller.android.spotnews;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OutletListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return OutletListFragment.newInstance();
    }
}
