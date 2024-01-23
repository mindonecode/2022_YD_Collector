package mone.db.conn;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.db.dao.PrcsDAO;

/**
 * db 재연결
 * @author iby
 *
 */
public class MariaDbReconnectThread implements Runnable {

	// 로그
	private static final Logger logger = LogManager.getLogger(MariaDbReconnectThread.class);

	/**
	 * 실행
	 */
	@Override
	public void run() {


			// 테스트 쿼리, 정상 : 1, 이상 : -1
			try {
				DbManager.mariaDbConnVal = PrcsDAO.dbConnectCheckMaria();
			} catch (Exception e) {
				System.out.println("Exception Message mariaDbConnVal 1st" );
			}

			if(DbManager.mariaDbConnVal < 0) {
				//logger.info("(NG)disconnected db, try connection ::: MariaDB ");
				try {
					DbManager.connMaria();
				} catch (IOException e1) {
					System.out.println("Exception Message connMaria" );
				}

				// 테스트 쿼리, 정상 : 1, 이상 : -1
				try {
					DbManager.mariaDbConnVal = PrcsDAO.dbConnectCheckMaria();
				} catch (Exception e) {
					System.out.println("Exception Message mariaDbConnVal 2nd");
				}
				if(DbManager.mariaDbConnVal < 0) {
					//logger.debug("(OK)db connected  ::: MariaDB ");
				}
			} else {
				//logger.debug("(OK)db connected  ::: MariaDB ");
			}

	}
}
