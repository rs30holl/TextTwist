import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Write a description of class TextTwistPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextTwistPanel extends JFrame
{
    private int width, height;
    private ArrayList<JButton> buttonList = new ArrayList<>();
    public static ArrayList<JLabel> labelList = new ArrayList<>();
    private JLabel guess, timer;
    public static JLabel winLabel;
    private JButton clear, reshuffle, enter;
    private JMenuBar menuBar;
    private File file;

    public TextTwistPanel(String fileName) throws FileNotFoundException{
        file = new File(fileName);

        TextTwist test = new TextTwist(file);

        setPreferredSize(new Dimension(750,1000));
        width = getPreferredSize().width;
        height = getPreferredSize().height;
        generateMenu();
        this.setJMenuBar(menuBar);

        JPanel ttPanel = new JPanel();
        ttPanel.setPreferredSize(new Dimension(750,1000));
        ttPanel.setLayout(null);
        ttPanel.setBackground(Color.WHITE);
        
        Font f = new Font("sans-serif", Font.BOLD, 30);

        clear = new JButton();
        clear.setBounds(495,250,75,50);
        clear.setText("Clear");
        clear.setVisible(true);
        clear.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    guess.setText("");
                    for (JButton b : buttonList){
                        b.setEnabled(true);
                    }
                }
            });

        reshuffle = new JButton();
        reshuffle.setBounds(410,250,75,50);
        reshuffle.setText("Reshuffle");
        reshuffle.setVisible(true);
        reshuffle.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    Collections.shuffle(buttonList);
                    int counter = 1;
                    for (JButton b : buttonList){
                        b.setBounds(325 + (50 * counter),175,75,50);
                        counter++;
                    }
                }
            });

        enter = new JButton();
        enter.setBounds(325,250,75,50);
        enter.setText("Enter");
        enter.setVisible(true);
        enter.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    test.play(guess.getText());
                    guess.setText("");
                    for (JButton b : buttonList){
                        b.setEnabled(true);
                    }
                }
            });

        guess = new JLabel();
        guess.setBounds(350,75,200,75);
        guess.setFont(f);
        guess.setVisible(true);

        winLabel = new JLabel();
        winLabel.setBounds(0,0,725,150);
        winLabel.setText("Congratulations you found all the words!");
        winLabel.setFont(f);
        winLabel.setVisible(false);

        int counter = 1;

        for (char c : test.letters.toCharArray()){
            JButton b = new JButton();
            b.setText(Character.toString(c));
            b.setBounds(325 + (50 * counter),175,75,50);
            b.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        guess.setText(guess.getText() + " " + c);
                        b.setEnabled(false);
                    }
                });
            buttonList.add(b);
            ttPanel.add(b);
            counter++;
        }

        counter = 1;

        for (String s : test.words){
            JLabel l = new JLabel();
            String text = "";
            for (int i = 0; i < s.length(); i++){
                text = text + "X ";
            }
            l.setBounds(5,counter * 75,200,75);
            l.setFont(f);
            l.setText(text);
            l.setLayout(null);
            labelList.add(l);
            ttPanel.add(l);
            counter++;
        }

        ttPanel.add(clear);
        ttPanel.add(reshuffle);
        ttPanel.add(enter);
        ttPanel.add(guess);
        ttPanel.add(winLabel);
        //ttPanel.add(timer);

        this.add(ttPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void generateMenu(){
        menuBar = new JMenuBar();

        JMenu help = new JMenu("Help");
        JMenu tools = new JMenu("Tools");

        JMenuItem preferences = new JMenuItem("Preferences   ");
        JMenuItem rules = new JMenuItem("How to Play   ");

        help.add(rules);
        tools.add(preferences);

        menuBar.add(help);
        menuBar.add(tools);
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    try{
                        new TextTwistPanel("test.txt");
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
            });
    }
}
