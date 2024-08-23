package com.themore.moamoatown.persistence;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:**/*-context.xml")
@Log4j
public class JDBCTest {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		Properties properties = new Properties();
        try {
			properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			fail(e.getMessage());
		}

        String url = properties.getProperty("JDBC_URL");
        String username = properties.getProperty("DATABASE_USERNAME");
        String password = properties.getProperty("DATABASE_PASSWORD");
		try (
				Connection con = DriverManager.getConnection(url, username, password)) {
				log.info("connection :"+ con);
			} catch (Exception e) {
				fail(e.getMessage());
		}
	}
}