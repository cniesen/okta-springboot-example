Spring Boot Application with Okta
=================================
This project documents how to use Okta with Spring Boot.  Especially where the authorization roles should be based on Okta groups that are assigned to the user __and__ application.

This project compares various implementations.

| git branch                                                                             |  method        | Okta Group as roles                           | Implementation based on           |
|----------------------------------------------------------------------------------------|----------------|-----------------------------------------------|-----------------------------------|
| [oauth2-client](https://github.com/cniesen/okta-springboot-example/tree/oauth2-client) | OpenID Connect | Yes                                           | spring-boot-starter-oauth2-client |
| [okta-starter](https://github.com/cniesen/okta-springboot-example/tree/okta-starter)   | OpenID Connect | Only globally with Authorization Server claim | okta-spring-boot-starter          |

Constructive feedback is welcome.  Feel free to use Githubs Issues. Thanks! 

----

Okta OpenId Connect SpringBoot application with ROLE authorization
==================================================================
This implementation uses the spring-boot-starter-oauth2-client dependency which requires a little more configuration but enables the usage of Okta groups that are assigned to the 
application to be used for role based authorization.  No Authorization Server changes are needed and thus other applications are not affected.

See other branches for different implementations.


Okta Application Setup
----------------------
1. Switch to "Classic UI" since not all features are exposed in the "Developer Console"

2. Create the role groups
   1. Navigate to "Directory" > "Groups"
   2. Click "Add Group"
   3. Create a group named "OIDC Sample App Admin"
   4. Click on newly crated group  "OIDC Sample App Admin"
   5. Click "Manage People"
   6. Search for and add the desired users to the group
   4. Create another group named "OIDC Sample App Privileged User" and assign members
   
3. Create the OIDC App
    1. Navigate to "Applications -> Applications" 
    2. Click "Add Application" and then "Create New App"
    3. Choose platform "Web", sign on method "OpenID Connect" and click "Create"
    4. Create the new application with the following properties:
        * Application Name: something meaningful to you
        * Login redirect URIs: "http://localhost:8080/login/oauth2/code/okta"
    5. Note "Client ID" and "Client secret" for application.yaml
    6. On the "Sign On" tab of the application and edit the token credential:
        * Set the groups claim type to "Filter"
        * Set the groups claim filter to "groups" "Starts with" "./"
    7. On the the Assignments tab and assign the "OIDC Sample App Admin" and "OIDC Sample App Privileged User" groups
    
Running the App
---------------
1. Set the okta.host, okta.client-id, and okta.client-secret properties in the application.yml file 
   (see Okta Application Setup)
   
2. Set the authentication role to okta group mapping under auth.roleGroupMapping in the application.yml file
```
auth:
  roleGroupMapping:
    PRIVILEGED: OIDC Sample App Privileged User
    ADMIN: OIDC Sample App Admin
```
The above sample creates the "ROLE_PRIVILEGED" role for everyone in the "OIDC Sample App Privileged User" Okta group and the "ROLE_ADMIN" role for everyone in the "OIDC Sample App Admin" Okta group.

3. Run the Gradle task "bootRun":  `gradlew bootRun`

4. Navigate to: http://localhost:8080/ 
