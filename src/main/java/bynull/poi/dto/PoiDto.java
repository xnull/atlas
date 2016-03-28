package bynull.poi.dto;

/**
 * Point of interest
 * Created by null on 3/27/16.
 */
public class PoiDto {
    private GeoPointDto geoPoint;
    private String description;
    private Integer floorNumber;
    private Integer roomNumber;
    private boolean hasWifi;

    //Json serialization
    public PoiDto() {

    }

    public PoiDto(GeoPointDto geoPoint, String description, Integer floorNumber, Integer roomNumber, boolean wifi) {
        this.geoPoint = geoPoint;
        this.description = description;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.hasWifi = wifi;
    }

    public GeoPointDto getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPointDto geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean hasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PoiDto{");
        sb.append("geoPoint=").append(geoPoint);
        sb.append(", description='").append(description).append('\'');
        sb.append(", floorNumber=").append(floorNumber);
        sb.append(", roomNumber=").append(roomNumber);
        sb.append(", hasWifi=").append(hasWifi);
        sb.append('}');
        return sb.toString();
    }
}
