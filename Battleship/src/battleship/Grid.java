package battleship;

import java.util.Arrays;

public class Grid { //The grid where this game will take place.
    //- is unrevealed, 0 is never existed, A is aircraft carrier, B is battleship, S is submarine, C is cruiser, P is patrol boat, F is fired, X is destroyed
    char[][] GRID = new char[10][10]; //True grid.  First compartment is row, second is column.
    
    
    public Grid()
    {
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                GRID[i][j] = '0';
            }
        }
    }
    
    public Grid(char[][] grid)
    {
        GRID = grid;
    }
    
    public char[][] getGrid()
    {
        return GRID;
    }
    
    public char getValue(int x, int y)
    {
        return GRID[x][y];
    }
    
    public boolean hasEnoughSpace(int x, int y, int length) //Checks if there is room in all four cardinal directions.
    {
        Grid grid = this;
        boolean up = true, down = true, left = true, right = true;
        int x_up = x, x_down = x, y_left = y, y_right = y;
        for (int i=0; i<length; i++)//Checks up
        {
            if (x_up < 0) //Hit a wall
            {
                up = false;
                break;
            }
            else if (grid.getGrid()[x_up][y] != '0') //Hit another ship.
            {
                up = false;
                break;
            }
            x_up--;
        }
        
        for (int i=0; i<length; i++)//Checks down
        {
            if (x_down > 9) //Hit a wall
            {
                down = false;
                break;
            }
            else if (grid.getGrid()[x_down][y] != '0') //Hit another ship.
            {
               down = false;
                break;
            }
            x_down++;
        }
        
        for (int i=0; i<length; i++)//Checks left
        {
            if (y_left < 0) //Hit a wall
            {
                left = false;
                break;
            }
            else if (grid.getGrid()[x][y_left] != '0') //Hit another ship.
            {
               left = false;
               break;
            }
            y_left--;
        }
        
        for (int i=0; i<length; i++)//Checks right
        {
            if (y_right > 9) //Hit a wall
            {
                right = false;
                break;
            }
            else if (grid.getGrid()[x][y_right] != '0') //Hit another ship.
            {
               right = false;
               break;
            }
            y_right++;
        }
        
        return (up || down || left || right);
    }
    
     public boolean hasEnoughSpace(int x, int y, int length, String direction) //Checks if there is room in all four cardinal directions.
    {
        Grid grid = this;
        boolean up = true, down = true, left = true, right = true;
        int x_up = x, x_down = x, y_left = y, y_right = y;
        
        if ("u".equals(direction))
        {
            for (int i=0; i<length; i++)//Checks up
            {
                if (x_up < 0) //Hit a wall
                {
                    up = false;
                    break;
                }
                else if (grid.getGrid()[x_up][y] != '0') //Hit another ship.
                {
                    up = false;
                    break;
                }
                x_up--;
            }
            return up;
        }
        
        if ("d".equals(direction))
        {
            for (int i=0; i<length; i++)//Checks down
            {
                if (x_down > 9) //Hit a wall
                {
                    down = false;
                    break;
                }
                else if (grid.getGrid()[x_down][y] != '0') //Hit another ship.
                {
                   down = false;
                    break;
                }
                x_down++;
            }
            return down;
        }
        
        if ("l".equals(direction))
        {
            for (int i=0; i<length; i++)//Checks left
            {
                if (y_left < 0) //Hit a wall
                {
                    left = false;
                    break;
                }
                else if (grid.getGrid()[x][y_left] != '0') //Hit another ship.
                {
                   left = false;
                   break;
                }
                y_left--;
            }
            return left;
        }
        
        if ("r".equals(direction))
        {
            for (int i=0; i<length; i++)//Checks right
            {
                if (y_right > 9) //Hit a wall
                {
                    right = false;
                    break;
                }
                else if (grid.getGrid()[x][y_right] != '0') //Hit another ship.
                {
                   right = false;
                   break;
                }
                y_right++;
            }
            return right;
        }
        
        return false; //By default return false.
    }
    
    public void printGrid() //Prints grid.
    {
        System.out.println("Row number is on left side down, column number is on top.");
        System.out.println("\t0|\t1|\t2|\t3|\t4|\t5|\t6|\t7|\t8|\t9|");
        for (int i=0; i<10; i++)
        {
            System.out.print(i+"\t");
            for (int j=0; j<10; j++)
            {
                if (GRID[i][j] == '0')
                    System.out.print('*'+"|\t");
                else
                    System.out.print(GRID[i][j]+"|\t");
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
    
    public void printGrid(String message, int x, int y) //Prints grid with a specified letter at a specified coordinate.
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
                    if (GRID[i][j] == '0')
                    System.out.print('*'+"|\t");
                else
                System.out.print(GRID[i][j]+"|\t");
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
    
        public void printGrid(char message, int x, int y) //Prints grid with a specified letter at a specified coordinate.
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
                    if (GRID[i][j] == '0')
                    System.out.print('*'+"|\t");
                else
                System.out.print(GRID[i][j]+"|\t");
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
        
    public boolean contains(char value)//Checks to see if this grid contains the value
    {
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                if (GRID[i][j] == value)
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
                if (GRID[i][j] == value)
                    return ((i*10)+j);
            }
        }
        return null;
    }
    
    public Integer locate(Character... values)//Variant that accepts a bunch of char values to test.
    {
        while (true)
        {
            for (int i=0; i<10; i++)
            {
                for (int j=0; j<10; j++)
                {
                    if (Math.random()*100 < 20) //So it doesn't always pick the upper-left most character.
                    {
                        if (Arrays.asList( values ).contains(GRID[i][j]))
                            return ((i*10)+j);
                    }
                }
            }
        }
    }

}
