package com.mobile.openlibraryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.text.method.LinkMovementMethod;
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
        View rootView = inflater.inflate(R.layout.fragment_footer, container, false);

        // Footer link
        TextView aboutPara1 = rootView.findViewById(R.id.about_para1);
        TextView aboutPara2 = rootView.findViewById(R.id.about_para2);
        TextView latestBlogTitle = rootView.findViewById(R.id.latest_blog_title);
        TextView blogPost1 = rootView.findViewById(R.id.blog_post_1);
        TextView blogPost2 = rootView.findViewById(R.id.blog_post_2);
        TextView blogPost3 = rootView.findViewById(R.id.blog_post_3);

        if (aboutPara1 != null) {
            aboutPara1.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (aboutPara2 != null) {
            aboutPara2.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (latestBlogTitle != null) {
            latestBlogTitle.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (blogPost1 != null) {
            blogPost1.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (blogPost2 != null) {
            blogPost2.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (blogPost3 != null) {
            blogPost3.setMovementMethod(LinkMovementMethod.getInstance());
        }
        // Footer link
        TextView linkVision = rootView.findViewById(R.id.linkVision);
        TextView linkVolunteer = rootView.findViewById(R.id.linkVolunteer);
        TextView linkPartnerWithUs = rootView.findViewById(R.id.linkPartnerWithUs);
        TextView linkCarrers = rootView.findViewById(R.id.linkCarrers);
        TextView linkBlog = rootView.findViewById(R.id.linkBlog);
        TextView linkTermsOfService = rootView.findViewById(R.id.linkTermsOfService);
        TextView linkDonate = rootView.findViewById(R.id.linkDonate);

        // Webview for footer
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