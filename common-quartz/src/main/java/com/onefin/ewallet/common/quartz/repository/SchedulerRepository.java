package com.onefin.ewallet.common.quartz.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.onefin.ewallet.common.quartz.entity.SchedulerJobInfo;

@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerJobInfo, UUID> {

	SchedulerJobInfo findByJobName(String jobName);
	
	SchedulerJobInfo findByJobNameAndJobGroup(String jobName, String jobGroup);
	
	SchedulerJobInfo findByJobClass(String jobClass);

	@Query(value = "SELECT * FROM scheduler_job_info sji WHERE sji.job_group = (:jobGroup) AND sji.job_status IN (:listStatus)", nativeQuery = true)
	List<SchedulerJobInfo> findByJobGroupAndListJobStatus(String jobGroup, List<String> listStatus);

	Page<SchedulerJobInfo> findAll(Pageable pageable);

	Page<SchedulerJobInfo> findAll(Specification<SchedulerJobInfo> spec, Pageable pageable);

	SchedulerJobInfo findByJobId(UUID jobId);
}
