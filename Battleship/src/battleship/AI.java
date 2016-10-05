
package battleship;
import java.util.Random;
//Normal uses a typical hunt-target system, randomly guessing squares until it hits one of your ships, then targeting adjacent squares until it gets another hit, then goes in a straight line.
//Easy does the same, but has a 60% chance of switching to hunt mode and picking a random tile even in target mode.
//Hard, in hunt mode, uses a "checkerboard" system and only picks oscillating colors to speed up hunt.
//Brutal is same as hard, but has a 10% chance of "cheating" and automatically hitting a square with a ship in hunt mode.

public class AI { 
     private Random rand = new Random();
     private final int DIFFICULTY;//0 = easy, 1 = normal, 2 = hard, 3 = brutal
    
     public AI(int difficulty)
     {
         DIFFICULTY = difficulty;
     }
     
     
     
     public Grid setupShips(Ship ... ships) //Setup all the ships!
     {
         Grid grid = new Grid();
         int x = 0, y = 0;
         boolean legit = false;
         for (Ship ship : ships)
         {
             while (legit == false) //Keep searching for a coordinate set that we can place the ship under.
             {
                 x = rand.nextInt(10);
                 y = rand.nextInt(10);
                 if (grid.hasEnoughSpace(x, y, ship.getLength()))
                 {
                     legit = true;
                 }
             }
             //System.out.println(x+"+"+y); //Used for debugging
             grid = setupShip(ship, x, y, grid);
             legit = false;
         }
         return grid;
     }
     
     public Grid setupShip(Ship ship, int x, int y, Grid grid) //Setup one ship.  Provide the coordinates and it'll take care of directions and placements.
     {
         int length = ship.getLength();
         String name = ship.getName();
         
         int[][] imbedding = new int[length][3];
         
         imbedding[0][1] = x;
         imbedding[0][2] = y;
         Grid newGrid = grid;
         if (newGrid.getGrid()[x][y] != '0')
         {
             System.out.println("Attempted to place "+name+" in an invalid location!");
             return newGrid;
         }
         
         String[] directions = {"u", "d", "l", "r"};
         String direction = "u";
         int value = rand.nextInt(4);
         
         for (int i=0; i<4; i++)
         {
            direction = directions[value];
            if (Game.hasEnoughSpace(x, y, length, direction, newGrid)) //There is space!
            {
                break;
            }
            else
            {
                if (i != 3)
                {
                    value = (value + 1) % 4;
                }
                else //This spot is invalid!
                {
                    System.out.println("All directions for "+name+" is blocked!");
                    return newGrid;
                }
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
        newGrid = ship.setupShip(imbedding, direction, newGrid);
        return newGrid;
     }
 
     public int attack(Grid_Unrevealed grid, Ship[] Players, Grid real_grid) //Uses current conditions and difficulty to decide where to attack.  Returns a two-digit number.
     {
         if (DIFFICULTY == 0) //easy
         {
             return (easy_ai(grid, Players));
         }
         else if (DIFFICULTY == 1)//normal
         {
             return (normal_ai(grid, Players));
         }
         else if (DIFFICULTY == 2)//hard
         {
             return (hard_ai(grid, Players));
         }
         else if (DIFFICULTY == 3)//brutal
         {
             return (brutal_ai(grid, Players, real_grid));
         }
         return (normal_ai(grid, Players)); //Returns normal by default.
     }
     
     private char hunt(Grid_Unrevealed grid, Ship[] Players)//Checks to see if AI should hunt or target.  A '0' means hunt, while any letter means target ship with that ID.
     {
          if (grid.contains('P') && Players[3].isDestroyed() == false ) //go after Patrol Boat
          {
              return 'P';
          }
          else if (grid.contains('C') && Players[2].isDestroyed() == false)//go after Cruiser
          {
              return 'C';
          }
          else if (grid.contains('S') && Players[4].isDestroyed() == false)//go after Submarine
          {
              return 'S';
          }
          else if (grid.contains('B') && Players[1].isDestroyed() == false)//go after Battleship
          {
              return 'B';
          }
          else if (grid.contains('A') && Players[0].isDestroyed() == false)//go after Aircraft Carrier
          {
              return 'A';
          }
          else
          {
              return '0';
          }
     }
     
     //Variant for "cheaters".
     private char hunt(Grid grid, Ship[] Players)//Checks to see if AI should hunt or target.  A '0' means hunt, while any letter means target ship with that ID.
     {
          if (grid.contains('P') && Players[3].isDestroyed() == false ) //go after Patrol Boat
          {
              return 'P';
          }
          else if (grid.contains('C') && Players[2].isDestroyed() == false)//go after Cruiser
          {
              return 'C';
          }
          else if (grid.contains('S') && Players[4].isDestroyed() == false)//go after Submarine
          {
              return 'S';
          }
          else if (grid.contains('B') && Players[1].isDestroyed() == false)//go after Battleship
          {
              return 'B';
          }
          else if (grid.contains('A') && Players[0].isDestroyed() == false)//go after Aircraft Carrier
          {
              return 'A';
          }
          else
          {
              return '0';
          }
     }
     
     private int easy_ai(Grid_Unrevealed grid, Ship[] Players) //Easy ai
     {
         int x, y, response;
         char hunt = hunt(grid, Players);
         if (hunt == '0')//Hunt
         {
            do
            {
                response = rand.nextInt(100);
                x = response/10;
                y = response%10;
            }
            while (grid.getGrid()[x][y] != '-');
            return response;
         }
         else //Target.
         {
             if (grid.contains(hunt))
             {
                 if (rand.nextInt(100) < 40) //We actually target the target.
                 {
                    response = grid.locate(hunt);
                    x = response/10;
                    y = response%10;
                    return (findTarget(grid, x, y));
                 }
                 else //We completely ignore the target and do our own thing.
                 {
                    do
                   {
                       response = rand.nextInt(100);
                       x = response/10;
                       y = response%10;
                   }
                   while (grid.getGrid()[x][y] != '-');
                   return response;
                 }
             }
             else
             {
                   do
                   {
                       response = rand.nextInt(100);
                       x = response/10;
                       y = response%10;
                   }
                   while (grid.getGrid()[x][y] != '-');
                   return response;
             }
         }
     }
     
     private int normal_ai(Grid_Unrevealed grid, Ship[] Players) //Normal ai
     {
         int x, y, response;
         char hunt = hunt(grid, Players);
         if (hunt == '0')//Hunt
         {
            do
            {
                response = rand.nextInt(100);
                x = response/10;
                y = response%10;
            }
            while (grid.getGrid()[x][y] != '-');
            return response;
         }
         else //Target
         {
             if (grid.contains(hunt))
             {
                 response = grid.locate(hunt);
                 x = response/10;
                 y = response%10;
                 return (findTarget(grid, x, y));
             }
             else
             {
                   do
                   {
                       response = rand.nextInt(100);
                       x = response/10;
                       y = response%10;
                   }
                   while (grid.getGrid()[x][y] != '-');
                   return response;
             }
         }
     }
     
     private int hard_ai(Grid_Unrevealed grid, Ship[] Players) //Hard ai
     { //Checkboard means that x can be anything, but if x is even y must be even, and if x is odd y must be odd.
         int x, y, response;
         char hunt = hunt(grid, Players);
         if (hunt == '0')//Hunt
         {
            do
            {
                response = rand.nextInt(100);
                x = response/10;
                y = response%5;
                y *= 2;

                if (x%2 == 1) //x is odd
                {
                    y++; //y must also be odd. 
                }
            }
            while (grid.getGrid()[x][y] != '-');
            response = (10 * x)+y;
            return response;
         }
         else //Target
         {
             if (grid.contains(hunt))
             {
                 response = grid.locate(hunt);
                 x = response/10;
                 y = response%10;
                 return (findTarget(grid, x, y));
             }
             else
             {
                   do
                   {
                       response = rand.nextInt(100);
                       x = response/10;
                       y = response%10;
                       y = (y/2)*2; //This performs integer division, chucking the remainder, then multiplies by two to get 0, 2, 4, 6, or 8.
                        if (x%2 == 1) //x is odd
                        {
                            y++; //y must also be odd. 
                        }
                   }
                   while (grid.getGrid()[x][y] != '-');
                   return response;
             }
         }
     }
     
     private int brutal_ai(Grid_Unrevealed grid, Ship[] Players, Grid real_grid) //Brutal ai
     {
         int x, y, response;
         char hunt = hunt(grid, Players);
         if (hunt == '0')//Hunt
         {
            if (rand.nextInt(100) < 90) //We do regular 'ol hunting.
            {
                do
                {
                    response = rand.nextInt(100);
                    x = response/10;
                    y = response%5;
                    y *= 2; 
                    if (x%2 == 1) //x is odd
                    {
                        y++; //y must also be odd. 
                    }
                }
                while (grid.getGrid()[x][y] != '-');
                response = (10 * x)+y;
                return response;
            }
            else //We "cheat" and hit an enemy ship directly.
            {
                char hunted = hunt(real_grid, Players);
                if (hunted != '0') //Hit this enemy ship!
                {
                      response = real_grid.locate('P', 'C', 'S', 'B', 'A');
                      return response;
                }
                else
                {
                    do
                    {
                        response = rand.nextInt(100);
                        x = response/10;
                        y = response%10;
                        y = (y/2)*2; //This performs integer division, chucking the remainder, then multiplies by two to get 0, 2, 4, 6, or 8.
                        if (x%2 == 1) //x is odd
                        {
                            y++; //y must also be odd. 
                        }
                    }
                    while (grid.getGrid()[x][y] != '-');
                    return response;
                }
            }
         }
         else //Target
         {
             if (grid.contains(hunt))
             {
                 response = grid.locate(hunt);
                 x = response/10;
                 y = response%10;
                 return (findTarget(grid, x, y));
             }
             else
             {
                   do
                   {
                       response = rand.nextInt(100);
                       x = response/10;
                       y = response%10;
                       y = (y/2)*2; //This performs integer division, chucking the remainder, then multiplies by two to get 0, 2, 4, 6, or 8.
                        if (x%2 == 1) //x is odd
                        {
                            y++; //y must also be odd. 
                        }
                   }
                   while (grid.getGrid()[x][y] != '-');
                   return response;
             }
         }
     }
     
     private int findTarget(Grid_Unrevealed grid, int x, int y)//This algorithm takes the enemy damaged ship tile, and tries to find additional tiles of the same ship type.
     {//If it can find ship's direction, it will keep following it until it hits an undiscovered tile (where it will attack that tile) or a dead end (where it will go backwards).  You know something's wrong if there's an infinite loop here- that means the ship failed to destroy itself.
         int response;
         int X = x;
         int Y = y;
         char[][] gridArray = grid.getGrid();
         char precedent = checkAdjacencies(grid, X, Y); //Tells us our direction.
         char[] directionGrid = {'u', 'd', 'l', 'r'};
         

         char ahead = gridArray[X][Y]; //Tells us what's ahead.  This starts at x, y and keeps going in the direction of precedent.
         char ID = ahead;
             if (precedent == '0') //We don't know ship's facing. Pick a random tile around this tile.  If no available pick a random tile on the map.
             {
                 int direction_value = rand.nextInt(4);
                 char direction_type = directionGrid[direction_value];
                 for (int i=0; i<4; i++)
                 {
                    if (direction_type == 'u' && X != 0 && gridArray[X-1][Y] == '-') //Pick the tile on top!
                    {
                        response = ( (X-1)*10 ) + Y;
                        return response;
                    }
                    else if (direction_type ==  'd' && X != 9 && gridArray[X+1][Y] == '-')//Pick the tile on bottom!
                    {
                        response = ( (X+1)*10 ) + Y;
                        return response;
                    }
                    else if (direction_type ==  'l' &&  Y != 0 && gridArray[X][Y-1] == '-')//Pick the tile on left!
                    {
                        response = ( (X)*10 ) + (Y-1);
                        return response;
                    }
                    else if (direction_type ==  'r' && Y != 9 && gridArray[X][Y+1] == '-')//Pick the tile on right!
                    {
                        response = ( (X)*10 ) + (Y+1);
                        return response;
                    }
                    else //Keep searching!
                    {
                        direction_value = (direction_value + 1)%4;
                        direction_type = directionGrid[direction_value];
                    }
                 }
                 do // We haven't found anything yet...
                  {
                    response = rand.nextInt(100);
                    x = response/10;
                    y = response%10;
                  }
                  while (grid.getGrid()[x][y] != '-');
                  return response;
             }
             else //We know the direction, follow it!
             {
                 while (true)
                 {
                     boolean move_forward = true;
                    if (precedent == 'u'&& X != 0)
                    {
                        X--;
                    }
                    else if (precedent == 'd' && X != 9)
                    {
                        X++;
                    }
                    else if (precedent == 'l' && Y != 0)
                    {
                        Y--;
                    }
                    else if (precedent == 'r' && Y != 9)
                    {
                        Y++;
                    }
                    else //Hit a wall! Reverse direction!
                    {
                        move_forward = false;
                        precedent = reverseDirection(precedent);
                    }
                    
                    if (move_forward == true)
                    {   
                        ahead = gridArray[X][Y];
                        if (ahead == '-') //Likely enemy ship!  Fire!
                        {
                            response = ( (X)*10 ) + (Y);
                            return response;
                        }
                        else if (ahead == ID) //More ship to scan!  Put this here for reference.
                        {
                            continue;
                        }
                        else  //Dead end!  Reverse direction!
                        {
                            precedent = reverseDirection(precedent);
                        }
                    }
                 }
             }
     }
     
     private char reverseDirection(char direction)
     {
         if (direction == 'u')
             return 'd';
         if (direction == 'd')
             return 'u';
         if (direction == 'l')
             return 'r';
         if (direction == 'r')
             return 'l';
         return '0'; 
     }
     
     private char checkAdjacencies(Grid_Unrevealed grid, int x, int y)//Checks if adjacent tiles have the same character of this tile's character, and points to direction.  A '0' means there aren't any adjacencies.
     {
         char[][] gridArray = grid.getGrid();
         char value = gridArray[x][y];
         
         if (x != 0 && gridArray[x-1][y] == value) //Up
         {
             return 'u';
         }
         else if (x != 9 && gridArray[x+1][y] == value) //Down
         {
             return 'd';
         }
         else if (y != 0 && gridArray[x][y-1] == value) //Left
         {
             return 'l';
         }
         else if (y != 9 && gridArray[x][y+1] == value)//Right
         {
             return 'r';
         }
         else
         {
             return '0';
         }
     }
     
     
     
}
