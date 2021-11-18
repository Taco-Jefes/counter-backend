# Taco Jefes

An application to track the tacos consumed by our cohort 1 teams.  The application is setup with four teams -- Team TACo, 
Hot Ham Sandwich (HHS), Team TLC, and the instructor team.  Each team has a button to click each time a taco is eaten. 
The grand total of tacos consumed by all groups is displayed. 

Another portion of the app tracks comments made by those consuming tacos.  The comments are listed newest first.


### RDS Decision

Explored using EC2 and compared with RDS.  RDS seemed to have better documentation and support on AWS.

### S3 to transfer back-end jar file  

Rather than cloning repo for the backend and performing a build, we found a way to transfer the jar file to S3 and then
to the EC2 instance.  


### Original goals:

- public subnet for front-end, where our UI code would run.
- private subnet for the database and back-end api.
- user traffic going through an internet gateway.
- load balancers for both front-end and back-end.


![Original Arch](https://user-images.githubusercontent.com/82593277/142494108-cda7695c-e9b8-465c-aff2-67ccb92704de.jpg)

### Actual result:

- public subnet for the front-end.  
- private subnet for back-end api and database.
- automated script for back-end api installation, with hard-coded credentials.
- automated script for ui installation.
- internet gateway created.  

- no load balancers
- UI fetches are timing out. 

![actual arch](https://user-images.githubusercontent.com/82593277/142500994-0e92e4c2-b24d-434a-bf22-9ec1dd4e5eea.jpg)
### More Time

- automate nat gateway
- use AWS secrets manager or .env file for credentials.
- figure out what is causing the time-outs.
- add load balancers

### What We Learned

- learned how to navigate Terraform documentation to automate infrastructure
- learned how to setup an RDS instance
- re-learned how to start a java application from the command line
- learned a lot of additional CLI commands
