package battleship;

public class Ship_Battleship extends Ship{
    private final static int length = 4;
    private final static String name = "Battleship";
    private final static char id = 'B';
    
    public Ship_Battleship(String owner)
    {
        super(name, length, owner, id);
    }
}
