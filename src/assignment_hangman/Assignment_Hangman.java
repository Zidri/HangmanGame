/**
 * Joanna Hannigan 
 * Hangman Assignment
 * Description: A program that does the following
 * 1. read a text file
 * 2. Dynamically create text fields or labels to represent a letter
 * 3. If the person guesses correctly, reveal the letter
 * 4. If the person guesses wrong, show a letter in HANGMAN. 
 *      If they spell Hangman they lose.
 * 5. If the user guesses the correct phrase – they win. 
 * 6. Ask the user if they wish to play again. 
 *      If yes, then load a new word or phrase.
 * 7. Create the alphabet dynamically. 
 *      Allow the user to click on a letter to choose. 
 *      Provide a mechanism that will keep track of the letters already chosen.
 * 8. Provide a scoring mechanism that tracks the number of wins and losses
 * 9. You must also have your name in the title of the frame 
 *      and an appropriate menu bar
 * Updated: 3/8/2022
 */
package assignment_hangman;

/**
 *
 * @author joannaahannigan
 */
public class Assignment_Hangman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameFrame game = new GameFrame();
        game.setVisible(true);
        game.setLocationRelativeTo(null);
        
        
        game.setTitle("Joanna's Hangman Game");
    }
    
}
