package com.bk.progresstracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProgressService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgressService.class);
	private ObjectMapper objectMapper;
	
	public boolean saveTrackingInfo(String jsonData) throws Exception {
		ObjectNode jsonNode = objectMapper.readValue(jsonData, ObjectNode.class);
		String value = jsonNode.get("value").asText();
		
		return true;
	}
}
