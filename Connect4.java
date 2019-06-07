import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
/**
 * Developers: Marko Golemovic, Nikica Kovacevic, Ivan Jerkovic & Jorge Olavarria
 * Course: ISTE-121
 * Assignment: Group Project
 * Professor: Ante Poljicak
 */
public class Connect4 extends JFrame implements ActionListener{

   private JPanel board, score;
   private JLabel redScore, orangeScore;
   private JButton[][] buttons = new JButton[6][7];
   private JButton exit, restart, scoreRestart;
   private ImageIcon redCircle, orangeCircle;
   int turn = 0;
   int redWinCounter;
   int orangeWinCounter;
   
   /**
    * Default constructor without parameters that contains the entire GUI.
    * It features board panel, buttons panel, icons, menu bar and many other things.
    * Constructor is called in main method.
    */
   public Connect4(){
      
      redCircle = new ImageIcon("red.png");
      orangeCircle = new ImageIcon("orange.png");
      
      score = new JPanel();
      score.setLayout(new FlowLayout(FlowLayout.CENTER));
      add(score, BorderLayout.SOUTH);
      exit = new JButton("Exit");
      restart = new JButton("Clear the field");
      scoreRestart = new JButton("Restart the scoreline");
      redScore = new JLabel("Red wins: " + redWinCounter);
      redScore.setFont(new Font("Serif", Font.BOLD, 20));
      redScore.setForeground(Color.RED);
      orangeScore = new JLabel("Orange wins: " + orangeWinCounter);
      orangeScore.setFont(new Font("Serif", Font.BOLD, 20));
      orangeScore.setForeground(Color.ORANGE);
      score.add(exit);
      score.add(restart);
      score.add(scoreRestart);
      exit.addActionListener(this);
      restart.addActionListener(this);
      scoreRestart.addActionListener(this);
      score.add(redScore);
      score.add(orangeScore);
      
      board = new JPanel();
      board.setLayout(new GridLayout(6, 7));
      add(board, BorderLayout.CENTER);
      
      /**
       * double for loop that creates a 2D array of 6x7 dimensions that is made entirely of buttons.
       */
      for(int i = 0; i < 6; i++){
         for(int j = 0; j < 7; j++){
            
            buttons[i][j] = new JButton();
            buttons[i][j].addActionListener(this);
            buttons[i][j].setBackground(Color.WHITE);
            board.add(buttons[i][j]);
         
         }
      }
      
      /**
       * Chunks of code responsible for the menu bar's functionality.
       * Features help, different color layouts, etc.
       * Contains action listeners for certain menu items in order to change the color of board layout.
       */
      JMenuBar jmb = new JMenuBar();
      
      JMenu jmAbout = new JMenu("About");
      JMenuItem jmiRules = new JMenuItem("Rules");
      jmiRules.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               JOptionPane.showMessageDialog(null, "**Two-player turn based game with one player as the red chip and other as the orange chip.\n"
                  						  + "**Objective is to connect four chips in a row either vertically, horizontally, or diagnally.\n"
                  						  + "**Each player must place their chip at the lowest position in a column of their choosing.");
            }
         });
      jmAbout.add(jmiRules);
      JMenuItem jmiHelp = new JMenuItem("Help");
      jmiHelp.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               JOptionPane.showMessageDialog(null, "Clear the field button clears the entire field\nExit the game by clicking on the exit button or the X button in upper right corner\nRestart the scorline button sets the scoreline for both players to zero");
            }
         });
      jmAbout.add(jmiHelp);
      jmb.add(jmAbout);
      
      JMenu jmLayout = new JMenu("Layout");
      JMenuItem jmiWhite = new JMenuItem("White/Default");
      jmiWhite.addActionListener(
         new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               for(int i = 0; i < 6; i++){
                  for(int j = 0; j < 7; j++){
                  
                     buttons[i][j].setBackground(Color.WHITE);
                  
                  }
               }
            
            }
         });
      JMenuItem jmiBlue = new JMenuItem("Blue");
      jmiBlue.addActionListener(
         new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               for(int i = 0; i < 6; i++){
                  for(int j = 0; j < 7; j++){
                  
                     buttons[i][j].setBackground(Color.BLUE);
                  
                  }
               }
            
            }
         });
      JMenuItem jmiGreen = new JMenuItem("Green");
      jmiGreen.addActionListener(
         new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               for(int i = 0; i < 6; i++){
                  for(int j = 0; j < 7; j++){
                  
                     buttons[i][j].setBackground(Color.GREEN);
                  
                  }
               }
            
            }
         });
      JMenuItem jmiCyan = new JMenuItem("Cyan");
      jmiCyan.addActionListener(
         new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               for(int i = 0; i < 6; i++){
                  for(int j = 0; j < 7; j++){
                  
                     buttons[i][j].setBackground(Color.CYAN);
                  
                  }
               }
            
            }
         });
      JMenuItem jmiMagenta = new JMenuItem("Purple");
      jmiMagenta.addActionListener(
         new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               for(int i = 0; i < 6; i++){
                  for(int j = 0; j < 7; j++){
                  
                     buttons[i][j].setBackground(Color.MAGENTA);
                  
                  }
               }
            
            }
         });
      JMenuItem jmiBlack = new JMenuItem("Black");
      jmiBlack.addActionListener(
         new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               for(int i = 0; i < 6; i++){
                  for(int j = 0; j < 7; j++){
                  
                     buttons[i][j].setBackground(Color.BLACK);
                  
                  }
               }
            
            }
         });
      JMenuItem jmiPink = new JMenuItem("Pink");
      jmiPink.addActionListener(
         new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               for(int i = 0; i < 6; i++){
                  for(int j = 0; j < 7; j++){
                  
                     buttons[i][j].setBackground(Color.PINK);
                  
                  }
               }
            
            }
         });
      jmLayout.add(jmiWhite);
      jmLayout.add(jmiBlue);
      jmLayout.add(jmiGreen);
      jmLayout.add(jmiCyan);
      jmLayout.add(jmiMagenta);
      jmLayout.add(jmiBlack);
      jmLayout.add(jmiPink);
      jmb.add(jmLayout);
      
      setJMenuBar(jmb);
      setTitle("Connect Four");
      setLocation(600, 300);
      setVisible(true);
      setResizable(false);
      setSize(700, 600);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
   }
   /**
    * Method that sets all button fields to null, used to restart the game.
    */
   public void restart(){
   
      for(int i = 0; i < 6; i++){
         for(int j = 0; j < 7; j++){
            
            buttons[i][j].setIcon(null);
            
         } 
      }  
   }
   /**
    * Action performed method that gives buttons their functionalities.
    * E.g. exit button exits the program, restart button clears the fields, score restart resets the scoreline to zero.
    */
   public void actionPerformed(ActionEvent ae){
   
      if(ae.getSource() == exit){
      
         JOptionPane.showMessageDialog(null, "Thanks for playing the game! :)");
         System.exit(0);
         
      }
      
      else if(ae.getSource() == restart){
         
         restart();            
                  
      }
      
      else if(ae.getSource() == scoreRestart){
      
         redWinCounter = 0;
         redScore.setText("Red wins: " + redWinCounter);
         orangeWinCounter = 0;
         orangeScore.setText("Orange wins: " + orangeWinCounter);
         
      }
      
      /**
       * Uses turn which is an incrementing counter and divides it with the modulus operator to
       * determine whose turn it is. In case it gives 0 then it's orange player's turn.
       * Likewise, if it gives 1 then it's the red player's turn.
       * Furthermore, goes through the 2D array of buttons and checks the status of button rows(whether they're empty or occupied)
       * It uses if statements to check if the rows below are occupied, so if there's an empty button below the pressed button, the chip will drop to the button below.
       */
      if(turn%2 == 0){
         for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
               if(ae.getSource() == buttons[i][j]){
                  turn++;
                  if(buttons[5][j].getIcon() == null){
                     buttons[5][j].setIcon(orangeCircle);
                  }
                  else if(buttons[4][j].getIcon() == null){
                     buttons[4][j].setIcon(orangeCircle);
                  }
                  else if(buttons[3][j].getIcon() == null){
                     buttons[3][j].setIcon(orangeCircle);
                  }
                  else if(buttons[2][j].getIcon() == null){
                     buttons[2][j].setIcon(orangeCircle);
                  }
                  else if(buttons[1][j].getIcon() == null){
                     buttons[1][j].setIcon(orangeCircle);
                  }
                  else if(buttons[0][j].getIcon() == null){
                     buttons[0][j].setIcon(orangeCircle);
                  }
               }
            }
         }
      }
      else if(turn%2 == 1){
         for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
               if(ae.getSource() == buttons[i][j]){
                  turn++;
                  if(buttons[5][j].getIcon() == null){
                     buttons[5][j].setIcon(redCircle);
                  }
                  else if(buttons[4][j].getIcon() == null){
                     buttons[4][j].setIcon(redCircle);
                  }
                  else if(buttons[3][j].getIcon() == null){
                     buttons[3][j].setIcon(redCircle);
                  }
                  else if(buttons[2][j].getIcon() == null){
                     buttons[2][j].setIcon(redCircle);
                  }
                  else if(buttons[1][j].getIcon() == null){
                     buttons[1][j].setIcon(redCircle);
                  }
                  else if(buttons[0][j].getIcon() == null){
                     buttons[0][j].setIcon(redCircle);
                  }
               }
            }
         }
      }
      /**
       * Custom made algorithm that covers all win combinations for horizonal type of win.
       */
      for(int i = 0; i < 6; i++){
         for(int j = 0; j < 4; j++){
            if(buttons[i][j].getIcon() == orangeCircle && buttons[i][j+1].getIcon() == orangeCircle && buttons[i][j+2].getIcon() == orangeCircle && buttons[i][j+3].getIcon() == orangeCircle){
            
               JOptionPane.showMessageDialog(null, "Orange wins!");
               orangeWinCounter++;
               orangeScore.setText("Orange wins: " + orangeWinCounter);
               restart();
            
            }
            else if(buttons[i][j].getIcon() == redCircle && buttons[i][j+1].getIcon() == redCircle && buttons[i][j+2].getIcon() == redCircle && buttons[i][j+3].getIcon() == redCircle){
            
               JOptionPane.showMessageDialog(null, "Red wins!");
               redWinCounter++;
               redScore.setText("Red wins: " + redWinCounter);
               restart();
            
            }
         }
      }
      /**
       * Custom made algorithm that covers all win combinations for vertical type of win.
       */
      for(int i = 0; i < 3; i++){
         for(int j = 0; j < 7; j++){
            if(buttons[i][j].getIcon() == orangeCircle && buttons[i+1][j].getIcon() == orangeCircle && buttons[i+2][j].getIcon() == orangeCircle && buttons[i+3][j].getIcon() == orangeCircle){
            
               JOptionPane.showMessageDialog(null, "Orange wins!");
               orangeWinCounter++;
               orangeScore.setText("Orange wins: " + orangeWinCounter);
               restart();
            
            }
            else if(buttons[i][j].getIcon() == redCircle && buttons[i+1][j].getIcon() == redCircle && buttons[i+2][j].getIcon() == redCircle && buttons[i+3][j].getIcon() == redCircle){
            
               JOptionPane.showMessageDialog(null, "Red wins!");
               redWinCounter++;
               redScore.setText("Red wins: " + redWinCounter);
               restart();
            
            }
         }
      }
      /**
       * Custom made algorithm that covers all win combinations for diagonal wins in \ direction.
       */
      for(int i = 0; i < 3; i++){
         for(int j = 0; j < 4; j++){
            if(buttons[i][j].getIcon() == orangeCircle && buttons[i+1][j+1].getIcon() == orangeCircle && buttons[i+2][j+2].getIcon() == orangeCircle && buttons[i+3][j+3].getIcon() == orangeCircle){
            
               JOptionPane.showMessageDialog(null, "Orange wins!");
               orangeWinCounter++;
               orangeScore.setText("Orange wins: " + orangeWinCounter);
               restart();
            
            }
            else if(buttons[i][j].getIcon() == redCircle && buttons[i+1][j+1].getIcon() == redCircle && buttons[i+2][j+2].getIcon() == redCircle && buttons[i+3][j+3].getIcon() == redCircle){
            
               JOptionPane.showMessageDialog(null, "Red wins!");
               redWinCounter++;
               redScore.setText("Red wins: " + redWinCounter);
               restart();
            
            }
         }
      }
      /**
       * Custom made algorithm that covers all win combinations for diagonal wins in / direction.
       */
      for(int i = 0; i < 3; i++){
         for(int j = 6; j > 2; j--){
            if(buttons[i][j].getIcon() == orangeCircle && buttons[i+1][j-1].getIcon() == orangeCircle && buttons[i+2][j-2].getIcon() == orangeCircle && buttons[i+3][j-3].getIcon() == orangeCircle){
            
               JOptionPane.showMessageDialog(null, "Orange wins!");
               orangeWinCounter++;
               orangeScore.setText("Orange wins: " + orangeWinCounter);
               restart();
            
            }
            else if(buttons[i][j].getIcon() == redCircle && buttons[i+1][j-1].getIcon() == redCircle && buttons[i+2][j-2].getIcon() == redCircle && buttons[i+3][j-3].getIcon() == redCircle){
            
               JOptionPane.showMessageDialog(null, "Red wins!");
               redWinCounter++;
               redScore.setText("Red wins: " + redWinCounter);
               restart();
            
            }
         }
      }            
   }
   /**
    * Main method, calls the constructor, runs the program.
    */
   public static void main(String[] args){
   
      Connect4 frame = new Connect4();
      
   }
   
}