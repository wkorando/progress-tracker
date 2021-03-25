package com.bk.progresstracker;

import java.time.LocalDateTime;
import java.util.HashMap;

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

	public Iterable<ProgressData> findAllTrackingForDay(LocalDateTime endDate) {
		LocalDateTime startDate = LocalDateTime.from(endDate).minusDays(1);
		return progressRepo.findByTimestampBetweenOrderByTimestampDesc(startDate, endDate);
	}

	public Iterable<ProgressData> findAllTrackingForDayAndGroupByTrackingId(LocalDateTime endDate) {
		LocalDateTime startDate = LocalDateTime.from(endDate).minusDays(1);
		return progressRepo.findByTimestampBetweenOrderByTrackingIdDescTimestampDesc(startDate, endDate);
	}

	public Iterable<ProgressData> findMostRecentUserActivity(LocalDateTime endDate) {
		LocalDateTime startDate = LocalDateTime.from(endDate).minusDays(1);
		Iterable<ProgressData> progressData = progressRepo.findByTimestampBetweenOrderByTimestampAsc(startDate, endDate);
		HashMap<String, ProgressData> progressDataTable = new HashMap<>();
		progressData.forEach(p -> progressDataTable.put(p.getTrackingId(), p));
		
		return progressDataTable.values();
	}

}
