package com.mohiesen.codefellowship.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String body;
    Date timeStamp;
    @ManyToOne
    ApplicationUser postOwner;

    public ApplicationUser getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(ApplicationUser postOwner) {
        this.postOwner = postOwner;
    }

    public Post(String body, Date timeStamp, ApplicationUser postOwner) {
        this.body = body;
        this.timeStamp = timeStamp;
        this.postOwner = postOwner;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
