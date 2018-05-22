package com.github2136.wardrobe.model.util;

import java.util.List;

/**
 * Created by yb on 2018/5/22.
 * 返回的结果集
 **/
public class Results<T> {

    private List<T> results;

    public List<T> getResults() { return results;}

    public void setResults(List<T> results) { this.results = results;}


}
