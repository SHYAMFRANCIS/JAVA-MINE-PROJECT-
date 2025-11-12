package com.shyam;

import java.io.*;
import java.util.*;

/**
 * Manages game statistics and leaderboard data, including file I/O operations.
 */
public class GameStats {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";
    private List<PlayerStats> leaderboard;
    
    /**
     * Represents the statistics for a single player.
     */
    public static class PlayerStats {
        private String name;
        private int totalGames;
        private int wins;
        private int losses;
        private int draws;
        private long timestamp;
        
        public PlayerStats(String name, int totalGames, int wins, int losses, int draws) {
            this.name = name;
                this.totalGames = totalGames;
            this.wins = wins;
            this.losses = losses;
            this.draws = draws;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getName() {
            return name;
        }
        
        public int getTotalGames() {
            return totalGames;
        }
        
        public int getWins() {
            return wins;
        }
        
        public int getLosses() {
            return losses;
        }
        
        public int getDraws() {
            return draws;
        }
        
        public double getWinRate() {
            if (totalGames == 0) {
                return 0.0;
            }
            return (double) wins / totalGames * 100;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        @Override
        public String toString() {
            return String.format("%s|%d|%d|%d|%d|%d", name, totalGames, wins, losses, draws, timestamp);
        }
    }
    
    /**
     * Constructor for GameStats - loads existing leaderboard from file.
     */
    public GameStats() {
        leaderboard = new ArrayList<>();
        loadLeaderboard();
    }
    
    /**
     * Loads the leaderboard from the file.
     */
    private void loadLeaderboard() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String name = parts[0];
                    int totalGames = Integer.parseInt(parts[1]);
                    int wins = Integer.parseInt(parts[2]);
                    int losses = Integer.parseInt(parts[3]);
                    int draws = Integer.parseInt(parts[4]);
                    long timestamp = Long.parseLong(parts[5]);
                    
                    PlayerStats stats = new PlayerStats(name, totalGames, wins, losses, draws);
                    stats.timestamp = timestamp;
                    leaderboard.add(stats);
                }
            }
        } catch (IOException | NumberFormatException e) {
            // If file doesn't exist or is corrupted, start with an empty leaderboard
            leaderboard = new ArrayList<>();
        }
    }
    
    /**
     * Saves the current leaderboard to the file.
     */
    public void saveLeaderboard() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LEADERBOARD_FILE))) {
            for (PlayerStats stats : leaderboard) {
                writer.println(stats.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
        }
    }
    
    /**
     * Adds a new player's stats to the leaderboard and saves to file.
     */
    public void addPlayerStats(String name, int totalGames, int wins, int losses, int draws) {
        PlayerStats newStats = new PlayerStats(name, totalGames, wins, losses, draws);
        leaderboard.add(newStats);
        
        // Sort leaderboard by wins (descending) then by win rate
        leaderboard.sort((a, b) -> {
            if (b.getWins() != a.getWins()) {
                return Integer.compare(b.getWins(), a.getWins());
            } else {
                return Double.compare(b.getWinRate(), a.getWinRate());
            }
        });
        
        // Keep only top 10 if there are more
        if (leaderboard.size() > 10) {
            leaderboard = leaderboard.subList(0, 10);
        }
        
        saveLeaderboard();
    }
    
    /**
     * Gets the top N players from the leaderboard.
     */
    public List<PlayerStats> getTopPlayers(int n) {
        List<PlayerStats> top = new ArrayList<>();
        int count = Math.min(n, leaderboard.size());
        for (int i = 0; i < count; i++) {
            top.add(leaderboard.get(i));
        }
        return top;
    }
    
    /**
     * Gets the entire leaderboard.
     */
    public List<PlayerStats> getLeaderboard() {
        return new ArrayList<>(leaderboard);
    }
    
    /**
     * Updates a player's stats if they already exist in the leaderboard, otherwise adds them.
     */
    public void updatePlayerStats(String name, int totalGames, int wins, int losses, int draws) {
        PlayerStats existingStats = null;
        for (PlayerStats stats : leaderboard) {
            if (stats.getName().equals(name)) {
                existingStats = stats;
                break;
            }
        }
        
        if (existingStats != null) {
            // Update existing stats
            existingStats.totalGames = totalGames;
            existingStats.wins = wins;
            existingStats.losses = losses;
            existingStats.draws = draws;
            existingStats.timestamp = System.currentTimeMillis();
        } else {
            // Add new stats
            addPlayerStats(name, totalGames, wins, losses, draws);
        }
        
        // Re-sort the leaderboard
        leaderboard.sort((a, b) -> {
            if (b.getWins() != a.getWins()) {
                return Integer.compare(b.getWins(), a.getWins());
            } else {
                return Double.compare(b.getWinRate(), a.getWinRate());
            }
        });
        
        saveLeaderboard();
    }
}