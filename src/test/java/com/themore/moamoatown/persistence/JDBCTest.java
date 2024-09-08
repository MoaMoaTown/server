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

/**
 * JDBC 연결 테스트
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정       최초 생성
 * </pre>
 */

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