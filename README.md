# PSCO-TEAM-X-SPRING-26
# Game 4 — Liar's Dice (Console Version)

This is **Game 4** of the Team Based Project: a console-based Java implementation of a **Liar’s Dice-style** game. :contentReference[oaicite:1]{index=1}

## Overview
Liar’s Dice is a bluffing game. Players roll dice in secret, then take turns making bids about the **total number of dice showing a face value** across all players. On your turn you either:
- **Raise the bid** (higher quantity, or same quantity with higher face), OR
- **Call “Liar!”** to challenge the previous bid.

The round ends when a challenge happens.

---

## Rules (this version)
### Setup
- 2–4 players (configurable)
- Each player starts with **5 dice**
- Dice are rolled secretly each round

### Bidding
A bid is: **(quantity, face)**  
Example: “**three 4s**” means *there are at least 3 dice showing face 4 among all players’ dice.*

### Legal raise
A new bid must be higher than the previous bid:
- Higher **quantity**, OR
- Same quantity but higher **face**  
Examples:
- (3,4) → (3,5) ✅  
- (3,4) → (4,1) ✅  
- (3,4) → (2,6) ❌

### Challenge (“Liar!”)
When a player calls **Liar**:
1. All dice are revealed
2. Count how many dice match the bid’s face value  
   - If the count is **>= quantity**, the bid was TRUE → challenger loses a die  
   - Otherwise the bid was FALSE → bidder loses a die  

### Elimination & Winning
- When a player reaches **0 dice**, they are out
- Last player remaining wins

---

## Controls / Input Format
On your turn, enter one of:

### Raise bid
`BID <quantity> <face>`
Example:
`BID 3 4`

### Challenge
`LIAR`

### Optional features (if implemented)
- `HELP` shows commands
- `HISTORY` shows the bid history for the round

---

## How to Run
### In GitHub Codespaces / Terminal
1. Compile:
   `javac -d out src/**/*.java`
2. Run:
   `java -cp out Main`

> If you use Maven/Gradle, replace this section with your actual commands.

---

## Project Structure (suggested)
- `src/`
  - `Main.java` (menu + routing)
  - `games/liarsdice/`
    - `LiarsDiceGame.java` (game loop)
    - `Bid.java` (quantity + face)
    - `Player.java` (name + dice count)
    - `DiceCup.java` (roll + store dice)
- `data/`
  - `players.txt` (username + stats)

---

## Testing (suggested)
- Unit test bid validation:
  - valid raises
  - invalid raises
- Challenge correctness:
  - exact match
  - above threshold
  - below threshold
- Edge cases:
  - player elimination mid-game
  - last player wins

---

## Team Notes
- Keep the game consistent with the overall system requirements:
  - user registration
  - stats tracking (games played, score, last played date/time)
  - accessible via the main menu :contentReference[oaicite:2]{index=2}
