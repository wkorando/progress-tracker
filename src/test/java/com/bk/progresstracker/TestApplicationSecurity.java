package com.bk.progresstracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mifmif.common.regex.Generex;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { ProgressTrackerController.class, SecurityConfig.class })
@ExtendWith(MockitoExtension.class)
public class TestApplicationSecurity {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestApplicationSecurity.class);
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ProgressService progressService;
	@MockBean
	private ObjectMapper mapper;
	@MockBean
	private ProgressRepo progressRepo;

	@AfterEach
	public void cleanup() {
		Mockito.reset(mapper, progressService);
	}

	@Test
	public void testTrackingServiceRequest(@Mock ObjectNode mockNode) throws Exception {
		when(mapper.createObjectNode()).thenReturn(mockNode);
		when(mockNode.toString()).thenReturn("mockValue");

		mockMvc.perform(MockMvcRequestBuilders.get("/p/test-lab/1"))//
				.andExpect(status().isOk())//
				.andReturn();
	}

	/**
	 * Verify that Spring security is not rejecting request to
	 * {@code /actuator/health}. Actuator not being setup, so can't check for 200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCallActuatorHealthEndpoint() throws Exception {

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))//
				.andReturn();

		assertThat(result.getResponse().getStatus()).isNotEqualTo(403);
		assertThat(result.getResponse().getStatus()).isNotEqualTo(401);
	}

}
