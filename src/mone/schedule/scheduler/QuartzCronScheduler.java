package mone.schedule.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import mone.db.conn.DbManager;

/**
 * 스케쥴러
 * @author iby
 *
 */
public class QuartzCronScheduler {
	// 로그
	private static final Logger logger = LogManager.getLogger(QuartzCronScheduler.class);
	//
	private SchedulerFactory schedulerFactory = null;
	//
    private Scheduler scheduler = null;
	//


    /**
     * 생성자
     */
    public QuartzCronScheduler() {
    	init();
    }

    private static class Holder {
    	public static final QuartzCronScheduler INSTANCE = new QuartzCronScheduler();
    }

    /**
     * 싱글턴 인스턴스 반환
     * @return 인스턴스
     */
    public static QuartzCronScheduler getInstance() {
    	return Holder.INSTANCE;
    }

    /**
     * 초기화
     */
    private void init() {

			schedulerFactory = new StdSchedulerFactory();
			try {
				scheduler = schedulerFactory.getScheduler();
			} catch (SchedulerException e) {

			}

    }

    /**
     * 스케쥴의 정의
     * @param jobClass : 스케즆 job 객체
     * @param cronExpr : cron 표현식
     * @param jobNm : job명
     * @param cronTriggerNm : cron 트리거명
     * @param group : 스케쥴 job 그룹명
     * @throws SchedulerException
     */
    public void schedule(Class<? extends Job> jobClass, String cronExpr, String jobNm, String cronTriggerNm, String group) throws SchedulerException {

    	// 파라미터 체크
    	if("".equals(cronExpr) 		== true ||
    	   "".equals(jobNm) 		== true ||
    	   "".equals(cronTriggerNm) == true ||
    	   "".equals(group) 		== true) {
    		//logger.warn("설정파일확인 - 스케쥴러 파라미터 부재");
    		//System.exit(0);
    		System.out.println("Exception Message");
    		return;
    	} else {
    		//logger.info(String.format("%s, %s",jobNm, cronExpr));
    		System.out.println("Exception Message :: schedule ");
    	}


    		// JobDetail 생성
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobNm, group).build();
			// 특정 시간에 작동하도록 스케쥴 설정
			CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(cronTriggerNm, group).withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)).build();

			scheduler.scheduleJob(jobDetail, cronTrigger);
//			scheduler.start();

    }

    /**
     * @throws SchedulerException
     *
     */
    public void start() throws SchedulerException {

    		scheduler.start();

    }

    /**
     * 스케쥴 중단
     * @throws SchedulerException
     */
    public void shutDown() throws SchedulerException {

			if(scheduler == null) {
				System.out.println("cron scheduler not started..");
				return;
			}

			boolean isStarted = scheduler.isStarted();

			if(isStarted == true) {
				scheduler.shutdown();
				System.out.println("cron scheduler shutdown..");
			}

    }
}
