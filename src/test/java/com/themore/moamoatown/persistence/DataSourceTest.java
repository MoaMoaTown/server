package com.themore.moamoatown.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

/**
 * 데이터 소스 연결 테스트
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
public class DataSourceTest {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	
	@Test
	public void testMyBatis() {
		try (SqlSession session = sqlSessionFactory.openSession();
			 Connection con = session.getConnection()) {
			log.info(session);
			log.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testConnection() {
		try (Connection con = dataSource.getConnection()) {
				log.info(con);
				
			} catch (Exception e) {
				fail(e.getMessage());
		}
	}
}