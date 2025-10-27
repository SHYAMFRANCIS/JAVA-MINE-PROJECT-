# JAVA-MINE-PROJECT

Welcome to the JAVA-MINE-PROJECT repository! This repository contains various Java projects and implementations. Currently, the main project is a professional Rock, Paper, Scissors game built with Java Swing.

## Current Project: Rock, Paper, Scissors Game

A professional implementation of the classic Rock, Paper, Scissors game built with Java Swing. This application features a clean graphical user interface with game logic that follows object-oriented programming principles.

### Features

- Interactive graphical user interface built with Java Swing
- Three gameplay buttons: Rock, Paper, and Scissors
- Real-time display of player and computer choices
- Automatic game result calculation (Win, Lose, or Draw)
- Score tracking for both player and computer
- Color-coded results (Green for win, Red for loss, Blue for draw)
- Professional look and feel using Nimbus theme
- Clean, responsive design with proper padding and spacing

### How to Run

#### Prerequisites
- Java Runtime Environment (JRE) 8 or higher
- Or Java Development Kit (JDK) 8 or higher

#### Running with Maven
1. Navigate to the `project_sk` directory
2. Execute the following command:
   ```bash
   cd project_sk
   mvn clean package
   java -jar target/project_sk-1.0-SNAPSHOT.jar
   ```

#### Running from IDE
1. Open the project in your Java IDE (IntelliJ, Eclipse, VS Code, etc.)
2. Navigate to `project_sk/src/main/java/com/shyam/RPSGame.java`
3. Run the `main` method in the `RPSGame` class

## Repository Structure

```
JAVA-MINE-PROJECT/
├── README.md                    # This file (repository overview)
├── .gitignore                 # Git ignore rules
├── project_sk/                # Main Java project directory
│   ├── README.md              # Detailed project documentation
│   ├── pom.xml                # Maven configuration
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── shyam/
│   │   │               └── RPSGame.java   # Main application code
│   │   └── test/
│   │       └── java/
│   └── target/                # Generated build files (ignored by git)
```

## Technologies Used

- **Java 8+**: Core programming language
- **Swing**: Graphical user interface framework
- **Maven**: Build automation and dependency management
- **Git**: Version control system

## Contributing

This is a personal learning repository for Java projects. Feel free to explore the code and use it for educational purposes.

## License

This project is licensed under the terms specified in the LICENSE file.