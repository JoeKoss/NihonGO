import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import javax.imageio.*;
import java.util.Random;

public class NihonGO extends Initializer implements ActionListener
{
// Instance variables for the frame, panels, buttons, and image
///////////////////////////////////////////////////////////////////////////////////////////////////////////
   //font sizes
   public final float ynBtnFont = 20.0f;        public final float ansBtnFont = 35.0f;
   public final float questionFont = 40.0f;     public final float checkCorFont = 24.0f;
   
   // frame and button sizes
   public final int frameHeight = 800;          public final int frameWidth = 750;
   public final int ynWidth = 100;              public final int ynHeight = 50;
   public final int menuBtnWidth = 150;         public final int menuBtnHeight = 100;
   
   //values for the menu screen layout (including border for formatting)
   public final int homeRows = 2;               public final int homeCols = 0;
   public final int addMenuRows = 6;            
   public final int horiGap = 0;                public final int vertGap = 20;
   public final int menuBorderTop = 50;         public final int menuBorderLeft = 250;
   public final int menuBorderBottom = 450;     public final int menuBorderRight = 250;
   public final int addMenuBorderBottom = 50;   public final int resultsBorderBottom = 10;
   public final int resultsBorderLeft = 310;    public final int resultsBorderRight = 310;
   public final int resultsVertGap = 10;
   
   //default path will be used to reset imagePath after each character
   public String defaultPath;
   public String imagePath;
   
   //the main frame that displays the program
   public JFrame frame;
   
   //Home Screen panel - tack it all on and just remove / hide it
   //the panels
   public JPanel homePanel;            public JPanel quizMenuPanel;     public JPanel homeBtnPanel;
   public JPanel mainPanel;            public JPanel topPan1;           public JPanel centerPanel;
   public JPanel bottomContainer;      public JPanel bottomPan1;        public JPanel bottomPan2;
   
   //the buttons
   public JButton showAnswer;          public JButton correctYes;       public JButton correctNo;
   public JButton chooseHiragana;      public JButton chooseKatakana;   public JButton returnHomeBtn;
   public JButton chooseA;             public JButton chooseE;          public JButton chooseI;
   public JButton chooseO;             public JButton chooseU;          public JButton chooseAll;
   public JButton retryWrongBtn;
   
   //the labels
   public JLabel selectQuizLabel;      public JLabel topLabel; public JLabel askCorrect;
   public JLabel showPicture;          public JLabel charactersWrongLabel;
   
   //image for the character
   public BufferedImage showImage;
 
// Instance variables for the quizes
///////////////////////////////////////////////////////////////////////////////////////////////////////
  
   public String[] characterArray;
   public String charactersWrong = "<html>";
   public String quizType;
   
   public int count = 0;
   int numWrong = 0;

   Random rand = new Random();

//Creates the frame and adds the home screen - basically runs the program
////////////////////////////////////////////////////////////////////////////////////////////////////////

   public NihonGO()
   {
      createFrame();
      createHomeScreen();
   }
     
//Randomizes the character array and sets the default path
//////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void randomizeCharacterArray()
   {
      for(int i=0; i < characterArray.length; i++) //generates the randomized array
      {
         int randomIndex = rand.nextInt(characterArray.length);
         String temp = characterArray[i];
         characterArray[i] = characterArray[randomIndex];
         characterArray[randomIndex] = temp;
      } 
   }

//Creates the frame and its components  
///////////////////////////////////////////////////////////////////////////////////////////////////////
  
   //initializes the frame, its panels, and adds the static elements
   public void createFrame()
   {
      frame = new JFrame("NihonGO"); //creates frame
      frame.setSize(frameHeight,frameWidth);
      
      frame.setVisible(true); //allows the frame to be seen
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits program when frame is closed
   }
   
   public void createHomeScreen()
   {

      homePanel = new JPanel(new BorderLayout()); //creates the home panel
      topPan1 = new JPanel(); //creates a top panel
      quizMenuPanel = new JPanel(new GridLayout(homeRows, homeCols, horiGap, vertGap)); //creates the center panel
      quizMenuPanel.setBorder(new EmptyBorder(menuBorderTop, menuBorderLeft, menuBorderBottom, menuBorderRight));
      
      topLabel = new JLabel("Welcome! Select your quiz type!"); //creates an empty label for the top panel
      topLabel.setFont(topLabel.getFont().deriveFont(questionFont));
      topPan1.add(topLabel); //adds the label to the top panel
      
      chooseHiragana = createNewButton("Hiragana", ynBtnFont);
      chooseKatakana = createNewButton("Katakana", ynBtnFont);
      
      quizMenuPanel.add(chooseHiragana);
      quizMenuPanel.add(chooseKatakana);
            
      homePanel.add(topPan1, BorderLayout.NORTH);
      homePanel.add(quizMenuPanel, BorderLayout.CENTER);
      
      frame.getContentPane().add(homePanel);
      frame.getContentPane().revalidate(); //refreshes so that the home screen elements can be seen
   }
      
   //Creates a menu screen with additional options
   public void createQuizTypeScreen()
   {  
      homePanel.remove(quizMenuPanel);
      
      quizMenuPanel = new JPanel(new GridLayout(addMenuRows, homeCols, horiGap, vertGap)); //creates the center panel
      quizMenuPanel.setBorder(new EmptyBorder(menuBorderTop, menuBorderLeft, addMenuBorderBottom, menuBorderRight));
      
      topLabel.setText("Select the specifics of your quiz!");
      
      chooseA = createNewButton(quizType + " 'A'", ynBtnFont);
      chooseE = createNewButton(quizType + " 'E'", ynBtnFont);
      chooseI = createNewButton(quizType + " 'I'", ynBtnFont);  
      chooseO = createNewButton(quizType + " 'O'", ynBtnFont);
      chooseU = createNewButton(quizType + " 'U'", ynBtnFont);
      chooseAll = createNewButton(quizType + " All", ynBtnFont);
      
      quizMenuPanel.add(chooseA);
      quizMenuPanel.add(chooseE);
      quizMenuPanel.add(chooseI);
      quizMenuPanel.add(chooseO);
      quizMenuPanel.add(chooseU);
      quizMenuPanel.add(chooseAll);
      
      //adds the home button to return home
      returnHomeBtn = createNewButton("Home", ynBtnFont, ynWidth, ynHeight);
      
      homeBtnPanel = new JPanel();
      homeBtnPanel.add(returnHomeBtn);
      
      homePanel.add(quizMenuPanel, BorderLayout.CENTER);
      homePanel.add(homeBtnPanel, BorderLayout.SOUTH);
      
      frame.getContentPane().revalidate(); //refreshes so that the home screen elements can be seen
   }
   
   public void createQuizScreen()
   {  
      mainPanel = new JPanel(new BorderLayout()); //creates the main panel
      centerPanel = new JPanel(new BorderLayout()); //creates the center panel
      
      bottomContainer = new JPanel(new BorderLayout()); //allows a north and south position for this panel
      bottomPan1 = new JPanel(); //creates a panel for the askCorrect label  
      bottomPan2 = new JPanel(new FlowLayout(FlowLayout.CENTER)); //creates a bottom panel that allows multiple buttons
      
      showAnswer = createNewButton("Show answer", ansBtnFont);
      correctYes = createNewButton("Yes", ynBtnFont, ynWidth, ynHeight);
      correctNo = createNewButton("No", ynBtnFont, ynWidth, ynHeight);
            
      bottomPan2.add(correctYes);
      bottomPan2.add(correctNo);

      bottomContainer.add(bottomPan1, BorderLayout.NORTH); //creates a space for a label above the buttons
      bottomContainer.add(bottomPan2, BorderLayout.SOUTH); //places the buttons
      
      JLabel askCorrect = new JLabel("Did you get it correct?");
      askCorrect.setFont(askCorrect.getFont().deriveFont(checkCorFont));
      bottomPan1.add(askCorrect);
      
      //adds the button to the panel
      centerPanel.add(showAnswer);
      
      //adds the top, center, and bottom panels to the frame
      mainPanel.add(topPan1, BorderLayout.NORTH); 
      mainPanel.add(centerPanel, BorderLayout.CENTER);
      
      frame.getContentPane().add(mainPanel); //adds the panel to the frame
      frame.getContentPane().revalidate();
   }
   
//Button creation method
//////////////////////////////////////////////////////////////////////////////////////////////////

   public JButton createNewButton(String label, float fontSize)
   {
      JButton newButton = new JButton(label); //sets the button label
      newButton.setFont(newButton.getFont().deriveFont(fontSize)); //sets the font size
      newButton.addActionListener(this); //adds action listener for button clicks
      
      return newButton;
      
   }
   
   public JButton createNewButton(String label, float fontSize, int width, int height)
   {
      JButton newButton = new JButton(label);
      newButton.setFont(newButton.getFont().deriveFont(fontSize));
      newButton.setPreferredSize(new Dimension(width, height)); //sets size of the button
      newButton.addActionListener(this);
      
      return newButton;
   }
   
//Dynamic elements
//////////////////////////////////////////////////////////////////////////////////////////////////
   
   //removes the home screen elements from the frame in preparation for the additional button screen
   public void removeHomeScreenElements()
   {
      homePanel.remove(topPan1);
      
      frame.getContentPane().remove(homePanel);
      frame.getContentPane().revalidate();
   }
   //removes the additional buttons and the home button in preparation for the quiz screen
   public void removeQuizTypeScreenElements()
   {
      homePanel.remove(homeBtnPanel);
      homePanel.remove(quizMenuPanel);
   }
   
   //displays the image of the asked character
   public void displayCharacterImage()
   {
      //toggles display of the show answer button
      showAnswer.setVisible(false);
      //adds the bottom panel after the button is clicked to prevent the user from clicking "yes" or "no" beforehand
      mainPanel.add(bottomContainer, BorderLayout.SOUTH);

      try
      {
         //adds the image name and .png to the path, then displays the image.
         showImage = ImageIO.read(new File(imagePath + characterArray[count] + ".png"));
         showPicture = new JLabel(new ImageIcon(showImage));
         
         centerPanel.add(showPicture);
      }
       catch(IOException ex)
      {
         System.out.println("Invalid image path");
      }
   }
   
   //shows the button to hide the next character and removes the bottom panel to prevent clicks
   public void resetDynamicElements()
   {
      showAnswer.setVisible(true);
      mainPanel.remove(bottomContainer);
      centerPanel.remove(showPicture);
   }
      
   public void displayWrong()
   {
      //if it ended with a break, remove the break tag along with the space and comma
      if(numWrong % 5 == 0)
         charactersWrong = charactersWrong.substring(0, charactersWrong.length() - 6);
         
      //removes the space and comma after the last value if it didn't end with a break
      if(numWrong % 5 != 0)
         charactersWrong = charactersWrong.substring(0, charactersWrong.length() - 2);
         
      charactersWrong += "<html>";
      
      //removes the unneeded elements
      resetDynamicElements();
      
      //because the previous method sets showAnswer as visible, it needs to be hidden again
      showAnswer.setVisible(false);
      
      topLabel.setText("You got the following characters wrong:");
      
      //creates the new label and adds it to the center panel
      if(numWrong == 0)
         charactersWrongLabel = new JLabel("You got them all right!", SwingConstants.CENTER);
      else
         charactersWrongLabel = new JLabel(charactersWrong, SwingConstants.CENTER);
      charactersWrongLabel.setFont(charactersWrongLabel.getFont().deriveFont(questionFont));
      centerPanel.add(charactersWrongLabel);
      
      //creates a home button to allow the user to select another quiz
      retryWrongBtn = createNewButton("Retry Wrong", ynBtnFont, ynWidth, ynHeight);
      
      if(numWrong != 0)
      {
         homeBtnPanel = new JPanel(new GridLayout(homeRows, homeCols, horiGap, resultsVertGap));
         homeBtnPanel.setBorder(new EmptyBorder(menuBorderTop, resultsBorderLeft, resultsBorderBottom, resultsBorderRight));
         
         homeBtnPanel.add(retryWrongBtn);
         homeBtnPanel.add(returnHomeBtn);
      }
      else
      {
         homeBtnPanel = new JPanel(new BorderLayout());
         homeBtnPanel.setBorder(new EmptyBorder(menuBorderTop, resultsBorderLeft, resultsBorderBottom, resultsBorderRight));

         homeBtnPanel.add(returnHomeBtn);
      }
      
      mainPanel.add(homeBtnPanel, BorderLayout.SOUTH);
      
      //refreshes the pane to show the new label and panel
      frame.getContentPane().revalidate();
   }
   
    //allows the user to rety the characters they got wrong
    public void retryWrong()
   {
      characterArray = charactersWrong.replace("<br>", "").replace("<html>","").replace(",","").split(" ");
      count = 0;
      numWrong = 0;
      charactersWrong = "<html>";
      imagePath = defaultPath;
      
      randomizeCharacterArray();
      showAnswer.setVisible(true);
      
      centerPanel.remove(charactersWrongLabel);
      mainPanel.remove(homeBtnPanel);
      
      topLabel.setText("Write the character for '" + characterArray[count] + "'");      
      frame.getContentPane().revalidate();
   } 
      
   //clears the frame and initializes a new home screen
   public void returnHome()
   {
      frame.getContentPane().removeAll();
      homePanel.remove(quizMenuPanel);
      homePanel.remove(homeBtnPanel);
      
      quizMenuPanel = new JPanel(new GridLayout(homeRows, homeCols, horiGap, vertGap));
      quizMenuPanel.setBorder(new EmptyBorder(menuBorderTop, menuBorderLeft, menuBorderBottom, menuBorderRight));
      
      quizMenuPanel.add(chooseHiragana);
      quizMenuPanel.add(chooseKatakana);
      
      topLabel.setText("Welcome! Select your quiz type!");
      
      homePanel.add(topPan1, BorderLayout.NORTH);
      homePanel.add(quizMenuPanel, BorderLayout.CENTER);
      
      frame.getContentPane().add(homePanel);
      frame.getContentPane().revalidate();

   }
   
   //prepares for the quiz by randomizing the array, creating the quiz screen, and setting label text
   public void quizSetup()
   {
      randomizeCharacterArray();
      
      removeHomeScreenElements();
      removeQuizTypeScreenElements();
      
      createQuizScreen();
      topLabel.setText("Write the character for '" + characterArray[count] + "'");
   }
   
//action listener - checks for button clicks and updates most of the values
//////////////////////////////////////////////////////////////////////////////////////////////
   public void actionPerformed(ActionEvent e)
   {
      //initializes the path for Hiragana and creates the additional options menu
      if(e.getSource() == chooseHiragana)
      {
         quizType = "Hiragana";
         
         defaultPath = getHiraganaImagePath();
         imagePath = defaultPath;
         
         createQuizTypeScreen();
      }
      
      //same as previous, but for katakana
      else if(e.getSource() == chooseKatakana)
      {
         quizType = "Katakana";
         
         defaultPath = getKatakanaImagePath();
         imagePath = defaultPath;
         
         createQuizTypeScreen();
      }
      
      //initializes the array based on selection
      else if(e.getSource() == chooseA)
      {
         characterArray = getACharArray();
         quizSetup();
      }
      else if(e.getSource() == chooseE)
      {
         characterArray = getECharArray();
         quizSetup();
      }
      else if(e.getSource() == chooseI)
      {
         characterArray = getICharArray();
         quizSetup();
      }
      else if(e.getSource() == chooseO)
      {
         characterArray = getOCharArray();
         quizSetup();
      }
      else if(e.getSource() == chooseU)
      {
         characterArray = getUCharArray();
         quizSetup();
      }
      else if(e.getSource() == chooseAll)
      {
         characterArray = getBasicCharArray();
         quizSetup();
      }
            
      //displays the image after show answer is pressed
      else if(e.getSource() == showAnswer)
      {
         displayCharacterImage();
      }
      
      //resets the frame elements, increments count, sets the next image path and label, display wrong if finished
      else if(e.getSource() == correctYes)
      {
         resetDynamicElements();
         
         count++;
         
         if(count == characterArray.length)
            displayWrong();
         else
         {
            imagePath = defaultPath;
            topLabel.setText("Write the character for '" + characterArray[count] + "'"); //updates the label for the top panel
         }      
      }
      
      //same as previous, also tracks the incorrect answers
      else if(e.getSource() == correctNo)
      {
         resetDynamicElements();
         
         charactersWrong += characterArray[count] + ", ";
         numWrong++;
         
         if(numWrong % 5 == 0)
         {
            charactersWrong += "<br>";
         }
         count++;
         if(count == characterArray.length)
            displayWrong();
         else
         {
            imagePath = defaultPath;
            topLabel.setText("Write the character for '" + characterArray[count] + "'"); //updates the label for the top panel
         }
      }
      else if(e.getSource() == retryWrongBtn)
      {
         retryWrong();
      }
      
      //resets values and calls the return home method
      else
      {
         count = 0;
         numWrong = 0;
         charactersWrong = "<html>";
         
         returnHome();
      }
   }
} 
