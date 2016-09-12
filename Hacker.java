
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Hacker {
    private int size = 4;
    private int [][] numbersArray = new int [size][size];
    // for the 'take back' operation I will use two stacks:
    private Stack <String> action = new Stack <String> ();
    private Stack <String> coordinate = new Stack <String> ();
    
    public Hacker () {
        loadRandomly();
        displayArray();
        createGUI();
        undoGUI();
    }
    
    
    // takes in an array and Jumbles the numbers up:
    public void jumble (int [] dataArray) {
        int startIndex = 0;
        int endIndex = dataArray.length - 1;
        while (startIndex < endIndex) {
            int randomIndex = startIndex + (int) (Math.random()*(endIndex - startIndex + 1));
            int temp = dataArray[startIndex];
            dataArray[startIndex] = dataArray[randomIndex];
            dataArray[randomIndex] = temp;
            startIndex++;
            endIndex--;
        }
        // because the code above can only swap the int at the end of the array 
        // for the first iteration of the loop, this code will swap the end int with a random one
        int randomIndex2 = (int) (Math.random()*(dataArray.length));
        int temp = dataArray[randomIndex2];
        dataArray[randomIndex2] = dataArray[dataArray.length - 1];
        dataArray[dataArray.length - 1] = temp;
    }
    // load the numbersArray randomly:
    public void loadRandomly () {
        int num = 1;
        for (int row = 0; row < size; row  ++) {
            for (int col = 0 ; col < size; col ++) {
                numbersArray[row][col] = num;
                num++;
            }
            num = 1;
        }
        int [] rowArray = new int [size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                rowArray[col] = numbersArray[row][col];
            }
            jumble(rowArray);
            for (int col = 0; col < size; col ++) {
                numbersArray[row][col] = rowArray[col];
            }
        }

    }
    // swaps the two rows that are passed in:
    public void rowSwap (int row1, int row2) {
        int [] temp1 = new int [size];
        int [] temp2 = new int [size];
        for (int col = 0; col < size; col ++) {
            temp1[col] = numbersArray[row1][col];  
        }
        for (int col = 0; col < size; col ++) {
            temp2[col] = numbersArray[row2][col];
        }
        // now swap the rows:
        for (int col = 0; col < size; col ++) {
            numbersArray[row1][col] = temp2[col]; 
        }
        for (int col = 0; col < size; col ++) {
            numbersArray[row2][col] = temp1[col]; 
        }
    }
    // swaps the two columns that are passed in:
    public void colSwap (int col1, int col2) {
        int [] temp1 = new int [size];
        int [] temp2 = new int [size];
        for (int row = 0; row < size; row ++) {
            temp1[row] = numbersArray[row][col1];
        }
        for (int row = 0; row < size; row ++) {
            temp2[row] = numbersArray[row][col2];
        }
        // now reverse the columns in the numbersArray:
        for (int row = 0; row < size; row++) {
            numbersArray[row][col1] = temp2[row]; 
        }
        for (int row = 0; row < size; row++) {
            numbersArray[row][col2] = temp1[row]; 
        }
    }

    // determines if the puzzle is decoded:
    public boolean decoded () {
        boolean solved = true;
        for (int row = 0; row < size; row ++) {
            for (int col = 0; col < size; col ++) {
                if (numbersArray[row][col] != row + 1) {
                    solved = false;
                    break;
                }
            }
        }
        return solved;
    }
    
    // displays the contents of the 2D Array:
    public void displayArray() {
    	System.out.println("");

        for (int i = 0; i < size; i ++)
            System.out.print(i + " ");
        System.out.println("");
        System.out.println("");
        for (int row = 0; row < size; row ++) {

            for (int col = 0; col < size; col ++) {
                System.out.print(numbersArray[row][col] + " ");
            }
            System.out.print(" " + row);
            System.out.println("");
        }
        System.out.println("");
        if (decoded()) {
        	System.out.println("Matrix Decoded, Well Done! ");
        }
    }
    
    public void rotate90() {
    	 int [][] newArray = new int [size][size];
         for (int row = 0; row <= size - 1; row ++) {
             for (int col = 0; col <= size - 1; col ++) {
                 newArray [col][size - 1 - row] = numbersArray [row][col]; 
             }
         }
         numbersArray = newArray;	
    }
    
    // create the Graphical User Interface:
    public void createGUI () {
        JFrame frame = new JFrame("HackerPuzzle");
        frame.setVisible(true);
        frame.setSize(200,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel ();
        panel.setBackground(new Color(255,137,0));

        JButton rotate90 = new JButton("Rotate 90");
        rotate90.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    rotate90();
                    displayArray();
                    // for the 'take back' operation:
                    action.push("Rotate90");
                 }
            });
        JButton swap = new JButton ("Swap");
        swap.addActionListener(new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    String coordinates = JOptionPane.showInputDialog("Enter coordinate code:");
                    if (coordinates.length() == 0) {return;}
                    
                    String row1 = coordinates.substring(0,1);
                    String col1 = coordinates.substring(1,2);
                    String row2 = coordinates.substring(2,3);
                    String col2 = coordinates.substring(3);
                    int Row1 = Integer.parseInt(row1);
                    int Col1 = Integer.parseInt(col1);
                    int Row2 = Integer.parseInt(row2);
                    int Col2 = Integer.parseInt(col2);
                    
                    // perform the swap within the Array:
                    int temp = numbersArray[Row1][Col1];
                    numbersArray[Row1][Col1] = numbersArray[Row2][Col2];
                    numbersArray[Row2][Col2] = temp;
                    displayArray();
                    // for the 'take back' operation:
                     coordinate.push(coordinates);
                     action.push("Swap");
                }
            });
        JButton swapRows = new JButton("Swap Rows");
        swapRows.addActionListener(new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    String rows = JOptionPane.showInputDialog("Enter row codes:");
                    if (rows.length() == 0) {return;}
                    
                    String row1 = rows.substring(0,1);
                    String row2 = rows.substring(1);
                    
                    int Row1 = Integer.parseInt(row1);
                    int Row2 = Integer.parseInt(row2);
                    rowSwap(Row1,Row2);
                    displayArray();
                    // for the 'take back' operation:
                    action.push("ReverseRows");
                    coordinate.push(rows);
                }
            });
        JButton swapCols = new JButton("Swap Cols");
        swapCols.addActionListener(new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    String cols = JOptionPane.showInputDialog("Enter col codes:");
                    if (cols.length() == 0) {return;}
                    
                    String col1 = cols.substring(0,1);
                    String col2 = cols.substring(1);
                   
                    int Col1 = Integer.parseInt(col1);
                    int Col2 = Integer.parseInt(col2);
                    colSwap(Col1,Col2);
                    displayArray();
                    // for the 'take back' operation:
                    action.push("ReverseCols");
                    coordinate.push(cols);
                }

            });
        
        panel.add(rotate90);
        panel.add(swap);
        panel.add(swapRows);
        panel.add(swapCols);
        //panel.add(undo);
        frame.add(panel);
    }
    
    // I will create a separate GUI for the 'Undo' operation:
    
    public void undoGUI () {
    	JFrame takeBackMove = new JFrame ("Take Back Move");
    	takeBackMove.setVisible(true);
    	takeBackMove.setSize(100,100);
    	
    	JPanel display = new JPanel();
    	display.setBackground(new Color(255,137,0));
    	
    	JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
            	if (action.empty()) {return;}
            	
            	String actionToReverse = action.pop();
            	
                if (actionToReverse.equals("Swap")) {
                  String coordinates = coordinate.pop();
                  String row1 = coordinates.substring(0,1);
                  String col1 = coordinates.substring(1,2);
                  String row2 = coordinates.substring(2,3);
                  String col2 = coordinates.substring(3);
                  int Row1 = Integer.parseInt(row1);
                  int Col1 = Integer.parseInt(col1);
                  int Row2 = Integer.parseInt(row2);
                  int Col2 = Integer.parseInt(col2);
                  // re-swap:
                  int temp = numbersArray[Row1][Col1];
                  numbersArray[Row1][Col1] = numbersArray[Row2][Col2];
                  numbersArray[Row2][Col2] = temp;
                  displayArray();
                }
                
                if (actionToReverse.equals("Rotate90")) {
                	// reversing a 90 degree turn (i.e turning -90) can be achieved by a 270 turn:
                	rotate90();
                	rotate90();
                	rotate90();
                	displayArray();
                }
                
                if (actionToReverse.equals("ReverseCols")) {
                	String cols = coordinate.pop();
                    String col1 = cols.substring(0,1);
                	String col2 = cols.substring(1);
                	
                	int Col1 = Integer.parseInt(col1);
                    int Col2 = Integer.parseInt(col2);
                    colSwap(Col1,Col2);
                    displayArray();
                }
                
                if (actionToReverse.equals("ReverseRows")) {
                	String rows = coordinate.pop();
                	String row1 = rows.substring(0,1);
                    String row2 = rows.substring(1);
                    
                    int Row1 = Integer.parseInt(row1);
                    int Row2 = Integer.parseInt(row2);
                    rowSwap(Row1,Row2);
                    displayArray();
                }
                
                
            }

        });
        
        
        
        
        display.add(undo);
        takeBackMove.add(display);
    	
    }
    
    public static void main (String [] args) {
    	
    	new Hacker();
    	
    }

}