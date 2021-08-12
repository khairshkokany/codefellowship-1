package com.mohiesen.codefellowship.Controller;

import com.mohiesen.codefellowship.Model.ApplicationUser;
import com.mohiesen.codefellowship.Model.Post;
import com.mohiesen.codefellowship.infrastructure.ApplicationUserRepository;
import com.mohiesen.codefellowship.infrastructure.PostRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    BCryptPasswordEncoder encoder;
    public class RootController {
        @GetMapping("/")
        public String homePage() {
            return "index";
        }
    }

    @GetMapping(value = "/signup")
    public String getSignUp() {
        return "signup";
    }

    @GetMapping(value = "/login")
    public String getLogin() {
        return "login";
    }


    @PostMapping(value = "/signup")
    public RedirectView attemptSignUp(@RequestParam String username ,
                                      @RequestParam String password ,
                                      @RequestParam String firstname ,
                                      @RequestParam String lastname ,
                                      @RequestParam String dateofbirth ,
                                      @RequestParam String bio) {
        ApplicationUser newUser = new ApplicationUser(username , encoder.encode(password), firstname , lastname , dateofbirth , bio);
        newUser = applicationUserRepository.save(newUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(newUser , null , new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return new RedirectView("/");
    }

    @GetMapping(value = "/users/{id}")
    public String getUserData(@PathVariable Long id , Model chosenUser) {
        ApplicationUser userFound = applicationUserRepository.getById(id);
        chosenUser.addAttribute("user" , userFound);
//        List<Post> userPosts = postRepository.findAllByPostOwner(userFound);
        return "/publicuser";
    }

    @GetMapping(value = "/profile")
    public String getProfile(Principal currentUser , Model user) {
        user.addAttribute("user" , applicationUserRepository.findApplicationUserByUsername(currentUser.getName()));
        user.addAttribute("username" , currentUser.getName());
        return "profile";
    }

    @GetMapping(value = "/users")
    public String getAllUsers(Model users , Principal p){
        List<ApplicationUser> allUsers = applicationUserRepository.findAll();
        users.addAttribute("users" , allUsers);
        System.out.println(allUsers);
        users.addAttribute("loggeduser" , applicationUserRepository.findApplicationUserByUsername(p.getName()));
        return "users";
    }

    @PostMapping("/followUser/{username}")
    public RedirectView followUser(Principal p,@PathVariable String username){
        ApplicationUser currentUser = applicationUserRepository.findApplicationUserByUsername(p.getName());
        ApplicationUser userWantedToFollow = applicationUserRepository.findApplicationUserByUsername(username);
        currentUser.addFollowToUser(userWantedToFollow);
        applicationUserRepository.save(currentUser);
        return new RedirectView("/users/" + applicationUserRepository.findApplicationUserByUsername(p.getName()).getId());
    }
}

