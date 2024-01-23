package mone.schedule.job;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConfigManager;
import mone.common.conf.ConstDef;
import mone.common.util.Crawling;
import mone.db.conn.DbManager;
import mone.db.dao.AbnrDetDAO;
import mone.db.dao.BlowVolDAO;
import mone.db.dao.DeniphoDAO;
import mone.db.dao.FlowAdjDAO;
import mone.db.dao.InflDAO;
import mone.db.dao.InflPumpDAO;
import mone.db.dao.NewFcltDAO;
import mone.db.dao.OptmzCntrDataDao;
import mone.db.dao.PacInptDAO;
import mone.db.dao.PrcsDAO;
import mone.db.dao.PwrDAO;
import mone.db.dao.StngDAO;
import mone.db.dao.StngTankDAO;
import mone.db.dao.VbrtDAO;
import mone.db.dto.MariaResDTO;
import mone.db.dto.MariaResDTO.hm_minute_trend_history;
import mone.db.dto.MariaResDTO.hm_tag_dic;
import mone.db.dto.MariaResDTO.prcs_anly_flow_adj;
import mone.db.dto.MariaResDTO.prcs_anly_infl;
import mone.db.dto.MariaResDTO.prcs_anly_infl_pump;
import mone.db.dto.MariaResDTO.prcs_anly_pac_inpt;
import mone.db.dto.MariaResDTO.prcs_anly_stng_tank;
import mone.db.dto.MariaResDTO.prcs_anly_new_fclt;
import mone.db.dto.MariaResDTO.prcs_anly_abnr_det;
import mone.db.dto.MariaResDTO.prcs_anly_blow_vol;
import mone.db.dto.SystMngmAgtSttDTO;

/**
 * 데이터 조회 후 연계 저장
 * @author iby
 *
 */
public class CollectScadaData implements Job {
	// 로그
	private static final Logger logger = LogManager.getLogger(CollectScadaData.class);

	/**
	 * constructor
	 */
	public CollectScadaData() {
	}

	public static String selectFinalDb(String TbNm) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("postgres.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
        String result = "";

		String sqlId = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_CHECK_FINAL_DATE);
		try {

			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("chkTablenm", TbNm);

			resultMap.put("scadaSelectFromDt", ConfigManager.getProps("scada.select.from.date"));
			resultMap.put("scadaSelectEndDt", ConfigManager.getProps("scada.select.end.date="));

			result = session.selectOne(sqlId, resultMap);
			session.commit();

		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
			//e.printStackTrace();
		}finally {
			if(session!=null) {	session.close();}
		}

		//logger.debug("selectFinalDb >>>>>>>>>>>>>>>>>>>>       "+ TbNm + " ::::::::::: "  + result );

		return result;
	}


	/*
	 * 실행
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Calendar now = Calendar.getInstance();
		int minute = now.get(Calendar.MINUTE);
		int intMin = 0;
		if(minute<10) {
			intMin = minute;
		}else {
			intMin = minute%10;
		}


		//임시용 웹 크롤링 nh4 / nh3
		/*
		try {
			Crawling.MeasuringCrawling();
		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}
		*/


		String nowReordTime = String.format("%02d", now.get(Calendar.HOUR_OF_DAY));
		String nowReordMin = String.format("%02d", now.get(Calendar.MINUTE));
		//logger.debug("nowReordTime ::::::::: "+ nowReordTime + (!nowReordTime.equals("01") && !nowReordTime.equals("02")  && !nowReordTime.equals("03") && !nowReordTime.equals("15")) );



		if(   !nowReordMin.equals("59")  ) {
			try {
				/*******************************START prcs_anly_abnr_det [진동기반 이상감지] ********************************/
				//AbnrDetDAO.execPrcsAnlyAbnrDet(PrcsDAO.prcsAnlyAbnrDet("", ""));
				/*******************************END prcs_anly_abnr_det *************************************************/

				/*******************************START prcs_anly_flow_adj [유량 조절설비] ***********************************/
				FlowAdjDAO.insertPrcsAnlyFlowAdj(PrcsDAO.prcsAnlyFlowAdj(selectFinalDb("flux_eqlt_eqp_tag_clct")));
				/*******************************END prcs_anly_flow_adj *************************************************/

				/*******************************START prcs_anly_pac_inpt [PAC 투입량] ************************************/
				PacInptDAO.execPrcsAnlyPacInpt(PrcsDAO.prcsAnlyPacInpt(selectFinalDb("pac_inpt_vol_predict_data")));
				/*******************************END prcs_anly_pac_inpt *************************************************/

				/*******************************START prcs_anly_stng_tank [최초 침전지] ***********************************/
				StngTankDAO.execPrcsAnlyStngTank(PrcsDAO.prcsAnlyStngTank(selectFinalDb("frst_sdmt_tank_tag_clct")));
				/*******************************END prcs_anly_stng_tank ************************************************/

				/*******************************START prcs_anly_blow_vol [송풍량] ****************************************/
				BlowVolDAO.execPrcsAnlyBlowVol(PrcsDAO.prcsAnlyBlowVol(selectFinalDb("wot_vol_tag_clct")));
				/*******************************END prcs_anly_blow_vol *************************************************/

				/*******************************START prcs_anly_infl [유입량] ********************************************/
				InflDAO.execPrcsAnlyInfl(PrcsDAO.prcsAnlyInfl(selectFinalDb("infl_vol_tag_clct")));
				/*******************************END prcs_anly_infl *****************************************************/

				/*******************************START prcs_anly_infl_pump [유입펌프 전력] **********************************/
				InflPumpDAO.execPrcsAnlyInflPump(PrcsDAO.prcsAnlyInflPump(selectFinalDb("infl_pump_pwrer_tag_clct")));
				/*******************************END prcs_anly_infl_pump ************************************************/

				/*******************************START prcs_anly_new_fclt [신규설비알람] ************************************/
				NewFcltDAO.execPrcsAnlyNewFclt(PrcsDAO.prcsAnlyNewFclt(selectFinalDb("new_eqp_tag_clct")));
				/*******************************END prcs_anly_new_fclt *************************************************/

				/*******************************START prcs_anly_pwr [전력량 측정] *****************************************/
				PwrDAO.execPrcsAnlyPwr(PrcsDAO.prcsAnlyPwr(selectFinalDb("pwrer_tag_clct")));
				/*******************************END prcs_anly_pwr ******************************************************/

				/*******************************START prcs_anly_vbrt [진동 측정] *****************************************/
				VbrtDAO.execPrcsAnlyVbrt(PrcsDAO.prcsAnlyVbrt(selectFinalDb("vibrat_tag_clct")));
				/*******************************END prcs_anly_vbrt *****************************************************/

				/*******************************START prcs_anly_denipho [NH4 NO3] **************************************/
				DeniphoDAO.execPrcsAnlyDenipho(PrcsDAO.prcsAnlyDenipho(selectFinalDb("denipho_sers_msur_dnsty")));
				/*******************************END prcs_anly_vbrt *****************************************************/

				/*******************************START prcs_anly_stng_tag_clct  *****************************************/
				StngDAO.execPrcsAnlyStngInfl(PrcsDAO.prcsAnlyStngInfl(selectFinalDb("prcs_anly_stng_tag_clct")));
				/*******************************END prcs_anly_stng_tag_clct ********************************************/



				if(!"192.168.1.50".equals(InetAddress.getLocalHost().getHostAddress()) &&
					!"183.98.25.8".equals(InetAddress.getLocalHost().getHostAddress())
					) {
					/*******************************START 1. 지능화 모드 확인 ****************************************************/
					OptmzCntrDataDao.execHmiOptmzCntrData(PrcsDAO.hmi_optmz_cntr_data("pump"), 		"pump");
					OptmzCntrDataDao.execHmiOptmzCntrData(PrcsDAO.hmi_optmz_cntr_data("denipho"), 	"denipho");
					OptmzCntrDataDao.execHmiOptmzCntrData(PrcsDAO.hmi_optmz_cntr_data("psbr"), 		"psbr");
					OptmzCntrDataDao.execHmiOptmzCntrData(PrcsDAO.hmi_optmz_cntr_data("pac"), 		"pac");
					OptmzCntrDataDao.execHmiOptmzCntrData(PrcsDAO.hmi_optmz_cntr_data("mlss"), 		"mlss");
					/*******************************END  ********************************************************************/

					/*******************************START 2. 지능화제어 값 확인 **************************************************/
					OptmzCntrDataDao.execOptOptmzCntrData(PrcsDAO.opt_optmz_cntr_data("pump"), 		"pump");
					OptmzCntrDataDao.execOptOptmzCntrData(PrcsDAO.opt_optmz_cntr_data("denipho"), 	"denipho");
					OptmzCntrDataDao.execOptOptmzCntrData(PrcsDAO.opt_optmz_cntr_data("psbr"), 		"psbr");
					OptmzCntrDataDao.execOptOptmzCntrData(PrcsDAO.opt_optmz_cntr_data("pac"), 		"pac");
					OptmzCntrDataDao.execOptOptmzCntrData(PrcsDAO.opt_optmz_cntr_data("mlss"), 		"mlss");
					/*******************************END  ********************************************************************/
				}


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


}
