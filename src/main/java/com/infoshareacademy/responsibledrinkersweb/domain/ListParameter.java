package com.infoshareacademy.responsibledrinkersweb.domain;

import com.infoshareacademy.drinkers.domain.drink.Alcoholic;
import com.infoshareacademy.drinkers.service.filtering.FilterElements;


public class ListParameter {
    private int sort;
    private String keyword;
    private Alcoholic alcoholic;
    private FilterElements filterElements;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Alcoholic getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(Alcoholic alcoholic) {
        this.alcoholic = alcoholic;
    }

    public FilterElements getFilterElements() {
        return filterElements;
    }

    public void setFilterElements(FilterElements filterElements) {
        this.filterElements = filterElements;
    }

    @Override
    public String toString() {
        return "ListParameter{" +
                "sort=" + sort +
                ", keyword='" + keyword + '\'' +
                ", alcoholic=" + alcoholic +
                ", filterElements=" + filterElements +
                '}';
    }
}
