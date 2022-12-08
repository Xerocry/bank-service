# Test task for Senior Software Developer position at Energized Global Services
It was asked to develop ATM emulator consiting of 2 microservices: 
* Bank Service
* ATM Service 

**Mandatory functionality**
1. User should be capable to set own preferred authentication method (example - PIN, fingerprint etc.),
2. Have a simplistic credit card validation mechanism when card number is provided ("Block" card after three unsuccessful attempts - should be present),
3. Based on the authentication method which user chose as "preferred" provide "session" initialization mechanism,
4. After authentication is successful initialized User should be capable to do standard list of operations with ATM: Cash deposit, Cash withdrawal, Check balance,

All of that was developed, containerized and were covered with some tests.

## Technologies
* Java
* Maven
* Docker
* Git
* PostgreSQL
* **Libraries**
  * Springboot 2.7
  * Swagger
  * Resilience4j
  * lombok

## How to run solved problems
There are two options:

* Run the project in the docker container (recommended way since you don't need to worry about dependencies)
* Build the project on PC through maven

### Run the project in the docker container
1. Clone the project to your PC.
2. Go to the root directory of the project.
3. Run the command `docker-compose up -d --build`. The command will build the images based on the Dockerfile instructions and start it alongside with DB

### Run the project on the PC
1. Clone the project to your PC.
2. Go to the root directory of the particular service.
3. Run `mvn spring-boot:run` to get the service running.

### Documentation
After running the app the best entry point is Swagger and the url are

* http://localhost:8100/swagger-ui/index.html - bank-service
* http://localhost:8300/swagger-ui/index.html - atm-service

## Problems design notes
JWT Authentication method was chosen because it's easier to implement rather than OAuth and others.

Not all corner-cases are covered with proper HTTP response and exceptions due to lack of time.

I implemented enough functionality to solve given problem. There are many functionality that can be added in the future.

## Author
Andrey Raskin - goldmask000@gmail.com; telegram/linkedin: @xerocry