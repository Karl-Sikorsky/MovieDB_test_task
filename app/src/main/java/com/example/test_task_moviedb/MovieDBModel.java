package com.example.test_task_moviedb;

import com.example.test_task_moviedb.POJO.Films;
import com.example.test_task_moviedb.POJO.Result;
import com.example.test_task_moviedb.network.FilmsService;
import com.example.test_task_moviedb.network.RetrofitHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MovieDBModel implements MvpContract.Model {

    List<Result> films;
    @NonNull
    private FilmsService mFilmsService;

    @NonNull

    public List<Result> getFilms() {
        return films;
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
    }

    public MovieDBModel() {
        mFilmsService = new RetrofitHelper().getFilmsFromApi();
    }

    public void setFilms(List<Result> films) {
        this.films = films;

    }


    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();



    @Override
    public void loadFilms(final MovieDBPresenter movieDBPresenter) {
        mCompositeDisposable.add(mFilmsService.queryFilms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Films, List<Result>>() {
                    @Override
                    public List<Result> apply(
                            @io.reactivex.annotations.NonNull final Films filmsResponse) throws Exception {

                        return filmsResponse.getResults();
                    }
                })
                .subscribe(new Consumer<List<Result>>() {

                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<Result> results)
                            throws Exception {
                        setFilms(results);
                        movieDBPresenter.setFilms(results);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        movieDBPresenter.showError();
                    }
                })

        );
    }
}
