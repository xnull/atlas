package bynull.poi.dao;

import bynull.poi.dto.GeoPointDto;
import bynull.poi.dto.PoiDto;
import bynull.poi.rest.messages.PoiQueryMessage;
import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Poi repository access object. Provides access to a geo database. Geo db is based on Neoj4 spatial
 * Transaction management note: in this simple example I don't need manage transactions on a server layer,
 * so I decided to do transaction management on a dao layer.
 *
 * Created by null on 3/27/16.
 */
@Repository
public class PoiRepository {
    private static final Logger log = LoggerFactory.getLogger(PoiRepository.class);
    private static final String POI_INDEX_NAME = "PoiIndex";

    private GraphDatabaseService graphDb;

    @PostConstruct
    public void init() {
        File dbPath = new File("/tmp/geo.db");
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    /**
     * Add a poi to the database
     *
     * @param poi poi
     */
    public void add(PoiDto poi) {
        log.trace("Save a POI: {}", poi);

        try (Transaction tx = graphDb.beginTx()) {
            Label poiLabel = DynamicLabel.label("POI");

            Node poiNode = graphDb.createNode(poiLabel);

            poiNode.setProperty("description", poi.getDescription());
            poiNode.setProperty("floorNumber", poi.getFloorNumber());
            poiNode.setProperty("roomNumber", poi.getRoomNumber());
            poiNode.setProperty("hasWifi", poi.hasWifi());
            poiNode.setProperty("lat", poi.getGeoPoint().getLatitude());
            poiNode.setProperty("lon", poi.getGeoPoint().getLongitude());

            Index<Node> index = graphDb.index().forNodes(POI_INDEX_NAME, SpatialIndexProvider.SIMPLE_POINT_CONFIG);
            index.add(poiNode, "", "");

            tx.success();
        }
    }

    public <T> List<T> find(PoiQueryMessage queryMessage, Converter<Node, T> converter) {
        log.trace("Find poi with query: {}", queryMessage);

        try (Transaction tx = graphDb.beginTx()) {
            Index<Node> index = graphDb.index().forNodes(POI_INDEX_NAME, SpatialIndexProvider.SIMPLE_POINT_CONFIG);

            GeoPointDto location = queryMessage.getLocation();

            Map<String, Object> params = new HashMap<>();
            params.put(LayerNodeIndex.POINT_PARAMETER, new Double[]{location.getLatitude(), location.getLongitude()});
            params.put(LayerNodeIndex.DISTANCE_IN_KM_PARAMETER, queryMessage.getDistance());

            List<T> result = new ArrayList<>();
            for (Node poiNode : index.query(LayerNodeIndex.WITHIN_DISTANCE_QUERY, params)) {
                Integer floorNumber = Integer.valueOf(poiNode.getProperty("floorNumber").toString());
                Integer roomNumber = Integer.valueOf(poiNode.getProperty("roomNumber").toString());
                Boolean hasWifi = Boolean.valueOf(poiNode.getProperty("hasWifi").toString());

                //The Filters should be migrated into a query
                if (!floorNumber.equals(queryMessage.getFloorNumber())) {
                    continue;
                }
                if (!roomNumber.equals(queryMessage.getRoomNumber())) {
                    continue;
                }
                if (!hasWifi.equals(queryMessage.hasWifi())){
                    continue;
                }

                result.add(converter.convert(poiNode));
            }

            tx.success();
            return result;
        }
    }
}
