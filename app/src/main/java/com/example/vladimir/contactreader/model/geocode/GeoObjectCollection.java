package com.example.vladimir.contactreader.model.geocode;

import com.example.vladimir.contactreader.model.geocode.FeatureMember;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoObjectCollection {

    @SerializedName("featureMember")
    @Expose
    private List<FeatureMember> featureMember = null;

    public List<FeatureMember> getFeatureMember() {
        return featureMember;
    }

}
