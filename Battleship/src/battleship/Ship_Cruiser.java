package battleship;

public class Ship_Cruiser extends Ship{
    private final static int length = 3;
    private final static String name = "Cruiser";
    private final static char id = 'C';
    
    public Ship_Cruiser(String owner)
    {
        super(name, length, owner, id);
    }
}
