package com.example.test_task_moviedb;

import com.example.test_task_moviedb.POJO.Result;

import java.util.List;


public class MovieDBPresenter implements MvpContract.Presenter {
    private MvpContract.MainView mView;
    private MvpContract.Model mModel;

    public void setFilms(List<Result> films) {

        mView.displayFilms(films);
        mView.hideLoading();
    }


    public MovieDBPresenter(MvpContract.MainView mView) {
        this.mView = mView;
        this.mModel = new MovieDBModel();


    }


    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }

    @Override
    public void requestFilms() {

        mView.showLoading();
        mModel.loadFilms(this);

    }

    @Override
    public void setGridForRecycler(int spanCount) {
        mView.setGridForRecycler(mModel.getFilms(), spanCount);
    }

    public void showError() {
        mView.displayErrorDialog();
    }
}
