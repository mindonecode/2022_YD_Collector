package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConstDef;
import mone.db.dao.PrcsDAO;

/**
 * 프로그램 실행 시 처리
 * @author iby
 *
 */
public class PostgresDBRunProcessThread implements Runnable {

	// 로그
	private static final Logger logger = LogManager.getLogger(PostgresDBRunProcessThread.class);

	/**
	 * 프로그램 실행 시 실행 함수
	 * TH_PROCS_STTUS, TN_PROCS_STTUS 테이블에 ON:001 로 값 갱신
	 */
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			//logger.debug("PostgresDB Run(ON:001)");

			// 프로세스 상태 현황 & 이력 갱신 - ON
			PrcsDAO prcsDAO = new PrcsDAO();
			prcsDAO.updateProcsStatus(ConstDef.PROCS_STTUS_CD.ON, "PostgresDB");

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
