package com.example.sobhy.blogger;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class BloggerApi {

    public static final String key = "AIzaSyDj_P50pn7gj6meECWpXeqDOzYaH2O-yd8";
    public static final String url = "https://www.googleapis.com/blogger/v3/blogs/2847227214232852724/posts/";


    public static PostService postService = null;

    public static PostService getService()
    {
        if(postService==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService = retrofit.create(PostService.class);


        }
        return postService;

    }


    public interface PostService
    {
        @GET
        Call<Postlist> getPostlist(@Url String url);

    }


}
