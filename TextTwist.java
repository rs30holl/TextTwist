import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
/**
 * TextTwist searches the text file to see if the letters selected
 * form a worm that is within the document.
 *
 * @author (Ryan Holland, Julia Krasinski, Briella Sala,
 * Matt Harrison, Michael Lostritto)
 * @version (4.7.2019)
 */
public class TextTwist
{
    public String letters;
    public static ArrayList<String> words;
    private static int check;

    /**
     * Itterates through a file to see if there is a word that it at least
     * 2 letters long. If there is, it is added to an ArrayList. 
     * 
     * @param input: File
     */
    public TextTwist(File input) throws FileNotFoundException{
        File file = input;
        Scanner reader = new Scanner(file);
        letters = reader.next();
        words = new ArrayList<>();
        
        while (reader.hasNext()){
            String str = reader.nextLine();
            if (str.length() > 2){
                words.add(str);
            }
        }
        check = words.size();

        reader.close();
    }

    /**
     * If the ArrayList contains the word that the enters, return true
     * if not, return false. 
     * 
     * @param guess: String
     * @return true
     * @return false
     */
    public static boolean checkWord(String guess){
        if (words.contains(guess)){
            return true;
        }

        return false;
    }

    /**
     * Takes the letters that the user clicks to form a word and removes the spaces. 
     * Displays scrambled letters as buttons with letters on each. 
     * 
     * @param guess: String
     */
    public static void play(String guess){
        guess = guess.replace(" ", "");
        if (checkWord(guess)){
            for (int i = 0; i < words.size(); i++){
                if (guess.equals(words.get(i))){
                    TextTwistPanel.labelList.get(i).setText(guess);
                    check--;
                    if (check == 0){
                        TextTwistPanel.winLabel.setVisible(true);
                        for (JButton b : TextTwistPanel.buttonList){
                            b.setEnabled(false);
                        }
                    }
                    break;
                }
            }
        }
    }
}
