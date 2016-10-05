package battleship;

public abstract class Ship {
    protected final int LENGTH;
    protected final String NAME;
    protected final String OWNER;
    protected final char ID; //Used to identify ship in print.
    
    protected String direction; //WHY CAN'T CHARS BE CONVERTED TO STRINGS?!
    protected boolean destroyed;
    protected int[][] ship_body; //A 2-dimensional array representing ship's status: first dimension is the partID, second contains three compartments: 0 = status (alive- 0 or dead- 1), 1 = x-coordinate, 2 = y-coordinate
    
    public Ship(String name, int length, String owner, char id)
    {
        NAME = name;
        LENGTH = length;
        OWNER = owner;
        ID = id;
        ship_body = new int[LENGTH][3];
        
        destroyed = false;
    }
    
    public void setupShip(int[][] body)
    {
        ship_body = body;
    }
    
    public char[][] setupShip(int[][] body, String direction, char[][] grid) //combines setShipBody and imbed
    {
        ship_body = body;
        this.direction = direction;
        return (imbed(grid));
    }
    
    public Grid setupShip(int[][] body, String direction, Grid grid) //combines setShipBody and imbed
    {
        ship_body = body;
        this.direction = direction;
        return new Grid(imbed(grid.getGrid()));
    }
    
    private char[][] imbed(char[][] grid) //takes the status of the board and changes it
    {
        for (int i=0; i<LENGTH; i++)
        {
            grid[ ship_body[i][1] ][ ship_body[i][2] ] = ID;
        }
        return grid;
    }
    
    public Grid hit(int part, Grid grid) //Returns the grid with the new status of the ship.
    {
        char[][] gridarray = grid.getGrid();
        if (destroyed == false) //ship should still be alive.
        {
            if (ship_body[part][0] != 1) //not destroyed
            {
                ship_body[part][0] = 1;
                System.out.println(OWNER+"'s "+NAME+" was hit!");
                gridarray = setDestroyedStatus(gridarray);
            }
            else //destroyed
            {
                System.out.println("That part of "+OWNER+"'s "+NAME+" was already hit!");
            }
        }
        else
        {
            System.out.println(OWNER+"'s "+NAME+" is already sunk!");
        }
        return new Grid(gridarray);
    }
    
    public char[][] hit(int part, char[][] grid) //Returns the grid with the new status of the ship.
    {
        if (destroyed == false) //ship should still be alive.
        {
            if (ship_body[part][0] != 1) //not destroyed
            {
                ship_body[part][0] = 1;
                System.out.println(OWNER+"'s "+NAME+" was hit!");
                grid = setDestroyedStatus(grid);
            }
            else //destroyed
            {
                System.out.println("That part of "+OWNER+"'s "+NAME+" was already hit!");
            }
        }
        else
        {
            System.out.println(OWNER+"'s "+NAME+" is already sunk!");
        }
        return grid;
    }
    
    private char[][] setDestroyedStatus(char[][] grid ) //Changes destroyed ships to Xs
    {
        if (checkDestroyed())//destroyed
        {
            char[][] newGrid = grid;
            destroyed = true;
            System.out.println(OWNER+"'s "+NAME+" was sunk!");
            int x, y;
            for (int i=0; i<LENGTH; i++)
            {
                x = ship_body[i][1];
                y = ship_body[i][2];
                newGrid[x][y] = 'X';
            }
            return newGrid;
        }
        else
        {
            destroyed = false;
            return grid;
        }
    }
    
    private boolean checkDestroyed()
    {
        boolean isDestroyed = true;
        for (int i=0; i<LENGTH; i++)
        {
            if (ship_body[i][0] != 1)
            {
                isDestroyed = false;
                break;
            }
        }
        return isDestroyed;
    }
    
    public boolean isDestroyed()
    {
        return destroyed;
    }
    
    public int[][] getBody()
    {
        return ship_body;
    }
    
    public int getLength()
    {
        return LENGTH;
    }
    
    public String getName()
    {
        return NAME;
    }
    
    public char getID()
    {
        return ID;
    }
    
    public String getDirection()
    {
        return direction;
    }
}
