package com.bk.progresstracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/progress")
public class ProgressTrackerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgressTrackerController.class);
	private ProgressService service;
	
	@PostMapping
	public ResponseEntity<?> receiveTrackingData(@RequestBody String jsonData) {
		LOGGER.info("Data received: " + jsonData);
		
		return ResponseEntity.ok().build();
	}
}