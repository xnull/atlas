package bynull.poi.service;

import bynull.poi.dao.PoiRepository;
import bynull.poi.dto.PoiDto;
import bynull.poi.rest.messages.PoiQueryMessage;
import bynull.poi.service.mapper.PoiMapper;
import bynull.poi.service.mapper.PoiMapper.PoiDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Poi Service layer, allows to add and search POIs
 * Created by null on 3/26/16.
 */
@Service
public class PoiService {
    @Autowired
    private PoiManager poiManager;
    @Autowired
    private PoiRepository poiRepository;

    public void add(PoiDto poi) {
        poiManager.add(poi);
    }

    public List<PoiDto> find(PoiQueryMessage query) {
        return poiRepository.find(query, new PoiDtoMapper());
    }
}
