package com.chaseatucker.codefellowship;

import com.chaseatucker.codefellowship.model.ApplicationUser;
import com.chaseatucker.codefellowship.model.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;

@Controller
public class ApplicationController {

  @Autowired
  ApplicationUserRepository applicationUserRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/")
  public String getHome(Principal p, Model m) {
    if(p != null) {
      m.addAttribute("user", applicationUserRepository.findByUsername(p.getName()));
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

  @PostMapping("/signup")
  public RedirectView signup(String username,
                             String password,
                             String firstName,
                             String lastName,
                             Date dateOfBirth,
                             String bio) {
    ApplicationUser newUser = new ApplicationUser(username,
        passwordEncoder.encode(password),
        firstName, lastName, dateOfBirth, bio);
    applicationUserRepository.save(newUser);
    return new RedirectView("/");
  }

//  @PostMapping("/user/{id}")
//  public Re
}
