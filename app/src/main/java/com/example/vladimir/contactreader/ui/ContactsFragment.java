package com.example.vladimir.contactreader.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vladimir.contactreader.CustomAdapter;
import com.example.vladimir.contactreader.MyDecoration;
import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.presenter.ContactPresenter;
import com.example.vladimir.contactreader.presenter.MainPresenter;
import com.example.vladimir.contactreader.view.ContactView;
import com.example.vladimir.contactreader.view.MainActivityView;

import java.util.List;


public class ContactsFragment extends MvpAppCompatFragment implements
        ContactView, MainActivityView, android.support.v7.widget.SearchView.OnQueryTextListener {

    private static final String TAG = "TAG";
    @InjectPresenter(type = PresenterType.GLOBAL, tag = "mainPresenter")
    MainPresenter mainPresenter;

    @InjectPresenter
    ContactPresenter contactPresenter;
    @ProvidePresenter
    ContactPresenter provideContactPresenter() {
        return new ContactPresenter(getContext());
    }


    private CustomAdapter customAdapter;
    private boolean isTablet;
    private ProgressBar progressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler);
        progressBar = rootView.findViewById(R.id.progressBar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomAdapter.ItemClickListener itemClickListener = idItem -> {
            isTablet = getResources().getBoolean(R.bool.isTablet);
            if (isTablet) {
                mainPresenter.itemClickInTablet(idItem);
            } else {
                mainPresenter.itemClickInPhone(idItem);
            }
        };

        customAdapter = new CustomAdapter(itemClickListener);
        Log.d(TAG, "constructor ");
        recyclerView.setAdapter(customAdapter);
        MyDecoration myDecoration = new MyDecoration(getContext());
        recyclerView.addItemDecoration(myDecoration);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void showContacts(List<String> contact) {
        customAdapter.setContacts(contact);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startDetailsFragmentForPhone(int itemKey) {
    }

    @Override
    public void startDetailsFragmentForTablet(int itemKey) {
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        customAdapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        customAdapter.filter(newText);
        return true;
    }
}






