package highsuit;

import java.io.*;
import java.util.*;

public class HighSuitGame {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String HIGH_SCORE_FILE = "highscores.txt";
    private static final List<RoundRecord> replayHistory = new ArrayList<>();

    // Replay game
    static class RoundRecord {
        String name;
        int round;
        List<Card> initialHand;
        String bonusSuit;
        List<Card> finalHand;
        int score;
    }

    public static void main(String[] args) {
        System.out.println(" Welcome to HighSuit ");

        System.out.print("Enter number of players (1 or 2): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        String[] playerNames = new String[numPlayers];
        int[] totalScores = new int[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            playerNames[i] = scanner.nextLine();
        }

        // max 3 rounds
        System.out.print("Enter number of rounds (1-3): ");
        int rounds = scanner.nextInt();

        for (int r = 1; r <= rounds; r++) {
            System.out.println("\n=== ROUND " + r + " ===");
            Deck deck = new Deck();

            for (int p = 0; p < numPlayers; p++) {
                totalScores[p] += playTurn(playerNames[p], r, deck);
            }
        }

        displayFinalLeaderboard(playerNames, totalScores);


        for (int i = 0; i < numPlayers; i++) {
            saveHighScore(playerNames[i], totalScores[i] / rounds);
        }

        handlePostGameMenu();
    }

    private static int playTurn(String name, int roundNum, Deck deck) {
        RoundRecord record = new RoundRecord();
        record.name = name;
        record.round = roundNum;

        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 5; i++) hand.add(deck.draw());
        record.initialHand = new ArrayList<>(hand);

        System.out.println("\n" + name + "'s hand: " + hand);

        //Display current  best possible score
        int currentMax = getSuitTotals(hand).values().stream().max(Integer::compare).orElse(0);
        System.out.println("Current max suit score: " + currentMax);

        System.out.print("Nominate bonus suit (Hearts, Diamonds, Clubs, Spades): ");
        String bonus = scanner.next();
        record.bonusSuit = bonus;

        // card swapping
        System.out.print("How many cards will you replace (0-4)? ");
        int count = scanner.nextInt();
        for (int i = 0; i < count; i++) {
            System.out.print("Card(s) to replace (0-4): ");
            int idx = scanner.nextInt();
            hand.set(idx, deck.draw());
        }

        System.out.println("Final hand: " + hand);
        record.finalHand = new ArrayList<>(hand);

        int score = calculateScore(hand, bonus);
        record.score = score;
        replayHistory.add(record);

        System.out.println("Turn Score: " + score);
        return score;
    }

    private static int calculateScore(List<Card> hand, String bonus) {
        Map<String, Integer> totals = getSuitTotals(hand);
        int max = 0;
        String bestSuit = "";
        for (var entry : totals.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                bestSuit = entry.getKey();
            }
        }

        return bestSuit.equalsIgnoreCase(bonus) ? max + 5 : max;
    }

    private static Map<String, Integer> getSuitTotals(List<Card> hand) {
        Map<String, Integer> totals = new HashMap<>();
        for (Card c : hand) {
            totals.put(c.getSuit(), totals.getOrDefault(c.getSuit(), 0) + c.getValue());
        }
        return totals;
    }

    private static void displayFinalLeaderboard(String[] names, int[] scores) {
        System.out.println("\n--- FINAL RESULTS (Descending) ---");

        if (names.length == 2 && scores[1] > scores[0]) {
            System.out.println("Winner: " + names[1] + " (" + scores[1] + ")");
            System.out.println("Runner-up: " + names[0] + " (" + scores[0] + ")");
        } else {
            System.out.println("Winner: " + names[0] + " (" + scores[0] + ")");
            if (names.length == 2) System.out.println("Runner-up: " + names[1] + " (" + scores[1] + ")");
        }
    }

    private static void saveHighScore(String name, int avgScore) {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) scores.add(line);
        } catch (IOException ignored) {}

        scores.add(name + "," + avgScore);
        scores.sort((a, b) -> Integer.compare(Integer.parseInt(b.split(",")[1]), Integer.parseInt(a.split(",")[1])));

        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
            for (int i = 0; i < Math.min(5, scores.size()); i++) {
                writer.println(scores.get(i));
            }
        } catch (IOException ignored) {}
    }

    private static void handlePostGameMenu() {
        while (true) {
            System.out.println("\nOptions: (1) View Replay (2) View High Scores (3) Exit");
            int choice = scanner.nextInt();
            if (choice == 1) {
                for (RoundRecord r : replayHistory) {
                    System.out.println("\nRound " + r.round + " Player: " + r.name);
                    System.out.println("Initial Hand: " + r.initialHand + " | Bonus Suit: " + r.bonusSuit);
                    System.out.println("Final Hand: " + r.finalHand + " | Score: " + r.score);
                }
            } else if (choice == 2) {
                System.out.println("\n--- TOP 5 LIFETIME SCORES ---");
                try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
                    String line;
                    while ((line = reader.readLine()) != null) System.out.println(line.replace(",", ": "));
                } catch (IOException e) { System.out.println("No high scores yet."); }
            } else break;
        }
    }
}