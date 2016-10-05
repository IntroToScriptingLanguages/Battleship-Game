package battleship;
import java.util.Random;
import java.util.Scanner;

public class Battleship {



    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input your name here:");
        String name = "";
        while(true)
        {
            try
            {
                name = scan.next();
                break;
            }
            catch (Exception e)
            {
                System.out.println("Invalid input.");
                scan.next();
            }
        }
        Random rand = new Random();
        
        boolean on = true;
        //Create game
        while (on)
        {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("=================================");
        
            System.out.println("=================================");
            System.out.println("|          BATTLESHIP           |");
            System.out.println("=================================");
            System.out.println();
            System.out.println("Welcome, "+name+"!  Please select a mode below.");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("s-Skirmish\nh-Hot-Seat\nq-Quit");
            String response;
            while(true)
            {
                //try
                //{
                    response = scan.next();
                    if("h".equals(response)) //Hot-Seat
                    {
                            int p1 = 0, p2 = 0;
                            System.out.println("Please input the second player's name here:");
                            String name2 = "";
                            while(true)
                            {
                                try
                                {
                                    name2 = scan.next();
                                    break;
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Invalid input.");
                                    scan.next();
                                }
                            }
                            name2 = "Admiral "+name2;
                                    while (true)
                                    {
                                        
                                        HotSeatGame campaign = new HotSeatGame(name, name2);
                                       boolean win = campaign.playMatch();
                                       if (win) //P1 wins
                                       {
                                           System.out.println("Admiral "+name+" defeats "+name2+"!");
                                           p1++;
                                       }
                                       else
                                       {
                                           System.out.println(name2+" defeats Admiral "+name+"!");
                                           p2++;
                                       }
                                       System.out.println();
                                        System.out.println("SCORE:");
                                        System.out.println();
                                        System.out.println("Admiral "+name+": "+p1);
                                        System.out.println();
                                        System.out.println(name2+": "+p2);
                                        System.out.println();
                                    System.out.println("Do you want to play again? [y/n]");
                                    boolean playAgain;
                                    while (true)
                                    {
                                        try
                                        {
                                            String yresponse = scan.next();
                                            if ("y".equals(yresponse))
                                            {
                                                playAgain = true;
                                                break;
                                            }
                                            else if ("n".equals(yresponse))
                                            {
                                                playAgain = false;
                                                break;
                                            }
                                            else
                                            {
                                                System.out.println("Input \"y\" if you want to play again and \"n\" if you want to quit.");
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            System.out.println("Invalid input.");
                                            scan.next();
                                        }
                            }
                            if (playAgain == false)
                            {
                                break;
                            }
                        }
                    }
                    else if("s".equals(response))//Skirmish
                    {
                        Skirmish skirmish = new Skirmish(name);
                        skirmish.playSkirmish();
                        break;
                    }
                    else if("q".equals(response)) //Quit
                    {
                        on = false;
                        break;
                    }
                    else
                     {
                         System.out.println("Invalid input.");
                     }
               // }
              // catch (Exception e)
              // {
              //     System.out.println("Invalid input.");
              //     scan.next();
              // }
            }
        }
        
        System.out.println("Thanks for playing!");
        System.out.println("** Press Enter to Quit. **");
        scan.nextLine();
        scan.nextLine();
    }
}
    

