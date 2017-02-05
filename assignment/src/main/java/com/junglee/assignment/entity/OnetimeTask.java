/*
 *  Copyright 2015 Shopsity Tech Pvt Ltd . All Rights Reserved.
 *  SHOPSITY TECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 30-Dec-2015
 *  @author parijatrathore
 */
package com.junglee.assignment.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "onetime_task")
public class OnetimeTask implements Serializable {

	/**
     * 
     */
	private static final long	serialVersionUID	= 8893783150891366041L;

	public enum StatusCode {
		SCHEDULED, RUNNING, COMPLETE, CANCELLED
	}

	public enum Type {
		EXPORT, IMPORT
	}

	private Integer						id;
	private String						userId;
	private String						name;
	private String						taskClass;
	private String						type;
	private Date						scheduledTime;
	private String						notificationEmail;
	private String						emailTemplate;
	private boolean						enabled;
	private String						result;
	private String						statusCode;
	private Integer						taskDurationInSec;
	private Date						created;
	private Date						updated;
	private Set<String>	onetimeTaskParameters	= new HashSet<String>(0);

	public OnetimeTask() {
	}

	public OnetimeTask(String userId, String name, String taskClass, String type, Date scheduledTime, boolean enabled, String result, String statusCode,
			Date created, Date updated) {
		this.userId = userId;
		this.name = name;
		this.taskClass = taskClass;
		this.type = type;
		this.scheduledTime = scheduledTime;
		this.enabled = enabled;
		this.result = result;
		this.statusCode = statusCode;
		this.created = created;
		this.updated = updated;
	}

	public OnetimeTask(String userId, String name, String taskClass, String type, Date scheduledTime, String notificationEmail, String emailTemplate,
			boolean enabled, String result, String statusCode, Date created, Date updated, Set<String> onetimeTaskParameters) {
		this.userId = userId;
		this.name = name;
		this.taskClass = taskClass;
		this.type = type;
		this.scheduledTime = scheduledTime;
		this.notificationEmail = notificationEmail;
		this.emailTemplate = emailTemplate;
		this.enabled = enabled;
		this.result = result;
		this.statusCode = statusCode;
		this.created = created;
		this.updated = updated;
		this.onetimeTaskParameters = onetimeTaskParameters;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false, length = 100)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "task_class", nullable = false, length = 500)
	public String getTaskClass() {
		return this.taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	@Column(name = "type", nullable = false, length = 45)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduled_time", nullable = false, length = 19)
	public Date getScheduledTime() {
		return this.scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	@Column(name = "notification_email", length = 100)
	public String getNotificationEmail() {
		return this.notificationEmail;
	}

	public void setNotificationEmail(String notificationEmail) {
		this.notificationEmail = notificationEmail;
	}

	@Column(name = "email_template", length = 45)
	public String getEmailTemplate() {
		return this.emailTemplate;
	}

	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "result", length = 65535)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "status_code", nullable = false, length = 45)
	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false, length = 19, insertable = false, updatable = false)
	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Column(name = "task_duration_in_sec")
	public Integer getTaskDurationInSec() {
		return taskDurationInSec;
	}

	public void setTaskDurationInSec(Integer taskDurationInSec) {
		this.taskDurationInSec = taskDurationInSec;
	}

	@Transient
	public Set<String> getOnetimeTaskParameters() {
		return this.onetimeTaskParameters;
	}

	public void setOnetimeTaskParameters(Set<String> onetimeTaskParameters) {
		this.onetimeTaskParameters = onetimeTaskParameters;
	}
}
