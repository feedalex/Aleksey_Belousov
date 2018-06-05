package com.belousov.aleksey.alexey_belousov;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.belousov.aleksey.alexey_belousov.interfaces.OnSelectItemListener;

public class MainActivity extends AppCompatActivity implements OnSelectItemListener {
    FragmentManager fm;
    CatNewsListFragment catNewsListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        catNewsListFragment = new CatNewsListFragment();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            fm.beginTransaction().replace(R.id.container, catNewsListFragment).commit();
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CatNewsDetailsFragment catNewsDetailsFragment = CatNewsDetailsFragment.newInstance(0);
            fm.beginTransaction().replace(R.id.list_container, catNewsListFragment).commit();
            fm.beginTransaction().replace(R.id.detail_container, catNewsDetailsFragment).commit();
        }
    }

    @Override
    public void OnSelectItem(int position) {
        CatNewsDetailsFragment catNewsDetailsFragment = CatNewsDetailsFragment.newInstance(position);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            fm.beginTransaction().replace(R.id.container, catNewsDetailsFragment).addToBackStack(null).commit();
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fm.beginTransaction().replace(R.id.detail_container, catNewsDetailsFragment).commit();
        }
    }

    @Override
    public void refreshList() {
        catNewsListFragment.refreshList();
    }
}
