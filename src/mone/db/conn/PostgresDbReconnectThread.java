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
public class PostgresDbReconnectThread implements Runnable {

	// 로그
	private static final Logger logger = LogManager.getLogger(PostgresDbReconnectThread.class);

	/**
	 * 실행
	 */
	@Override
	public void run(){

			// 테스트 쿼리, 정상 : 1, 이상 : -1
			try {
				DbManager.postgresDbConnVal = PrcsDAO.dbConnectCheckPostgres();
			} catch (Exception e1) {
				System.out.println("Exception postgresDbConnVal 1st");
			}

			if(DbManager.postgresDbConnVal < 0) {
				//logger.info("(NG)disconnected db, try connection ::: PostgresDB ");
				try {
					DbManager.connPostgres();
				} catch (IOException e1) {
					System.out.println("Exception connPostgres ");
				}

				// 테스트 쿼리, 정상 : 1, 이상 : -1
				try {
					DbManager.postgresDbConnVal = PrcsDAO.dbConnectCheckPostgres();
				} catch (Exception e) {
				System.out.println("Exception postgresDbConnVal 2nd");
				}
				if(DbManager.postgresDbConnVal < 0) {
					//logger.debug("(OK)db connected  ::: PostgresDB ");
				}
			} else {
				//logger.debug("(OK)db connected  ::: PostgresDB ");
			}

	}
}
