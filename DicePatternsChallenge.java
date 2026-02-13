import java.util.*;

    public class DicePatternsChallenge {
        private static final Random rand = new Random();

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int totalScore = 0;
            int round = 1;

            System.out.println(" Dice Patterns Challenge ");

            while (true) {
                System.out.println("\n--- Round " + round + " ---");
                int[] dice = rollAll(5);

                for (int reroll = 1; reroll <= 2; reroll++) {
                    System.out.println("Current dice (unordered): " + Arrays.toString(dice));
                    System.out.print("Reroll #" + reroll + " - Enter positions to KEEP (1-5, e.g., 1 3 5) or press Enter to reroll all: ");
                    String line = sc.nextLine().trim();

                    boolean[] keep = new boolean[5];
                    if (!line.isEmpty()) {
                        String[] parts = line.split("\\s+");
                        for (String p : parts) {
                            try {
                                int idx = Integer.parseInt(p) - 1;
                                if (idx >= 0 && idx < 5) keep[idx] = true;
                            } catch (NumberFormatException ignored) {}
                        }
                    }

                    // Prevent division-by-zero style errors in gameplay: reroll only dice not kept.
                    for (int i = 0; i < 5; i++) {
                        if (!keep[i]) dice[i] = rollDie();
                    }

                    System.out.print("Reroll again? (y/n): ");
                    String ans = sc.nextLine().trim().toLowerCase();
                    if (!ans.equals("y")) break;
                }

                System.out.println("Final dice (unordered): " + Arrays.toString(dice));

                ScoreResult result = score(dice);
                totalScore += result.points;

                System.out.println("Pattern: " + result.name + " => " + result.points + " pts");
                System.out.println("Total Score: " + totalScore);

                System.out.print("Play another round? (y/n): ");
                String again = sc.nextLine().trim().toLowerCase();
                if (!again.equals("y")) break;

                round++;
            }

            System.out.println("\nGame over! Final Score: " + totalScore);
            sc.close();
        }

        private static int rollDie() {
            return rand.nextInt(6) + 1;
        }

        private static int[] rollAll(int n) {
            int[] dice = new int[n];
            for (int i = 0; i < n; i++) dice[i] = rollDie();
            return dice;
        }

        private static class ScoreResult {
            String name;
            int points;
            ScoreResult(String name, int points) { this.name = name; this.points = points; }
        }

        private static ScoreResult score(int[] dice) {
            int[] counts = new int[7]; // 1..6
            for (int d : dice) counts[d]++;

            List<Integer> freq = new ArrayList<>();
            for (int i = 1; i <= 6; i++) if (counts[i] > 0) freq.add(counts[i]);
            freq.sort(Collections.reverseOrder());

            boolean straight = isStraight(counts);

            // Check highest-scoring patterns first so the best valid pattern is awarded.
            if (freq.equals(Arrays.asList(5))) return new ScoreResult("Five of a Kind", 50);
            if (freq.equals(Arrays.asList(4, 1))) return new ScoreResult("Four of a Kind", 40);
            if (freq.equals(Arrays.asList(3, 2))) return new ScoreResult("Full House", 35);
            if (straight) return new ScoreResult("Straight", 30);
            if (freq.equals(Arrays.asList(3, 1, 1))) return new ScoreResult("Three of a Kind", 25);
            if (freq.equals(Arrays.asList(2, 2, 1))) return new ScoreResult("Two Pairs", 20);
            if (freq.equals(Arrays.asList(2, 1, 1, 1))) return new ScoreResult("One Pair", 10);

            return new ScoreResult("No Pattern", 0);
        }

        private static boolean isStraight(int[] counts) {
            boolean s1 = counts[1] == 1 && counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1;
            boolean s2 = counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1 && counts[6] == 1;
            return s1 || s2;
        }
    }

