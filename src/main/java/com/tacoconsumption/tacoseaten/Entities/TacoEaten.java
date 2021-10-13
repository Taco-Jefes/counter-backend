package com.tacoconsumption.tacoseaten.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="TACOS_LOGGED")
public class TacoEaten {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp ateTacoRecordDate;
    private String teamAssociation;

    public TacoEaten(){

    }

    public TacoEaten(String team) {
        this.teamAssociation = team.toLowerCase();
        this.ateTacoRecordDate = new Timestamp((System.currentTimeMillis()));
    }



    public String getTeamAssociation() {
        return teamAssociation;
    }

    public void setTeamAssociation(String teamAssociation) {
        this.teamAssociation = teamAssociation.toLowerCase();
    }

    public Timestamp getAteTacoRecordDate() {
        return ateTacoRecordDate;
    }

    public void setAteTacoRecordDate(Timestamp ateTacoRecordDate) {
        this.ateTacoRecordDate = ateTacoRecordDate;
    }
}
