package battleship;
import java.util.Scanner;

public class Game {
    //- is unrevealed, 0 is never existed, A is aircraft carrier, B is battleship, S is submarine, C is cruiser, P is patrol boat, X is sunk.
    //0 = aircraft carrier, 1 = battleship, 2 = cruiser, 3 = patrol, 4 = submarine
    protected Ship[] Players = new Ship[5];
    protected Ship[] Enemies = new Ship[5];
    protected Scanner scan = new Scanner(System.in);
    protected Grid grid; //You see your enemy's grids in game.
    protected Grid grid_enemy;
    protected Grid_Unrevealed p_grid; //This is what you/your enemy see
    protected Grid_Unrevealed e_grid;
    protected AI ai;
    
    protected final Ship_Aircraft air1;
    protected final Ship_Aircraft air2;
    protected final Ship_Battleship battle1;
    protected final Ship_Battleship battle2;
    protected final Ship_Cruiser cruise1;
    protected final Ship_Cruiser cruise2;
    protected final Ship_Patrol patrol1;
    protected final Ship_Patrol patrol2;
    protected final Ship_Submarine sub1;
    protected final Ship_Submarine sub2;
    
    
    
    protected final int DIFFICULTY; //0 = easy, 1 = normal, 2 = hard, 3 = brutal
    protected final String NAME;
    protected final String ENEMY_NAME;
    protected final String GENERIC_NAME = "Admiral Sheehan";
    
    public Game(String name)
    {
        NAME = "Admiral "+name;
        ENEMY_NAME = GENERIC_NAME;
        grid = new Grid();
        grid_enemy = new Grid();
        p_grid = new Grid_Unrevealed(); //Player sees e_grid, enemy sees p_grid
        e_grid = new Grid_Unrevealed();
        DIFFICULTY = 1; //By default, normal
        
        ai = new AI(DIFFICULTY);
        
         //Declare all the units.
       air1 = new Ship_Aircraft(NAME);
       air2 = new Ship_Aircraft(ENEMY_NAME);
        battle1 = new Ship_Battleship(NAME);
        battle2 = new Ship_Battleship(ENEMY_NAME);
        cruise1 = new Ship_Cruiser(NAME);
        cruise2 = new Ship_Cruiser(ENEMY_NAME);
        patrol1 = new Ship_Patrol(NAME);
        patrol2 = new Ship_Patrol(ENEMY_NAME);
        sub1 = new Ship_Submarine(NAME);
         sub2 = new Ship_Submarine(ENEMY_NAME);
    }
    
    public Game(String name, int difficulty)
    {
        NAME = "Admiral "+name;
        ENEMY_NAME = GENERIC_NAME;
        grid = new Grid();
        grid_enemy = new Grid();
        p_grid = new Grid_Unrevealed();
        e_grid = new Grid_Unrevealed();
        DIFFICULTY = difficulty;
        
        ai = new AI(DIFFICULTY);
        
         //Declare all the units.
       air1 = new Ship_Aircraft(NAME);
       air2 = new Ship_Aircraft(ENEMY_NAME);
        battle1 = new Ship_Battleship(NAME);
        battle2 = new Ship_Battleship(ENEMY_NAME);
        cruise1 = new Ship_Cruiser(NAME);
        cruise2 = new Ship_Cruiser(ENEMY_NAME);
        patrol1 = new Ship_Patrol(NAME);
        patrol2 = new Ship_Patrol(ENEMY_NAME);
        sub1 = new Ship_Submarine(NAME);
         sub2 = new Ship_Submarine(ENEMY_NAME);
    }
    
    public Game(String name, String enemy_name, int difficulty)
    {
        NAME = "Admiral "+name;
        ENEMY_NAME = enemy_name;
        grid = new Grid();
        grid_enemy = new Grid();
        p_grid = new Grid_Unrevealed();
        e_grid = new Grid_Unrevealed();
        DIFFICULTY = difficulty;
        
        ai = new AI(DIFFICULTY);
        
         //Declare all the units.
       air1 = new Ship_Aircraft(NAME);
       air2 = new Ship_Aircraft(ENEMY_NAME);
        battle1 = new Ship_Battleship(NAME);
        battle2 = new Ship_Battleship(ENEMY_NAME);
        cruise1 = new Ship_Cruiser(NAME);
        cruise2 = new Ship_Cruiser(ENEMY_NAME);
        patrol1 = new Ship_Patrol(NAME);
        patrol2 = new Ship_Patrol(ENEMY_NAME);
        sub1 = new Ship_Submarine(NAME);
         sub2 = new Ship_Submarine(ENEMY_NAME);
    }
    
    public boolean makeGame() //Combines setup and play.
    {
        setupGame();
        return (playGame());
    }
    
    private void setupGame() 
    {
        grid.printGrid();
        
        setupUnit(air1);
        setupUnit(battle1);
        setupUnit(cruise1);
        setupUnit(patrol1);
        setupUnit(sub1);
        
        grid_enemy = ai.setupShips(air2, battle2, cruise2, patrol2, sub2);
        
        //Because initializer lists just don't seem to work here... hmm...
        
        Players[0] = air1;
        Players[1] = battle1;
        Players[2] = cruise1;
        Players[3] = patrol1;
        Players[4] = sub1;
        
        Enemies[0] = air2;
        Enemies[1] = battle2;
        Enemies[2] = cruise2;
        Enemies[3] = patrol2;
        Enemies[4] = sub2;
        
        System.out.println();
        System.out.println();
        System.out.println("===========================================================");
        System.out.println("================="+NAME+" vs "+ENEMY_NAME+"================");
        System.out.println("===========================================================");
        System.out.println();
        System.out.println();
        System.out.println("** Press Enter to Continue. **");
        scan.nextLine();
        scan.nextLine();
        //grid_enemy.printGrid();//Debugging
    }
    
    private boolean playGame() //True = player 1 won, False = player 2 won.
    {
         boolean p1_win;
         int turn = 0; //0 = player 1, 1 = player 2
         
         while (playerDefeated() == 0)
         {
             if (turn == 0) //Player 1!
             {
                        System.out.println();
                        System.out.println();
                        System.out.println("================="+NAME+"'s turn================");
                        System.out.println();
                        System.out.println();
                        System.out.println("** Press Enter to Continue. **");
                        scan.nextLine();
                        

                        System.out.println();
                        System.out.println();
                        System.out.println(ENEMY_NAME+"'s Battlefield:");
                        System.out.println();
                        e_grid.printGrid();
                        System.out.println();


                        System.out.println();
                        System.out.println();
                        System.out.println("Input two coordinates (0-9) on the 10x10 grid to ATTACK that target!");
                        System.out.println("The first digit you input is the row, the second is the column.  Don't put spaces between them. \n Ex:\"25\" gets you row 2 (third row), column 5 (sixth column)");
                        int coordinate, x, y;
                        char value = '0';
                        while (true)
                        {
                           try
                           {
                               coordinate = scan.nextInt();
                               if (coordinate < 0 || coordinate > 99) //not a coordinate.
                               {
                                   System.out.println("Invalid coordinate set.  The first digit you input is the row, the second is the column.  Don't put spaces between them. \n Ex:\"25\" gets you row 2 (third row), column 5 (sixth column)");
                               }
                               else
                               {
                                   x = coordinate/10;
                                   y = coordinate%10;
                                   char value_unrevealed = e_grid.getValue(x, y);
                                   if (value_unrevealed != '-') //Already hit
                                   {
                                       System.out.println("This target has already been attacked.  Please attack another.");
                                   }
                                   else
                                   {
                                       value = grid_enemy.getValue(x, y);
                                       break;
                                   }
                               }
                           }
                           catch (Exception e)
                           {
                               System.out.println("Invalid input.");
                               scan.next();
                           }
                       }
                        
                       System.out.print(NAME+" fired on ");
                       printCoordinates(x, y);
                       System.out.println("!");
                       
                        
                       if (value == '0') //Hit water
                       {
                           System.out.println("Hit water!");
                           System.out.println(NAME+"'s attack missed!");
                       }
                       else if (value == 'A')//Hit the aircraft carrier!
                       {
                           System.out.println(NAME+"'s attack hit "+ENEMY_NAME+"'s Aircraft Carrier!");
                           grid_enemy = attackShip(air2, x, y, grid_enemy);
                       }
                       else if (value == 'B')//Hit the battleship!
                       {
                           System.out.println(NAME+"'s attack hit "+ENEMY_NAME+"'s Battleship!");
                           grid_enemy = attackShip(battle2, x, y, grid_enemy);
                       }
                       else if (value == 'C')//Hit the cruiser!
                       {
                           System.out.println(NAME+"'s attack hit "+ENEMY_NAME+"'s Cruiser!");
                           grid_enemy = attackShip(cruise2, x, y, grid_enemy);
                       }
                       else if (value == 'S')//Hit the submarine!
                       {
                           System.out.println(NAME+"'s attack hit "+ENEMY_NAME+"'s Submarine!");
                           grid_enemy = attackShip(sub2, x, y, grid_enemy);
                       }
                       else if (value == 'P')//Hit the patrol ship!
                       {
                           System.out.println(NAME+"'s attack hit "+ENEMY_NAME+"'s Patrol Ship!");
                           grid_enemy = attackShip(patrol2, x, y, grid_enemy);
                       }
                       else
                       {
                           System.out.println(NAME+"'s attack missed!");
                       }
                       
                       System.out.println("** Press Enter to Continue. **");
                        scan.nextLine();
                        scan.nextLine();
                        
                        value = grid_enemy.getGrid()[x][y];
                        e_grid.setGrid(value, x, y);
                       e_grid.correctGrid(grid_enemy);
                       System.out.println();
                        System.out.println();
                        System.out.println(ENEMY_NAME+"'s Battlefield:");
                        System.out.println();
                        e_grid.printGrid('F', x, y);
                        System.out.println();
                        System.out.println("\"F\" denotes the tile that was fired upon.");
                        System.out.println();
                       System.out.println("** Press Enter to Continue. **");
                        scan.nextLine();
                     }
                     else if (turn == 1)//Player 2, the AI
                     {
                         System.out.println();
                        System.out.println();
                        System.out.println("================="+ENEMY_NAME+"'s turn================");
                        System.out.println();
                        System.out.println();
                        System.out.println("** Press Enter to Continue. **");
                        scan.nextLine();
                        
                         System.out.println();
                        System.out.println();
                        System.out.println(NAME+"'s Battlefield:");
                        System.out.println();
                        p_grid.printGrid();
                        System.out.println();
                        System.out.println("** Press Enter to Continue. **");
                        scan.nextLine();
                         
                         int response = ai.attack(p_grid, Players, grid);
                         int x = response/10;
                         int y = response%10;
                         
                         System.out.print(ENEMY_NAME+" fired on ");
                         printCoordinates(x, y);
                         System.out.println("!");
                         
                         char value = grid.getValue(x, y);
                          if (value == '0') //Hit water
                          {
                              System.out.println("Hit water!");
                              System.out.println(ENEMY_NAME+"'s attack missed!");
                          }
                          else if (value == 'A')//Hit the aircraft carrier!
                          {
                              System.out.println(ENEMY_NAME+"'s attack hit "+NAME+"'s Aircraft Carrier!");
                              grid = attackShip(air1, x, y, grid);
                          }
                          else if (value == 'B')//Hit the battleship!
                          {
                              System.out.println(ENEMY_NAME+"'s attack hit "+NAME+"'s Battleship!");
                              grid = attackShip(battle1, x, y, grid);
                          }
                          else if (value == 'C')//Hit the cruiser!
                          {
                              System.out.println(ENEMY_NAME+"'s attack hit "+NAME+"'s Cruiser!");
                              grid = attackShip(cruise1, x, y, grid);
                          }
                          else if (value == 'S')//Hit the submarine!
                          {
                              System.out.println(ENEMY_NAME+"'s attack hit "+NAME+"'s Submarine!");
                              grid = attackShip(sub1, x, y, grid);
                          }
                          else if (value == 'P')//Hit the patrol ship!
                          {
                              System.out.println(ENEMY_NAME+"'s attack hit "+NAME+"'s Patrol Ship!");
                              grid = attackShip(patrol1, x, y, grid);
                          }
                          else
                          {
                              System.out.println(ENEMY_NAME+"'s attack missed!");
                          }

                          System.out.println("** Press Enter to Continue. **");
                           scan.nextLine();
                           

                          value = grid.getGrid()[x][y];
                         p_grid.setGrid(value, x, y);
                          p_grid.correctGrid(grid);
                          System.out.println();
                           System.out.println();
                           System.out.println(NAME+"'s Battlefield:");
                           System.out.println();
                           p_grid.printGrid('F', x, y);
                            System.out.println();
                            System.out.println("\"F\" denotes the tile that was fired upon.");
                           System.out.println();
                          System.out.println("** Press Enter to Continue. **");
                           scan.nextLine();
                     }
                     turn = (turn + 1)%2;
                 }
         
         p1_win = (playerDefeated() == 1);
         return p1_win;
    }
    
    public int playerDefeated() //Checks if a player is defeated.  0 = none, 1 = player1 lost, 2 = player2 lost
    {
        Ship ship;
        boolean defeated = true;
        for (int i=0; i<5; i++) //Check if player one has lost
        {
            ship = Players[i];
            if (ship.isDestroyed() == false)
            {
                defeated = false;
                break;
            }
        }
        
        if (defeated == true)
        {
            System.out.println(ENEMY_NAME+" wins!");
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
             return 1;
        }
        
        defeated = true;
        
        for (int i=0; i<5; i++) //Check if player two has lost
        {
            ship = Enemies[i];
            if (ship.isDestroyed() == false)
            {
                defeated = false;
                break;
            }
        }
        
        if (defeated == true)
        {
            System.out.println(NAME+" wins!");
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
             return 2;
        }
        else
            return 0;
    }
    
    public void setupUnit(Ship ship) //Automatically performs any setup operations.
    {
        String name = ship.getName();
        char ID = ship.getID();
        int length = ship.getLength();
        int[][] imbedding = new int[length][3]; //The ship's body.
        System.out.println("Input two coordinates (0-9) on the 10x10 grid.  This is where the front tile of the "+name+" will be placed.");
        System.out.println("The first digit you input is the row, the second is the column.  Don't put spaces between them. \n Ex:\"25\" gets you row 2 (third row), column 5 (sixth column)");
        int coordinate, x, y;
        while (true)
        {
            try
            {
                coordinate = scan.nextInt();
                if (coordinate < 0 || coordinate > 99) //not a coordinate.
                {
                    System.out.println("Invalid coordinate set.  The first digit you input is the row, the second is the column.  Don't put spaces between them. \n Ex:\"25\" gets you row 2 (third row), column 5 (sixth column)");
                }
                else
                {
                    x = coordinate/10;
                    y = coordinate%10;
                    if (hasEnoughSpace(x, y, length, grid) == false)
                    {
                        System.out.println("Not enough room to deploy this ship here!  Choose another coordinate set.");
                    }
                    else
                    {
                        imbedding[0][1] = x;
                        imbedding[0][2] = y;
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid input.");
                scan.next();
            }
        }
        
        grid.printGrid(ID, x, y);
        
        String direction;
        while (true)
        {
            System.out.println("Select the direction you wish this ship to face.  u = up, d = down, l = left, r = right.");
            try
            {
                direction = scan.next();
                if (!( "u".equals(direction) || "d".equals(direction) || "l".equals(direction) || "r".equals(direction) ))
                {
                    System.out.println("Invalid character.");
                }
                else if (hasEnoughSpace(x, y, length, direction, grid) == false)
                {
                    System.out.println("Something is preventing you from deploying in that direction!  Choose another direction.");
                }
                else
                {
                    break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid input.");
                scan.next();
            }
        }
        
        for (int i=1; i<length; i++) //Row decreases with up, increases with down.  Column decreases with left, increases with right.
        {
            if ("u".equals(direction))
            {
                x--;
            }
            else if ("d".equals(direction))
            {
                x++;
            }
            else if ("l".equals(direction))
            {
                y--;
            }
            else if ("r".equals(direction))
            {
                y++;
            }
            imbedding[i][1] = x;
            imbedding[i][2] = y;
        }
        
        grid = new Grid( ship.setupShip(imbedding, direction, grid.getGrid()) );
        
        grid.printGrid();
    }
    
    public static boolean hasEnoughSpace(int x, int y, int length, Grid grid) //Checks if there is room in all four cardinal directions.
    {
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
    
     public static boolean hasEnoughSpace(int x, int y, int length, String direction, Grid grid) //Checks if there is room in all four cardinal directions.
    {
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
     
     public Grid attackShip(Ship ship, int x, int y, Grid grid)
     {
         Grid newGrid = grid;
         int segment = getShipSegment(ship, x, y);
         newGrid = ship.hit(segment, grid);
         return newGrid;
     }
     
     private int getShipSegment(Ship ship, int x, int y) //Checks if a pair of coordinates is a segment of a ship, returns -1 if not.
     {
         int[][] body = ship.getBody();
         int length = ship.getLength();
         int segment = -1;
         for (int i=0; i<length; i++)
         {
             if (body[i][1] == x && body[i][2] == y)
             {
                 segment = i;
                 return segment;
             }
         }
         System.out.println("Error: cannot find ship.  Returning -1");
         return segment;
     }
    
    public static void printCoordinates(int x, int y)
    {
        System.out.print("("+x+", "+y+")");
    }
}
