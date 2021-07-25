package com.mohiesen.codefellowship.Controller;

import com.mohiesen.codefellowship.Model.ApplicationUser;
import com.mohiesen.codefellowship.infrastructure.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder encoder;


    @GetMapping(value = "/signup")
    public String getSignUp() {
        return "signup";
    }

    @GetMapping("/login")
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
        applicationUserRepository.save(newUser);
        return new RedirectView("/");
    }

    @GetMapping(value = "users/{id}")
    public String getUserData(@PathVariable Long id) {
        ApplicationUser userFound = applicationUserRepository.getById(id);
        System.out.println(userFound.getBio());
        return "/users";
    }
}
