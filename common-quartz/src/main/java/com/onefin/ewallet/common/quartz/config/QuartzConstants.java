package com.onefin.ewallet.common.quartz.config;

public class QuartzConstants {
	
	public static final String UUID2 = "uuid2";
	public static final String UUID = "uuid";
	
	public static final String JOB_STATUS_PAUSED = "PAUSED";
	public static final String JOB_STATUS_RESUMED = "RESUMED";
	public static final String JOB_STATUS_SCHEDULED_STARTED = "SCHEDULED & STARTED";
	public static final String JOB_STATUS_SCHEDULED = "SCHEDULED";
	public static final String JOB_STATUS_EDITED_SCHEDULED = "EDITED & SCHEDULED";
	public static final String JOB_STATUS_HARD_PAUSED = "HARD_PAUSED"; // Can't resume by another job, only resume manually by admin

}
