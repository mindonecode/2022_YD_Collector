package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConstDef;
import mone.db.dao.PrcsDAO;

/**
 * 프로그램 종료 시 처리
 * @author iby
 *
 */
public class MariaDBShutdownHookThread extends Thread {

	// 로그
	private static final Logger logger = LogManager.getLogger(MariaDBShutdownHookThread.class);

	/**
	 * 프로그램 종료 시 실행 함수
	 * TH_PROCS_STTUS, TN_PROCS_STTUS 테이블에 OFF:002 로 값 갱신
	 */
	@SuppressWarnings("static-access")
	public void run() {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				System.out.println("Exception Message sleep" );
			}

			// 프로세스 상태 현황 & 이력 갱신 - OFF
			PrcsDAO prcsDAO = new PrcsDAO();
			try {
				prcsDAO.updateProcsStatus(ConstDef.PROCS_STTUS_CD.OFF, "MariaDB");
			} catch (Exception e) {
				System.out.println("Exception Message updateProcsStatus" );
			}

			//logger.info("MariaDB Shutdown(OFF:002)");


	}
}
