# Rock, Paper, Scissors Game

A professional implementation of the classic Rock, Paper, Scissors game built with Java Swing. This application features a clean graphical user interface with game logic that follows object-oriented programming principles.

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [Design Patterns](#design-patterns)
- [Code Structure](#code-structure)
- [Dependencies](#dependencies)
- [How to Run](#how-to-run)
- [How to Build](#how-to-build)
- [Project Structure](#project-structure)
- [Implementation Details](#implementation-details)

## Features

- Interactive graphical user interface built with Java Swing
- Three gameplay buttons: Rock, Paper, and Scissors
- Real-time display of player and computer choices
- Automatic game result calculation (Win, Lose, or Draw)
- Score tracking for both player and computer
- Color-coded results (Green for win, Red for loss, Blue for draw)
- Professional look and feel using Nimbus theme
- Clean, responsive design with proper padding and spacing

## Architecture

The application follows a clear separation of concerns with multiple components working together:

1. **GUI Components**: Swing elements for user interaction and display
2. **Game Logic**: Enum-based logic for determining game outcomes
3. **Event Handling**: ActionListener for processing button clicks
4. **State Management**: Score tracking and game state updates
5. **Presentation**: Visual feedback for players

## Design Patterns

### Enum Pattern
- The `Choice` enum represents all possible game moves (ROCK, PAPER, SCISSORS)
- Encapsulates the game logic in `determineWinner()` method
- Provides type safety and prevents invalid game states

### Model-View-Controller (MVC) Pattern Elements
- **Model**: The `Choice` enum and game logic
- **View**: The Swing GUI components (buttons, labels)
- **Controller**: The `GameActionListener` and button event handlers

## Code Structure

The project consists of a single main class with nested elements:

### RPSGame.java
- **Main Class**: `RPSGame` extends `JFrame`
- **Enum**: `Choice` with ROCK, PAPER, SCISSORS constants
- **Nested Class**: `GameActionListener` implementing ActionListener
- **Instance Variables**: GUI components and score tracking

### Choice Enum
- **Constants**: ROCK, PAPER, SCISSORS
- **Method**: `determineWinner()` implements game logic
- **Logic**: Compares player and computer choices to determine outcome

### GameActionListener Class
- **Implementation**: ActionListener interface
- **Responsibility**: Handles button click events
- **Function**: Processes player choice, generates computer choice, updates GUI

## Dependencies

This project depends on standard Java libraries and the following Maven dependency:

- **JUnit**: Version 4.11 (for testing, scope: test)

There are no external dependencies beyond standard Java Swing and utility classes.

## How to Run

### Prerequisites
- Java Runtime Environment (JRE) 8 or higher
- Or Java Development Kit (JDK) 8 or higher

### Running the JAR
1. Navigate to the `target` directory containing the built JAR file
2. Execute the following command:
   ```bash
   java -jar project_sk-1.0-SNAPSHOT.jar
   ```

### Running with Maven
If you have Maven installed and want to run without building first:
```bash
cd project_sk
mvn exec:java -Dexec.mainClass="com.shyam.RPSGame"
```

### Running from IDE
1. Open the project in your Java IDE (IntelliJ, Eclipse, VS Code, etc.)
2. Navigate to `RPSGame.java`
3. Run the `main` method in the `RPSGame` class

## How to Build

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Maven (recommended) or build tool support

### Building with Maven
1. Open a terminal/command prompt
2. Navigate to the project directory (`project_sk`)
3. Execute the following command:
   ```bash
   mvn clean package
   ```
4. The compiled JAR file will be available in the `target` directory as `project_sk-1.0-SNAPSHOT.jar`

### Alternative Build Commands
- To compile without building JAR: `mvn compile`
- To run tests: `mvn test`
- To compile and run: `mvn compile exec:java -Dexec.mainClass="com.shyam.RPSGame"`
- To install to local Maven repository: `mvn clean install`

### Building without Maven
1. Compile all Java files:
   ```bash
   javac -d bin src/main/java/com/shyam/RPSGame.java
   ```
2. Package into JAR:
   ```bash
   jar cfe project_sk.jar com.shyam.RPSGame -C bin .
   ```
3. Run the application:
   ```bash
   java -jar project_sk.jar
   ```

## Project Structure

```
project_sk/
├── pom.xml                    # Maven configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── shyam/
│   │               └── RPSGame.java   # Main application code
│   └── test/
│       └── java/              # Test directory (empty in this project)
└── target/                    # Generated build files
    ├── project_sk-1.0-SNAPSHOT.jar
    └── classes/
        └── com/
            └── shyam/
                └── RPSGame.class
```

## Implementation Details

### Enum-Based Game Logic
The `Choice` enum contains the core game logic:
- `determineWinner()` method implements the classic rules:
  - Rock beats Scissors
  - Paper beats Rock
  - Scissors beats Paper
  - Matching choices result in a draw
- This approach provides type safety and makes the code more maintainable

### GUI Construction
The interface is built programmatically using Swing:
- `BorderLayout` for main window organization
- `FlowLayout` for button panel
- `GridLayout` for result display panel
- Proper padding and margins using `EmptyBorder`
- Professional fonts and styling

### Event Handling
- The `GameActionListener` class handles all button clicks
- When a button is clicked:
  - Player's choice is extracted from the action command
  - Computer's choice is randomly generated
  - Game result is calculated using the enum method
  - GUI components are updated with new information
  - Scores are updated based on the result

### Score Tracking
- Two integer variables track player and computer scores
- Scores are updated in real-time after each round
- Display automatically refreshes with new score values
- Scores persist across multiple rounds during the session

### Look and Feel
- Attempts to set the Nimbus Look and Feel for modern appearance
- Falls back to cross-platform look if Nimbus is unavailable
- Provides a professional appearance across different operating systems

### Threading Safety
- GUI is initialized and updated on the Event Dispatch Thread (EDT)
- Uses `SwingUtilities.invokeLater()` to ensure thread safety
- Prevents potential UI rendering issues

### Error Handling
- The look and feel initialization has try-catch blocks
- Graceful fallback to default look and feel if Nimbus is unavailable
- Proper exception handling for UI initialization

## Game Flow

1. **Initialization**: The game window appears with three choice buttons
2. **Player Input**: Player clicks one of the three buttons (Rock, Paper, or Scissors)
3. **Computer Calculation**: The computer makes a random choice
4. **Result Calculation**: The game logic determines the winner using the enum method
5. **Display Update**: The interface updates to show both choices and the result
6. **Score Update**: Scores are updated based on the game outcome
7. **Repeat**: Players can continue making choices for additional rounds

## Customization Options

The game can be easily customized by modifying:

- **Visual Elements**: Colors, fonts, and spacing can be modified in the GUI setup
- **Game Rules**: Logic in the `determineWinner()` method can be altered for variants
- **UI Components**: New features could be added by extending the display panels
- **Theme**: Different look and feels can be applied in the `initLookAndFeel()` method

## Maintenance Notes

- The code is well-documented with JavaDoc comments
- Clear separation of concerns makes it easy to modify specific aspects
- The enum-based design makes it easy to extend game logic
- Proper error handling prevents crashes during runtime