package com.mobile.openlibraryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FooterFragment extends Fragment {

    public FooterFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout first
        View rootView = inflater.inflate(R.layout.fragment_footer, container, false);

        // Find your TextView by ID
        TextView linkVision = rootView.findViewById(R.id.linkVision);
        TextView linkVolunteer = rootView.findViewById(R.id.linkVolunteer);
        TextView linkPartnerWithUs = rootView.findViewById(R.id.linkPartnerWithUs);
        TextView linkCarrers = rootView.findViewById(R.id.linkCarrers);
        TextView linkBlog = rootView.findViewById(R.id.linkBlog);
        TextView linkTermsOfService = rootView.findViewById(R.id.linkTermsOfService);
        TextView linkDonate = rootView.findViewById(R.id.linkDonate);

        // Set Link for webview
        linkVision.setOnClickListener(v -> openWebPage("https://openlibrary.org/about"));
        linkVolunteer.setOnClickListener(v -> openWebPage("https://openlibrary.org/volunteer"));
        linkPartnerWithUs.setOnClickListener(v -> openWebPage("https://openlibrary.org/partner-with-us"));
        linkCarrers.setOnClickListener(v -> openWebPage("https://archive.org/about/jobs"));
        linkBlog.setOnClickListener(v -> openWebPage("https://blog.openlibrary.org/"));
        linkTermsOfService.setOnClickListener(v -> openWebPage("https://archive.org/about/terms"));
        linkDonate.setOnClickListener(v -> openWebPage("https://archive.org/donate/?platform=ol&origin=olwww-TopNavDonateButton"));

        return rootView;
    }
    private void openWebPage(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}