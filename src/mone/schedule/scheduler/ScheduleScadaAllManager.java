package mone.schedule.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;

import mone.common.conf.ConfigManager;
import mone.common.conf.ConstDef;
import mone.schedule.job.CollectAllScadaData;
import mone.schedule.job.CollectScadaData;


/**
 * 연계 api별 스케쥴링
 * @author iby
 *
 */
public class ScheduleScadaAllManager {
	// 로그
	private static final Logger logger = LogManager.getLogger(ScheduleScadaAllManager.class);

	//   스케쥴 실행 여부
	private String _getScadaAllDBScheduleOnOff;


	/**
	 * constructor
	 */
	public ScheduleScadaAllManager() {
	}

	/**
	 * initialize
	 */
	public void init() {



		/*  */
		this._getScadaAllDBScheduleOnOff = ConfigManager.getProps("getScadaAllDB.schedule.onoff");

        /*  */
		if(ConstDef.OnOff.ON.equals(_getScadaAllDBScheduleOnOff) == true) {


		}

	}

	/**
	 *
	 * @return
	 * @throws SchedulerException
	 */
	public void start() throws SchedulerException {
		// 스케쥴 크론 표현식
		String cronExpr = "";
		// 스케쥴 job name
		String jobNm = "";
		// 스케쥴 cron trigger name
		String cronTriggerNm = "";
		// 스케쥴 group
		String group = "";



			// maria db 접속하에 데이터 처리


			logger.info(String.format("[스케쥴링ON/OFF] 온세미로 All DB TAG 수집 %s ", _getScadaAllDBScheduleOnOff));

			/*  maria db data select -> postgres db data insert 스케쥴 */
			if(ConstDef.OnOff.ON.equals(_getScadaAllDBScheduleOnOff) == true) {
				cronExpr = ConfigManager.getProps("getScadaAllDB.cron.rule");
				jobNm = ConfigManager.getProps("getScadaAllDB.job.name");
				cronTriggerNm = ConfigManager.getProps("getScadaAllDB.crontrigger.name");
				group = ConfigManager.getProps("getScadaAllDB.schedule.group");

				// maria db data select  스케쥴러 시작
		    	QuartzCronScheduler.getInstance().schedule(CollectAllScadaData.class, cronExpr, jobNm, cronTriggerNm, group);

			}


			//
			if(ConstDef.OnOff.ON.equals(_getScadaAllDBScheduleOnOff) == false ) {
				logger.warn("설정파일확인..   모두 OFF");
				return;
			} else {
				logger.info("start schedule!");
				QuartzCronScheduler.getInstance().start();
			}

	}





}
