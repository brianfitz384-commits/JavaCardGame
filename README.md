# HighSuit Card Game (Java)

## Overview
HighSuit is a console-based card game developed in Java as part of an individual programming assignment.  
The game supports one or two players, including an optional computer-controlled player, competing over multiple rounds to achieve the highest-scoring suit-based hand.

This project demonstrates:
- Object-Oriented Programming (OOP)
- Game logic and state management
- File handling for data persistence
- Basic AI behaviour
- Replay and scoring systems

---

## Game Rules (Summary)

- Each player is dealt **5 cards** from a standard **52-card deck**.

In each round:
- The player is shown how many points each suit scores.
- The player chooses a **bonus suit** (Clubs, Diamonds, Hearts, or Spades).
- The player may swap **up to 4 cards**.
- The score for the round is calculated from the **highest-value suit** in the hand.
- If the highest-value suit matches the chosen bonus suit, a **+5 bonus** is awarded.

Card values:
- Jack, Queen, King = **10 points**
- Ace = **11 points**

The player with the highest total score after all rounds wins the game.

---

## Computer Player

If a player is named **"Computer"**, the game automatically enables a computer-controlled opponent.

The computer player:
- Chooses the suit with the highest current value
- Swaps cards that do not match its chosen suit (up to 4 cards)
- Plays fully automatically

---

## Project Structure
HighSuit/
│
├── Card.java
├── Deck.java
├── HighSuitGame.java


---

## Design Overview

### Object-Oriented Design
Each class has a single responsibility, improving code readability, maintainability, and extensibility.

### Data Structures Used
- ArrayList for player hands and deck management
- File I/O for persistent high scores
- Java serialization for replay storage

### Scoring Logic
- Suit-based scoring encourages strategic decision-making
- The bonus suit mechanic adds an extra layer of risk and reward

---

## Replay System

After each game, a replay file is saved containing:
- Initial hands
- Chosen bonus suit
- Cards swapped
- Final hand
- Round score

Players can view a full round-by-round replay at the end of the game.

---

## High Score System

- Stores the **top 5 scores**
- Scores are normalised using **average score per round**
- High scores persist between game runs using `highscores.txt`

---

## How to Run

### Requirements
- Java JDK 8 or newer
- Terminal or command prompt

### Compile
```bash
javac *.java

Run
java HighSuitGame

Testing

The game has been manually tested for:
- Correct card dealing and deck exhaustion
- Card swapping limits (maximum of 4)
- Accurate score calculations
- Correct computer player behaviour
- Replay saving and loading
- High score file persistence
- Edge cases such as invalid user input and empty decks

---

Possible Improvements

- Graphical User Interface (GUI)
- More advanced computer AI strategies
- Online multiplayer support
- Automated unit testing using JUnit
- Probability-based card replacement logic

---

Technologies Used

- Java
- Object-Oriented Programming (OOP)
- File I/O
- Java Collections Framework


---
Author
Brian Fitzgerald





