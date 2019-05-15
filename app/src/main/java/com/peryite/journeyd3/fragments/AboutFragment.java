package com.peryite.journeyd3.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peryite.journeyd3.R;

public class AboutFragment extends Fragment {

    private final static String WEBSITE_JOURNEY = "https://d3resource.com/journey/";
    private final static String BAGNSTONE_PROFILE_REDDIT = "https://www.reddit.com/user/bagstone";
    private final static String WEBSITE_FLATICON = "https://www.flaticon.com/";

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        view.findViewById(R.id.websiteJourney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl(WEBSITE_JOURNEY);
            }
        });
        view.findViewById(R.id.websiteBagstone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl(BAGNSTONE_PROFILE_REDDIT);
            }
        });
        view.findViewById(R.id.websiteFreepik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl(WEBSITE_FLATICON);
            }
        });
        view.findViewById(R.id.websiteIconixar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl(WEBSITE_FLATICON);
            }
        });
        return view;
    }


    private void goToUrl(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
