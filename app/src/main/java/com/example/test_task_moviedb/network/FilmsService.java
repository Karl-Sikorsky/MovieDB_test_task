package com.example.test_task_moviedb.network;

import com.example.test_task_moviedb.POJO.Films;

import io.reactivex.Single;
import retrofit2.http.GET;


public interface FilmsService {
    @GET("3/discover/movie?")
    Single<Films> queryFilms();
}
