package com.tacoconsumption.tacoseaten.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="TACO_COMMENTS")
public class TacoComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp commentTimeStamp;
    private String message;

        public TacoComment(){
        }

    public TacoComment(String comment) {
        this.message = comment;
        this.commentTimeStamp = new Timestamp((System.currentTimeMillis()));
    }

    public String getComment() {
        return message;
    }

    public void setComment(String comment) {
        this.message = comment;
    }

    public Timestamp getCommentTimeStamp() {
        return commentTimeStamp;
    }

    public void setCommentTimeStamp(Timestamp commentTimeStamp) {
        this.commentTimeStamp = commentTimeStamp;
    }
}
