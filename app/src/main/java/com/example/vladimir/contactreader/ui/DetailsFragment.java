package com.example.vladimir.contactreader.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;
import com.example.vladimir.contactreader.view.DetailsView;

import javax.inject.Inject;
import javax.inject.Provider;


public class DetailsFragment extends MvpAppCompatFragment implements DetailsView {

    private static final String TAG = "TAG";
    @InjectPresenter
    DetailsPresenter detailsPresenter;

    @Inject
    public Provider<DetailsPresenter> contactpresenterProvider;

    @ProvidePresenter
    DetailsPresenter provideDetailsPresenter() {
        return contactpresenterProvider.get();
    }


    public final String INDEX = "index";
    private TextView phoneText;
    private TextView emailText;
    private TextView nameText;
    private TextView surnameText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App app = (App) getActivity().getApplication();
        app.getContactComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        phoneText = rootView.findViewById(R.id.textPhone);
        emailText = rootView.findViewById(R.id.textEmail);
        nameText = rootView.findViewById(R.id.textName);
        surnameText = rootView.findViewById(R.id.textFamily);

        Bundle args = getArguments();
        if (args != null) {
            Long key = args.getLong(INDEX);
            detailsPresenter.getKeyItem(key);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setDetailsName(String name, String surname) {
        nameText.setText(name);
        surnameText.setText(surname);
    }

    @Override
    public void setDetailsPhone(String phone) {
        phoneText.setText(phone);
    }

    @Override
    public void setDetailsEmail(String email) {
        emailText.setText(email);
    }
}





