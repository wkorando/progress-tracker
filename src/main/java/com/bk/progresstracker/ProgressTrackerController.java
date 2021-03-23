package com.bk.progresstracker;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/p")
public class ProgressTrackerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgressTrackerController.class);
	private ProgressService service;
	private ObjectMapper mapper;

	public ProgressTrackerController(ProgressService service, ObjectMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	/**
	 * Endpoint for receiving user progress data.
	 * 
	 * @param labId   - the id of the lab, should be human readable (i.e.
	 *                java16-workshop)
	 * @param stepId  - the step within the lab the user is currently on should be
	 *                numberic
	 * @param request - HttpServletRequest
	 * @return
	 * @throws IOException -
	 * @throws Exception
	 */
	@GetMapping(value = "/{labId}/{stepId}", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] receiveTrackingData(@PathVariable String labId, @PathVariable String stepId,
			HttpServletRequest request) throws IOException {
		ObjectNode jsonData = extractHeadersAsJson(request);
		String sessionId = extractSessionId(request);
		LOGGER.info("LabId: " + labId + " stepId: " + stepId + " sessionId: " + sessionId);
		service.saveTrackingInfo(labId, stepId, sessionId, jsonData);
		InputStream in = getClass().getResourceAsStream("/static/pixel.png");
		return IOUtils.toByteArray(in);
	}

	@GetMapping("/index")
	public String showUserList(Model model) {
		model.addAttribute("progressDatas", service.findAllTrackingForDay(LocalDateTime.now(ZoneId.of("UTC"))));
		return "index";
	}

	/**
	 * Extracts session id from request, if no request id is present one is created.
	 * The session id is user to correlate user progress across requests.
	 * 
	 * @param request
	 * @return
	 */
	private String extractSessionId(HttpServletRequest request) {
		String sessionId = request.getRequestedSessionId();

		if (sessionId == null) {
			sessionId = request.getSession(true).getId();
		}
		return sessionId;
	}

	/**
	 * Generates a JSON representation of all header values in request.
	 * 
	 * @param request
	 * @return
	 */
	private ObjectNode extractHeadersAsJson(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		ObjectNode jsonData = mapper.createObjectNode();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			jsonData.put(headerName, request.getHeader(headerName));
		}
		LOGGER.info("Extracted headers as JSON: " + jsonData.toString());
		return jsonData;
	}

}