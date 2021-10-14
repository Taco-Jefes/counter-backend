package com.tacoconsumption.tacoseaten;


public class TacoCounts {
    private String team;
    private int tacoCount;

    public TacoCounts(String team,  int tacoCount) {
        this.team = team;
        this.tacoCount = tacoCount;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getTacoCount() {
        return tacoCount;
    }

    public void setTacoCount(int tacoCount) {
        this.tacoCount = tacoCount;
    }
}
