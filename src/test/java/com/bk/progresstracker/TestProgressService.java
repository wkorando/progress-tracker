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
		ObjectNode mockNode = Mockito.mock(ObjectNode.class);

		when(mockNode.toString()).thenReturn("{}");

		service.saveTrackingInfo("labId", "1", "12345", mockNode);

		// Call to Repo to persist data should occur
		verify(progressRepo, times(1)).save(any(ProgressData.class));
	}

}
