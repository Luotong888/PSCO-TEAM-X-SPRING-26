import java.util.Random;
import java.util.Scanner;

public class Game3 {

    public static void start(Scanner sc) {

        System.out.println("====== Game 3 : Dice Codebreaker ======");

        Random random = new Random();

        int length = 4;
        int maxAttempts = 10;

        int[] secret = new int[length];

        for (int i = 0; i < length; i++) {
            secret[i] = random.nextInt(6) + 1;
        }

        int attempts = 0;
        boolean win = false;

        while (attempts < maxAttempts && !win) {

            attempts++;
            System.out.println("Attempt " + attempts + "/" + maxAttempts);

            int[] guess = new int[length];

            for (int i = 0; i < length; i++) {
                System.out.print("Enter number " + (i + 1) + " (1-6): ");
                guess[i] = sc.nextInt();
            }

            int hits = 0;
            int blows = 0;

            boolean[] secretUsed = new boolean[length];
            boolean[] guessUsed = new boolean[length];

            for (int i = 0; i < length; i++) {
                if (guess[i] == secret[i]) {
                    hits++;
                    secretUsed[i] = true;
                    guessUsed[i] = true;
                }
            }

            for (int i = 0; i < length; i++) {
                if (!guessUsed[i]) {
                    for (int j = 0; j < length; j++) {
                        if (!secretUsed[j] && guess[i] == secret[j]) {
                            blows++;
                            secretUsed[j] = true;
                            break;
                        }
                    }
                }
            }

            if (hits == length) {
                win = true;
                System.out.println("You win!");
            } else {
                System.out.println("Hits: " + hits);
                System.out.println("Blows: " + blows);
            }

            System.out.println();
        }

        if (!win) {
            System.out.println("You lose!");
            System.out.print("Secret code was: ");
            for (int i = 0; i < length; i++) {
                System.out.print(secret[i] + " ");
            }
            System.out.println();
        }

        System.out.println("Game Over.");
    }
}