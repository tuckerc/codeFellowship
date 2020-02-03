package com.chaseatucker.codefellowship.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

@Entity
public class ApplicationUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private Date dateOfBirth;
  private String bio;

  // relationship to allow users to follow other users
  @ManyToMany
  @JoinTable (
      name="followers",
      joinColumns = { @JoinColumn(name = "follower_id")},
      inverseJoinColumns = { @JoinColumn(name = "followee_id")}
  )
  List<ApplicationUser> usersThatIFollow;

  @ManyToMany(mappedBy = "usersThatIFollow")
  List<ApplicationUser> usersThatFollowMe;

  // a user has many posts
  @OneToMany(mappedBy = "user")
  List<Post> posts;

  public ApplicationUser() {}

  public ApplicationUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.bio = bio;
  }

  public long getId() {
    return this.id;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public String getBio() {
    return bio;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public List<ApplicationUser> getUsersThatIFollow() {
    return usersThatIFollow;
  }

  public List<ApplicationUser> getUsersThatFollowMe() {
    return usersThatFollowMe;
  }
}
