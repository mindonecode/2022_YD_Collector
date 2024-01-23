package mone.db.dao;

import java.io.File;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConfigManager;
import mone.common.conf.ConstDef;
import mone.common.util.Utils;
import mone.db.conn.DbManager;
import mone.db.dto.SystMngmAgtSttDTO;
import mone.db.dto.MariaResDTO;
import mone.db.dto.MariaResDTO.hm_minute_trend_history;
import mone.db.dto.MariaResDTO.hm_tag_dic;
import mone.db.dto.MariaResDTO.optmz_cntr_data;
import mone.db.dto.MariaResDTO.prcs_anly_abnr_det;
import mone.db.dto.MariaResDTO.prcs_anly_blow_vol;
import mone.db.dto.MariaResDTO.prcs_anly_denipho;
import mone.db.dto.MariaResDTO.prcs_anly_infl;
import mone.db.dto.MariaResDTO.prcs_anly_infl_pump;
import mone.db.dto.MariaResDTO.prcs_anly_new_fclt;
import mone.db.dto.MariaResDTO.prcs_anly_pac_inpt;
import mone.db.dto.MariaResDTO.prcs_anly_stng_tag_clct;
import mone.db.dto.MariaResDTO.prcs_anly_stng_tank;
import mone.db.dto.PostgresResDTO;

/**
 * db 데이터 취득 및 갱신
 * @author iby
 *
 */
public class PrcsDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(PrcsDAO.class);

	/**
	 * constructor
	 */
	public PrcsDAO() {
	}

	/**
	 * db 연결 체크
	 * @return 정상 : 1, 이상 : -1
	 */
	public static int dbConnectCheckMaria() throws Exception{
		SqlSession session = null;
		String sqlId  =  String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_MARIA_DB_CONN_CHECK);

		int result = -1;

			session = DbManager.getSqlSessionMaria();
			if(session!=null) {
				result = session.selectOne(sqlId);
				session.commit();
			}


		return result;
	}

	public static int dbConnectCheckPostgres() throws Exception{
		SqlSession session = null;
		String sqlId =  String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_POSTGRES_DB_CONN_CHECK);

		int result = -1;

			session = DbManager.getSqlSessionPostgres();
			if(session!=null) {
				result = session.selectOne(sqlId);
				session.commit();
			}


		return result;
	}



	/**
	 * (프로세스 상태)
	 * @param param
	 * @return
	 */
	public static int insertSystMngmAgtStt(SystMngmAgtSttDTO param) throws Exception{
		SqlSession session = null;
		String sqlId =  String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_SYST_MNGM_AGT_STT);

		int result = 0;

			session = DbManager.getSqlSessionPostgres();
			result = session.insert(sqlId, param);
			session.commit();

			/* 지능화 자동제어 프로세스 실행 */
			//prcs_opt_run();

		return result;
	}


	public static void prcs_opt_run() throws Exception{
		SqlSession session = null;
		String sqlId =  String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_PRCS_OPT_RUN);

		session = DbManager.getSqlSessionPostgres();
		session.insert(sqlId);
		session.commit();
	}



	public static List<MariaResDTO.prcs_anly_abnr_det> prcsAnlyAbnrDet(String srcDate, String srcTime) throws Exception {
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_ABNR_DET  	);
		List<prcs_anly_abnr_det> result = null;


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = DbManager.getSqlSessionMaria();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			Calendar now = Calendar.getInstance();
			int endMinute = now.get(Calendar.MINUTE);
			int startMinute = (now.get(Calendar.MINUTE) < 2) ? 0 : now.get(Calendar.MINUTE)-2;



			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}

			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);

			if(!srcDate.equals(""))   {
				resultMap.put("srcDate", srcDate);
			}else {
				resultMap.put("srcDate", now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DATE));
			}

			if(!srcDate.equals(""))   {
				resultMap.put("srcTime", srcTime);
			}else {
				resultMap.put("srcTime", now.get(Calendar.MINUTE));
			}


			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();



		return result;
	}

	public static List<MariaResDTO.prcs_anly_flow_adj> prcsAnlyFlowAdj(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);


		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_FLOW_ADJ);
		List<MariaResDTO.prcs_anly_flow_adj> result =  new ArrayList<MariaResDTO.prcs_anly_flow_adj>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			//logger.debug("prcsAnlyFlowAdj ::::::::::::: " +  finalChkDt);

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}

			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);

			result = session.selectList(sqlId, resultMap);

			session.commit();


		return result;
	}


	public static List<MariaResDTO.prcs_anly_pac_inpt> prcsAnlyPacInpt(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_PAC_INPT);
		List<MariaResDTO.prcs_anly_pac_inpt> result = new ArrayList<prcs_anly_pac_inpt>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);
			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();



		return result;
	}



	public static List<MariaResDTO.prcs_anly_stng_tank> prcsAnlyStngTank(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_STNG_TANK  	);
		List<MariaResDTO.prcs_anly_stng_tank> result = new ArrayList<prcs_anly_stng_tank>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);
			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();



		return result;
	}

	public static List<MariaResDTO.prcs_anly_blow_vol> prcsAnlyBlowVol(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_BLOW_VOL  	);
		List<MariaResDTO.prcs_anly_blow_vol> result = new ArrayList<prcs_anly_blow_vol>();

			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);
			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();



		return result;
	}

	public static List<MariaResDTO.prcs_anly_infl> prcsAnlyInfl(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_INFL);
		List<MariaResDTO.prcs_anly_infl> result = new ArrayList<prcs_anly_infl>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);

			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();


		return result;
	}

	public static List<MariaResDTO.prcs_anly_infl_pump> prcsAnlyInflPump(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_INFL_PUMP  	);
		List<MariaResDTO.prcs_anly_infl_pump> result = new ArrayList<prcs_anly_infl_pump>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);
			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();



		return result;
	}


	public static List<MariaResDTO.prcs_anly_new_fclt> prcsAnlyNewFclt(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_NEW_FCLT 	);
		List<MariaResDTO.prcs_anly_new_fclt> result = new ArrayList<prcs_anly_new_fclt>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			//logger.debug("recordDate::======> " +  resultMap.get("recordDate"));
			//logger.debug("recordTime::======> " +  resultMap.get("recordTime"));
			//logger.debug("startMinute::======> " +  startMinute);
			//logger.debug("endMinute::======> " +  endMinute);

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);
			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();



		return result;
	}

	public static List<MariaResDTO.prcs_anly_denipho> prcsAnlyDenipho(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_DENIPHO 	);
		List<MariaResDTO.prcs_anly_denipho> result = new ArrayList<prcs_anly_denipho>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			//logger.debug("recordDate::======> " +  resultMap.get("recordDate"));
			//logger.debug("recordTime::======> " +  resultMap.get("recordTime"));
			//logger.debug("startMinute::======> " +  startMinute);
			//logger.debug("endMinute::======> " +  endMinute);

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);

			//logger.debug(">>>>>>>>>>>>>>>>>>>>>" + resultList);

			result = session.selectList(sqlId, resultMap);


			//logger.debug(">>>>>>>>>>>>>>>>>>>>>" + result.size());



			session.commit();



		return result;
	}




	public static List<MariaResDTO.prcs_anly_stng_tag_clct> prcsAnlyStngInfl(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_STNG_INFL 	);
		List<MariaResDTO.prcs_anly_stng_tag_clct> result = new ArrayList<prcs_anly_stng_tag_clct>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			//logger.debug("recordDate::======> " +  resultMap.get("recordDate"));
			//logger.debug("recordTime::======> " +  resultMap.get("recordTime"));
			//logger.debug("startMinute::======> " +  startMinute);
			//logger.debug("endMinute::======> " +  endMinute);

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);

			//logger.debug(">>>>>>>>>>>>>>>>>>>>>" + resultList);

			result = session.selectList(sqlId, resultMap);


			//logger.debug(">>>>>>>>>>>>>>>>>>>>>" + result.size());



			session.commit();



		return result;
	}


	public static List<MariaResDTO.hm_tag_dic> maria_tag_dic() throws Exception {

		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_DB_MARIA_TAG_DIC);
		List<hm_tag_dic> result = null;


			if(DbManager.mariaDbConnVal < 0) {
				logger.warn(" [maria] db conn NG..");
				return null;
			}

			if(DbManager.postgresDbConnVal < 0) {
				logger.warn(" [postgres] db conn NG..");
				return null;
			}

			session = DbManager.getSqlSessionMaria();
			result = session.selectList(sqlId);
			session.commit();
			//logger.debug("SCADA min Data Collect ::::  " + result);


		return result;
	}



	/**
	 * 프로세스 상태 현황 & 이력 갱신
	 * @param procs_sttus_code
	 * @return
	 * @throws Exception
	 */
	public static int updateProcsStatus(String procs_sttus_code, String dbType) throws Exception  {
		int result = -1;



			if(DbManager.mariaDbConnVal < 0) {
				logger.warn(dbType + " [maria] db conn NG..");
				return result;
			}

			if(DbManager.postgresDbConnVal < 0) {
				logger.warn(dbType + " [postgres] db conn NG..");
				return result;
			}

			SystMngmAgtSttDTO param = new SystMngmAgtSttDTO();

			param.setProcsType(dbType+"::conn");
			param.setProcsSttusCd(procs_sttus_code);
			param.setProcsCnt(DbManager.postgresDbConnVal);

			result = insertSystMngmAgtStt(param);



		return result;
	}


	public static List<PostgresResDTO.chk_alram_condtion> chk_alram_condtion(String updateTime, String flag, String tagId, Float val) throws Exception {
		SqlSession session = null;
		String resource = "config" + File.separator + ConfigManager.getProps("postgres.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_SELECT_CHK_ALRAM_CONDTION);
		List<PostgresResDTO.chk_alram_condtion> result = null;


			if(DbManager.mariaDbConnVal < 0) {
				logger.warn(" [maria] db conn NG..");
				return null;
			}

			if(DbManager.postgresDbConnVal < 0) {
				logger.warn(" [postgres] db conn NG..");
				return null;
			}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("tagId", tagId);
			result = session.selectList(sqlId, resultMap);
			session.commit();

			Calendar now = Calendar.getInstance();
			int minute = now.get(Calendar.MINUTE);
			int intMin = 0;
			if(minute<10) {
				intMin = minute;
			}else {
				intMin = minute%10;
			}

			for(int i=0; i<result.size(); i++) {
					String resultTagid = result.get(i).getTagId();
					double resultMaxVal = result.get(i).getMaxVal();
					double resultminVal = result.get(i).getMinVal();

					//logger.debug(":::::::::::::    resultTagid: " + resultTagid + "      tagId: " + tagId + "     resultMaxVal::  " + resultMaxVal + "  resultminVal::  " + resultminVal);

				//if( intMin == 5 ) {
					if(	flag=="cur" &&
						resultTagid.equals(tagId) &&
						(val >= resultMaxVal || val <= resultminVal )
					) {
						logger.debug(">>>>>>>>>>>>>>> " + 	( resultTagid.equals(tagId) && (val >= resultMaxVal || val <= resultminVal))  );

						NewAlrmlDAO.insertNewAlrmCr(	updateTime,
													result.get(i).getTableName(),
													result.get(i).getTagId(),
													Utils.tagAlramMsg(resultTagid + " " + result.get(i).getAlrmDscr(), resultMaxVal, resultminVal, val),
													val);
					}
				//}
			}


		return result;


	 }

	public static List<MariaResDTO.hm_minute_trend_history> minute_trend_all_history() throws Exception {

		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_MINUTE_TREND_ALL_HISTORY);
		List<hm_minute_trend_history> result = null;

			if(DbManager.mariaDbConnVal < 0) {
				logger.warn(" [maria] db conn NG..");
				return null;
			}

			if(DbManager.postgresDbConnVal < 0) {
				logger.warn(" [postgres] db conn NG..");
				return null;
			}

			session = DbManager.getSqlSessionMaria();
			result = session.selectList(sqlId);
			session.commit();
			//logger.debug("SCADA min Data Collect ::::  " + result);


		return result;
	}


	public static List<MariaResDTO.optmz_cntr_data> hmi_optmz_cntr_data(String optType) throws Exception {

		SqlSession session = null;
		String sqlId = "";

		if("pump".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_OPTMZ_CNTR_DATA_PUMP);
		}else if("denipho".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_OPTMZ_CNTR_DATA_DENIPHO);
		}else if("psbr".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_OPTMZ_CNTR_DATA_PSBR);
		}else if("pac".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_OPTMZ_CNTR_DATA_PAC);
		}else if("mlss".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_OPTMZ_CNTR_DATA_MLSS);
		}

		List<optmz_cntr_data> result = null;
		try {

			if(DbManager.mariaDbConnVal < 0) {
				logger.warn(" [maria] db conn NG..");
				return null;
			}

			if(DbManager.postgresDbConnVal < 0) {
				logger.warn(" [postgres] db conn NG..");
				return null;
			}

			session = DbManager.getSqlSessionMaria();
			result = session.selectList(sqlId);
			session.commit();

			//logger.debug("hmi ::::::::::::::::::::::::::::: " + optType);
			//logger.debug("hmi ::::::::::::::::::::::::::::: " + result);

		}catch (Exception e) {
			logger.error(" read maria & mark error" + e.toString());
		}

		return result;
	}


	public static List<PostgresResDTO.optmz_cntr_data> opt_optmz_cntr_data(String optType) throws Exception {

		SqlSession session = null;
		String sqlId = "";

		if("pump".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_SELECT_OPTMZ_CNTR_DATA_PUMP);
		}else if("denipho".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_SELECT_OPTMZ_CNTR_DATA_DENIPHO);
		}else if("psbr".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_SELECT_OPTMZ_CNTR_DATA_PSBR);
		}else if("pac".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_SELECT_OPTMZ_CNTR_DATA_PAC);
		}else if("mlss".equals(optType)) {
			sqlId = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_SELECT_OPTMZ_CNTR_DATA_MLSS);
		}

		List<PostgresResDTO.optmz_cntr_data> result = null;

			if(DbManager.mariaDbConnVal < 0) {
				logger.warn(" [maria] db conn NG..");
				return null;
			}

			if(DbManager.postgresDbConnVal < 0) {
				logger.warn(" [postgres] db conn NG..");
				return null;
			}

			session = DbManager.getSqlSessionPostgres();
			result = session.selectList(sqlId);
			session.commit();

			//logger.debug("opt ::::::::::::::::::::::::::::: " + optType);
			//logger.debug("opt ::::::::::::::::::::::::::::: " + result);

		return result;
	}


	public static List<MariaResDTO.prcs_anly_pwr> prcsAnlyPwr(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_PWR 	);
		List<MariaResDTO.prcs_anly_pwr> result = new ArrayList<MariaResDTO.prcs_anly_pwr>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);
			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();



		return result;
	}

	public static List<MariaResDTO.prcs_anly_vbrt> prcsAnlyVbrt(String finalChkDt) throws Exception {
		SqlSession session = null;
		String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");
        Reader reader 	= Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

		String sqlId = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_SELECT_PRCS_ANLY_VBRT 	);
		List<MariaResDTO.prcs_anly_vbrt> result = new ArrayList<MariaResDTO.prcs_anly_vbrt>();


			if(DbManager.mariaDbConnVal < 0) 	{	logger.warn(" [maria] db conn NG..");		return null;	}
			if(DbManager.postgresDbConnVal < 0) {	logger.warn(" [postgres] db conn NG..");	return null;	}

			session = sqlMapper.openSession();

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<String> resultList = new ArrayList<String>();

			int endMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("endMinute").toString());
			int startMinute = Integer.parseInt(Utils.BeginFrom(finalChkDt).get("startMinute").toString());
			resultMap.put("recordDate", Utils.BeginFrom(finalChkDt).get("recordDate").toString());
			resultMap.put("recordTime", Utils.BeginFrom(finalChkDt).get("recordTime").toString());

			for (int i=startMinute; i <= endMinute; i++) {
				resultList.add("T"+String.format("%02d", i));
				//resultList.add("T"+String.format("%02d", i)+"_STATUS");
        	}
			resultList.add("MIN_VAL");
			resultList.add("MAX_VAL");
			resultList.add("AVG_VAL");
			resultList.add("SUM_VAL");

			resultMap.put("resultList", resultList);
			//logger.debug(resultMap);

			result = session.selectList(sqlId, resultMap);
			session.commit();


		return result;
	}




}
