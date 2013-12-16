package com.ethos.theethosapp;

import java.util.Date;

public class Comment {
	private String objectId;
	private String message;
	private Date dateCreated;
	private Date dateUpdated;
	private String fromUserId;
	private String toUserId;
	private boolean isHidden;
	private boolean isLocked;
	private boolean isAngel;
	private boolean isSpam;
	private String fromUsername;
	private String toName;

	public Comment(String objectId, String message, Date dateCreated, Date dateUpdated,
			String fromUserId, String toUserId, boolean isHidden,
			boolean isLocked, boolean isAngel, boolean isSpam,
			String fromUsername, String toName) {
		this.objectId = objectId;
		this.message = message;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.isHidden = isHidden;
		this.isLocked = isLocked;
		this.isAngel = isAngel;
		this.isSpam = isSpam;
		this.fromUsername = fromUsername;
		this.toName = toName;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isAngel() {
		return isAngel;
	}

	public void setAngel(boolean isAngel) {
		this.isAngel = isAngel;
	}

	public boolean isSpam() {
		return isSpam;
	}

	public void setSpam(boolean isSpam) {
		this.isSpam = isSpam;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

}
