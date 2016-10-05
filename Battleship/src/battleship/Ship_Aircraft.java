package battleship;

public class Ship_Aircraft extends Ship{
    private final static int length = 5;
    private final static String name = "Aircraft Carrier";
    private final static char id = 'A';
    
    public Ship_Aircraft(String owner)
    {
        super(name, length, owner, id);
    }
}
