package com.world.headlineexpress.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class SettingFragment extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);

        /////////


////////////////////

        ImageView fb_icon = findViewById(R.id.fb_icon);
        TextView fb_text = findViewById(R.id.fb_text);


        fb_text.setOnClickListener(v -> {
            fb_text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.teal));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100070179483307"));
            startActivity(intent);
        });


        fb_icon.setOnClickListener(v -> {
            fb_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100070179483307"));
            startActivity(intent);

        });


////////

        ImageView insta_icon = findViewById(R.id.insta_icon);
        TextView insta_text = findViewById(R.id.insta_text);

        insta_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insta_text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.teal));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https:/www.instagram.com/anisshah.official"));
                startActivity(intent);

            }
        });


        insta_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insta_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https:/www.instagram.com/anisshah.official"));
                startActivity(intent);

            }
        });

        //////////


        TextView privacy = findViewById(R.id.privacy);

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacy.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://princeanisshah.wordpress.com/fact-tak-app/"));
                startActivity(intent);

            }
        });


        TextView terms = findViewById(R.id.terms);

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terms.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://princeanisshah.wordpress.com/terms-conditions/"));
                startActivity(intent);

            }
        });


        TextView app_info = findViewById(R.id.app_info);

        app_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_info.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri= Uri.fromParts("package",getPackageName(),null);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivity(intent);

            }
        });


        /////////


    }
}