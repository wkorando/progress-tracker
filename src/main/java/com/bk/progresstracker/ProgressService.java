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

	public boolean saveTrackingInfo(String labId, String stepId, String sessionId, ObjectNode jsonData) {
		isDomainRegistered(jsonData);
		ProgressData data = new ProgressData(labId, stepId, sessionId, jsonData.toString());
		progressRepo.save(data);
		return true;
	}

	/**
	 * Verifies the request origin is from a registered domain.
	 * 
	 * @param jsonData
	 */
	private void isDomainRegistered(ObjectNode jsonData) {
		String hostName = jsonData.get("host").asText();
		LOGGER.debug("Request host: " + hostName);
		if (registeredDomainRepo.findByDomainHost(hostName).isEmpty()) {
			throw new ClientException("Domain isn't registered");
		}
	}

}
