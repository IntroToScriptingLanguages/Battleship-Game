package battleship;

public class HotSeatGame extends Game{
    
    String ENEMY_NAME = ""; //Overrides other name.
    
    public HotSeatGame(String name, String enemy_name)
    {
        super(name, enemy_name, 0);
        ENEMY_NAME = enemy_name;
    }
    
     public boolean playMatch() //Combines setup and play.
    {
                setupGame();
                boolean P2_wins = (playGame());
                if (P2_wins)
                {

                    return false;
                }
                else
                {
                    
                    return true;
                }

    }
    
    private void setupGame() 
    {
        System.out.println("===========================================================");
        System.out.println("================="+NAME+", SETUP YOUR SHIPS!================");
        System.out.println("===========================================================");
        System.out.println();
        System.out.println();
        System.out.println("** Press Enter to Continue. **");
        scan.nextLine();
        grid.printGrid();
        
        setupUnit(air1);
        setupUnit(battle1);
        setupUnit(cruise1);
        setupUnit(patrol1);
        setupUnit(sub1);
        
        System.out.println("===========================================================");
        System.out.println("================="+ENEMY_NAME+", SETUP YOUR SHIPS!================");
        System.out.println("===========================================================");
        System.out.println();
        System.out.println();
        System.out.println("** Press Enter to Continue. **");
        scan.nextLine();
        scan.nextLine();
        
        grid_enemy.printGrid();
        
        setupUnitEnemy(air2);
        setupUnitEnemy(battle2);
        setupUnitEnemy(cruise2);
        setupUnitEnemy(patrol2);
        setupUnitEnemy(sub2);
        
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
                                   char value_unrevealed = p_grid.getValue(x, y);
                                   if (value_unrevealed != '-') //Already hit
                                   {
                                       System.out.println("This target has already been attacked.  Please attack another.");
                                   }
                                   else
                                   {
                                       value = grid.getValue(x, y);
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
                         
                         System.out.print(ENEMY_NAME+" fired on ");
                         printCoordinates(x, y);
                         System.out.println("!");
                         
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
    
    public void setupUnitEnemy(Ship ship) //Automatically performs any setup operations.
    {
        Grid this_grid = grid_enemy;
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
                    if (hasEnoughSpace(x, y, length, this_grid) == false)
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
        
        this_grid.printGrid(ID, x, y);
        
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
                else if (hasEnoughSpace(x, y, length, direction, this_grid) == false)
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
        
        this_grid = new Grid( ship.setupShip(imbedding, direction, this_grid.getGrid()) );
        
        this_grid.printGrid();
        grid_enemy = this_grid;
    }
}
