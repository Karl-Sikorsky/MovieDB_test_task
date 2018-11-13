package com.example.test_task_moviedb;

import com.example.test_task_moviedb.POJO.Result;

import java.util.List;

public interface MvpContract {

    interface View {
        void showLoading();

        void hideLoading();

        void displayBaseContent();
    }

    interface MainView extends View {

        void displayFilms(final List<Result> films);

        void displayErrorDialog();

        void setGridForRecycler(final List<Result> films, int spanCount);
    }

    interface Presenter {

        void onDestroy();

        void requestFilms();

        void setGridForRecycler(int spanCount);

        void showError();

    }

    interface Model {
        void onDestroy();

        List<Result> getFilms();

        void loadFilms(MovieDBPresenter movieDBPresenter);
    }

}
