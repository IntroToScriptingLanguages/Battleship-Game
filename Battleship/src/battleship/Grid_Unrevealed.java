/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;

/**
 *
 * @author Me
 */
public class Grid_Unrevealed{ //Originally planned for this to be an extension of Grid, but noted two don't really interact with each other.
    char[][] GRID_UNREVEALED = new char[10][10]; //Unrevealed grid.
    
    public Grid_Unrevealed()
    {
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                GRID_UNREVEALED[i][j] = '-';
            }
        }
    }
    
    public Grid_Unrevealed(char[][] grid)
    {
        GRID_UNREVEALED = grid;
    }
    
    public char[][] getGrid()
    {
        return GRID_UNREVEALED;
    }
    
    public char getValue(int x, int y)
    {
        return GRID_UNREVEALED[x][y];
    }
    
    public void setGrid(char[][] grid) //changes entire grid
    {
        GRID_UNREVEALED = grid;
    }
    
    public void setGrid(char message, int x, int y) //changes one tile in grid
    {
        GRID_UNREVEALED[x][y] = message;
    }
    
    public void correctGrid(Grid grid)//Changes all revealed tiles to their proper values.
    {
        char[][] grid_array = grid.getGrid();
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                if (GRID_UNREVEALED[i][j] != '-') //revealed
                {
                    GRID_UNREVEALED[i][j] = grid_array[i][j];
                }
            }
        }
    }
    
    public boolean contains(char value)//Checks to see if this grid contains the value
    {
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                if (GRID_UNREVEALED[i][j] == value)
                    return true;
            }
        }
        
        return false;
    }
    
    public Integer locate(char value)//Gives a coordinate set if the value exists, otherwise returns null.  Should not use if contains returns false.
    {
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                if (GRID_UNREVEALED[i][j] == value)
                    return ((i*10)+j);
            }
        }
        return null;
    }
    
    public void printGrid() //Prints unrevealed grid.
    {
        System.out.println("Row number is on left side down, column number is on top.");
        System.out.println("\t0|\t1|\t2|\t3|\t4|\t5|\t6|\t7|\t8|\t9|");
        for (int i=0; i<10; i++)
        {
            System.out.print(i+"\t");
            for (int j=0; j<10; j++)
            {
                if (GRID_UNREVEALED[i][j] == '0')
                    System.out.print('*'+"|\t");
                else
                System.out.print(GRID_UNREVEALED[i][j]+"|\t");
            }
            System.out.println();
            for (int k=0; k<83; k++)
            {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println("Row number is on left side down, column number is on top.");
        System.out.println();
    }
    
        public void printGrid(String message, int x, int y) //Prints unrevealed grid.
    {
            System.out.println("Row number is on left side down, column number is on top.");
        System.out.println("\t0|\t1|\t2|\t3|\t4|\t5|\t6|\t7|\t8|\t9|");
        for (int i=0; i<10; i++)
        {
            System.out.print(i+"\t");
            for (int j=0; j<10; j++)
            {
                if (i == x && j == y) //Print here!
                {
                    System.out.print(message+"|\t");
                }
                else
                {
                    if (GRID_UNREVEALED[i][j] == '0')
                    System.out.print('*'+"|\t");
                else
                    System.out.print(GRID_UNREVEALED[i][j]+"|\t");
                }
            }
            System.out.println();
            for (int k=0; k<83; k++)
            {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println("Row number is on left side down, column number is on top.");
        System.out.println();
    }
        
     public void printGrid(char message, int x, int y) //Prints unrevealed grid.
    {
         System.out.println("Row number is on left side down, column number is on top.");
        System.out.println("\t0|\t1|\t2|\t3|\t4|\t5|\t6|\t7|\t8|\t9|");
        for (int i=0; i<10; i++)
        {
            System.out.print(i+"\t");
            for (int j=0; j<10; j++)
            {
                if (i == x && j == y) //Print here!
                {
                    System.out.print(message+"|\t");
                }
                else
                {
                    if (GRID_UNREVEALED[i][j] == '0')
                    System.out.print('*'+"|\t");
                    else
                    System.out.print(GRID_UNREVEALED[i][j]+"|\t");
                }
            }
            System.out.println();
            for (int k=0; k<83; k++)
            {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println("Row number is on left side down, column number is on top.");
        System.out.println();
    }
}
