# bbpos-api-jwt

4 APIs

/api/test/all (unit test: AllAPITests)

/api/test/user (unit test: UserAPITests)

/api/auth/login (unit test: LoginAPITests)

/api/auth/signup (unit test: SignupAPITests)

Tested in Eclipse and Maven (command prompt)

--------------------------------------------
Execute unit tests (Maven)

mvn test

--------------------------------------------
Execute compiled jar

In target folder
java -jar api-jwt-0.0.1-SNAPSHOT.jar

--------------------------------------------

postman collection: bbpos-api-jwt.postman_collection.json

For API "/api/test/user" - Please re-generate the token by API "/api/auth/login"

--------------------------------------------

Future improvement

- implement quota in all public APIs
