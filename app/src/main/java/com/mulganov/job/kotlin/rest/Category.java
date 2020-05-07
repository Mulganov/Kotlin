package com.mulganov.job.kotlin.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category {

    @SerializedName("drinks")
    @Expose
    public ArrayList<Array> list;

    public class Array{
        @SerializedName("strCategory")
        @Expose
        public String category;
    }


}