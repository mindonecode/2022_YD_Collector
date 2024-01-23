package mone.schedule.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;

import mone.common.conf.ConfigManager;
import mone.common.conf.ConstDef;
import mone.common.conf.ConstDef.EnumDateFormat;
import mone.common.util.Utils;
import mone.db.dao.PrcsDAO;
import mone.db.dao.WeatherPrcsDAO;
import mone.db.dto.PwnCdReqDTO;
import mone.db.dto.PwnCdResDTO;
import mone.db.dto.UltraSrtFcstReqDTO;
import mone.db.dto.UltraSrtNcstReqDTO;
import mone.schedule.job.PwnCdJob;
import mone.schedule.job.UltraSrtFcstJob;
import mone.schedule.job.UltraSrtNcstJob;


/**
 * 연계 api별 스케쥴링
 * @author iby
 *
 */
public class WeatherScheduleManager {
	// 로그
	private static final Logger logger = LogManager.getLogger(ScheduleManager.class);
	// 페이지번호
	public static String _reqPageNo;
	// 한 페이지 결과 수
	public static String _reqNumOfRows;
	// 요청자료형식
	public static String _reqDataType;

	/* 특보코드조회 처리용 변수 */
	//
	public static List<String> _uniqueKeyList;
	//
	public static String _today_yyyymmdd;
	//
	public static String _yesterday_yyyymmdd;


	// 초단기 실황 정보 취득 대상 지역 리스트
	public static List<UltraSrtNcstReqDTO> _ultraSrtNcstTargetList;

	public static List<UltraSrtFcstReqDTO> _ultraSrtFcstTargetList;

	// 특보코드조회 정보 취득 대상 지역 리스트
	public static List<PwnCdReqDTO> _pwnCdTargetList;

	// 초단기실황 API 연계 스케쥴 실행 여부
	private String _ultrasrtncstScheduleOnOff;

	// 초단기예보 API 연계 스케쥴 실행 여부
	private String _ultrasrtfcstScheduleOnOff;

	// 특보코드조회 API 연계 스케쥴 실행 여부
	private String _pwncdScheduleOnOff;

	/**
	 * constructor
	 */
	public WeatherScheduleManager() {
	}

	/**
	 * initialize
	 * @throws Exception
	 */
	public void init() throws Exception {

		/*  */
		this._reqPageNo 	= ConfigManager.getProps("weather.api.pageno");
		this._reqNumOfRows 	= ConfigManager.getProps("weather.api.numofrows");
		this._reqDataType 	= ConfigManager.getProps("weather.api.datatype");

		/*  */
		this._ultrasrtncstScheduleOnOff = ConfigManager.getProps("ultrasrtncst.schedule.onoff");
		this._ultrasrtfcstScheduleOnOff = ConfigManager.getProps("ultrasrtfcst.schedule.onoff");

		this._pwncdScheduleOnOff = ConfigManager.getProps("pwncd.schedule.onoff");

        /*  */
		if(ConstDef.OnOff.ON.equals(_ultrasrtncstScheduleOnOff) == true) {
			loadUltraSrtNcstDb();
		}
		if(ConstDef.OnOff.ON.equals(_ultrasrtfcstScheduleOnOff) == true) {
			loadUltraSrtFcstDb();
		}
		/* 특보 */
		if(ConstDef.OnOff.ON.equals(_pwncdScheduleOnOff) == true) {
			//loadPwnCdFile();

			//
			_uniqueKeyList = new ArrayList<String>();
			_today_yyyymmdd = Utils.getDate(EnumDateFormat.YYYYMMDD);
			_yesterday_yyyymmdd = Utils.getDate(-1);
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


			logger.info(String.format("[스케쥴링ON/OFF]초단기실황:%s", _ultrasrtncstScheduleOnOff));

			/* 1) 초단기실황 API 연계 스케쥴 */
			if(ConstDef.OnOff.ON.equals(_ultrasrtncstScheduleOnOff) == true) {
				cronExpr = ConfigManager.getProps("ultrasrtncst.cron.rule");
				jobNm = ConfigManager.getProps("ultrasrtncst.job.name");
				cronTriggerNm = ConfigManager.getProps("ultrasrtncst.crontrigger.name");
				group = ConfigManager.getProps("ultrasrtncst.schedule.group");

				// 초단기실황 API 연계 스케쥴러 시작
		    	QuartzCronScheduler.getInstance().schedule(UltraSrtNcstJob.class, cronExpr, jobNm, cronTriggerNm, group);
			}

			logger.info(String.format("[스케쥴링ON/OFF]초단기예보:%s", _ultrasrtfcstScheduleOnOff));
			/* 2) 초단기예보 API 연계 스케쥴 */
			if(ConstDef.OnOff.ON.equals(_ultrasrtfcstScheduleOnOff) == true) {
				cronExpr = ConfigManager.getProps("ultrasrtfcst.cron.rule");
				jobNm = ConfigManager.getProps("ultrasrtfcst.job.name");
				cronTriggerNm = ConfigManager.getProps("ultrasrtfcst.crontrigger.name");
				group = ConfigManager.getProps("ultrasrtfcst.schedule.group");

				// 초단기예보 API 연계 스케쥴러 시작
		    	QuartzCronScheduler.getInstance().schedule(UltraSrtFcstJob.class, cronExpr, jobNm, cronTriggerNm, group);
			}


			//logger.info(String.format("[스케쥴링ON/OFF]특보코드:%s", _pwncdScheduleOnOff));
			/* 3) 특보코드조회 API 연계 스케쥴 */
			/*
			if(ConstDef.OnOff.ON.equals(_pwncdScheduleOnOff) == true) {
				cronExpr = ConfigManager.getProps("pwncd.cron.rule");
				jobNm = ConfigManager.getProps("pwncd.job.name");
				cronTriggerNm = ConfigManager.getProps("pwncd.crontrigger.name");
				group = ConfigManager.getProps("pwncd.schedule.group");

				// 특보코드조회 API 연계 스케쥴러 시작
		    	QuartzCronScheduler.getInstance().schedule(PwnCdJob.class, cronExpr, jobNm, cronTriggerNm, group);
			}
			 */

			//
			if(ConstDef.OnOff.ON.equals(_ultrasrtncstScheduleOnOff) == false &&
			   ConstDef.OnOff.ON.equals(_ultrasrtfcstScheduleOnOff)	== false &&
			   ConstDef.OnOff.ON.equals(_pwncdScheduleOnOff) 		== false  ) {
				logger.warn("설정파일확인.. 연계 대상 API 플래그 모두 OFF");
				return;
			} else {
				logger.info("start schedule!");
				QuartzCronScheduler.getInstance().start();
			}



	}


	/**
	 * 기상 정보 취득 대상 지역 정보 DB 로드
	 * @throws Exception
	 */
	private void loadUltraSrtNcstDb() throws Exception {
			_ultraSrtNcstTargetList = new ArrayList<UltraSrtNcstReqDTO>();
			_ultraSrtNcstTargetList = WeatherPrcsDAO.selectUltraSrtNcst();

			//logger.debug("::::::::::::::::::=>  weather xy "+ _ultraSrtNcstTargetList.size());
	}

	private void loadUltraSrtFcstDb() throws Exception {

			_ultraSrtFcstTargetList = new ArrayList<UltraSrtFcstReqDTO>();
			_ultraSrtFcstTargetList = WeatherPrcsDAO.selectUltraSrtFcst();

			//logger.debug("::::::::::::::::::=>  weather xy "+ _ultraSrtFcstTargetList.size());

	}

	/**
	 * (unused)
	 * 특보구역코드 정보 파일 로드
	 */
	@SuppressWarnings("unused")
	private void loadPwnCdFile() {
	/*
		_pwnCdTargetList = new ArrayList<PwnCdReqDTO>();

		String filePath = ConstDef.USER_DIR
					    + File.separator
					    + ConfigManager.getProps("ref.path")
					    + File.separator
					    + ConfigManager.getProps("pwncd.target.list");

		try {
            FileReader fr = new FileReader(new File(filePath));
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] tokens;
            int num = 0;

            while((line = br.readLine()) != null) {
                tokens = line.split("\\" + ConstDef.SEPARATOR);
                if(tokens.length == 2) {
                	PwnCdReqDTO item = new PwnCdReqDTO();
                	item.setAreaCd(tokens[0]);
                    item.setAreaNm(tokens[1]);
                    item.setNum(num++);

                    _pwnCdTargetList.add(item);
                }
            }
            br.close();
        } catch (Exception e) {
        	System.out.println("Exception Message"  + e.getClass().getSimpleName() );
        }
    */
	}
}
