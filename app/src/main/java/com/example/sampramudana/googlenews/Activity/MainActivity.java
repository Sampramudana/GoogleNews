package com.example.sampramudana.googlenews.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sampramudana.googlenews.Model.ArticlesItem;
import com.example.sampramudana.googlenews.Model.ResponseGoogle;
import com.example.sampramudana.googlenews.Model.Source;
import com.example.sampramudana.googlenews.Network.ApiService;
import com.example.sampramudana.googlenews.Network.InstanceRetrofit;
import com.example.sampramudana.googlenews.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcGoogle;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO Initialize widget to variable
        rcGoogle = findViewById(R.id.rcGoogle);

        getData();
    }

    private void getData() {
        final ApiService apiService = InstanceRetrofit.getInstance();
        Call<ResponseGoogle> call = apiService.readNewsApi();
        call.enqueue(new Callback<ResponseGoogle>() {
            @Override
            public void onResponse(Call<ResponseGoogle> call, Response<ResponseGoogle> response) {
                List<ArticlesItem> articlesItems = response.body().getArticles();
                adapter = new CustomAdapter(rcGoogle, MainActivity.this, articlesItems);
                rcGoogle.setAdapter(adapter);
                rcGoogle.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(Call<ResponseGoogle> call, Throwable t) {

            }
        });
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        Context context;
        List<ArticlesItem> articlesItems;

        public CustomAdapter(RecyclerView rcGoogle, Context context, List<ArticlesItem> articlesItems) {

            this.context = context;
            this.articlesItems = articlesItems;

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.googlelist, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder myViewHolder, int i) {

            myViewHolder.txtPublish.setText(articlesItems.get(i).getPublishedAt());
            myViewHolder.txtTitle.setText(articlesItems.get(i).getTitle());
            myViewHolder.txtAuthor.setText(articlesItems.get(i).getAuthor());
            myViewHolder.txtDesc.setText(articlesItems.get(i).getDescription());
            Source source = (Source) articlesItems.get(i).getSource();
            myViewHolder.txtName.setText(source.getName());

            Glide.with(context)
                    .load(articlesItems.get(i).getUrlToImage())
                    .centerCrop()
                    .into(myViewHolder.image);

        }

        @Override
        public int getItemCount() {
            return articlesItems.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txtName, txtTitle, txtAuthor, txtPublish, txtDesc;
            ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                txtTitle = itemView.findViewById(R.id.txtTitle);
                txtName = itemView.findViewById(R.id.txtname);
                txtAuthor = itemView.findViewById(R.id.txtAuthor);
                txtPublish = itemView.findViewById(R.id.txtPublished);
                txtDesc = itemView.findViewById(R.id.txtDescription);
                image = itemView.findViewById(R.id.img);

            }
        }
    }
}
