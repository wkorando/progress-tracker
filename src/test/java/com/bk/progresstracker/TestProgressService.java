package com.bk.progresstracker;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@ExtendWith(MockitoExtension.class)
public class TestProgressService {

	@Test
	public void testReceiveValidTrackingData(@Mock ProgressRepo progressRepo) {
		ProgressService service = new ProgressService(progressRepo);
		String HOST_NAME = "registeredDomain.com";
		ObjectNode mockNode = Mockito.mock(ObjectNode.class);
		JsonNode mockHostNode = Mockito.mock(JsonNode.class);

		when(mockNode.toString()).thenReturn("{}");
		when(mockNode.get("host")).thenReturn(mockHostNode);
		when(mockHostNode.asText()).thenReturn(HOST_NAME);

		service.saveTrackingInfo("labId", "1", "12345", mockNode);

		// Call to Repo to persist data should occur
		verify(progressRepo, times(1)).save(any(ProgressData.class));
	}

}
