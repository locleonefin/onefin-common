package com.onefin.ewallet.common.quartz.controller;

import java.util.List;
import java.util.UUID;

import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onefin.ewallet.common.quartz.entity.SchedulerJobInfo;
import com.onefin.ewallet.common.quartz.model.Message;
import com.onefin.ewallet.common.quartz.service.SchedulerJobService;

@RequestMapping("/quartz")
public abstract class JobController {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

	@Autowired
	private SchedulerJobService scheduleJobService;	
	
	@Value("${spring.quartz.properties.org.quartz.scheduler.instanceName}")
	private String jobGroup;

	protected Object saveOrUpdate(SchedulerJobInfo job) throws ClassNotFoundException {
		job.setJobClass(Class.forName(job.getJobClass()).getName());
		job.setJobGroup(jobGroup);
		LOGGER.info("params, job = {}", job);
		Message message = Message.failure();
		try {
			scheduleJobService.saveOrupdate(job);
			message = Message.success();
		} catch (Exception e) {
			message.setMsg(e.getMessage());
			LOGGER.error("updateCron ex:", e);
		}
		return message;
	}

	@GetMapping("/metaData")
	public Object metaData() throws SchedulerException {
		SchedulerMetaData metaData = scheduleJobService.getMetaData();
		return metaData;
	}

	@GetMapping("/getAllJobs")
	public Object getAllJobs() throws SchedulerException {
		List<SchedulerJobInfo> jobList = scheduleJobService.getAllJobList();
		return jobList;
	}

	@PostMapping(value = "/runJob")
	public Object runJob(@RequestBody(required = true) SchedulerJobInfo job) {
		job.setJobGroup(jobGroup);
		LOGGER.info("params, job = {}", job);
		Message message = Message.failure();
		try {
			scheduleJobService.startJobNow(job);
			message = Message.success();
		} catch (Exception e) {
			message.setMsg(e.getMessage());
			LOGGER.error("runJob ex:", e);
		}
		return message;
	}

	@PostMapping(value = "/pauseJob")
	public Object pauseJob(@RequestBody(required = true) SchedulerJobInfo job) {
		job.setJobGroup(jobGroup);
		LOGGER.info("params, job = {}", job);
		Message message = Message.failure();
		try {
			scheduleJobService.pauseJob(job);
			message = Message.success();
		} catch (Exception e) {
			message.setMsg(e.getMessage());
			LOGGER.error("pauseJob ex:", e);
		}
		return message;
	}
	
	@PostMapping(value = "/hardPauseJob")
	public Object hardPauseJob(@RequestBody(required = true) SchedulerJobInfo job) {
		job.setJobGroup(jobGroup);
		LOGGER.info("params, job = {}", job);
		Message message = Message.failure();
		try {
			scheduleJobService.hardPauseJob(job);
			message = Message.success();
		} catch (Exception e) {
			message.setMsg(e.getMessage());
			LOGGER.error("pauseJob ex:", e);
		}
		return message;
	}

	@PostMapping(value = "/resumeJob")
	public Object resumeJob(@RequestBody(required = true) SchedulerJobInfo job) {
		job.setJobGroup(jobGroup);
		LOGGER.info("params, job = {}", job);
		Message message = Message.failure();
		try {
			scheduleJobService.resumeJob(job);
			message = Message.success();
		} catch (Exception e) {
			message.setMsg(e.getMessage());
			LOGGER.error("resumeJob ex:", e);
		}
		return message;
	}

	@PostMapping(value = "/deleteJob")
	public Object deleteJob(@RequestBody(required = true) SchedulerJobInfo job) {
		job.setJobGroup(jobGroup);
		LOGGER.info("params, job = {}", job);
		Message message = Message.failure();
		try {
			scheduleJobService.deleteJob(job);
			message = Message.success();
		} catch (Exception e) {
			message.setMsg(e.getMessage());
			LOGGER.error("deleteJob ex:", e);
		}
		return message;
	}

	@PostMapping(value = "/getJobDetail")
	public ResponseEntity<?> getJobDetail(@RequestBody(required = true) SchedulerJobInfo job){
		return new ResponseEntity<>( scheduleJobService.getJobDetail(job.getJobName()), HttpStatus.OK);
	}


	protected ResponseEntity<?> getAllJobDetails(Specification<SchedulerJobInfo> spec, Pageable pageable) {

		Page<SchedulerJobInfo> result = scheduleJobService.getAllJobDetails(spec,pageable);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	protected ResponseEntity<?> getJobDetails(UUID jobId) {
		SchedulerJobInfo jobInfo = scheduleJobService.getJobDetails(jobId);
		return new ResponseEntity<>(jobInfo,HttpStatus.OK);
	}


}
