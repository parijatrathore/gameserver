package com.junglee.assignment.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.UniqueConstraint;

/**
 * @author parijatrathore
 *
 */
@Entity
@Table(name = "task", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "vendor_id" }))
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7094902690229235483L;
	private Integer				id;
	private String				name;
	private String				taskClass;
	private String				executionLevel;
	private Date				startTime;
	private Date				endTime;
	private int					delay;
	private String				lastExecResult;
	private Date				lastExecTime;
	private int					severity;
	private int					notificationLevel;
	private String				notificationEmail;
	private String				notificationEmailTemplate;
	private boolean				enabled;
	private Date				created;
	private Date				updated;
	private Set<String>	taskParameters		= new HashSet<String>(0);

	public Task() {
	}

	public Task(String name, String taskClass, Date startTime, int delay, int severity, int notificationLevel, boolean enabled, Date created, Date updated) {
		this.name = name;
		this.taskClass = taskClass;
		this.startTime = startTime;
		this.delay = delay;
		this.severity = severity;
		this.notificationLevel = notificationLevel;
		this.enabled = enabled;
		this.created = created;
		this.updated = updated;
	}

	public Task(String name, String taskClass, Date startTime, Date endTime, int delay, String lastExecResult, Date lastExecTime, int severity,
			int notificationLevel, String notificationEmail, String notificationEmailTemplate, boolean enabled, Date created, Date updated,
			Set<String> taskParameters) {
		this.name = name;
		this.taskClass = taskClass;
		this.startTime = startTime;
		this.endTime = endTime;
		this.delay = delay;
		this.lastExecResult = lastExecResult;
		this.lastExecTime = lastExecTime;
		this.severity = severity;
		this.notificationLevel = notificationLevel;
		this.notificationEmail = notificationEmail;
		this.notificationEmailTemplate = notificationEmailTemplate;
		this.enabled = enabled;
		this.created = created;
		this.updated = updated;
		this.taskParameters = taskParameters;
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

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "store_id", nullable = false) public Store getStore()
	 * { return this.store; }
	 * 
	 * public void setStore(Store store) { this.store = store; }
	 */

	@Column(name = "name", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "task_class", nullable = false, length = 256)
	public String getTaskClass() {
		return this.taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	@Column(name = "execution_level", nullable = false)
	public String getExecutionLevel() {
		return this.executionLevel;
	}

	public void setExecutionLevel(String executionLevel) {
		this.executionLevel = executionLevel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable = false, length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "delay", nullable = false)
	public int getDelay() {
		return this.delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Column(name = "last_exec_result", length = 65535)
	public String getLastExecResult() {
		return this.lastExecResult;
	}

	public void setLastExecResult(String lastExecResult) {
		this.lastExecResult = lastExecResult;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_exec_time", length = 19)
	public Date getLastExecTime() {
		return this.lastExecTime;
	}

	public void setLastExecTime(Date lastExecTime) {
		this.lastExecTime = lastExecTime;
	}

	@Column(name = "severity", nullable = false)
	public int getSeverity() {
		return this.severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	@Column(name = "notification_level", nullable = false)
	public int getNotificationLevel() {
		return this.notificationLevel;
	}

	public void setNotificationLevel(int notificationLevel) {
		this.notificationLevel = notificationLevel;
	}

	@Column(name = "notification_email", length = 200)
	public String getNotificationEmail() {
		return this.notificationEmail;
	}

	public void setNotificationEmail(String notificationEmail) {
		this.notificationEmail = notificationEmail;
	}

	@Column(name = "notification_email_template", length = 45)
	public String getNotificationEmailTemplate() {
		return this.notificationEmailTemplate;
	}

	public void setNotificationEmailTemplate(String notificationEmailTemplate) {
		this.notificationEmailTemplate = notificationEmailTemplate;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	@Transient
	public Set<String> getTaskParameters() {
		return this.taskParameters;
	}

	public void setTaskParameters(Set<String> taskParameters) {
		this.taskParameters = taskParameters;
	}
}
