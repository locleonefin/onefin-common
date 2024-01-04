package com.onefin.ewallet.common.quartz.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onefin.ewallet.common.quartz.config.CronUtil;
import org.hibernate.annotations.GenericGenerator;

import com.onefin.ewallet.common.quartz.config.QuartzConstants;

import lombok.Data;

@Data
@Entity
@Table(name = "Scheduler_Job_Info")
public class SchedulerJobInfo {

	@Id
	@GenericGenerator(name = QuartzConstants.UUID, strategy = QuartzConstants.UUID2)
	@GeneratedValue(generator = QuartzConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID jobId;

	@Column
	private String jobName;

	@Column
	private String jobGroup;

	@Column
	private String jobStatus;

	@Column
	private String jobClass;

	@Column
	private String cronExpression; // Null => Simple Job, !Null => Cron Job

	@Column
	private String description;

	@Column
	private String interfaceName;

	@Column
	private Long repeatTime;

	@Column
	private Boolean cronJob;

	@Transient
	private Map<String,String> parameters;

	@Transient
	private boolean forceUpdate;



}
