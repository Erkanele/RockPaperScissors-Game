# RockPaperScissorsGame

## Description
The classic Rock, Paper Scissors game using Spring Boot and Swagger.
I've used Swagger UI for sending and receiving request because of the simplicity it offers but also learning
purpose.


## Installation
- Build war file using 'mvn clean package' in terminal
- Configuration is available in resources/application.properties file where it's possible to change port,
  debug output or Swagger UI path for the game for example.
## Usage

- Run application using java -jar target/erik-0.0.1-SNAPSHOT.jar, with mvn spring-boot:run or the GUI in some
  development environment.
- Go to http://localhost:8080/swagger-ui/index.html#
- Use the Swagger-UI for desired action in the game.


1. Create a new game: Extend /api/games and send a POST-request to with a name in the Request body {"name": "MyName"}.
   If a 200 response is given in the Swagger GUI. A UUID will be generated which is the key for making a move or
   join an existing game.

2. Join an existing game: Extend /api/games/{id}/join column, fill in the generated game key in the "id" field and
   the name in the Request body {"name": "MyName"} and execute the POST-Request.

3. Make a move: Extend /api/games/{id}/move column, fill in the generated game key in the "id" field and
   the name in the Request body {"name": "MyName", "move" "ROCK"} and execute the POST-Request. The available moves are
   ROCK, PAPER, SCISSORS in capital letters.

4. View the score: Extend /api/games/{id} column, fill in the generated game key in the "id" field and
   execute the GET request. Then the game status will be showed.

## Tests
- Run tests from IDE or by command line by running 'mvnw tests'


1. TestingWebbApplicationTests: Testing if application context can start.
2. SmokeTest: Simple smoke tests to verify functionality of GameController Class
