package battleship;

public class Ship_Submarine extends Ship{
    private final static int length = 3;
    private final static String name = "Submarine";
    private final static char id = 'S';
    
    public Ship_Submarine(String owner)
    {
        super(name, length, owner, id);
    }
}
