package com.ian.springbootmall.util;

import com.ian.springbootmall.model.Product;

import java.util.List;

//  Java 泛型（Generics）中，<T>是類型參數（Type Parameter）
//  允許我們在定義時不需要指定具體的類型
public class Page<T> {

    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
