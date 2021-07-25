package com.mohiesen.codefellowship.infrastructure;

import com.mohiesen.codefellowship.Model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser ,Long> {
    ApplicationUser findApplicationUserByUserName(String userName);
}
