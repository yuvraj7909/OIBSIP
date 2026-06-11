# Number Guessing Game

## Objective

The objective of this project is to create a simple Number Guessing Game using Java. The program generates a random number, and the user has to guess it. The game provides hints whether the guessed number is too high or too low until the correct number is guessed.

## Features

* Random number generation between 1 and 100.
* User input through console.
* Hints for incorrect guesses.
* Counts the number of attempts.
* Displays a success message when the correct number is guessed.

## Technologies Used

* Java
* VS Code / Any Java IDE
* Java Scanner Class
* Java Random Class

## Steps Performed

1. Generated a random number using the Random class.
2. Accepted user input using the Scanner class.
3. Compared the user's guess with the generated number.
4. Displayed hints ("Too High" or "Too Low").
5. Continued the game until the correct number was guessed.
6. Displayed the total number of attempts taken.

## How to Run

1. Save the file as `NumberGuessingGame.java`.
2. Open the terminal in the project folder.
3. Compile the program:

   ```bash
   javac NumberGuessingGame.java
   ```
4. Run the program:

   ```bash
   java NumberGuessingGame
   ```

## Sample Output

```
=== NUMBER GUESSING GAME ===
Guess a number between 1 and 100

Enter your guess: 50
Too Low!

Enter your guess: 75
Too High!

Enter your guess: 63
Correct Number!
Attempts: 3
```

## Outcome


👨‍💻 Author
Yuvraj Bordiya

GitHub: @yuvraj7909
Email: bordiyayuvraj@gmail.com

This project demonstrates the use of loops, conditional statements, random number generation, and user input handling in Java. It helps beginners understand the fundamentals of Java programming through an interactive game.
