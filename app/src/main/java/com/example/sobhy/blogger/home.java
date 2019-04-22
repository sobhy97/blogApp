package com.example.sobhy.blogger;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    PostAdapter postAdapter;
    List<Item> items = new ArrayList<>();
    Boolean isScrolling = false;
    int currentItems , totalItems,scrollOutItems;
    String token = "";
    SpinKitView progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.postlist);
        manager = new LinearLayoutManager(this);
        postAdapter = new PostAdapter(this,items);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(postAdapter);

        progress = findViewById(R.id.spin_kit);

        navigationView = findViewById(R.id.navigation_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        Toast.makeText(home.this,"Clicked home",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_android:
                        Toast.makeText(home.this,"Clicked android",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_ios:
                        Toast.makeText(home.this,"Clicked ios",Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_news:
                        Toast.makeText(home.this,"Clicked news",Toast.LENGTH_LONG).show();
                        break;


                }

                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false;

                    getData();

                }


            }
        });








        setUpToolbar();

        getData();

    }

    public void setUpToolbar()
    {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }


    private void getData()
    {

        String url = BloggerApi.url + "?key=" + BloggerApi.key;

        if (token != "")
        {
            url = url + "&pageToken" + token;
        }

        if (token == null)
        {
            return;
        }

        progress.setVisibility(View.VISIBLE);
        final Call<Postlist> postlistCall = BloggerApi.getService().getPostlist(url);

        postlistCall.enqueue(new Callback<Postlist>() {
            @Override
            public void onResponse(Call<Postlist> call, Response<Postlist> response) {

                Postlist postlist = response.body();
                token = postlist.getNextPageToken();
                items.addAll(postlist.getItems());
                postAdapter.notifyDataSetChanged();
//                recyclerView.setAdapter(new PostAdapter(home.this,postlist.getItems()));
                Toast.makeText(home.this,"Success",Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Postlist> call, Throwable t) {

                Toast.makeText(home.this,"Error",Toast.LENGTH_SHORT).show();


            }
        });

    }


}
