package com.udacity.javand.model;

public class Room implements IRoom {
    private String roomId;
    private Double price;
    private RoomType roomType;
    public Boolean isFree = true;

    public Room(String roomId, Double price, RoomType enumeration) {
        this.roomId = roomId;
        this.price = price;
        this.roomType = enumeration;
    }

    @Override
    public String getroomId() {
        return this.roomId;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        this.isFree = !this.isFree;
        return this.isFree;
    }

    @Override
    public int hashCode() {
        // This allows for rooms "numbers" having letters or symbols
       return this.getroomId().hashCode();
    }

    @Override public boolean equals(Object obj)
    {
        IRoom anotherRoom = (IRoom) obj; // type casting object to the intended class type

        // checking if the two two
        // objects share all the same values
        return this.getroomId().equals(anotherRoom.getroomId());
    }

    /**
     * @return customized string describing the instance
     */
    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", price=" + price +
                ", enumeration=" + roomType +
                '}';
    }
}
