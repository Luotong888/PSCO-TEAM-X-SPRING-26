import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class DiceUtils {

    // One single Random object for the whole game (Better performance)
    private static final Random rand = new Random();

    /**
     * Rolls a single die (1-6)
     */
    public static int roll() {
        return rand.nextInt(6) + 1;
    }

    /**
     * Rolls a specific number of dice and returns them as a sorted list
     * ( Liar's Dice and Dice Patterns)
     */
    public static ArrayList<Integer> rollMany(int count) {
        ArrayList<Integer> dice = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dice.add(roll());
        }
        return dice;
    }

    /**
     * Rolls a specific number of dice as an Array (Primitive int[])
     * (Codebreaker or Grid) maybeeee...
     */
    public static int[] rollArray(int count) {
        int[] dice = new int[count];
        for (int i = 0; i < count; i++) {
            dice[i] = roll();
        }
        return dice;
    }
}