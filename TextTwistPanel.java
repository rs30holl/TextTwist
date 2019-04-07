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
    public static ArrayList<JButton> buttonList = new ArrayList<>();
    public static ArrayList<JLabel> labelList = new ArrayList<>();
    private JLabel guess, timer, score, points;
    public static JLabel winLabel;
    private JButton clear, twist, enter;
    private JMenuBar menuBar;
    private File file;
    private javax.swing.Timer t;

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
        ttPanel.setBackground(Color.CYAN);

        Font f = new Font("sans-serif", Font.BOLD, 30);

        clear = new JButton();
        clear.setBounds(545,350,100,75);
        clear.setText("Clear");
        clear.setBackground(Color.YELLOW);
        clear.setFont(new Font("sans-serif", Font.BOLD, 18));
        clear.setVisible(true);
        clear.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    guess.setText("");
                    for (JButton b : buttonList){
                        b.setEnabled(true);
                    }
                }
            });

        twist = new JButton();
        twist.setBounds(435,350,100,75);
        twist.setText("Twist");
        twist.setBackground(Color.YELLOW);
        twist.setFont(new Font("sans-serif", Font.BOLD, 18));
        twist.setVisible(true);
        twist.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    Collections.shuffle(buttonList);
                    int counter = 1;
                    for (JButton b : buttonList){
                        b.setBounds(225 + (65 * counter),275,65,65);
                        counter++;
                    }
                }
            });

        enter = new JButton();
        enter.setBounds(325,350,100,75);
        enter.setText("Enter");
        enter.setBackground(Color.YELLOW);
        enter.setFont(new Font("sans-serif", Font.BOLD, 18));
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
        guess.setBounds(350,175,200,75);
        guess.setFont(f);
        guess.setBackground(Color.BLUE);
        guess.setVisible(true);

        winLabel = new JLabel();
        winLabel.setBounds(0,0,725,150);
        winLabel.setText("Congratulations you found all the words!");
        winLabel.setFont(f);
        winLabel.setVisible(false);

        score = new JLabel();
        score.setBounds(325,475,150,75);
        score.setFont(f);
        score.setText("Score:");
        score.setVisible(true);

        timer = new JLabel();
        timer.setBounds(325,550,250,75);
        timer.setFont(f);
        timer.setText("Time:");
        timer.setVisible(true);

        t = new javax.swing.Timer(1000, new ActionListener(){
                int seconds = 0;
                int minutes = 0;
                public void actionPerformed(ActionEvent evt){
                    if (seconds == 60){
                        seconds = 0;
                        minutes++;
                    }
                    if (seconds < 10){
                        timer.setText("Time: " + minutes + ":" + "0" + seconds++);
                    }
                    else {
                        timer.setText("Time: " + minutes + ":" + seconds++);
                    }
                }
            });

        t.start();
        int counter = 1;

        for (char c : test.letters.toCharArray()){
            JButton b = new JButton();
            b.setText(Character.toString(c));
            b.setBounds(225 + (65 * counter),275,65,65);
            b.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        guess.setText(guess.getText() + " " + c);
                        b.setEnabled(false);
                    }
                });
            b.setBackground(Color.LIGHT_GRAY);
            b.setFont(f);
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
        ttPanel.add(twist);
        ttPanel.add(enter);
        ttPanel.add(guess);
        ttPanel.add(winLabel);
        ttPanel.add(score);
        //ttPanel.add(points);
        ttPanel.add(timer);

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
