
package com.shyam;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the choices in the Rock, Paper, Scissors game.
 */
enum Choice {
    ROCK,
    PAPER,
    SCISSORS;

    /**
     * Determines the winner between two choices.
     * @param playerChoice The player's choice.
     * @param computerChoice The computer's choice.
     * @return A string indicating the result: "Draw!", "Win!", or "Lose!".
     */
    public static String determineWinner(Choice playerChoice, Choice computerChoice) {
        if (playerChoice == computerChoice) {
            return "Draw!";
        } else if ((playerChoice == ROCK && computerChoice == SCISSORS) ||
                   (playerChoice == PAPER && computerChoice == ROCK) ||
                   (playerChoice == SCISSORS && computerChoice == PAPER)) {
            return "Win!";
        } else {
            return "Lose!";
        }
    }
}

/**
 * Represents the result of a single round in the game.
 */
class RoundResult {
    private Choice playerChoice;
    private Choice computerChoice;
    private String result;
    private long timestamp;
    
    public RoundResult(Choice playerChoice, Choice computerChoice, String result) {
        this.playerChoice = playerChoice;
        this.computerChoice = computerChoice;
        this.result = result;
        this.timestamp = System.currentTimeMillis();
    }
    
    public Choice getPlayerChoice() {
        return playerChoice;
    }
    
    public Choice getComputerChoice() {
        return computerChoice;
    }
    
    public String getResult() {
        return result;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return String.format(
            "[%s] You: %s, Computer: %s, Result: %s",
            new java.util.Date(timestamp),
            playerChoice,
            computerChoice,
            result
        );
    }
}

/**
 * Represents the difficulty levels for the game.
 */
enum Difficulty {
    EASY, MEDIUM, HARD
}

/**
 * A professional implementation of the Rock, Paper, Scissors game with a Swing GUI.
 * Adheres to OOP principles, uses enums for choices, and provides a clean user experience.
 */
class RPSGame extends JFrame {

    private JLabel playerChoiceLabel;
    private JLabel computerChoiceLabel;
    private JLabel resultLabel;
    private JLabel scoreLabel;

    private int playerScore = 0;
    private int computerScore = 0;
    private int maxRounds = 3;  // Default to 3 rounds
    private int roundsPlayed = 0;
    private Difficulty difficulty = Difficulty.EASY;  // Default difficulty
    private Choice previousComputerChoice = null;
    private int[] playerChoiceFrequency = new int[3]; // Track Rock(0), Paper(1), Scissors(2) choices
    private GameStats gameStats;
    private int totalGames = 0;
    private int totalWins = 0;
    private int totalLosses = 0;
    private int totalDraws = 0;
    private String playerName = "Player";
    private List<RoundResult> gameHistory;
    
    /**
     * Constructs the RPSGame frame, setting up the GUI components and layout.
     */
    public RPSGame() {
        super("Rock, Paper, Scissors");
        setupGamePreferences();
        initLookAndFeel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null); // Center the window

        // Create a custom background panel
        JPanel backgroundPanel = new GradientBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout(10, 10)); // Add gaps between regions
        backgroundPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding for the content pane

        // Add title/prompt
        JLabel titleLabel = new JLabel("Make your choice:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Add choice buttons
        backgroundPanel.add(createChoiceButtonPanel(), BorderLayout.CENTER);

        // Add display panel for choices, result, and score
        backgroundPanel.add(createDisplayPanel(), BorderLayout.SOUTH);

        setContentPane(backgroundPanel);

        // Initialize game stats
        gameStats = new GameStats();
        gameHistory = new ArrayList<>();
        
        updateScoreLabel();
    }
    
    /**
     * Sets up game preferences via dialog boxes.
     */
    private void setupGamePreferences() {
        // Prompt user for player name
        playerName = (String) JOptionPane.showInputDialog(
            this,
            "Enter your name:",
            "Player Name",
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            "Player"
        );
        
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player"; // default
        }
        
        // Prompt user to select number of rounds
        String[] roundOptions = {"3", "5", "10"};
        String roundsInput = (String) JOptionPane.showInputDialog(
            this,
            "Select number of rounds:",
            "Rounds Selection",
            JOptionPane.QUESTION_MESSAGE,
            null,
            roundOptions,
            roundOptions[0]
        );
        
        if (roundsInput != null) {
            maxRounds = Integer.parseInt(roundsInput);
        } else {
            maxRounds = 3; // default
        }
        
        // Prompt user to select difficulty level
        String[] difficultyOptions = {"Easy", "Medium", "Hard"};
        String difficultyInput = (String) JOptionPane.showInputDialog(
            this,
            "Select difficulty level:",
            "Difficulty Selection",
            JOptionPane.QUESTION_MESSAGE,
            null,
            difficultyOptions,
            difficultyOptions[0]
        );
        
        if (difficultyInput != null) {
            switch (difficultyInput) {
                case "Easy": difficulty = Difficulty.EASY; break;
                case "Medium": difficulty = Difficulty.MEDIUM; break;
                case "Hard": difficulty = Difficulty.HARD; break;
            }
        } else {
            difficulty = Difficulty.EASY; // default
        }
    }

    /**
     * Initializes the Look and Feel for the application.
     * Attempts to set Nimbus L&F, falls back to system default.
     */
    private void initLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Handle any other exceptions
                System.err.println("Error setting Look and Feel: " + ex.getMessage());
            }
        }
    }

    /**
     * Creates the panel containing the Rock, Paper, and Scissors buttons.
     * @return A JPanel with the choice buttons.
     */
    private JPanel createChoiceButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); // Centered with gaps
        panel.setOpaque(false); // Make it transparent to show the gradient background
        panel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Padding

        JButton rockButton = createIconButton("🪨", Choice.ROCK.name());
        rockButton.addActionListener(e -> {
            playButtonSound();
            new GameActionListener().actionPerformed(e);
        });
        panel.add(rockButton);

        JButton paperButton = createIconButton("📄", Choice.PAPER.name());
        paperButton.addActionListener(e -> {
            playButtonSound();
            new GameActionListener().actionPerformed(e);
        });
        panel.add(paperButton);

        JButton scissorsButton = createIconButton("✂️", Choice.SCISSORS.name());
        scissorsButton.addActionListener(e -> {
            playButtonSound();
            new GameActionListener().actionPerformed(e);
        });
        panel.add(scissorsButton);

        return panel;
    }
    
    /**
     * Creates an icon button with the specified icon and action command.
     * @param icon The icon to use on the button
     * @param actionCommand The action command to assign to the button
     * @return JButton with the specified icon
     */
    private JButton createIconButton(String icon, String actionCommand) {
        JButton button = new JButton(icon);
        button.setActionCommand(actionCommand);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24)); // Use font that supports emojis
        button.setPreferredSize(new Dimension(80, 60));
        return button;
    }

    /**
     * Creates the panel to display the player's choice, computer's choice, result, and score.
     * @return A JPanel with display labels.
     */
    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5)); // 6 rows, 1 column, with gaps
        panel.setOpaque(false); // Make it transparent to show the gradient background
        panel.setBorder(new EmptyBorder(10, 0, 0, 0)); // Padding

        playerChoiceLabel = new JLabel("Your Choice: ", SwingConstants.CENTER);
        playerChoiceLabel.setForeground(Color.WHITE);
        computerChoiceLabel = new JLabel("Computer's Choice: ", SwingConstants.CENTER);
        computerChoiceLabel.setForeground(Color.WHITE);
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(Color.WHITE);
        scoreLabel = new JLabel("Score: ", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setForeground(Color.WHITE);
        
        // Create a button for viewing the leaderboard
        JButton leaderboardButton = new JButton("View Leaderboard");
        leaderboardButton.addActionListener(e -> showLeaderboard());
        
        // Create a button for viewing the history
        JButton historyButton = new JButton("View History");
        historyButton.addActionListener(e -> showHistory());
        
        panel.add(playerChoiceLabel);
        panel.add(computerChoiceLabel);
        panel.add(resultLabel);
        panel.add(scoreLabel);
        panel.add(leaderboardButton);
        panel.add(historyButton);

        return panel;
    }

    /**
     * Updates the score label with the current player and computer scores.
     */
    private void updateScoreLabel() {
        scoreLabel.setText(String.format("Score: Player %d - Computer %d", playerScore, computerScore));
    }
    
    /**
     * Shows the leaderboard in a dialog.
     */
    private void showLeaderboard() {
        StringBuilder sb = new StringBuilder();
        sb.append("Top 5 Players:\n\n");
        
        for (int i = 0; i < Math.min(5, gameStats.getLeaderboard().size()); i++) {
            GameStats.PlayerStats player = gameStats.getLeaderboard().get(i);
            sb.append(String.format(
                "%d. %s - Wins: %d, Losses: %d, Draws: %d, Win Rate: %.2f%%\n",
                i + 1,
                player.getName(),
                player.getWins(),
                player.getLosses(),
                player.getDraws(),
                player.getWinRate()
            ));
        }
        
        JOptionPane.showMessageDialog(
            this,
            sb.toString(),
            "Leaderboard",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Shows the game history in a dialog.
     */
    private void showHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("Last 10 Rounds:\n\n");
        
        // Show last 10 rounds or all rounds if less than 10
        int start = Math.max(0, gameHistory.size() - 10);
        for (int i = start; i < gameHistory.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, gameHistory.get(i).toString()));
        }
        
        // If no rounds have been played yet
        if (gameHistory.isEmpty()) {
            sb.append("No rounds have been played yet.\n");
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setCaretPosition(0); // Scroll to top
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        
        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Game History",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Generates a choice for the computer based on the selected difficulty.
     * @return The computer's selected Choice.
     */
    private Choice getComputerChoice() {
        Choice[] choices = Choice.values();
        Random random = new Random();
        
        switch (difficulty) {
            case EASY:
                // Random choices (current behavior)
                return choices[random.nextInt(choices.length)];
                
            case MEDIUM:
                // Avoid repeating the previous computer choice
                Choice newChoice;
                do {
                    newChoice = choices[random.nextInt(choices.length)];
                } while (previousComputerChoice != null && newChoice == previousComputerChoice);
                
                previousComputerChoice = newChoice;
                return newChoice;
                
            case HARD:
                // Predict player's most frequent choice and counter it
                int mostFrequentPlayerChoiceIndex = 0;
                int maxFrequency = 0;
                
                // Find the player's most frequent choice
                for (int i = 0; i < playerChoiceFrequency.length; i++) {
                    if (playerChoiceFrequency[i] > maxFrequency) {
                        maxFrequency = playerChoiceFrequency[i];
                        mostFrequentPlayerChoiceIndex = i;
                    }
                }
                
                // Counter the most frequent player choice
                Choice mostFrequentPlayerChoice = choices[mostFrequentPlayerChoiceIndex];
                Choice counterChoice;
                
                if (mostFrequentPlayerChoice == Choice.ROCK) {
                    counterChoice = Choice.PAPER; // Paper beats Rock
                } else if (mostFrequentPlayerChoice == Choice.PAPER) {
                    counterChoice = Choice.SCISSORS; // Scissors beats Paper
                } else {
                    counterChoice = Choice.ROCK; // Rock beats Scissors
                }
                
                previousComputerChoice = counterChoice;
                return counterChoice;
                
            default:
                return choices[random.nextInt(choices.length)];
        }
    }

    /**
     * ActionListener for the game buttons. Handles player's choice and updates game state.
     */
    private class GameActionListener implements ActionListener {
        private Timer roundTimer;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // Cancel any existing timer for the current round
            if (roundTimer != null && roundTimer.isRunning()) {
                roundTimer.stop();
            }
            
            Choice playerChoice = Choice.valueOf(e.getActionCommand());
            
            // Update player choice frequency for HARD difficulty
            if (playerChoice == Choice.ROCK) {
                playerChoiceFrequency[0]++;
            } else if (playerChoice == Choice.PAPER) {
                playerChoiceFrequency[1]++;
            } else if (playerChoice == Choice.SCISSORS) {
                playerChoiceFrequency[2]++;
            }
            
            Choice computerChoice = getComputerChoice();

            playerChoiceLabel.setText("Your Choice: " + playerChoice.name());
            computerChoiceLabel.setText("Computer's Choice: " + computerChoice.name());

            String result = Choice.determineWinner(playerChoice, computerChoice);
            resultLabel.setText("Result: " + result);

            // Update score and result label color based on outcome
            switch (result) {
                case "Win!":
                    playerScore++;
                    resultLabel.setForeground(new Color(0, 150, 0)); // Green for win
                    playResultSound("win");
                    break;
                case "Lose!":
                    computerScore++;
                    resultLabel.setForeground(Color.RED); // Red for lose
                    playResultSound("lose");
                    break;
                case "Draw!":
                    resultLabel.setForeground(Color.BLUE); // Blue for draw
                    playResultSound("draw");
                    break;
            }
            
            // Add round result to history
            gameHistory.add(new RoundResult(playerChoice, computerChoice, result));
            
            updateScoreLabel();
            
            roundsPlayed++;
            
            // Check if max rounds have been reached
            if (playerScore > maxRounds/2 || computerScore > maxRounds/2 || roundsPlayed >= maxRounds) {
                // Update total stats
                totalGames++;
                if (playerScore > computerScore) {
                    totalWins++;
                } else if (computerScore > playerScore) {
                    totalLosses++;
                } else {
                    totalDraws++;
                }
                
                // Update the leaderboard
                gameStats.updatePlayerStats(playerName, totalGames, totalWins, totalLosses, totalDraws);
                
                // Game over - announce winner
                String gameResult;
                if (playerScore > computerScore) {
                    gameResult = "Congratulations! You won the game!";
                } else if (computerScore > playerScore) {
                    gameResult = "Game over! Computer won the game!";
                } else {
                    gameResult = "Game ended in a draw!";
                }
                
                // Play game over sound
                playResultSound("gameover");
                
                JOptionPane.showMessageDialog(RPSGame.this, gameResult, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                
                // Reset scores and rounds for new game
                playerScore = 0;
                computerScore = 0;
                roundsPlayed = 0;
                resetChoiceFrequencies();
                updateScoreLabel();
            }
            
            // Start a new timer for the next round
            startNewRoundTimer();
        }
        
        /**
         * Starts a new timer for the round, with 5 seconds limit.
         */
        private void startNewRoundTimer() {
            // Create and start a 5-second timer for the next round
            roundTimer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Timeout reached - auto-generate a random player choice
                    Random random = new Random();
                    Choice[] choices = Choice.values();
                    Choice randomChoice = choices[random.nextInt(choices.length)];
                    
                    // Display timeout message
                    playerChoiceLabel.setText("Your Choice: " + randomChoice.name() + " (Timeout)");
                    Choice computerChoice = getComputerChoice();
                    computerChoiceLabel.setText("Computer's Choice: " + computerChoice.name());
                    
                    String result = Choice.determineWinner(randomChoice, computerChoice);
                    resultLabel.setText("Result: Timeout! " + (result.equals("Win!") ? "You win!" : "You lose!"));
                    
                    // Update score and result label color based on outcome
                    if (result.equals("Win!")) {
                        playerScore++;
                        resultLabel.setForeground(new Color(0, 150, 0)); // Green for win
                        playResultSound("win");
                    } else if (result.equals("Lose!")) {
                        computerScore++;
                        resultLabel.setForeground(Color.RED); // Red for lose
                        playResultSound("lose");
                    } else {
                        resultLabel.setForeground(Color.BLUE); // Blue for draw
                        playResultSound("draw");
                    }
                    
                    // Add round result to history
                    gameHistory.add(new RoundResult(randomChoice, computerChoice, result.contains("win") ? "Win!" : result.contains("lose") ? "Lose!" : "Draw!"));
                    
                    updateScoreLabel();
                    
                    roundsPlayed++;
                    
                    // Check if max rounds have been reached
                    if (playerScore > maxRounds/2 || computerScore > maxRounds/2 || roundsPlayed >= maxRounds) {
                        // Update total stats
                        totalGames++;
                        if (playerScore > computerScore) {
                            totalWins++;
                        } else if (computerScore > playerScore) {
                            totalLosses++;
                        } else {
                            totalDraws++;
                        }
                        
                        // Update the leaderboard
                        gameStats.updatePlayerStats(playerName, totalGames, totalWins, totalLosses, totalDraws);
                        
                        // Game over - announce winner
                        String gameResult;
                        if (playerScore > computerScore) {
                            gameResult = "Congratulations! You won the game!";
                        } else if (computerScore > playerScore) {
                            gameResult = "Game over! Computer won the game!";
                        } else {
                            gameResult = "Game ended in a draw!";
                        }
                        
                        // Play game over sound
                        playResultSound("gameover");
                        
                        JOptionPane.showMessageDialog(RPSGame.this, gameResult, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Reset scores and rounds for new game
                        playerScore = 0;
                        computerScore = 0;
                        roundsPlayed = 0;
                        resetChoiceFrequencies();
                        updateScoreLabel();
                    }
                    
                    // Stop and dispose of the timer
                    ((Timer)e.getSource()).stop();
                }
            });
            roundTimer.setRepeats(false); // Execute only once
            roundTimer.start();
        }
        
        /**
         * Resets the choice frequencies after a game ends.
         */
        private void resetChoiceFrequencies() {
            for (int i = 0; i < playerChoiceFrequency.length; i++) {
                playerChoiceFrequency[i] = 0;
            }
            previousComputerChoice = null;
            
            // Clear the game history after a full game is completed
            gameHistory.clear();
        }
    }
    
    /**
     * Plays a sound effect using MIDI.
     * @param type Type of sound: "win", "lose", "draw", "gameover", or "button"
     */
    private void playResultSound(String type) {
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            MidiChannel[] channels = synthesizer.getChannels();
            MidiChannel channel = channels[0];
            
            // Set instrument (0 = Acoustic Grand Piano)
            channel.programChange(0);
            
            int note;
            int velocity = 80; // Volume (0-127)
            
            switch (type) {
                case "win":
                    note = 60; // Middle C
                    channel.noteOn(note, velocity);
                    Thread.sleep(200);
                    channel.noteOff(note);
                    break;
                case "lose":
                    note = 57; // A below middle C
                    channel.noteOn(note, velocity);
                    Thread.sleep(200);
                    channel.noteOff(note);
                    break;
                case "draw":
                    // Play two notes together
                    channel.noteOn(60, velocity);
                    channel.noteOn(64, velocity); // E
                    Thread.sleep(200);
                    channel.noteOff(60);
                    channel.noteOff(64);
                    break;
                case "gameover":
                    // Play a short melody
                    for (int i = 0; i < 3; i++) {
                        channel.noteOn(60 + i, velocity);
                        Thread.sleep(100);
                        channel.noteOff(60 + i);
                    }
                    break;
                case "button":
                    note = 65; // E above middle C
                    channel.noteOn(note, velocity);
                    Thread.sleep(50);
                    channel.noteOff(note);
                    break;
            }
            
            synthesizer.close();
        } catch (Exception ex) {
            // If sound system is not available, ignore the error
            System.out.println("Could not play sound: " + ex.getMessage());
        }
    }
    
    /**
     * Plays a button click sound.
     */
    private void playButtonSound() {
        playResultSound("button");
    }
    
    /**
     * A custom JPanel that draws a gradient background
     */
    private class GradientBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            
            // Create a gradient paint from top-left to bottom-right
            int width = getWidth();
            int height = getHeight();
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(100, 150, 255),  // Light blue
                width, height, new Color(50, 100, 200)  // Darker blue
            );
            
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);
            g2d.dispose();
        }
    }

    /**
     * Main method to create and run the Rock, Paper, Scissors game.
     * Ensures the GUI is launched on the Event Dispatch Thread.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RPSGame().setVisible(true);
        });
    }
}
