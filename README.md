Okta OpenId Connect SpringBoot Application with okta-spring-boot-starter
========================================================================
This implementation uses the okta-spring-boot-starter (https://github.com/okta/okta-spring-boot) dependency.

See other branches for different implementations.

Running the App
---------------
1. Set the okta.oauth2 issuer, client-id, client-secret properties in the application.yml file 
   (see Okta Application Setup)
2. Run the Gradle task "bootRun":  `gradlew bootRun`
3. Navigate to:
    * http://localhost:8080/ for public access
    * http://localhost:8080/user for "OIDC Sample App User" group member access
    * http://localhost:8080/admin for "OIDC Sample App Admin" group member access

Okta Developer Account Setup
----------------------------
1. Create the role groups
   1. Navigate to "Users" > "Groups"
   2. Click "Add Group"
   3. Create a group named "OIDC Sample App Admin"
   4. Click on newly crated group  "OIDC Sample App Admin"
   5. Click "Add Members"
   6. Search for and add the desired users to the grpup
   4. Create another group named "OIDC Sample App User" and assign members
   
2. Add groups claim to the id token of the authorization server
   1. Navigate to "API" > "Authorization Servers"
   2. On the "Authorization Servers" tab edit the default entry
   3. Select the "Claims" tab and click "Add Claim"
   4. Create the new claim with the following properties:
      * Name: "groups"
      * Include in Token Type: "ID Token" and "Always"
      * Value type: "Groups"
      * Filter: "Matches regex" and the value ".*"
      * Disable claim: not checked
      * Include in: "Any scope"

5. Create the OIDC App
    1. Navigate to "Applications" 
    2. Click "Add Application"
    3. Choose "Web" and click "NEXT"
    4. Create the new application with the following properties:
        * Name: something meaningful to you
        * Base URIs: none
        * Login redirect URIs: "http://localhost:8080/login/oauth2/code/okta"
        * Group assignments: "OIDC Sample App Admin" and "OIDC Sample App User"
        * Grand type allowed: "Authorization Code"
    5. Note "Client ID" and "Client secret" for application.yaml