package com.example.sobhy.blogger;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.postViewHolder> {

    private Context context;
    private List<Item>items;

    public PostAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_item,viewGroup,false);
        return new postViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postViewHolder holder, int i) {

        final Item item = items.get(i);
        holder.posttitle.setText(item.getTitle());


        Document document = Jsoup.parse(item.getContent());
        holder.postDesc.setText(document.text());
        Elements elements = document.select("img");




//        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
//        Matcher m = p.matcher(item.getContent());
//        List<String> tokens = new ArrayList<>();
//        while (m.find())
//        {
//            String token = m.group(1);
//            tokens.add(token);
//        }

        Glide.with(context).load(elements.get(0).attr("src")).into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(context,Detailactivity.class);
                intent.putExtra("url",item.getUrl());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class postViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView ;
        TextView posttitle;
        TextView postDesc;


        public postViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.post_img);
            posttitle = itemView.findViewById(R.id.post_title);
            postDesc = itemView.findViewById(R.id.post_desc);
        }
    }

}
