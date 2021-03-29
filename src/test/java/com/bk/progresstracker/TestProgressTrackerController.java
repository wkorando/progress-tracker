package com.bk.progresstracker;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class })
@ContextConfiguration(classes = { ProgressTrackerController.class })
public class TestProgressTrackerController {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	@MockBean
	private ObjectMapper mapper;
	@MockBean
	private ProgressService progressService;

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(documentationConfiguration(restDocumentation))
				.build();
	}

	@Test
	public void testProgressTrackerController(@Mock ObjectNode mockNode) throws Exception {
		when(mapper.createObjectNode()).thenReturn(mockNode);
		when(mockNode.toString()).thenReturn("mockValue");

		mockMvc.perform(RestDocumentationRequestBuilders.get("/p/{labId}/{stepId}", "testId", "1"))//
				.andDo(document("send-tracking-data", pathParameters(
						parameterWithName("labId").description("Should be human readable lab id. Example: if you have a workshop for deploying Java applications on Kubernetes, a lab id could be: `java-on-k8s`"),
						parameterWithName("stepId").description(
								"The step within the lab the user is on. Recommend this be a numeric value, but is stored as a String."))))
				.andExpect(status().isOk())//
				.andExpect(content().contentType(MediaType.IMAGE_PNG))//
				.andReturn();
	}
}
