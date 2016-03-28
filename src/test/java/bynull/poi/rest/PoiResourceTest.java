package bynull.poi.rest;

import bynull.poi.Application;
import bynull.poi.dto.GeoPointDto;
import bynull.poi.dto.PoiDto;
import bynull.poi.rest.messages.OkMessage;
import bynull.poi.service.PoiManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static bynull.poi.rest.PoiResource.POI_ENDPOINT;
import static bynull.poi.rest.PoiResource.SAVE_POI_ENDPOINT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
@DirtiesContext
public class PoiResourceTest {
    private static final String HOST = "http://localhost:9000/";

    private RestTemplate restTemplate = new TestRestTemplate("admin", "admin");

    @Autowired
    private PoiManager poiManager;

    @Test
    public void add() throws Exception {
        PoiDto poi = new PoiDto(new GeoPointDto(1.11D, 1.22D), "Test place", 1, 3, true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        HttpEntity<PoiDto> entity = new HttpEntity<>(poi, headers);

        ResponseEntity<OkMessage> response = restTemplate.postForEntity(
                HOST + POI_ENDPOINT + SAVE_POI_ENDPOINT,
                entity,
                OkMessage.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getBody().getResult(), "A POI has added to the store");

        poiManager.awaitTermination();
    }
}