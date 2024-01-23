package mone.db.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConstDef;
import mone.db.conn.DbManager;
import mone.db.dto.TnProcsSttusDto;
import mone.db.dto.UltraSrtFcstReqDTO;
import mone.db.dto.UltraSrtNcstReqDTO;

/**
 * db 데이터 취득 및 갱신
 * @author iby
 *
 */
public class WeatherPrcsDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(PrcsDAO.class);

	/**
	 * constructor
	 */
	public WeatherPrcsDAO() {
	}


	/**
	 * 프로세스 상태 현황 & 이력 갱신
	 * @param procs_sttus_code
	 * @return
	 */
	public static int updateProcsStatus(String procs_sttus_code) throws Exception {
		int result = -1;


			if(DbManager.postgresDbConnVal < 0) {
				logger.warn("db conn NG..");
				return result;
			}

			TnProcsSttusDto param = new TnProcsSttusDto();
			param.setProcs_id(ConstDef.POSTGRES_PROCESS_ID);
			param.setProcs_sttus_cd(procs_sttus_code);

			// MERGE TN_PROCS_STTUS (프로세스 상태)
			result = mergeTnProcsSttus(param);

			// INSERT TH_PROCS_STTUS (프로세스 상태이력)
			insertThProcsSttus(param);


		return result;
	}

	/**
	 * MERGE TN_PROCS_STTUS (프로세스 상태)
	 * @param param
	 * @return
	 */
	private static int mergeTnProcsSttus(TnProcsSttusDto param) throws Exception{
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.WeatherDB.NAMESPACE, ConstDef.WeatherDB.SQLID_MERGE_TH_WTHR_PRCS_STTS);

		int result = 0;

			session = DbManager.getSqlSessionPostgres();
			result = session.update(sqlId, param);
			session.commit();


		return result;
	}

	/**
	 * INSERT TH_PROCS_STTUS (프로세스 상태이력)
	 * @param param
	 * @return
	 */
	private static int insertThProcsSttus(TnProcsSttusDto param) throws Exception{
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.WeatherDB.NAMESPACE, ConstDef.WeatherDB.SQLID_INSERT_SYST_MNGM_AGT_STT);

		int result = 0;

			session = DbManager.getSqlSessionPostgres();
			result = session.insert(sqlId, param);
			session.commit();


		return result;
	}

	/**
	 * SELECT ULTRASRTNCST
	 * @return
	 */
	public static List<UltraSrtNcstReqDTO> selectUltraSrtNcst() throws Exception{
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.WeatherDB.NAMESPACE, ConstDef.WeatherDB.SQLID_SELECT_ULTRASRTNCST);

		List<UltraSrtNcstReqDTO> resultList = null;

			if(DbManager.postgresDbConnVal < 0) {
				logger.warn("db conn NG..");
				return resultList;
			}

			logger.debug(String.format("SQLID:%s, getSize:%s", sqlId, resultList));

			session = DbManager.getSqlSessionPostgres();
			resultList = session.selectList(sqlId);
			session.commit();



		return resultList;
	}

	public static List<UltraSrtFcstReqDTO> selectUltraSrtFcst() throws Exception{
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.WeatherDB.NAMESPACE, ConstDef.WeatherDB.SQLID_SELECT_ULTRASRTFCST);

		List<UltraSrtFcstReqDTO> resultList = null;

			if(DbManager.postgresDbConnVal < 0) {
				logger.warn("db conn NG..");
				return resultList;
			}

			logger.debug(String.format("SQLID:%s, getSize:%s", sqlId, resultList));

			session = DbManager.getSqlSessionPostgres();
			resultList = session.selectList(sqlId);
			session.commit();



		return resultList;
	}


	/**
	 * 초단기실황 db 테이블 데이터 추가
	 * @param dataMap
	 */
	public static void insertAllUltraSrtNcst(Map<String, Object> dataMap) throws Exception{
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.WeatherDB.NAMESPACE, ConstDef.WeatherDB.SQLID_INSERTALL_ULTRASRTNCST);


			if(DbManager.postgresDbConnVal < 0) {
				logger.warn("db conn NG..");
				return;
			}

			session = DbManager.getSqlSessionPostgres();
			int result = session.insert(sqlId, dataMap);
			session.commit();
			//logger.debug("[초단기 실황]db commit insertAllUltraSrtNcst() " + result);

	}

	public static void insertAllUltraSrtFcst(Map<String, Object> dataMap) throws Exception{
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.WeatherDB.NAMESPACE, ConstDef.WeatherDB.SQLID_INSERTALL_ULTRASRTFCST);


			if(DbManager.postgresDbConnVal < 0) {
				logger.warn("db conn NG..");
				return;
			}

			session = DbManager.getSqlSessionPostgres();
			int result = session.insert(sqlId, dataMap);
			session.commit();
			//logger.debug("[초단기 :: 예보]db commit insertAllUltraSrtFcst() " + result);

	}

	/**
	 * 특보코드조회 db 테이블 데이터 추가
	 * @param dataMap
	 */
	public static void insertAllPwnCd(Map<String, Object> dataMap) throws Exception{
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.WeatherDB.NAMESPACE, ConstDef.WeatherDB.SQLID_INSERTALL_PWNCD);


			if(DbManager.postgresDbConnVal < 0) {
				logger.warn("db conn NG..");
				return;
			}

			session = DbManager.getSqlSessionPostgres();
			int result = session.insert(sqlId, dataMap);
			session.commit();
			//logger.debug("[특보]db commit insertAllPwnCd() " + result);

	}

	/**
	 * 특보코드조회 db 테이블 데이터 추가
	 * @param dataMap
	 */
	public static void mergeAllPwnCd(Map<String, Object> dataMap) throws Exception{
		SqlSession session = null;
		String sqlId = String.format("%s.%s", ConstDef.WeatherDB.NAMESPACE, ConstDef.WeatherDB.SQLID_MERGEALL_PWNCD);


			if(DbManager.postgresDbConnVal < 0) {
				logger.warn("db conn NG..");
				return;
			}

			session = DbManager.getSqlSessionPostgres();
			int result = session.insert(sqlId, dataMap);
			session.commit();
			//logger.debug("[특보]db commit mergeAllPwnCd() " + result);

	}
}
