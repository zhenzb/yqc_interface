//package com.youqiancheng.initdata;
//
//import com.bootdo.clouddoadmin.config.TaskSchedulerFactory;
//import com.bootdo.clouddoadmin.domain.TaskCronJobDO;
//import com.bootdo.clouddoadmin.domain.TaskSimJobDO;
//import com.bootdo.clouddoadmin.service.TaskCronJobService;
//import com.bootdo.clouddoadmin.service.TaskSimJobService;
//import com.bootdo.clouddoadmin.utils.TaskUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.logging.LogFactory;
//import org.quartz.*;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.List;
//
//import static org.quartz.CronExpression.isValidExpression;
//
///**
// * 定时任务初始化服务类
// * @author pengjunlee
// *
// */
//@Component
//public class TaskInitService {
//	public static org.apache.commons.logging.Log log = LogFactory.getLog(TaskInitService.class);
//	@Resource
//	private TaskCronJobService taskCronJobService;
//
//	@Resource
//	private TaskSimJobService taskSimJobService;
//
//	@Resource
//	private TaskSchedulerFactory schedulerFactory;
//
//	/**
//	 * 初始化
//	 */
//	@PostConstruct
//	public void init() {
//		Scheduler scheduler = schedulerFactory.getScheduler();
//		if (scheduler == null) {
//			log.error("初始化定时任务组件失败，Scheduler is null...");
//			return;
//		}
//
//		// 初始化基于cron时间配置的任务列表
//		try {
//			initCronJobs(scheduler);
//		} catch (Exception e) {
//			log.error("init cron tasks error," + e.getMessage(), e);
//		}
//
//		// 初始化基于固定间隔时间配置的任务列表
//		try {
//			initSimJobs(scheduler);
//		} catch (Exception e) {
//			log.error("init sim tasks error," + e.getMessage(), e);
//		}
//
//		try {
//			log.info("The scheduler is starting...");
//			scheduler.start(); // start the scheduler
//		} catch (Exception e) {
//			log.error("The scheduler start is error," + e.getMessage(), e);
//		}
//	}
//
//	/**
//	 * 初始化任务（基于cron触发器）
//	 *
//	 */
//	private void initCronJobs(Scheduler scheduler) throws Exception {
//		Iterable<TaskCronJobDO> jobList = taskCronJobService.list(new HashMap<>());
//		if (jobList != null) {
//			for (TaskCronJobDO job : jobList) {
//				scheduleCronJob(job, scheduler);
//			}
//		}
//	}
//
//	/**
//	 * 初始化任务（基于simple触发器）
//	 *
//	 */
//	private void initSimJobs(Scheduler scheduler) throws Exception {
//		Iterable<TaskSimJobDO> jobList = taskSimJobService.list(new HashMap<>());
//		if (jobList != null) {
//			for (TaskSimJobDO job : jobList) {
//				scheduleSimJob(job, scheduler);
//			}
//		}
//	}
//
//	/**
//	 * 安排任务(基于simple触发器)
//	 *
//	 * @param job
//	 * @param scheduler
//	 */
//	private void scheduleSimJob(TaskSimJobDO job, Scheduler scheduler) {
//		if (job != null && StringUtils.isNotBlank(job.getJobName())
//				&& StringUtils.isNotBlank(job.getJobClassName())
//				&& scheduler != null) {
//			if (job.getStatus()==2) {
//				return;
//			}
//			try {
//				JobKey jobKey = TaskUtils.genSimJobKey(job);
//
//				if (!scheduler.checkExists(jobKey)) {
//					// This job doesn't exist, then add it to scheduler.
//					log.info("Add new simple job to scheduler, jobName = " + job.getJobName());
//					this.newJobAndNewSimTrigger(job, scheduler, jobKey);
//				} else {
//					log.info("Update simple job to scheduler, jobName = " + job.getJobName());
//					this.updateSimTriggerOfJob(job, scheduler, jobKey);
//				}
//
//			} catch (Exception e) {
//				log.error("ScheduleCronJob is error," + e.getMessage(), e);
//			}
//		} else {
//			log.error("Method scheduleSimJob arguments are invalid.");
//		}
//	}
//
//	/**
//	 * 安排任务(基于cron触发器)
//	 *
//	 * @param job
//	 * @param scheduler
//	 */
//	private void scheduleCronJob(TaskCronJobDO job, Scheduler scheduler) {
//		if (job != null && StringUtils.isNotBlank(job.getJobName())
//				&& StringUtils.isNotBlank(job.getJobClassName())
//				&& StringUtils.isNotBlank(job.getCron()) && scheduler != null) {
//			if (job.getStatus()==2) {
//				return;
//			}
//
//			try {
//				JobKey jobKey = TaskUtils.genCronJobKey(job);
//
//				if (!scheduler.checkExists(jobKey)) {
//					// This job doesn't exist, then add it to scheduler.
//					log.info("Add new cron job to scheduler, jobName = " + job.getJobName());
//					this.newJobAndNewCronTrigger(job, scheduler, jobKey);
//				} else {
//					log.info("Update cron job to scheduler, jobName = " + job.getJobName());
//					this.updateCronTriggerOfJob(job, scheduler, jobKey);
//				}
//			} catch (Exception e) {
//				log.error("ScheduleCronJob is error," + e.getMessage(), e);
//			}
//		} else {
//			log.error("Method scheduleCronJob arguments are invalid.");
//		}
//	}
//
//	/**
//	 * 新建job和trigger到scheduler(基于simple触发器)
//	 *
//	 * @param job
//	 * @param scheduler
//	 * @param jobKey
//	 * @throws SchedulerException
//	 * @throws ClassNotFoundException
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private void newJobAndNewSimTrigger(TaskSimJobDO job, Scheduler scheduler, JobKey jobKey)
//			throws SchedulerException, ClassNotFoundException {
//		TriggerKey triggerKey = TaskUtils.genSimTriggerKey(job);
//		// get a Class object by string class name of job;
//		Class jobClass = Class.forName(job.getJobClassName().trim());
//		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).withDescription(job.getDesc())
//				.build();
//
//		SimpleTrigger trigger = null;
//		int intervalInSec = job.getIntervalTime();
//		if (intervalInSec > 0) {
//			// repeat the job every interval seconds.
//			trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey).startNow()
//					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(intervalInSec)).build();
//		} else {
//			// totally execute the job once.
//			trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey).startNow()
//					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(1)).build();
//		}
//
//		scheduler.scheduleJob(jobDetail, trigger);
//	}
//
//	/**
//	 * 更新job的trigger(基于simple触发器)
//	 *
//	 * @param job
//	 * @param scheduler
//	 * @param jobKey
//	 * @throws SchedulerException
//	 */
//	private void updateSimTriggerOfJob(TaskSimJobDO job, Scheduler scheduler, JobKey jobKey) throws SchedulerException {
//		TriggerKey triggerKey = TaskUtils.genSimTriggerKey(job);
//		int intervalInSec = job.getIntervalTime();
//
//		List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
//
//		for (int i = 0; triggers != null && i < triggers.size(); i++) {
//			Trigger trigger = triggers.get(i);
//			TriggerKey curTriggerKey = trigger.getKey();
//
//			if (TaskUtils.isTriggerKeyEqual(triggerKey, curTriggerKey)) {
//				SimpleTrigger newTrigger = null;
//				if (intervalInSec > 0) {
//					// repeat the job every interval seconds.
//					newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey).startNow()
//							.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(intervalInSec)).build();
//				} else {
//					// totally execute the job once.
//					newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey).startNow()
//							.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(1)).build();
//				}
//				scheduler.rescheduleJob(curTriggerKey, newTrigger);
//			} else {
//				// different trigger key // The trigger key is illegal,
//				// unschedule this trigger
//				scheduler.unscheduleJob(curTriggerKey);
//			}
//		}
//	}
//
//	/**
//	 * 新建job和trigger到scheduler(基于cron触发器)
//	 *
//	 * @param job
//	 * @param scheduler
//	 * @param jobKey
//	 * @throws SchedulerException
//	 * @throws ClassNotFoundException
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private void newJobAndNewCronTrigger(TaskCronJobDO job, Scheduler scheduler, JobKey jobKey)
//			throws SchedulerException, ClassNotFoundException {
//		TriggerKey triggerKey = TaskUtils.genCronTriggerKey(job);
//
//		String cronExpr = job.getCron();
//		if (!isValidExpression(cronExpr)) {
//			return;
//		}
//
//		// get a Class object by string class name of job;
//		Class jobClass = Class.forName(job.getJobClassName().trim());
//		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).withDescription(job.getDesc())
//				.build();
//		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
//				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpr).withMisfireHandlingInstructionDoNothing())
//				.build();
//
//		scheduler.scheduleJob(jobDetail, trigger);
//	}
//
//	/**
//	 * 更新job的trigger(基于cron触发器)
//	 * @param job
//	 * @param scheduler
//	 * @param jobKey
//	 * @throws SchedulerException
//	 */
//	private void updateCronTriggerOfJob(TaskCronJobDO job, Scheduler scheduler, JobKey jobKey) throws SchedulerException {
//		TriggerKey triggerKey = TaskUtils.genCronTriggerKey(job);
//		String cronExpr = job.getCron().trim();
//
//		List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
//
//		for (int i = 0; triggers != null && i < triggers.size(); i++) {
//			Trigger trigger = triggers.get(i);
//			TriggerKey curTriggerKey = trigger.getKey();
//
//			if (TaskUtils.isTriggerKeyEqual(triggerKey, curTriggerKey)) {
//				if (trigger instanceof CronTrigger
//						&& cronExpr.equalsIgnoreCase(((CronTrigger) trigger).getCronExpression())) {
//					// Don't need to do anything.
//				} else {
//					if (isValidExpression(job.getCron())) {
//						// Cron expression is valid, build a new trigger and
//						// replace the old one.
//						CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
//								.withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)
//										.withMisfireHandlingInstructionDoNothing())
//								.build();
//						scheduler.rescheduleJob(curTriggerKey, newTrigger);
//					}
//				}
//			} else {
//				// different trigger key ,The trigger key is illegal, unschedule
//				// this trigger
//				scheduler.unscheduleJob(curTriggerKey);
//			}
//
//		}
//
//	}
//
//}
