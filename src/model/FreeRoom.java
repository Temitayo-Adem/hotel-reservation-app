package model;

/**
 * Class that represents rooms that will be "Free" in the program.
 * FreeRoom is a subclass of Room
 */
public class FreeRoom extends Room {


    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, price = 0.0, enumeration);
    }

    @Override
    public String toString() {
        return "Free "+ super.toString();
    }
}
