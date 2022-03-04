package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CatOneDetailsActivity extends AppCompatActivity {

    private ImageView imageview;
    private TextView title;
    private TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_one_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageview = findViewById(R.id.image_dt);
        title = findViewById(R.id.title_details);
        description=findViewById(R.id.description_details);

        Intent intent = getIntent();
        String mTitle=intent.getStringExtra("title");
        String mDescription = intent.getStringExtra("description");
        String mImage = intent.getStringExtra("image");

        title.setText((mTitle));
        title.setText((mDescription));
        Picasso.get().load(mImage).networkPolicy(NetworkPolicy.OFFLINE).into(imageview, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

                Picasso.get().load(mImage).into(imageview);
            }
        });
    }
}