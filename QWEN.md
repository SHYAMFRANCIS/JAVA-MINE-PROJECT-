# Qwen Code Assistant Context

## Project Overview

This is a Java Swing application implementing a classic Rock, Paper, Scissors game with a graphical user interface. The project is built using Maven as the build tool and follows object-oriented programming principles.

### Project Details
- **Name**: project_sk
- **Group ID**: com.shyam
- **Artifact ID**: project_sk
- **Version**: 1.0-SNAPSHOT
- **Technology Stack**: Java 8, Swing GUI framework
- **Build Tool**: Apache Maven

### Main Components

#### Core Classes
- `RPSGame.java`: The main class implementing the Rock, Paper, Scissors game with a Swing GUI.
- Uses an enum `Choice` with values: ROCK, PAPER, SCISSORS
- Implements game logic in the `Choice.determineWinner()` method
- Score tracking for both player and computer
- Professional GUI with buttons, labels and color-coded results

#### Key Features
- Graphical interface with Rock, Paper, Scissors buttons
- Visual feedback for player and computer choices
- Score tracking with persistent display
- Color-coded game results (green for win, red for loss, blue for draw)
- Look and feel management (attempts to use Nimbus theme)

### Project Structure
```
E:\java_projectSK\
├── .vscode\
│   └── settings.json
└── project_sk\
    ├── pom.xml
    ├── src\
    │   ├── main\
    │   │   └── java\
    │   │       └── com\
    │   │           └── shyam\
    │   │               └── RPSGame.java
    │   └── test\
    │       └── java\
    └── target\
        ├── project_sk-1.0-SNAPSHOT.jar
        ├── classes\
        │   └── com\
        ├── generated-sources\
        │   └── annotations\
        ├── generated-test-sources\
        ├── maven-archiver\
        ├── maven-status\
        ├── surefire-reports\
        └── test-classes\
```

### Building and Running

#### Prerequisites
- Java 8 or higher
- Apache Maven

#### Build Commands
- To compile: `mvn compile`
- To build JAR: `mvn package`
- To run tests: `mvn test`
- To compile and package: `mvn clean install`

#### Running the Application
The application can be run directly from the main method in `RPSGame.java`:
```java
java -cp target/project_sk-1.0-SNAPSHOT.jar com.shyam.RPSGame
```

Or by running `mvn exec:java -Dexec.mainClass="com.shyam.RPSGame"` if using the exec plugin.

### Development Conventions
- Uses modern Java practices including enums for type safety
- Implements clean separation of concerns with GUI and game logic
- Uses anonymous inner classes for event handling
- Follows Java naming conventions and standard formatting
- Includes comprehensive JavaDoc comments

### Dependencies
- JUnit 4.11 (for testing, scope: test)

### Architecture Notes
- The game logic is encapsulated in the `Choice` enum
- GUI components are created programmatically (not using GUI builders)
- Event-driven architecture using ActionListener for button interactions
- Uses BorderLayout, FlowLayout, and GridLayout for different sections
- Implements proper Swing threading with SwingUtilities.invokeLater()

This is a complete, self-contained game that demonstrates good Java and Swing practices with a professional user interface.