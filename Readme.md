A) How to run the application?
1. Build the project
   
   Note: postgres should be running on 5432 port else the default contextLoad spring test fails. 
   
   To have postgres running you can run: docker-compose up phoneDirectoryDB
   
   Then run ./gradlew clean build
2. Build an image  
   Run following from main folder with Dockerfile: 
   docker build -t phone-directory .

3.  Run following from main folder with docker-compose.yml file:  
    docker-compose up  

Now you should see two containers running:  
1. postgres running on port 5432  
2. phoneDirectory ms running on port 8181
 

B) Swagger UI:

http://localhost:8181/swagger-ui/index.html#/phone-controller

C) Sample data:

Sample data has been prepopulated using script:

1. telecom-directory/sql/create_tables.sql
2. telecom-directory/sql/fill_tables.sql

D) Execute:
    
Swagger-UI url mentioned above can be used to try and test the API's. However, here are Curl commands.
   1. Get all phone numbers

    curl -X 'GET' \
        'http://localhost:8181/customer/phone-numbers' \
        -H 'accept: */*'


2. Get all phone numbers for a single customer

       curl -X 'GET' \
        'http://localhost:8181/customer/2/phone-numbers' \
        -H 'accept: */*'

3. Activate a phone number

       curl -X 'PUT' \
       'http://localhost:8181/customer/phone-number/activation' \
       -H 'accept: */*' \
       -H 'Content-Type: application/json' \
       -d '{
       "customerId": 2,
       "phoneNumber": "123-456-7890",
       "activationStatus": false
       }'

