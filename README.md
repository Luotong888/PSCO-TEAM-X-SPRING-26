## *PSCO-TEAM-X-SPRING-26: Games Description*

### *Game 1: Dice Patterns Challenge*

A game of strategy and probability where the player aims to achieve specific dice combinations. The player rolls five dice and has the option to re-roll selected dice up to two times to improve their hand. Points are awarded based on the final pattern achieved:

* *Five of a Kind:* 50 pts | *Four of a Kind:* 40 pts
* *Full House (3+2):* 35 pts | *Straight (1-5/2-6):* 30 pts
* *Three of a Kind:* 25 pts | *Two Pairs:* 20 pts | *One Pair:* 10 pts

### *Game 2: Dice Grid Puzzle*

A spatial placement game where the player fills a $3 \times 3$ grid one die at a time. After rolling a single die, the player chooses an empty cell. Once all 9 cells are filled, the game evaluates every row and column for the following patterns:

* *Three of a Kind:* 15 pts | *Straight:* 12 pts
* *Pair:* 8 pts | *All Different:* 5 pts
* Total Score = Sum of all 3 Row scores + 3 Column scores.

### *Game 3: Dice Codebreaker*

A logic-based deduction game where the player competes against the computer to uncover a hidden 4-dice sequence. After each guess, the player receives feedback:

* *Correct Position:* The number of dice that are the right value in the right spot.
* *Wrong Position:* The number of dice that are the right value but in the incorrect spot.
* The game ends when the code is cracked or the maximum number of attempts is reached.

### *Game 4: System Crash (Liar's Dice Variant)*

A high-stakes bluffing game where players bid on the total quantity of a specific die face across all hands. This version features unique "Corrupted Data" mechanics:

* *1s (Bugs):* Act as *Wilds*, counting toward any bid value.
* *6s (Patches):* Act as *Anti-Wilds*, where each 6 rolled cancels out one "Bug" (1) from the total count.
* *The Challenge:* Players must decide whether to raise the bid or call "System Crash" (Liar) on the previous player.
* *The AI:* Features a "Vibe Check" logic that uses probabilistic estimation and bluff factors to simulate a human-like opponent.

## Game 1 Guide:
This code is a solid implementation of the **Dice Patterns Challenge**. It uses a frequency-based scoring system (counting how many times each number appears) to determine the player's points.

---

### **1. `main(String[] args)**`

This is the **game engine**. It controls the flow of the game, including:

* **The Round Loop:** Keeps the game running until the player chooses to stop.
* **The Reroll Logic:** Handles the two-reroll limit. It takes user input (e.g., "1 3 5") to decide which dice to "lock" and which to roll again.
* **Score Integration:** Calls the scoring methods and updates the `totalScore`.

### **2. `rollDie()**`

A simple utility method that generates a single random integer between **1 and 6**.

* **Logic:** `rand.nextInt(6)` produces 0-5, so it adds `+ 1` to match a standard die face.

### **3. `rollAll(int n)**`

A helper method used at the start of a round.

* **Function:** It creates an array of size `n` (5 in this case) and fills every slot with a fresh die roll by calling `rollDie()` in a loop.

### **4. `score(int[] dice)**`

This is the **logic core** of the game. It determines what pattern the player has achieved.

* **Frequency Counting:** It creates a `counts` array where the index represents the die face (1-6) and the value represents how many times that face appeared.
* **Frequency List (`freq`):** It extracts the counts, ignores zeros, and sorts them from highest to lowest (e.g., a Full House becomes `[3, 2]`).
* **Pattern Matching:** It compares the `freq` list against known patterns (like `[5]` for Five of a Kind) to return a `ScoreResult` object.

### **5. `isStraight(int[] counts)**`

A specific boolean check for a "Straight."

* **Function:** It checks the `counts` array for two specific sequences: 1-2-3-4-5 or 2-3-4-5-6.
* **Why separate?** Straights have a frequency of `[1, 1, 1, 1, 1]`, which is the same as "No Pattern," so this method ensures they are identified correctly.

### **6. `ScoreResult` (Private Class)**

A simple **Data Wrapper**.

* Instead of the `score` method just returning a number, it returns this object so the program knows both the **name** of the pattern (e.g., "Full House") and the **points** (35) simultaneously.

---


| Method | Responsibility |
| --- | --- |
| `main` | Manages user input, rerolls, and round progression. |
| `rollDie` | Generates a single random 1–6 value. |
| `rollAll` | Generates the initial hand of 5 dice. |
| `score` | Analyzes the dice frequency to assign a point value. |
| `isStraight` | Checks if the dice form a 5-step sequence. |
| `ScoreResult` | Stores the name and point value of a result. |

---

