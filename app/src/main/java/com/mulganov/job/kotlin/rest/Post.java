package com.mulganov.job.kotlin.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Post {

    @SerializedName("drinks")
    @Expose
    public ArrayList<Array> list;

    public class Array{
        @SerializedName("strDrink")
        @Expose
        public String name;
        @SerializedName("strDrinkThumb")
        @Expose
        public String img;
    }


}