import java.util.Random;
import java.util.Scanner;
public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int score = 0;
        char playAgain;

        System.out.println("===== Number Guessing Game =====");

        do {

            int numberToGuess = random.nextInt(100) + 1;
            int attempts = 0;
            int maxAttempts = 10;
            int userGuess = -1;

            System.out.println("\nGuess a number between 1 and 100");
            System.out.println("You have only " + maxAttempts + " attempts.");

            while (attempts < maxAttempts) {

                System.out.print("Enter your guess: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next();
                    continue;
                }

                userGuess = sc.nextInt();
                attempts++;

                if (userGuess > numberToGuess) {
                    System.out.println("Too High!");
                }
                else if (userGuess < numberToGuess) {
                    System.out.println("Too Low!");
                }
                else {
                    System.out.println("🎉 Correct! You guessed it in " + attempts + " attempts.");
                    score++;
                    break;
                }

                System.out.println("Remaining attempts: " + (maxAttempts - attempts));
            }

            if (userGuess != numberToGuess) {
                System.out.println("❌ You lost! The correct number was: " + numberToGuess);
            }

            System.out.println("Your Score: " + score);

            System.out.print("Do you want to play again? (y/n): ");
            playAgain = sc.next().charAt(0);

        } while (playAgain == 'y' || playAgain == 'Y');

        System.out.println("Thanks for playing!");

        sc.close();
    }

}