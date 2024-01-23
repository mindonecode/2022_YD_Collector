package mone.schedule.job;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConstDef;
import mone.db.dao.AbnrDetDAO;
import mone.db.dao.AllScadaDataDAO;
import mone.db.dao.BlowVolDAO;
import mone.db.dao.FlowAdjDAO;
import mone.db.dao.InflDAO;
import mone.db.dao.PacInptDAO;
import mone.db.dao.PrcsDAO;
import mone.db.dao.StngTankDAO;
import mone.db.dto.MariaResDTO;
import mone.db.dto.MariaResDTO.hm_minute_trend_history;
import mone.db.dto.MariaResDTO.hm_tag_dic;
import mone.db.dto.PostgresResDTO.prcs_anly_abnr_det;
import mone.db.dto.SystMngmAgtSttDTO;

/**
 * 데이터 조회 후 연계 저장
 * @author iby
 *
 */
public class CollectAllScadaData implements Job {
	// 로그
	private static final Logger logger = LogManager.getLogger(CollectAllScadaData.class);

	/**
	 * constructor
	 */
	public CollectAllScadaData() {
	}
	/*
	 * 실행
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {


		try {
			//1. maria data select
			List<hm_tag_dic> tagDicResultList = new ArrayList<hm_tag_dic>();
			//tagDicResultList = PrcsDAO.maria_tag_dic();

			List<hm_minute_trend_history> tagResultList = new ArrayList<hm_minute_trend_history>();
			tagResultList = PrcsDAO.minute_trend_all_history();

			//2. postgres data 저장
			AllScadaDataDAO.execScdaDataMinuteTrendHistory(tagResultList);



		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );

			 try {
	                //5초동안 대기
	                Thread.sleep(1000*5);
	            } catch (InterruptedException e1) { }

	            JobExecutionException e2 = new JobExecutionException(e);

	            System.out.println("즉시 재시도 여부 설정 전:"+e2.refireImmediately()); //재시도 여부 확인 false

	            e2.setRefireImmediately(true); //재시도 여부 true로 설정.

	            System.out.println("즉시 재시도 여부 설정 후 :"+e2.refireImmediately()); //재시도 여부 확인 true

	            throw e2; //예외 발생.

		}
	}
}
