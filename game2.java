import java.util.Random;
import java.util.Scanner;

 class Game2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Grid grid = new Grid();
        Dice dice = new Dice();
        Check check = new Check();

        System.out.println("=== Dice Grid Puzzle (3x3) ===");
        System.out.println("Roll the dice and place it in the grid.");
        System.out.println("Scoring:");
        System.out.println("Three of a Kind = 15");
        System.out.println("Straight = 12");
        System.out.println("Pair = 8");
        System.out.println("All Different = 5\n");

        while (!grid.isFull()) {
            grid.print();

            int roll = dice.roll();
            System.out.println("\nYou rolled: " + roll);

            int row, col;

            while (true) {
                System.out.print("Row (1-3): ");
                row = sc.nextInt() - 1;

                System.out.print("Column (1-3): ");
                col = sc.nextInt() - 1;

                if (!grid.isInside(row, col)) {
                    System.out.println("❌ Invalid position. Try again.\n");
                } else if (!grid.isEmpty(row, col)) {
                    System.out.println("❌ Cell already used. Try again.\n");
                } else {
                    break;
                }
            }

            grid.place(row, col, roll);
            System.out.println("------------------------------");
        }

        System.out.println("\n=== FINAL GRID ===");
        grid.print();

        int totalScore = check.totalScore(grid);
        System.out.println("\n🎯 TOTAL SCORE = " + totalScore);

        sc.close();
    }
}

/* ================= Dice ================= */
class Dice {
    private Random random = new Random();

    public int roll() {
        return random.nextInt(6) + 1;
    }
}

/* ================= Grid ================= */
class Grid {
    private int[][] grid = new int[3][3];

    public boolean isInside(int r, int c) {
        return r >= 0 && r < 3 && c >= 0 && c < 3;
    }

    public boolean isEmpty(int r, int c) {
        return grid[r][c] == 0;
    }

    public void place(int r, int c, int value) {
        grid[r][c] = value;
    }

    public int get(int r, int c) {
        return grid[r][c];
    }

    public boolean isFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (grid[r][c] == 0) return false;
            }
        }
        return true;
    }

    public void print() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (grid[r][c] == 0)
                    System.out.print(" . ");
                else
                    System.out.print(" " + grid[r][c] + " ");
            }
            System.out.println();
        }
    }
}

/* ================= Check ================= */
class Check {

    public int totalScore(Grid grid) {
        int total = 0;

        // Rows
        for (int r = 0; r < 3; r++) {
            total += scoreLine(
                    grid.get(r, 0),
                    grid.get(r, 1),
                    grid.get(r, 2)
            );
        }

        // Columns
        for (int c = 0; c < 3; c++) {
            total += scoreLine(
                    grid.get(0, c),
                    grid.get(1, c),
                    grid.get(2, c)
            );
        }

        return total;
    }

    private int scoreLine(int a, int b, int c) {

        // Three of a kind
        if (a == b && b == c) return 15;

        // Straight
        if (isStraight(a, b, c)) return 12;

        // Pair
        if (isPair(a, b, c)) return 8;

        // All different
        return 5;
    }

    private boolean isPair(int a, int b, int c) {
        return (a == b && a != c) ||
                (a == c && a != b) ||
                (b == c && b != a);
    }

    private boolean isStraight(int a, int b, int c) {
        int x = a, y = b, z = c;

        if (x > y) { int t = x; x = y; y = t; }
        if (y > z) { int t = y; y = z; z = t; }
        if (x > y) { int t = x; x = y; y = t; }

        return y == x + 1 && z == y + 1;
    }
}
