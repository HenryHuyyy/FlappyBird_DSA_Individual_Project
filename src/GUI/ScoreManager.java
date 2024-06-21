package GUI;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {
    private Map<String, Integer> playerScores;

    public ScoreManager() {
        playerScores = new HashMap<>();
        loadScoresFromFile();
    }

    // Method to update the player's score in the HashMap
    public void updatePlayerScore(String player, int score) {
        playerScores.put(player, score);
        saveScoresToFile();
    }

    // Method to get the player's score from the HashMap
    public int getPlayerScore(String player) {
        return playerScores.getOrDefault(player, 0);
    }

    // Method to persist scores to a file
    private void saveScoresToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("scores.dat"))) {
            oos.writeObject(playerScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load scores from a file
    private void loadScoresFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("scores.dat"))) {
            playerScores = (Map<String, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
