package com.bk.progresstracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Service class for verifying requests and sending information to repo to be
 * persisted.
 * 
 * @author bkorando
 *
 */
@Service
public class ProgressService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgressService.class);
	private ProgressRepo progressRepo;
	private RegisteredDomainRepo registeredDomainRepo;

	public ProgressService(ProgressRepo progressRepo, RegisteredDomainRepo registeredDomainRepo) {
		this.progressRepo = progressRepo;
		this.registeredDomainRepo = registeredDomainRepo;
	}

	public void saveTrackingInfo(String labId, String stepId, String sessionId, ObjectNode jsonData) {
		if (isDomainRegistered(jsonData)) {
			ProgressData data = new ProgressData(labId, stepId, sessionId, jsonData.toString());
			progressRepo.save(data);
		}
	}

	/**
	 * Verifies the request origin is from a registered domain.
	 * 
	 * @param jsonData
	 */
	private boolean isDomainRegistered(ObjectNode jsonData) {
		String hostName = jsonData.get("host").asText();
		LOGGER.debug("Request host: " + hostName);
		if (registeredDomainRepo.findByDomainHost(hostName).isEmpty()) {
			LOGGER.warn("Domain: " + hostName + " is not registered. Tracking data will not be saved.");
			return false;
		}
		return true;
	}

}
