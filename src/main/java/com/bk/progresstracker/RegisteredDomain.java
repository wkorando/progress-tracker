package com.bk.progresstracker;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Data carrier class for registered domain table. New domains wishing to use
 * progress tracker need to register their domain in the table to be able to
 * store progress data.
 * 
 * @author bkorando
 *
 */
@Entity
public class RegisteredDomain {

	@Id
	private long id;
	/**
	 * 
	 */
	private String domainHost;

	public RegisteredDomain(long id, String domainHost) {
		this.id = id;
		this.domainHost = domainHost;
	}

	public long getId() {
		return id;
	}

	public String getDomainHost() {
		return domainHost;
	}
}