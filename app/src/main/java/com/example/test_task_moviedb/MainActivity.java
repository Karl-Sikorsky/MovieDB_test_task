package com.example.test_task_moviedb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.test_task_moviedb.POJO.Result;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class MainActivity extends AppCompatActivity implements MvpContract.MainView {

    RecyclerView rv;

    private ProgressDialog dialog;

    private MvpContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MovieDBPresenter(this);
        rv = (RecyclerView) findViewById(R.id.rv);


        dialog = new ProgressDialog(this);

        mPresenter.requestFilms();

    }

    public void showLoading() {
        dialog.setMessage("Loading data, please wait.");
        dialog.show();
    }

    public void hideLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void displayBaseContent() {

    }


    @Override
    protected void onDestroy() {


        mPresenter.onDestroy();
        super.onDestroy();
    }

    public void displayFilms(@NonNull final List<Result> films) {
        setGridForRecycler(films, 2);
    }

    @Override
    public void displayErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Something went wrong");
        builder.setMessage("Check your internet connection and retry");
        builder.setPositiveButton("Retry",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.requestFilms();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    public void setGridForRecycler(List<Result> films, int spanCount) {
        GridLayoutManager glm = new GridLayoutManager(this, spanCount);
        rv.setLayoutManager(glm);

        RVAdapter adapter = new RVAdapter(films, this);
        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            mPresenter.setGridForRecycler(4);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mPresenter.setGridForRecycler(2);
        }
    }


}