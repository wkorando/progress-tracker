package com.bk.progresstracker;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for sending data to thymeleaf generated table located under here;
 * /progress-tracker/src/main/resources/templates/progress-data.html
 * 
 * @author bkorando
 *
 */
@Controller
public class ViewDataController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewDataController.class);
	private ProgressService service;

	public ViewDataController(ProgressService service) {
		this.service = service;
	}

	/**
	 * Present all collected data over the past 24h period, ordered by timestamp descending.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/progress-data-all")
	public String displayAllProgessDataForPastDay(Model model) {
		model.addAttribute("progressDatas", service.findAllTrackingForDay(LocalDateTime.now(ZoneId.of("UTC"))));
		return "progress-data";
	}

	/**
	 * Present all collected data over the past 24h period, grouped by user id (JSESSIONID) and ordered by timestamp descending.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/progress-data-group-by-user")
	public String displayAllProgessDataForPastDayGroupByUser(Model model) {
		model.addAttribute("progressDatas",
				service.findAllTrackingForDayAndGroupByTrackingId(LocalDateTime.now(ZoneId.of("UTC"))));
		return "progress-data";
	}

	/**
	 * Show only the most recent request for each user, ordered by timestamp descending.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/progress-data-recent-activity")
	public String displayMostRecentProgessDataByUserForPastDay(Model model) {
		model.addAttribute("progressDatas", service.findMostRecentUserActivity(LocalDateTime.now(ZoneId.of("UTC"))));
		return "progress-data";
	}
}