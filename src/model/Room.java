package model;

import java.util.Objects;

/**
 * Room class objects represent each individual room in the program, along with its type and price
 */
public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
//        Room is a superclass (it's subclass is FreeRoom)
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setEnumeration(RoomType enumeration) {
        this.enumeration = enumeration;
    }
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {

        return enumeration;
    }

    @Override
    public boolean isFree() {

        return false;
    }

    @Override
    public String toString() {
        return "Room: " + roomNumber + " Price per night: $" + price + " Room type: " + enumeration + "\n";
    }
//    Overriding the "equals" method to ensure that two Room Objects/Instances with the same values/properties (though stored elsewhere in memory)
//    are considered equal.
//    This is necessary because the default equals method simply uses the equality operator.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(price, room.price) && enumeration == room.enumeration;
    }
//    Overriding hashCode method to ensure that two instances/objects that are equal (through equals method)
//    cannot have different hashCodes (for use in hashMaps). roomNumber is the only property hashed in this program.
    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
