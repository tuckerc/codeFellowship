package com.chaseatucker.codefellowship.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(columnDefinition="text")
  private String body;
  @CreationTimestamp
  private Date createdAt;

  @ManyToOne
  ApplicationUser user;

  public Post() {}

  public Post(String body, ApplicationUser user) {
    this.body = body;
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public String getBody() {
    return body;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public ApplicationUser getUser() {
    return user;
  }
}
