package com.tacoconsumption.tacoseaten;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacoconsumption.tacoseaten.Entities.TacoComment;

public class TacoCommentsList {
    private List<TacoComment> tacoCommentList;

    public TacoCommentsList() {
        this.tacoCommentList = new ArrayList<>();
    }

    public TacoCommentsList(List<TacoComment> tacoComments) {
        this.tacoCommentList = tacoComments;
    }

    public List<TacoComment> getTacoCommentList() {
        return tacoCommentList;
    }

    public void setTacoCommentList(List<TacoComment> tacoCommentList) {
        this.tacoCommentList = tacoCommentList;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.tacoCommentList.isEmpty();
    }

}
