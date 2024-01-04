package com.onefin.ewallet.common.quartz.service;

import java.util.*;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.onefin.ewallet.common.quartz.config.QuartzConstants;
import com.onefin.ewallet.common.quartz.entity.SchedulerJobInfo;
import com.onefin.ewallet.common.quartz.repository.SchedulerRepository;

//@Transactional(transactionManager = "primaryTransactionManager")
@Service
public class SchedulerJobService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobScheduleCreator.class);

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private SchedulerRepository schedulerRepository;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private JobScheduleCreator scheduleCreator;


	public SchedulerMetaData getMetaData() throws SchedulerException {
		SchedulerMetaData metaData = scheduler.getMetaData();
		return metaData;
	}

	public List<SchedulerJobInfo> getAllJobList() {
		return schedulerRepository.findAll();
	}

	public boolean deleteJob(SchedulerJobInfo jobInfo) {
		try {
			SchedulerJobInfo getJobInfo = schedulerRepository.findByJobNameAndJobGroup(jobInfo.getJobName(), jobInfo.getJobGroup());
			schedulerRepository.delete(getJobInfo);
			LOGGER.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " deleted.");
			return schedulerFactoryBean.getScheduler()
					.deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
		} catch (SchedulerException e) {
			LOGGER.error("Failed to delete job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}

	public boolean pauseJob(SchedulerJobInfo jobInfo) {
		try {
			SchedulerJobInfo getJobInfo = null;
			if (jobInfo.getJobName() != null && jobInfo.getJobGroup() != null) {
				LOGGER.info("Find by job name and group {}, {}", jobInfo.getJobName(), jobInfo.getJobGroup());
				getJobInfo = schedulerRepository.findByJobNameAndJobGroup(jobInfo.getJobName(), jobInfo.getJobGroup());
			} else if (jobInfo.getJobClass() != null) {
				LOGGER.info("Find by job class {}", jobInfo.getJobClass());
				getJobInfo = schedulerRepository.findByJobClass(jobInfo.getJobClass());
			}
			getJobInfo.setJobStatus(QuartzConstants.JOB_STATUS_PAUSED);
			schedulerRepository.save(getJobInfo);
			schedulerFactoryBean.getScheduler().pauseJob(new JobKey(getJobInfo.getJobName(), getJobInfo.getJobGroup()));
			LOGGER.info(">>>>> jobName = [" + getJobInfo.getJobName() + "]" + " paused.");
			return true;
		} catch (SchedulerException e) {
			LOGGER.error("Failed to pause job - {}", jobInfo, e);
			return false;
		}
	}

	public boolean hardPauseJob(SchedulerJobInfo jobInfo) {
		try {
			SchedulerJobInfo getJobInfo = schedulerRepository.findByJobNameAndJobGroup(jobInfo.getJobName(), jobInfo.getJobGroup());
			getJobInfo.setJobStatus(QuartzConstants.JOB_STATUS_HARD_PAUSED);
			schedulerRepository.save(getJobInfo);
			schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
			LOGGER.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " hard paused.");
			return true;
		} catch (SchedulerException e) {
			LOGGER.error("Failed to hard pause job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}

	public boolean resumeJob(SchedulerJobInfo jobInfo) {
		try {
			SchedulerJobInfo getJobInfo = schedulerRepository.findByJobNameAndJobGroup(jobInfo.getJobName(), jobInfo.getJobGroup());
			getJobInfo.setJobStatus(QuartzConstants.JOB_STATUS_RESUMED);
			schedulerRepository.save(getJobInfo);
			schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
			LOGGER.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " resumed.");
			return true;
		} catch (SchedulerException e) {
			LOGGER.error("Failed to resume job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}

	public boolean startJobNow(SchedulerJobInfo jobInfo) {
		try {
			SchedulerJobInfo getJobInfo = schedulerRepository.findByJobNameAndJobGroup(jobInfo.getJobName(), jobInfo.getJobGroup());
			getJobInfo.setJobStatus(QuartzConstants.JOB_STATUS_SCHEDULED_STARTED);
			schedulerRepository.save(getJobInfo);
			schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
			LOGGER.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled and started now.");
			return true;
		} catch (SchedulerException e) {
			LOGGER.error("Failed to start new job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public void saveOrupdate(SchedulerJobInfo scheduleJob) throws Exception {
		if (scheduleJob.getCronExpression().length() > 0) {
			//scheduleJob.setJobClass(clazzCronJob.getName());
			scheduleJob.setCronJob(true);
		} else {
			//scheduleJob.setJobClass(clazzSimpleJob.getName());
			scheduleJob.setCronJob(false);
			scheduleJob.setRepeatTime((long) 1);
		}
		if (StringUtils.isEmpty(scheduleJob.getJobId())) {
			SchedulerJobInfo jobExisted = schedulerRepository.findByJobName(scheduleJob.getJobName());
			if (jobExisted != null) {
				deleteJob(jobExisted);
			}
			LOGGER.info("Job Info: {}", scheduleJob);
			scheduleNewJob(scheduleJob);
		} else {
			Optional<SchedulerJobInfo> getJobInfo = schedulerRepository.findById(scheduleJob.getJobId());
			// Job name and job group are primary key in quartz
			scheduleJob.setJobName(getJobInfo.get().getJobName());
			scheduleJob.setJobGroup(getJobInfo.get().getJobGroup());
			updateScheduleJob(scheduleJob);
		}
		scheduleJob.setDescription("i am job number " + scheduleJob.getJobId());
		scheduleJob.setInterfaceName("interface_" + scheduleJob.getJobId());
		LOGGER.info(">>>>> jobName = [" + scheduleJob.getJobName() + "]" + " created.");
	}

	@SuppressWarnings("unchecked")
	private void scheduleNewJob(SchedulerJobInfo jobInfo) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			JobDetail jobDetail = JobBuilder
					.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
					.withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
			if (!scheduler.checkExists(jobDetail.getKey())) {

				jobDetail = scheduleCreator.createJob(
						(Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()), false, context,
						jobInfo.getJobName(), jobInfo.getJobGroup());

				Trigger trigger;
				if (jobInfo.getCronJob()) {
					trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
							jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				} else {
					trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
							jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				}

				Map<String, String> listParameters = jobInfo.getParameters();
				if (listParameters != null && !listParameters.isEmpty()) {
					for (Map.Entry<String, String> entry : listParameters.entrySet()) {
						jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
					}
				}
				scheduler.scheduleJob(jobDetail, trigger);
				jobInfo.setJobStatus(QuartzConstants.JOB_STATUS_SCHEDULED);
				schedulerRepository.save(jobInfo);
				LOGGER.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled.");
			} else {
				LOGGER.error("scheduleNewJobRequest.jobAlreadyExist");
			}
		} catch (ClassNotFoundException e) {
			LOGGER.error("Class Not Found - {}", jobInfo.getJobClass(), e);
		} catch (SchedulerException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void updateScheduleJob(SchedulerJobInfo jobInfo) {
		Trigger newTrigger;
		if (jobInfo.getCronJob()) {
			newTrigger = (Trigger) scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
					jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		} else {
			newTrigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(), jobInfo.getRepeatTime(),
					SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		}
		try {
			schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobInfo.getJobName()), newTrigger);
			jobInfo.setJobStatus(QuartzConstants.JOB_STATUS_EDITED_SCHEDULED);
			schedulerRepository.save(jobInfo);
			LOGGER.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " updated and scheduled.");
		} catch (SchedulerException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public SchedulerJobInfo getJobDetail(String jobName) {
		Optional<SchedulerJobInfo> jobInfo = Optional.ofNullable(schedulerRepository.findByJobName(jobName));
		if (!jobInfo.isPresent() || jobInfo == null) {
			return null;
		} else {
			return jobInfo.get();
		}
	}

	public Page<SchedulerJobInfo> getAllJobDetails(Specification<SchedulerJobInfo> spec, Pageable pageable) {
		if (spec != null) {
			return schedulerRepository.findAll(spec, pageable);
		} else {
			return schedulerRepository.findAll(pageable);
		}
	}

	public SchedulerJobInfo getJobDetails(UUID jobId) {
		return schedulerRepository.findByJobId(jobId);
	}
}
