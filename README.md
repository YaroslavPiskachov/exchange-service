## Do following steps to run:
1. unzip exchange-service.zip file and open it as Maven project in Intellij
2. open terminal in project folder
3. build jar with command “mvn -Dmaven.test.skip=true package”
4. run application with command “docker-compose up --build”
5. for testing you can use my postman collection https://www.getpostman.com/collections/c2bcf2cf4b1401b9b7b6

## Security:

Application is secured by session stateless basic authentication with 2 default users created in system with credentials below:

- username: test_1
- password: test_1

- username: test_2
- password: test_2
