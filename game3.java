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
