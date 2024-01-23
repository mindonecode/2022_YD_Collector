package mone.db.conn;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConfigManager;
import mone.common.conf.ConstDef;


/**
 * db 연동
 * @author iby
 *
 */
public class DbManager {

	// 로그
	private static final Logger logger = LogManager.getLogger(DbManager.class);
	// sql 세션
	private static SqlSession _sqlSession_maria = null;
	private static SqlSession _sqlSession_postgres = null;

	// 정상 : 1, 이상 : -1
	public static int mariaDbConnVal = -1;
	public static int postgresDbConnVal = -1;
	/**
	 * db 설정
	 * @throws IOException
	 */
	public static void connMaria() throws IOException {
	//static {

			closeMaria();

			String resource   = "config" + File.separator + ConfigManager.getProps("maria.mybatis.conf.name");


            Reader reader 	= Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            _sqlSession_maria = sqlMapper.openSession();

            mariaDbConnVal = 1;
            //logger.info(String.format("success initialize (MariaDB)"+ " : %s", resource));


    }


	public static void connPostgres() throws IOException {
		//static {

				closePostgres();

				String resource = "config" + File.separator + ConfigManager.getProps("postgres.mybatis.conf.name");


	            Reader reader 	= Resources.getResourceAsReader(resource);
	            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
	            _sqlSession_postgres = sqlMapper.openSession();

	            postgresDbConnVal = 1;
	            //logger.info(String.format("success initialize (PostgresDB)"+ " : %s", resource));


	    }



	/**
	 * 주기적 db 재연결 시도
	 */
	public static void startReconnectThreadMaria() {
		final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

		executor.scheduleAtFixedRate(new MariaDbReconnectThread(), 0, ConstDef.db_reconn_time_ms, TimeUnit.MILLISECONDS);

	}


	public static void startReconnectThreadPostgres() {
		final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		// 1분 주기
		executor.scheduleAtFixedRate(new PostgresDbReconnectThread(), 0, ConstDef.db_reconn_time_ms, TimeUnit.MILLISECONDS);
	}

	/**
	 * sql 세션 반환
	 * @return sql 세션
	 */
    public static SqlSession getSqlSessionMaria() {
    	return _sqlSession_maria;
    }

    public static SqlSession getSqlSessionPostgres() {
    	return _sqlSession_postgres;
    }

    /**
     * sql 세션 닫기
     */
    public static void closeMaria() {

    		mariaDbConnVal = -1;

    		if(getSqlSessionMaria() != null) {
        		_sqlSession_maria.close();
        		_sqlSession_maria = null;
        	}

    }

    public static void closePostgres() {

    		postgresDbConnVal = -1;

    		if(getSqlSessionPostgres() != null) {
    			_sqlSession_postgres.close();
    			_sqlSession_postgres = null;
        	}

    }
}
