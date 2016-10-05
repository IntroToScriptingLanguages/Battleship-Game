/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;
import java.util.Scanner;

/**
 *
 * @author Me
 */
public class Skirmish { //Choose an AI opponent...
    private int DIFFICULTY = 1; //Normal by default.
    final private String[] NAMES = {"Sean", "Larry", "Spencer", "Drake", "Matt", "Aaron", "Teach", "Omar", "Washington", "Gary", "Nimitz", "Yamamoto", "Jacobson", "Simpson", "Armando", "Jaegar", "Kaiju", "Lapras", "Blastoise", "Mewtwo"};//AI names...
    final private String PLAYER;
    final private Scanner scan = new Scanner(System.in);
    private int wins = 0, losses = 0;
    
    public Skirmish(String player)
    {
        PLAYER = player;
    }
    
    public void setDifficulty()
    {
        System.out.println("Please select a difficulty (0-easy, 1-medium, 2-hard, 3-brutal)");
        int difficulty;
        while (true)
        {
            try
            {
                difficulty = scan.nextInt();
                if (difficulty >= 0 && difficulty <= 3)
                {
                    DIFFICULTY = difficulty;
                    break;
                }
                else
                {
                    System.out.println("Invalid difficulty value.  Please choose a number between 0 and 3.");
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid input.");
                scan.next();
            }
        }
    }
    
    public void playSkirmish()
    {
        while (true)
        {
            setDifficulty();
            String enemyname = (NAMES[(int)(Math.random()*20)]);
            System.out.println("Today, your opponent is: Admiral "+enemyname+"!");
            System.out.println("Prepare for battle!");
            Game game = new Game(PLAYER, "Admiral "+enemyname, DIFFICULTY);
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
            scan.nextLine();
            boolean enemy_win = game.makeGame();
            if (enemy_win == false) //You win!
            {
                    System.out.println(PLAYER+" has defeated Admiral "+enemyname+"!");
                    wins++;
            }
            else //You lose!
            {
                    System.out.println(PLAYER+" lost to Admiral "+enemyname+"!");
                    losses++;
            }
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
            printScore();
            System.out.println("Do you want to play again? [y/n]");
            boolean playAgain;
            while (true)
            {
                try
                {
                    String response = scan.next();
                    if ("y".equals(response))
                    {
                        playAgain = true;
                        break;
                    }
                    else if ("n".equals(response))
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
    
    public void printScore()
    {
        System.out.println();
        System.out.println("SCORE:");
        System.out.println();
        System.out.println("Number of wins: "+wins);
        System.out.println();
        System.out.println("Number of losses: "+losses);
        System.out.println();
    }
}
