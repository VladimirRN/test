package com.example.vladimir.contactreader.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.model.DetailsInterface;
import com.example.vladimir.contactreader.view.DetailsView;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> implements DetailsInterface.onDetailsListener {

    private DetailsInterface detailsInterface;


    public DetailsPresenter(DetailsInterface detailsInterface) {
        this.detailsInterface = detailsInterface;
    }


    @Override
    public void getDataName(String name, String surname) {
        getViewState().setDetailsName(name, surname);
    }

    @Override
    public void getDataPhone(String phone) {
        getViewState().setDetailsPhone(phone);
    }

    @Override
    public void getLat(double lat) {
        getViewState().setLat(lat);
    }

    @Override
    public void getLng(double lng) {
        getViewState().setLng(lng);
    }

    @Override
    public void getDataEmail(String email) {
        getViewState().setDetailsEmail(email);
    }

    public void getKeyItem(Long key) {
        detailsInterface.getData(this, key);

    }
}



