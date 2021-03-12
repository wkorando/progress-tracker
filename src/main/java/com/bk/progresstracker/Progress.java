package com.bk.progresstracker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Progress {
	@Id
	@GeneratedValue
	private long id;
	/**
	 * Origin from where the request is coming from
	 */
	private String origin;
	/**
	 * Id for identifying user across requests
	 */
	private String trackingId;
	private String jsonData;
}
