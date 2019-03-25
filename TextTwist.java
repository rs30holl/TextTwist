import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
/**
 * Write a description of class TextTwist here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextTwist
{
    public String letters;
    public static ArrayList<String> words;
    private static int check;

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

    public static boolean checkWord(String guess){
        if (words.contains(guess)){
            return true;
        }

        return false;
    }

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
