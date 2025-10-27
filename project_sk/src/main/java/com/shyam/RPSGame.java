package com.shyam;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * A professional implementation of the Rock, Paper, Scissors game with a Swing GUI.
 * Adheres to OOP principles, uses enums for choices, and provides a clean user experience.
 */
public class RPSGame extends JFrame {

    private JLabel playerChoiceLabel;
    private JLabel computerChoiceLabel;
    private JLabel resultLabel;
    private JLabel scoreLabel;

    private int playerScore = 0;
    private int computerScore = 0;

    /**
     * Constructs the RPSGame frame, setting up the GUI components and layout.
     */
    public RPSGame() {
        super("Rock, Paper, Scissors");
        initLookAndFeel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null); // Center the window

        // Set up main layout
        setLayout(new BorderLayout(10, 10)); // Add gaps between regions
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding for the content pane

        // Add title/prompt
        JLabel titleLabel = new JLabel("Make your choice:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Add choice buttons
        add(createChoiceButtonPanel(), BorderLayout.CENTER);

        // Add display panel for choices, result, and score
        add(createDisplayPanel(), BorderLayout.SOUTH);

        updateScoreLabel();
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
        panel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Padding

        JButton rockButton = new JButton("Rock");
        rockButton.setActionCommand(Choice.ROCK.name());
        rockButton.addActionListener(new GameActionListener());
        panel.add(rockButton);

        JButton paperButton = new JButton("Paper");
        paperButton.setActionCommand(Choice.PAPER.name());
        paperButton.addActionListener(new GameActionListener());
        panel.add(paperButton);

        JButton scissorsButton = new JButton("Scissors");
        scissorsButton.setActionCommand(Choice.SCISSORS.name());
        scissorsButton.addActionListener(new GameActionListener());
        panel.add(scissorsButton);

        return panel;
    }

    /**
     * Creates the panel to display the player's choice, computer's choice, result, and score.
     * @return A JPanel with display labels.
     */
    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5)); // 4 rows, 1 column, with gaps
        panel.setBorder(new EmptyBorder(10, 0, 0, 0)); // Padding

        playerChoiceLabel = new JLabel("Your Choice: ", SwingConstants.CENTER);
        computerChoiceLabel = new JLabel("Computer's Choice: ", SwingConstants.CENTER);
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel = new JLabel("Score: ", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(playerChoiceLabel);
        panel.add(computerChoiceLabel);
        panel.add(resultLabel);
        panel.add(scoreLabel);

        return panel;
    }

    /**
     * Updates the score label with the current player and computer scores.
     */
    private void updateScoreLabel() {
        scoreLabel.setText(String.format("Score: Player %d - Computer %d", playerScore, computerScore));
    }

    /**
     * Generates a random choice for the computer.
     * @return The computer's randomly selected Choice.
     */
    private Choice getComputerChoice() {
        Choice[] choices = Choice.values();
        Random random = new Random();
        return choices[random.nextInt(choices.length)];
    }

    /**
     * ActionListener for the game buttons. Handles player's choice and updates game state.
     */
    private class GameActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Choice playerChoice = Choice.valueOf(e.getActionCommand());
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
                    break;
                case "Lose!":
                    computerScore++;
                    resultLabel.setForeground(Color.RED); // Red for lose
                    break;
                case "Draw!":
                    resultLabel.setForeground(Color.BLUE); // Blue for draw
                    break;
            }
            updateScoreLabel();
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
