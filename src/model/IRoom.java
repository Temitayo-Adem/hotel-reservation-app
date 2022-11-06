package model;

/**
 * Interface that provides the methods for all room types
 */
public interface IRoom {
    String getRoomNumber();
    Double getRoomPrice();
    RoomType getRoomType();
    boolean isFree();
}
