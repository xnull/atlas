package bynull.poi.service.mapper;

import bynull.poi.dto.GeoPointDto;
import bynull.poi.dto.PoiDto;
import org.neo4j.graphdb.Node;
import org.springframework.core.convert.converter.Converter;

/**
 * Mapping dao entity to dto
 * Created by null on 3/27/16.
 */
public interface PoiMapper {

    class PoiDtoMapper implements Converter<Node, PoiDto> {

        @Override
        public PoiDto convert(Node poiNode) {
            String description = poiNode.getProperty("description").toString();
            Integer floorNumber = Integer.valueOf(poiNode.getProperty("floorNumber").toString());
            Integer roomNumber = Integer.valueOf(poiNode.getProperty("roomNumber").toString());
            Boolean hasWifi = Boolean.valueOf(poiNode.getProperty("hasWifi").toString());
            Double latitude = Double.valueOf(poiNode.getProperty("lat").toString());
            Double longitude = Double.valueOf(poiNode.getProperty("lon").toString());

            GeoPointDto point = new GeoPointDto(latitude, longitude);
            return new PoiDto(point, description, floorNumber, roomNumber, hasWifi);
        }
    }
}
