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

	public ProgressService(ProgressRepo progressRepo) {
		this.progressRepo = progressRepo;

	}

	public void saveTrackingInfo(String labId, String stepId, String sessionId, ObjectNode jsonData) {
		ProgressData data = new ProgressData(labId, stepId, sessionId, jsonData.toString());
		progressRepo.save(data);
	}

}
