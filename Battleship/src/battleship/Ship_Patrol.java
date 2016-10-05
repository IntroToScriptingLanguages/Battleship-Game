package battleship;

public class Ship_Patrol extends Ship{
    private final static int length = 2;
    private final static String name = "Patrol Ship";
    private final static char id = 'P';
    
    public Ship_Patrol(String owner)
    {
        super(name, length, owner, id);
    }
}
