package com.cleytongoncalves.popmovies.data.remote;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class LoganSquareConverter extends Converter.Factory {
//    private final LoganSquare loganSquare;

    public LoganSquareConverter() {
//        if (loganSquare == null) {
//            throw new NullPointerException("loganSquare == null");
//        }
//
//        this.loganSquare = loganSquare;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return null;
    }
}
