# codefellowship

This web application considers making a community of users and allow them to add posts and follow
other users in order to see their posts and explore their own profile and posts  in additin to other users on the application.

## To run the app 

- From the CLI 
    - $ ./gradlew bootRun

- You can also run it from intellij directly.

## Localhost 
- On default port 8080.

## Application.properties file

- Edit the credintials of the database with yours to start properly.


## API 
#### GET requests
- / to show the home page
- /signup shows the signup page
- /login show the login page
- /profile shows the profile of the logged user
- /users shows all the users not followed and not the logged user
- /users/{id} showed a specific user public profile using his id
- /feed shows posts from followed users

#### POST requests
- /signup Gets the user credientials and signs up his data to the data base and authorize the user.
- /followUser/{username} allows the user to follow another user.
- /addpost allows the user to add a post to his profile which will be shown on the following users.