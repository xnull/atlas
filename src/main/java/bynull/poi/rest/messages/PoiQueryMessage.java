package bynull.poi.rest.messages;

import bynull.poi.dto.GeoPointDto;

/**
 * Query message to find POI's nearby some location
 * Created by null on 3/26/16.
 */
public class PoiQueryMessage implements Message {
    private GeoPointDto location;
    private double distance = 10;
    private String description;
    private Integer floorNumber;
    private Integer roomNumber;
    private boolean hasWifi;

    private PoiQueryMessage() {
    }

    public PoiQueryMessage(GeoPointDto location, double distance, String description, Integer floorNumber, Integer roomNumber, boolean hasWifi) {
        this.location = location;
        this.distance = distance;
        this.description = description;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.hasWifi = hasWifi;
    }

    public GeoPointDto getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasWifi() {
        return hasWifi;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PoiQueryMessage{");
        sb.append("location=").append(location);
        sb.append(", distance=").append(distance);
        sb.append(", description='").append(description).append('\'');
        sb.append(", floorNumber=").append(floorNumber);
        sb.append(", roomNumber=").append(roomNumber);
        sb.append(", hasWifi=").append(hasWifi);
        sb.append('}');
        return sb.toString();
    }
}
