package bynull.poi.dao;

import bynull.poi.dto.GeoPointDto;
import bynull.poi.dto.PoiDto;
import bynull.poi.rest.messages.PoiQueryMessage;
import bynull.poi.service.mapper.PoiMapper.PoiDtoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * The PoiRepository Test
 * Created by null on 3/25/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PoiRepositoryTest.class)
@Configuration
@ComponentScan("bynull.poi.dao")
@DirtiesContext
public class PoiRepositoryTest {

    @Autowired
    private PoiRepository repo;

    @Test
    public void find() throws Exception {
        cleanNeo4jDb();

        Integer floorNumber = new Random().nextInt(1000000);
        PoiDto poi = new PoiDto(new GeoPointDto(56, 16), "test description", 555, 123, true);
        PoiDto poi2 = new PoiDto(new GeoPointDto(56, 16.1), "test description", floorNumber, 123, true);
        PoiDto poi3 = new PoiDto(new GeoPointDto(55, 15), "test description", floorNumber, 123, true);

        repo.add(poi);
        repo.add(poi2);
        repo.add(poi3);

        List<PoiDto> result = repo.find(
                new PoiQueryMessage(new GeoPointDto(56, 16), 10000D, "", floorNumber, 123, true),
                new PoiDtoMapper()
        );

        assertEquals(2, result.size());
    }

    private void cleanNeo4jDb() {
        File dbPath = new File("/tmp/geo.db");
        FileSystemUtils.deleteRecursively(dbPath);
    }
}