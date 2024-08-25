package com.themore.moamoatown.coordi;

import com.themore.moamoatown.coordi.service.CoordiService;
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
public class CoordiServiceTests {
    @Autowired
    private CoordiService service;

    @Test
    public void testGetMyClothes() throws Exception {
        log.info(service.getMyClothes(17L));
    }
}

