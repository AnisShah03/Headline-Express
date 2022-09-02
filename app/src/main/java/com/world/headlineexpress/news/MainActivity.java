package com.world.headlineexpress.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategorClickInterface {

//103af4845aef4ca1afedbdab0597e1d1

    private RecyclerView newsRV, categoryRV;
    private ProgressBar progressBar;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    private ImageView settingsBtn;
    private ImageView btnToggleDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();

        checkInternet();
        newsRV = findViewById(R.id.RVNews);

        btnToggleDark = findViewById(R.id.themeBtn);

        settingsBtn = findViewById(R.id.settings);

        categoryRV = findViewById(R.id.RVcategories);
        progressBar = findViewById(R.id.loadingbar);
        articlesArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList, this, this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();


///
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            btnToggleDark.setImageResource(
                    R.drawable.sun);
        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            btnToggleDark.setImageResource(
                    R.drawable.moon);
        }

        btnToggleDark.setOnClickListener(
                view -> {
                    // When user taps the enable/disable
                    // dark mode button
                    if (isDarkModeOn) {

                        // if dark mode is on it
                        // will turn it off
                        AppCompatDelegate
                                .setDefaultNightMode(
                                        AppCompatDelegate
                                                .MODE_NIGHT_NO);
                        // it will set isDarkModeOn
                        // boolean to false
                        editor.putBoolean(
                                "isDarkModeOn", false);
                        editor.apply();

                        // change text of Button
                        btnToggleDark.setImageResource(
                                R.drawable.sun);
                    } else {

                        // if dark mode is off
                        // it will turn it on
                        AppCompatDelegate
                                .setDefaultNightMode(
                                        AppCompatDelegate
                                                .MODE_NIGHT_YES);

                        // it will set isDarkModeOn
                        // boolean to true
                        editor.putBoolean(
                                "isDarkModeOn", true);
                        editor.apply();

                        // change text of Button
                        btnToggleDark.setImageResource(
                                R.drawable.moon);
                    }
                });

        settingsBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SettingFragment.class);
            startActivity(i);
        });


    }


    private void getCategories() {
        categoryRVModelArrayList.add(new CategoryRVModel("All", "https://source.unsplash.com/random/300x300?buisness-work,mountains,all"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology", "https://source.unsplash.com/random/300x300?technology,computer,software"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science", "https://source.unsplash.com/random/300x300?science,galaxy,physics"));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports", "https://source.unsplash.com/random/300x300?sports,athelets,cricket,sport"));
        categoryRVModelArrayList.add(new CategoryRVModel("General", "https://source.unsplash.com/random/300x300?general,all,people,culture"));
        categoryRVModelArrayList.add(new CategoryRVModel("Business", "https://source.unsplash.com/random/300x300?buisness-work,money"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment", "https://source.unsplash.com/random/300x300?entertainment"));
        categoryRVModelArrayList.add(new CategoryRVModel("Health", "https://source.unsplash.com/random/300x300?health,fruits,vegitables"));

        categoryRVAdapter.notifyDataSetChanged();

    }


    private void getNews(String category) {
        progressBar.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categorUrl = "https://newsapi.org/v2/top-headlines/?country=in&category=" + category + "&apiKey=103af4845aef4ca1afedbdab0597e1d1";
        String url = "https://newsapi.org/v2/top-headlines/?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=103af4845aef4ca1afedbdab0597e1d1";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<NewsModel> call;
        if (category.equals("All")) {
            call = retrofitApi.getAllNews(url);
        } else {
            call = retrofitApi.getNewsCategory(categorUrl);
        }
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                progressBar.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),
                            articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),
                            articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

                if (null == activeNetwork) {
                    Toast.makeText(getApplicationContext(), "Please Connect To The Internet", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong..Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);

    }

    private void checkInternet() {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null == activeNetwork) {

            Toast.makeText(getApplicationContext(), "Please Connect To The Internet", Toast.LENGTH_SHORT).show();


        } else {


        }

    }


}