package com.themore.moamoatown.town;

import com.themore.moamoatown.town.dto.TownCreateRequestDTO;
import com.themore.moamoatown.town.service.TownService;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log4j
public class TownServiceTests {
    @Autowired
    private TownService service;

    @Test
    public void testTownCreate() throws Exception {
        TownCreateRequestDTO townCreateRequestDTO = TownCreateRequestDTO.builder()
                .name("원정이네")
                .description("냠냠")
                .payCycle(7L)
                .townCode("TESTTEST")  // 서버에서 생성한 타운 코드
                .build();
        log.info("타운 생성 :"+service.createTown(townCreateRequestDTO, 5L));
    }
}