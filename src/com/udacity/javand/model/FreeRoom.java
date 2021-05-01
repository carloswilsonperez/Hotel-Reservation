package com.udacity.javand.model;

public class FreeRoom extends Room {
    public FreeRoom(String roomId, RoomType roomType) {
        // Set the price to 0
        super(roomId, 0.0, roomType);
    }

    /**
     * @return customized string describing the instance
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
