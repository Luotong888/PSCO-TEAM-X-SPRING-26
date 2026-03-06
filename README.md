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
