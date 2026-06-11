import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int numberToGuess = random.nextInt(100) + 1;
        int guess;
        int attempts = 0;

        System.out.println("=== NUMBER GUESSING GAME ===");
        System.out.println("I have selected a number between 1 and 100.");

        do {
            System.out.print("Enter your guess: ");
            guess = sc.nextInt();
            attempts++;

            if (guess > numberToGuess) {
                System.out.println("Too High! Try again.");
            } else if (guess < numberToGuess) {
                System.out.println("Too Low! Try again.");
            } else {
                System.out.println("Congratulations! You guessed the correct number.");
                System.out.println("Total Attempts: " + attempts);
            }

        } while (guess != numberToGuess);

        sc.close();
    }
}
