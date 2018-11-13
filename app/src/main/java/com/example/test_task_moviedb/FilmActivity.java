package com.example.test_task_moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FilmActivity extends AppCompatActivity implements MvpContract.View {
    TextView tvName, tvDescription;
    ImageView filmPoster;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        intent = getIntent();
        tvName = (TextView) findViewById(R.id.textViewName);
        tvDescription = (TextView) findViewById(R.id.textViewDescription);
        filmPoster = (ImageView) findViewById(R.id.imageView);
        displayBaseContent();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void displayBaseContent() {

        if (intent.getStringExtra("film_name") != null) {
            tvName.setText(intent.getStringExtra("film_name"));
        }

        if (intent.getStringExtra("film_description") != null) {
            tvDescription.setText(intent.getStringExtra("film_description"));
        }
        if (intent.getStringExtra("image_url") != null) {
            Picasso.get()
                    .load(intent.getStringExtra("image_url"))

                    .placeholder(getApplicationContext().getResources().getDrawable(R.drawable.no_image))
                    .error(getApplicationContext().getResources().getDrawable(R.drawable.no_image))
                    .fit()
                    .into(filmPoster);
        }
    }
}
