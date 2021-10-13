package com.tacoconsumption.tacoseaten;

import com.tacoconsumption.tacoseaten.Entities.TacoEaten;

import java.util.ArrayList;
import java.util.List;

public class TacosEatenList {

    private List<TacoEaten> tacosEaten;

    public TacosEatenList() {this.tacosEaten = new ArrayList<>(); }

    public TacosEatenList(List<TacoEaten> tacosEaten) {this.tacosEaten = tacosEaten;}

    public List<TacoEaten> getTacosEaten() {
        return tacosEaten;
    }

    public void setTacosEaten(List<TacoEaten> tacosEaten) {
        this.tacosEaten = tacosEaten;
    }

    public boolean isEmpty() {return this.tacosEaten.isEmpty();}
}
