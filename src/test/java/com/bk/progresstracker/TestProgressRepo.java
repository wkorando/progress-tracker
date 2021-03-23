package com.bk.progresstracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties = { "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect",
		"spring.jpa.hibernate.ddl=create", "spring.jpa.properties.javax.persistence.validation.mode=none" })
public class TestProgressRepo {

	@Autowired
	private ProgressRepo repo;
	private final String JSON_DATA = """
			{
			   "host":"localhost:8080",
			   "user-agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:78.0) Gecko/20100101 Firefox/78.0",
			   "accept":"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
			   "accept-language":"en-US,en;q=0.5",
			   "accept-encoding":"gzip, deflate",
			   "connection":"keep-alive",
			   "cookie":"JSESSIONID=C11438D9AE56178D8821B7F0058B51F1",
			   "upgrade-insecure-requests":"1"
			}
			""";

	private final String TRACKING_ID = "C11438D9AE56178D8821B7F0058B51F1";
	private final String LAB_ID = "test-id";
	private final String STEP_ID = "1";

	@BeforeClass
	public void setup() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Test
	public void testPersistingRecord() {

		ProgressData data = new ProgressData(LAB_ID, STEP_ID, TRACKING_ID, JSON_DATA);
		System.out.println(repo.save(data));
		ProgressData updateData = repo.findById(10L).get();

		LocalDate today = LocalDate.now(ZoneId.of("UTC"));

		assertAll(() -> assertThat(updateData.getLabId()).isEqualTo(LAB_ID),
				() -> assertThat(updateData.getStepId()).isEqualTo(STEP_ID),
				() -> assertThat(updateData.getTrackingId()).isEqualTo(TRACKING_ID),
				() -> assertThat(updateData.getJsonData()).isEqualTo(JSON_DATA),
				() -> assertThat(updateData.getTimestamp().toLocalDate()).isEqualTo(today));
	}

}
