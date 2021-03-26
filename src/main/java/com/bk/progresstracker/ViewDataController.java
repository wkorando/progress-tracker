package com.bk.progresstracker;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewDataController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewDataController.class);
	private ProgressService service;

	public ViewDataController(ProgressService service) {
		this.service = service;
	}

	@GetMapping("/progress-data-all")
	public String displayAllProgessDataForPastDay(Model model) {
		model.addAttribute("progressDatas", service.findAllTrackingForDay(LocalDateTime.now(ZoneId.of("UTC"))));
		return "progress-data";
	}

	@GetMapping("/progress-data-group-by-user")
	public String displayAllProgessDataForPastDayGroupByUser(Model model) {
		model.addAttribute("progressDatas",
				service.findAllTrackingForDayAndGroupByTrackingId(LocalDateTime.now(ZoneId.of("UTC"))));
		return "progress-data";
	}

	@GetMapping("/progress-data-recent-activity")
	public String displayMostRecentProgessDataByUserForPastDay(Model model) {
		model.addAttribute("progressDatas", service.findMostRecentUserActivity(LocalDateTime.now(ZoneId.of("UTC"))));
		return "progress-data";
	}
}