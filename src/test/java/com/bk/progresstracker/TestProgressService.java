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
	public void testReceiveValidTrackingData(@Mock ProgressRepo progressRepo,
			@Mock RegisteredDomainRepo registeredDomainRepo) {
		ProgressService service = new ProgressService(progressRepo, registeredDomainRepo);
		String HOST_NAME = "registeredDomain.com";
		ObjectNode mockNode = Mockito.mock(ObjectNode.class);
		JsonNode mockHostNode = Mockito.mock(JsonNode.class);
		
		when(mockNode.toString()).thenReturn("{}");
		when(mockNode.get("host")).thenReturn(mockHostNode);
		when(mockHostNode.asText()).thenReturn(HOST_NAME);
		when(registeredDomainRepo.findByDomainHost(HOST_NAME))
				.thenReturn(Optional.of(new RegisteredDomain(1, HOST_NAME)));

		service.saveTrackingInfo("labId", "1", "12345", mockNode);
		
		//Call to Repo to persist data should occur
		verify(progressRepo, times(1)).save(any(ProgressData.class));
	}
	
	@Test
	public void testReceiveTrackingDataFromUnregisteredDomain(@Mock ProgressRepo progressRepo,
			@Mock RegisteredDomainRepo registeredDomainRepo) {
		ProgressService service = new ProgressService(progressRepo, registeredDomainRepo);
		String UNREGISTERED_HOST_NAME = "unregisteredDomain.com";
		ObjectNode mockNode = Mockito.mock(ObjectNode.class);
		JsonNode mockHostNode = Mockito.mock(JsonNode.class);
		
		when(mockNode.get("host")).thenReturn(mockHostNode);
		when(mockHostNode.asText()).thenReturn(UNREGISTERED_HOST_NAME);
		when(registeredDomainRepo.findByDomainHost(UNREGISTERED_HOST_NAME))
				.thenReturn(Optional.ofNullable(null));

		service.saveTrackingInfo("labId", "1", "12345", mockNode);

		//Call to Repo to persist data should NOT occur
		verify(progressRepo, times(0)).save(any(ProgressData.class));
	}
}
