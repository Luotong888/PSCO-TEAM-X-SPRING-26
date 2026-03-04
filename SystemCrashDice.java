import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class SystemCrashDice {

    // Global tools
    Scanner scnr = new Scanner(System.in);
    Random randGen = new Random();

    // Game State
    private int userNumDiceRemaining = 5;
    private int compNumDiceRemaining = 5;
    private Player currentPlayer;
    private SceneManager sceneManager;

    public SystemCrashDice(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    // The current "Bid" on the table
    private int activeDiceValue = 0; // The face value (e.g., "Fives")
    private int currentBidQuantity = 0; // The number of dice (e.g., "Four" Fives)

    // Lists to hold the dice
    private ArrayList<Integer> userDiceValues = new ArrayList<>();
    private ArrayList<Integer> compDiceValues = new ArrayList<>();

    // Utility for printing numbers as words
    private String[] numNames = new String[] {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX",
            "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE"};

    /**
     * Main Game Loop
     */
    public void playGame() {
        currentPlayer = new Player("ass");

        sceneManager.setPlayer(currentPlayer);
        System.out.println("Welcome back, " + currentPlayer.getName());
        System.out.println("Current System Crash Wins: " + currentPlayer.getScore("SystemCrash_Wins"));

        System.out.println("--- SYSTEM CRASH INITIALIZED ---");
        System.out.println("Rules: 1s are Bugs (Wild). 6s are Patches (Cancel Bugs).");
        System.out.println("--------------------------------");

        // Loop until someone runs out of dice (buffers overflow)
        while (compNumDiceRemaining > 0 && userNumDiceRemaining > 0) {
            startNewRound();
        }

        // Game Over
        if (userNumDiceRemaining > compNumDiceRemaining) {
            System.out.println("YOU WIN!");
            // Increment the specific game score
            currentPlayer.incrementScore("SystemCrash_Wins");
        } else {
            System.out.println("GAME OVER.");
            currentPlayer.incrementScore("SystemCrash_Losses");
        }

        GameDataManager.savePlayer(currentPlayer);
    }

    /**
     * Sets up a single round of bidding
     */
    public void startNewRound() {
        // 1. Clear Table
        userDiceValues.clear();
        compDiceValues.clear();
        activeDiceValue = 0;
        currentBidQuantity = 0;

        // 2. Roll Dice
        rollDice();

        // 3. Bidding Loop
        boolean roundActive = true;
        boolean playerTurn = true; // Player always goes first for simplicity here

        while (roundActive) {
            if (playerTurn) {
                // Returns true if the round ends (Challenge happened)
                boolean challengeHappened = playerAction();
                if (challengeHappened) {
                    roundActive = false;
                } else {
                    playerTurn = false; // Pass to computer
                }
            } else {
                // Computer Turn
                boolean challengeHappened = computerAction();
                if (challengeHappened) {
                    roundActive = false;
                } else {
                    playerTurn = true; // Pass to player
                }
            }
        }
    }

    /**
     * Generates random dice for both players
     */
    public void rollDice() {
        // Roll Player Dice
        System.out.print("\nYour Database: ");
        userDiceValues = DiceUtils.rollMany(userNumDiceRemaining);
        for (int roll : userDiceValues) {
            System.out.print("[" + roll + "] ");
        }
        System.out.println();

        // Roll Computer Dice using DiceUtils
        compDiceValues = DiceUtils.rollMany(compNumDiceRemaining);
        System.out.println("Davy Jones has " + numNames[compNumDiceRemaining] + " encrypted dice.");
        System.out.println("Total Dice on Table: " + (userNumDiceRemaining + compNumDiceRemaining));
    }

    /**
     * Handles the Player's turn logic
     * @return true if the round is over (Challenge made), false if bidding continues
     */
    public boolean playerAction() {
        System.out.println("\n--- YOUR TURN ---");
        if (currentBidQuantity > 0) {
            System.out.println("Current Bid: " + currentBidQuantity + " " + activeDiceValue + "s.");
            System.out.print("Do you want to Call Liar (0) or Raise (1)? ");
        } else {
            System.out.print("Start the bidding! Enter (1) to bid: ");
        }

        int choice = scnr.nextInt();

        if (choice == 0 && currentBidQuantity > 0) {
            // Player calls Liar!
            System.out.println("You called LIAR on Davy Jones!");
            resolveChallenge(false); // false means Player is the Challenger
            return true; // Round Over
        } else {
            // Player Bids
            boolean validBid = false;
            while (!validBid) {
                System.out.print("Enter Face Value (2-5) or 6 (Patches): ");
                int face = scnr.nextInt();

                System.out.print("Enter Quantity (Must be higher than " + currentBidQuantity + "): ");
                int quantity = scnr.nextInt();

                // Simple Logic: You must raise the quantity to keep it moving
                // (In full rules you can change face, but let's keep it linear for now)
                if (quantity > currentBidQuantity && (face >= 2 && face <= 6)) {
                    activeDiceValue = face;
                    currentBidQuantity = quantity;
                    validBid = true;
                    System.out.println("You bid: " + currentBidQuantity + " " + activeDiceValue + "s.");
                } else {
                    System.out.println("!! Invalid Bid. Quantity must increase. Face must be 2-6. !!");
                }
            }
            return false; // Round continues
        }
    }

    /**
     * 
     * @return true if the round is over
     */
    public boolean computerAction() {
        System.out.println("\n--- DAVY JONES IS THINKING ---");
        sleep(1000);

        // --- STEP 1: THE VIBE CHECK (Injecting Chaos) ---
        // A completely random roll (0 to 99)
        int chaosRoll = randGen.nextInt(100);

        // 10% Chance: The AI gets "Greedy" and raises no matter what
        if (chaosRoll < 10) {
            System.out.println("(Davy Jones looks reckless...)");
            currentBidQuantity++;
            // 50/50 chance to change the face value to something random
            if (randGen.nextBoolean()) {
                activeDiceValue = randGen.nextInt(5) + 2; // Random face 2-6
            }
            System.out.println("Davy Jones blindly raises to: " + currentBidQuantity + " " + activeDiceValue + "s.");
            return false;
        }

        // 10% Chance: The AI gets "Paranoid" and calls Liar immediately
        if (chaosRoll > 90) {
            System.out.println("(Davy Jones looks nervous...)");
            System.out.println("Davy Jones says: \"I don't trust you! LIAR!\"");
            resolveChallenge(true); // AI is challenger
            return true;
        }

        // --- STEP 2: THE MATH (If it passed the Vibe Check) ---

        // 1. Count AI's actual dice
        int myMatches = 0;
        int myBugs = 0;   // 1s
        int myPatches = 0; // 6s

        for (int die : compDiceValues) {
            if (die == activeDiceValue) myMatches++;
            if (die == 1) myBugs++;
            if (die == 6) myPatches++;
        }

        // 2. Calculate AI's Strength (System Crash Logic)
        int effectiveBugs = Math.max(0, myBugs - myPatches);
        int myStrength = myMatches + effectiveBugs;

        // 3. Estimate Player's Strength (The "Fuzzy" Math)
        // Instead of perfect 16% (1/6) probability, the AI guesses between 10% and 25%
        // This makes it overestimate or underestimate your hand randomly.
        double estimatedPlayerProb = 0.10 + (randGen.nextDouble() * 0.15);
        double expectedPlayerStrength = userNumDiceRemaining * estimatedPlayerProb;

        double totalEstimate = myStrength + expectedPlayerStrength;

        // --- STEP 3: THE DECISION ---

        // If the current bid is higher than the AI's estimate, it calls Liar.
        if (currentBidQuantity > totalEstimate) {
            System.out.println("Davy Jones calculates odds... and calls LIAR!");
            resolveChallenge(true);
            return true;
        } else {
            // AI Raises
            // It tries to pick a face value it actually has (Smart-ish move)
            if (myMatches == 0 && myBugs > 0) {
                // If it has no matches but has bugs, it might switch face value randomly
                activeDiceValue = randGen.nextInt(5) + 2;
            }

            currentBidQuantity++;
            System.out.println("Davy Jones raises the bid to: " + currentBidQuantity + " " + activeDiceValue + "s.");
            return false;
        }
    }
    /**
     * Calculates the score and determines the winner of the round.
     * Implements the specific "Patch vs Bug" math.
     * * @param computerIsChallenger true if computer called Liar, false if player called Liar
     */
    public void resolveChallenge(boolean computerIsChallenger) {
        System.out.println("\n*** SYSTEM AUDIT (SHOWING DICE) ***");

        // Show Computer Dice
        System.out.print("Davy Jones had: ");
        for (int d : compDiceValues) System.out.print("[" + d + "] ");
        System.out.println();

        // Show Player Dice
        System.out.print("You had:        ");
        for (int d : userDiceValues) System.out.print("[" + d + "] ");
        System.out.println();

        // 1. COUNT EVERYTHING
        int totalMatches = 0; // The face value (e.g. 4s)
        int totalBugs = 0;    // 1s
        int totalPatches = 0; // 6s

        // Count Player
        for (int d : userDiceValues) {
            if (d == activeDiceValue) totalMatches++;
            if (d == 1) totalBugs++;
            if (d == 6) totalPatches++;
        }
        // Count Computer
        for (int d : compDiceValues) {
            if (d == activeDiceValue) totalMatches++;
            if (d == 1) totalBugs++;
            if (d == 6) totalPatches++;
        }

        // 2. APPLY SYSTEM CRASH LOGIC
        // "Every 6 removes one 1"
        int remainingBugs = Math.max(0, totalBugs - totalPatches);
        int finalScore = totalMatches + remainingBugs;

        System.out.println("\n--- AUDIT REPORT ---");
        System.out.println("Natural " + activeDiceValue + "s found: " + totalMatches);
        System.out.println("Bugs (1s) found:    " + totalBugs);
        System.out.println("Patches (6s) found: " + totalPatches);

        if (totalPatches > 0) {
            System.out.println(">> " + Math.min(totalBugs, totalPatches) + " Bugs were patched out!");
        }

        System.out.println("TOTAL COUNT: " + finalScore);
        System.out.println("BID WAS:     " + currentBidQuantity);

        // 3. DETERMINE WINNER
        // Logic: Did the Bidder lie?
        // If finalScore >= currentBidQuantity, the Bidder told the truth (Safe).
        // If finalScore < currentBidQuantity, the Bidder lied (Busted).

        boolean bidderLied = (finalScore < currentBidQuantity);

        if (computerIsChallenger) {
            // Computer challenged Player
            if (bidderLied) {
                System.out.println("RESULT: You Lied! Davy Jones caught you.");
                userNumDiceRemaining--;
            } else {
                System.out.println("RESULT: You told the truth! Davy Jones challenged incorrectly.");
                compNumDiceRemaining--;
            }
        } else {
            // Player challenged Computer
            if (bidderLied) {
                System.out.println("RESULT: Davy Jones Lied! You caught him.");
                compNumDiceRemaining--;
            } else {
                System.out.println("RESULT: Davy Jones told the truth! You challenged incorrectly.");
                userNumDiceRemaining--;
            }
        }
    }

    // Helper to simulate thinking time
    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch(Exception e) {}
    }

    // Main method to run the program
    public static void main(String[] args) {
        SystemCrashDice game = new SystemCrashDice(new SceneManager());
        game.playGame();
    }
}