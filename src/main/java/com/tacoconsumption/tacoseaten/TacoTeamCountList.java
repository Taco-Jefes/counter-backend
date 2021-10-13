package com.tacoconsumption.tacoseaten;

import java.util.ArrayList;
import java.util.List;

public class TacoTeamCountList {

    private List<TacoCounts> tacoTeamCounts;

    public TacoTeamCountList() {
        this.tacoTeamCounts = new ArrayList<>();
    }

    public TacoTeamCountList(List<TacoCounts> tacoTeamCounts) {
        this.tacoTeamCounts = tacoTeamCounts;
    }

    public List<TacoCounts> getTacoTeamCounts() {
        return tacoTeamCounts;
    }

    public void setTacoTeamCounts(List<TacoCounts> tacoTeamCounts) {
        this.tacoTeamCounts = tacoTeamCounts;
    }

    public boolean isEmpty() {
        return this.tacoTeamCounts.isEmpty();
    }
}
