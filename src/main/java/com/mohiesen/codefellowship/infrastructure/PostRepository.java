package com.mohiesen.codefellowship.infrastructure;

import com.mohiesen.codefellowship.Model.ApplicationUser;
import com.mohiesen.codefellowship.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post , Long> {
Post findAllByPostOwner(ApplicationUser user);
}
