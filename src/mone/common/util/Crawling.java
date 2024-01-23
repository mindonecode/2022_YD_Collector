package mone.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mone.common.conf.ConstDef;
import mone.db.conn.DbManager;
import mone.db.dao.NewAlrmlDAO;
import mone.db.dao.PrcsDAO;
import mone.db.dto.MariaResDTO;
import mone.db.dto.PostgresResDTO;

public class Crawling {

	private static final Logger logger = LogManager.getLogger(NewAlrmlDAO.class);


	public static void MeasuringCrawling() throws IOException {


		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_PRCS_ANLY_DENIPHO);
		PostgresResDTO.prcs_anly_denipho prcsAnlyDenipho  = new  PostgresResDTO.prcs_anly_denipho();
		session = DbManager.getSqlSessionPostgres();

		Calendar now = Calendar.getInstance();
		String updtTm = now.get(Calendar.YEAR) + "-" +
						String.format("%02d", now.get(Calendar.MONTH)+1) + "-" +
						String.format("%02d", now.get(Calendar.DATE)) + " " +
						String.format("%02d", now.get(Calendar.HOUR_OF_DAY)) + ":" +
						String.format("%02d", now.get(Calendar.MINUTE)) + ":00 +09:00" ;


		// 자료를 가져올 사이트에 연결하기
		Document doc = Jsoup.connect("http://223.171.60.233/").get();
		//System.out.println(doc.data()); // html 코드를 가져온다.

		Elements S01 = doc.select("tr.EvenRow td");
		Elements S02 = doc.select("tr.OddRow td");

		//logger.debug(":::::::::::          "   + updtTm);
		for (int i = 0; i < 10; i++) {
			Element NH4 = S01.get(i);
			Element NO3 = S02.get(i);

			if(i==5 ) {

				prcsAnlyDenipho.setNh4NCncn(Float.parseFloat(NH4.text()));
				prcsAnlyDenipho.setNo3NCncn(Float.parseFloat(NO3.text()));
				prcsAnlyDenipho.setUpdateTime(updtTm);


				//logger.debug(" 5 ::::::::::::::::::::::=> " + prcsAnlyDenipho.getNh4NCncn() + " ::::::::::::: " + prcsAnlyDenipho.getNh3NCncn());

				prcsAnlyDenipho.setFlag("cur");
				session.insert(sqlId, prcsAnlyDenipho);
				session.commit();
			}

			if(i==8 ) {
				prcsAnlyDenipho.setNh4NClss(Float.parseFloat(NH4.text()));
				prcsAnlyDenipho.setNo3NClss(Float.parseFloat(NO3.text()));
				prcsAnlyDenipho.setUpdateTime(updtTm);

				//logger.debug(" 8 ::::::::::::::::::::::=> " + prcsAnlyDenipho.getNh4NClss() + " ::::::::::::: " + prcsAnlyDenipho.getNh3NClss());

				prcsAnlyDenipho.setFlag("cur");
				session.insert(sqlId, prcsAnlyDenipho);
				session.commit();
			}



		}



	}
}