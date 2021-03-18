package com.bk.progresstracker;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBlobType;

/**
 * Class representation for information to be tracked about user.
 * 
 * @author bkorando
 *
 */
@Entity
@TypeDef(
	    name = "json",
	    typeClass = JsonBlobType.class
	)
public class ProgressData {
	@Id
	@GeneratedValue(generator = "progress_data_id_generator")
	@SequenceGenerator(name = "progress_data_id_generator", allocationSize = 1, initialValue = 10)
	private long id;
	/**
	 * The id of the lab being tracked
	 */
	private String labId;
	/**
	 * The step within the lab the user is on
	 */
	private String stepId;
	/**
	 * Id for correlating request coming from a single user during a single session
	 * of attending the workshop.
	 */
	private String trackingId;
	/**
	 * Time in UTC when the request was received by the service
	 */
	private Date timestamp = new Date();
	/**
	 * Blob of unstructured header data stored in JSON format about the user. 
	 */
	@Type(type="json")
	private String jsonData;

	ProgressData() {
	}

	public ProgressData(String labId, String stepId, String trackingId, String jsonData) {
		this.labId = labId;
		this.stepId = stepId;
		this.trackingId = trackingId;
		this.jsonData = jsonData;
	}

	public long getId() {
		return id;
	}

	public String getLabId() {
		return labId;
	}

	public String getStepId() {
		return stepId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getJsonData() {
		return jsonData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((jsonData == null) ? 0 : jsonData.hashCode());
		result = prime * result + ((labId == null) ? 0 : labId.hashCode());
		result = prime * result + ((stepId == null) ? 0 : stepId.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((trackingId == null) ? 0 : trackingId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProgressData other = (ProgressData) obj;
		if (id != other.id)
			return false;
		if (jsonData == null) {
			if (other.jsonData != null)
				return false;
		} else if (!jsonData.equals(other.jsonData))
			return false;
		if (labId == null) {
			if (other.labId != null)
				return false;
		} else if (!labId.equals(other.labId))
			return false;
		if (stepId == null) {
			if (other.stepId != null)
				return false;
		} else if (!stepId.equals(other.stepId))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (trackingId == null) {
			if (other.trackingId != null)
				return false;
		} else if (!trackingId.equals(other.trackingId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProgressData [id=" + id + ", labId=" + labId + ", stepId=" + stepId + ", trackingId=" + trackingId
				+ ", timestamp=" + timestamp + ", jsonData=" + jsonData + "]";
	}

}