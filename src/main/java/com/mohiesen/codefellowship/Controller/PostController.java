package com.mohiesen.codefellowship.Controller;

import com.mohiesen.codefellowship.Model.ApplicationUser;
import com.mohiesen.codefellowship.Model.Post;
import com.mohiesen.codefellowship.infrastructure.ApplicationUserRepository;
import com.mohiesen.codefellowship.infrastructure.PostRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/addpost")
    public RedirectView addPost(Principal p , String body , Model userPost) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date timeStamp = new Date();
        ApplicationUser ownerUser = applicationUserRepository.findApplicationUserByUsername(p.getName());
        Post post = new Post(body, timeStamp, ownerUser);
        postRepository.save(post);
        userPost.addAttribute("posts" , ownerUser.getPosts());
        return new RedirectView("/profile");
    }

    @GetMapping("/feed")
    public String getFeed(Principal p , Model m) {
        ApplicationUser loggedUser = applicationUserRepository.findApplicationUserByUsername(p.getName());
        List<ApplicationUser> followedUsers = loggedUser.getUsers();
        List<Post> allUsersPosts = new ArrayList<>();
        for (int i = 0; i < followedUsers.size(); i++) {
            allUsersPosts.addAll(followedUsers.get(i).getPosts());
        }
        System.out.println(allUsersPosts);
        m.addAttribute("posts" , allUsersPosts);
        return "feed";
    }
}
