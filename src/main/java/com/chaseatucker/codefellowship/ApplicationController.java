package com.chaseatucker.codefellowship;

import com.chaseatucker.codefellowship.model.ApplicationUser;
import com.chaseatucker.codefellowship.model.ApplicationUserRepository;
import com.chaseatucker.codefellowship.model.Post;
import com.chaseatucker.codefellowship.model.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

@Controller
public class ApplicationController {

  @Autowired
  ApplicationUserRepository applicationUserRepository;

  @Autowired
  PostRepository postRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/")
  public String getHome(Principal p, Model m) {
    if(p != null) {
      m.addAttribute("user", applicationUserRepository.findByUsername(p.getName()));
      List<ApplicationUser> allUsers = new Vector<>(applicationUserRepository.findAll());
      allUsers.remove(applicationUserRepository.findByUsername(p.getName()));
      m.addAttribute("allUsers", allUsers);
      return "home";
    } else {
      return "login";
    }
  }

  @GetMapping("/signup")
  public String getSignup() {
    return "signup";
  }

  @GetMapping("/login")
  public String getLogin() {
    return "login";
  }

  @GetMapping("/user/{id}")
  public String getProfile(@PathVariable long id, Principal p, Model m) {
    // add the logged in user to the model
    m.addAttribute("user", applicationUserRepository.getOne(id));
    Vector<ApplicationUser> allUsers = new Vector<>(applicationUserRepository.findAll());
    allUsers.remove(applicationUserRepository.findByUsername(p.getName()));
    m.addAttribute("allUsers", allUsers);
    return "profile";
  }

  @PostMapping("/signup")
  public RedirectView signup(String username, String password, String firstName,
                             String lastName, Date dateOfBirth, String bio) {
    if(applicationUserRepository.findByUsername(username) == null) {
      ApplicationUser newUser = new ApplicationUser(username,
          passwordEncoder.encode(password),
          firstName, lastName, dateOfBirth, bio);
      applicationUserRepository.save(newUser);

      Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return new RedirectView("/");
    } else {
      return new RedirectView("/signup?taken=true");
    }
  }

  @PostMapping("/newPost/{id}")
  public RedirectView newPost(@PathVariable String id, String body) {
    ApplicationUser user = applicationUserRepository.getOne(Long.parseLong(id));
    Post newPost = new Post(body, user);
    postRepository.save(newPost);
    return new RedirectView("/");
  }

  @PostMapping("/user/{userId}/connect/{connectionId}")
  public RedirectView makeConnection(@PathVariable long userId, @PathVariable long connectionId) {
    ApplicationUser connection = applicationUserRepository.getOne(connectionId);
    ApplicationUser user = applicationUserRepository.getOne(userId);
    user.getUsersThatIFollow().add(connection);
    applicationUserRepository.save(user);
    return new RedirectView("/");
  }

  @PostMapping("/user/{userId}/disconnect/{connectionId}")
  public RedirectView removeConnection(@PathVariable long userId, @PathVariable long connectionId) {
    ApplicationUser connection = applicationUserRepository.getOne(connectionId);
    ApplicationUser user = applicationUserRepository.getOne(userId);
    user.getUsersThatIFollow().remove(connection);
    applicationUserRepository.save(user);
    return new RedirectView("/");
  }
}
