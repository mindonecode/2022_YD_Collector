package mone.db.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConstDef;
import mone.db.conn.DbManager;
import mone.db.dto.MariaResDTO;
import mone.db.dto.MariaResDTO.hm_minute_trend_history;
import mone.db.dto.MariaResDTO.hm_tag_dic;
import mone.db.dto.PostgresResDTO;
import mone.db.dto.SystMngmAgtSttDTO;

/**
 * db 데이터 취득 및 갱신
 * @author iby
 *
 */
public class PwrDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(PwrDAO.class);

	/**
	 * constructor
	 */
	public PwrDAO() {
	}


	public static void execPrcsAnlyPwr(List<MariaResDTO.prcs_anly_pwr> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_PWR);

		systMngmAgtStt.setProcsCnt(insertPrcsAnlyPwr(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);

	}

	public static int insertPrcsAnlyPwr(List<MariaResDTO.prcs_anly_pwr> tagParams) throws Exception {
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_PWR);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_pwr prcsAnlyPwr  = new  PostgresResDTO.prcs_anly_pwr();

			for(int i=0; i<tagParams.size(); i++) {

				String strDate = tagParams.get(i).getRecordDate();
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date formatDate = dtFormat.parse(strDate);
				String strNewDtFormat = newDtFormat.format(formatDate);

				if(tagParams.get(i).getLt101AiLev() > 0) {

					prcsAnlyPwr.setM117aAiW(tagParams.get(i).getM117aAiW());
					prcsAnlyPwr.setM117aAiWh(tagParams.get(i).getM117aAiWh());
					prcsAnlyPwr.setM117aAiPer(tagParams.get(i).getM117aAiPer());
					prcsAnlyPwr.setM117bAiW(tagParams.get(i).getM117bAiW());
					prcsAnlyPwr.setM117bAiWh(tagParams.get(i).getM117bAiWh());
					prcsAnlyPwr.setM117bAiPer(tagParams.get(i).getM117bAiPer());
					prcsAnlyPwr.setM117cAiW(tagParams.get(i).getM117cAiW());
					prcsAnlyPwr.setM117cAiWh(tagParams.get(i).getM117cAiWh());
					prcsAnlyPwr.setM117cAiPer(tagParams.get(i).getM117cAiPer());
					prcsAnlyPwr.setM118AiW(tagParams.get(i).getM118AiW());
					prcsAnlyPwr.setM118AiWh(tagParams.get(i).getM118AiWh());
					prcsAnlyPwr.setM118AiPer(tagParams.get(i).getM118AiPer());
					prcsAnlyPwr.setM405aAiW(tagParams.get(i).getM405aAiW());
					prcsAnlyPwr.setM405aAiWh(tagParams.get(i).getM405aAiWh());
					prcsAnlyPwr.setM405aAiPer(tagParams.get(i).getM405aAiPer());
					prcsAnlyPwr.setM405bAiW(tagParams.get(i).getM405bAiW());
					prcsAnlyPwr.setM405bAiWh(tagParams.get(i).getM405bAiWh());
					prcsAnlyPwr.setM405bAiPer(tagParams.get(i).getM405bAiPer());
					prcsAnlyPwr.setM1002aAiW(tagParams.get(i).getM1002aAiW());
					prcsAnlyPwr.setM1002aAiWh(tagParams.get(i).getM1002aAiWh());
					prcsAnlyPwr.setM1002aAiPer(tagParams.get(i).getM1002aAiPer());
					prcsAnlyPwr.setM1002bAiW(tagParams.get(i).getM1002bAiW());
					prcsAnlyPwr.setM1002bAiWh(tagParams.get(i).getM1002bAiWh());
					prcsAnlyPwr.setM1002bAiPer(tagParams.get(i).getM1002bAiPer());
					prcsAnlyPwr.setM820aAiW(tagParams.get(i).getM820aAiW());
					prcsAnlyPwr.setM820aAiWh(tagParams.get(i).getM820aAiWh());
					prcsAnlyPwr.setM820aAiPer(tagParams.get(i).getM820aAiPer());
					prcsAnlyPwr.setM820bAiW(tagParams.get(i).getM820bAiW());
					prcsAnlyPwr.setM820bAiWh(tagParams.get(i).getM820bAiWh());
					prcsAnlyPwr.setM820bAiPer(tagParams.get(i).getM820bAiPer());
					prcsAnlyPwr.setM805aAiW(tagParams.get(i).getM805aAiW());
					prcsAnlyPwr.setM805aAiWh(tagParams.get(i).getM805aAiWh());
					prcsAnlyPwr.setM805aAiPer(tagParams.get(i).getM805aAiPer());
					prcsAnlyPwr.setM805bAiW(tagParams.get(i).getM805bAiW());
					prcsAnlyPwr.setM805bAiWh(tagParams.get(i).getM805bAiWh());
					prcsAnlyPwr.setM805bAiPer(tagParams.get(i).getM805bAiPer());
					prcsAnlyPwr.setM505aAiW(tagParams.get(i).getM505aAiW());
					prcsAnlyPwr.setM505aAiWh(tagParams.get(i).getM505aAiWh());
					prcsAnlyPwr.setM505aAiPer(tagParams.get(i).getM505aAiPer());
					prcsAnlyPwr.setM505cAiW(tagParams.get(i).getM505cAiW());
					prcsAnlyPwr.setM505cAiWh(tagParams.get(i).getM505cAiWh());
					prcsAnlyPwr.setM505cAiPer(tagParams.get(i).getM505cAiPer());

					prcsAnlyPwr.setM117aAiAr(tagParams.get(i).getM117aAiAr());
					prcsAnlyPwr.setM117aAiAs(tagParams.get(i).getM117aAiAs());
					prcsAnlyPwr.setM117aAiAt(tagParams.get(i).getM117aAiAt());
					prcsAnlyPwr.setM117bAiAr(tagParams.get(i).getM117bAiAr());
					prcsAnlyPwr.setM117bAiAs(tagParams.get(i).getM117bAiAs());
					prcsAnlyPwr.setM117bAiAt(tagParams.get(i).getM117bAiAt());
					prcsAnlyPwr.setM117cAiAr(tagParams.get(i).getM117cAiAr());
					prcsAnlyPwr.setM117cAiAs(tagParams.get(i).getM117cAiAs());
					prcsAnlyPwr.setM117cAiAt(tagParams.get(i).getM117cAiAt());
					prcsAnlyPwr.setM118AiAr(tagParams.get(i).getM118AiAr());
					prcsAnlyPwr.setM118AiAs(tagParams.get(i).getM118AiAs());
					prcsAnlyPwr.setM118AiAt(tagParams.get(i).getM118AiAt());
					prcsAnlyPwr.setM405aAiAr(tagParams.get(i).getM405aAiAr());
					prcsAnlyPwr.setM405aAiAs(tagParams.get(i).getM405aAiAs());
					prcsAnlyPwr.setM405aAiAt(tagParams.get(i).getM405aAiAt());
					prcsAnlyPwr.setM405bAiAr(tagParams.get(i).getM405bAiAr());
					prcsAnlyPwr.setM405bAiAs(tagParams.get(i).getM405bAiAs());
					prcsAnlyPwr.setM405bAiAt(tagParams.get(i).getM405bAiAt());
					prcsAnlyPwr.setM1002aAiAr(tagParams.get(i).getM1002aAiAr());
					prcsAnlyPwr.setM1002aAiAs(tagParams.get(i).getM1002aAiAs());
					prcsAnlyPwr.setM1002aAiAt(tagParams.get(i).getM1002aAiAt());
					prcsAnlyPwr.setM1002bAiAr(tagParams.get(i).getM1002bAiAr());
					prcsAnlyPwr.setM1002bAiAs(tagParams.get(i).getM1002bAiAs());
					prcsAnlyPwr.setM1002bAiAt(tagParams.get(i).getM1002bAiAt());
					prcsAnlyPwr.setM820aAiAr(tagParams.get(i).getM820aAiAr());
					prcsAnlyPwr.setM820aAiAs(tagParams.get(i).getM820aAiAs());
					prcsAnlyPwr.setM820aAiAt(tagParams.get(i).getM820aAiAt());
					prcsAnlyPwr.setM820bAiAr(tagParams.get(i).getM820bAiAr());
					prcsAnlyPwr.setM820bAiAs(tagParams.get(i).getM820bAiAs());
					prcsAnlyPwr.setM820bAiAt(tagParams.get(i).getM820bAiAt());
					prcsAnlyPwr.setM805aAiAr(tagParams.get(i).getM805aAiAr());
					prcsAnlyPwr.setM805aAiAs(tagParams.get(i).getM805aAiAs());
					prcsAnlyPwr.setM805aAiAt(tagParams.get(i).getM805aAiAt());
					prcsAnlyPwr.setM805bAiAr(tagParams.get(i).getM805bAiAr());
					prcsAnlyPwr.setM805bAiAs(tagParams.get(i).getM805bAiAs());
					prcsAnlyPwr.setM805bAiAt(tagParams.get(i).getM805bAiAt());
					prcsAnlyPwr.setM505aAiAr(tagParams.get(i).getM505aAiAr());
					prcsAnlyPwr.setM505aAiAs(tagParams.get(i).getM505aAiAs());
					prcsAnlyPwr.setM505aAiAt(tagParams.get(i).getM505aAiAt());
					prcsAnlyPwr.setM505cAiAr(tagParams.get(i).getM505cAiAr());
					prcsAnlyPwr.setM505cAiAs(tagParams.get(i).getM505cAiAs());
					prcsAnlyPwr.setM505cAiAt(tagParams.get(i).getM505cAiAt());

					prcsAnlyPwr.setM505cAiAt(tagParams.get(i).getM505cAiAt());
					prcsAnlyPwr.setM117aAiVr(tagParams.get(i).getM117aAiVr());
					prcsAnlyPwr.setM117aAiVs(tagParams.get(i).getM117aAiVs());
					prcsAnlyPwr.setM117aAiVt(tagParams.get(i).getM117aAiVt());
					prcsAnlyPwr.setM117bAiVr(tagParams.get(i).getM117bAiVr());
					prcsAnlyPwr.setM117bAiVs(tagParams.get(i).getM117bAiVs());
					prcsAnlyPwr.setM117bAiVt(tagParams.get(i).getM117bAiVt());
					prcsAnlyPwr.setM117cAiVr(tagParams.get(i).getM117cAiVr());
					prcsAnlyPwr.setM117cAiVs(tagParams.get(i).getM117cAiVs());
					prcsAnlyPwr.setM117cAiVt(tagParams.get(i).getM117cAiVt());
					prcsAnlyPwr.setM118AiVr(tagParams.get(i).getM118AiVr());
					prcsAnlyPwr.setM118AiVs(tagParams.get(i).getM118AiVs());
					prcsAnlyPwr.setM118AiVt(tagParams.get(i).getM118AiVt());
					prcsAnlyPwr.setM405aAiVr(tagParams.get(i).getM405aAiVr());
					prcsAnlyPwr.setM405aAiVs(tagParams.get(i).getM405aAiVs());
					prcsAnlyPwr.setM405aAiVt(tagParams.get(i).getM405aAiVt());
					prcsAnlyPwr.setM405bAiVr(tagParams.get(i).getM405bAiVr());
					prcsAnlyPwr.setM405bAiVs(tagParams.get(i).getM405bAiVs());
					prcsAnlyPwr.setM405bAiVt(tagParams.get(i).getM405bAiVt());
					prcsAnlyPwr.setM1002aAiVr(tagParams.get(i).getM1002aAiVr());
					prcsAnlyPwr.setM1002aAiVs(tagParams.get(i).getM1002aAiVs());
					prcsAnlyPwr.setM1002aAiVt(tagParams.get(i).getM1002aAiVt());
					prcsAnlyPwr.setM1002bAiVr(tagParams.get(i).getM1002bAiVr());
					prcsAnlyPwr.setM1002bAiVs(tagParams.get(i).getM1002bAiVs());
					prcsAnlyPwr.setM1002bAiVt(tagParams.get(i).getM1002bAiVt());
					prcsAnlyPwr.setM820aAiVr(tagParams.get(i).getM820aAiVr());
					prcsAnlyPwr.setM820aAiVs(tagParams.get(i).getM820aAiVs());
					prcsAnlyPwr.setM820aAiVt(tagParams.get(i).getM820aAiVt());
					prcsAnlyPwr.setM820bAiVr(tagParams.get(i).getM820bAiVr());
					prcsAnlyPwr.setM820bAiVs(tagParams.get(i).getM820bAiVs());
					prcsAnlyPwr.setM820bAiVt(tagParams.get(i).getM820bAiVt());
					prcsAnlyPwr.setM805aAiVr(tagParams.get(i).getM805aAiVr());
					prcsAnlyPwr.setM805aAiVs(tagParams.get(i).getM805aAiVs());
					prcsAnlyPwr.setM805aAiVt(tagParams.get(i).getM805aAiVt());
					prcsAnlyPwr.setM805bAiVr(tagParams.get(i).getM805bAiVr());
					prcsAnlyPwr.setM805bAiVs(tagParams.get(i).getM805bAiVs());
					prcsAnlyPwr.setM805bAiVt(tagParams.get(i).getM805bAiVt());
					prcsAnlyPwr.setM505aAiVr(tagParams.get(i).getM505aAiVr());
					prcsAnlyPwr.setM505aAiVs(tagParams.get(i).getM505aAiVs());
					prcsAnlyPwr.setM505aAiVt(tagParams.get(i).getM505aAiVt());

					prcsAnlyPwr.setM505bAiAr(tagParams.get(i).getM505bAiAr());
					prcsAnlyPwr.setM505bAiAs(tagParams.get(i).getM505bAiAs());
					prcsAnlyPwr.setM505bAiAt(tagParams.get(i).getM505bAiAt());

					prcsAnlyPwr.setM505cAiVr(tagParams.get(i).getM505cAiVr());
					prcsAnlyPwr.setM505cAiVs(tagParams.get(i).getM505cAiVs());
					prcsAnlyPwr.setM505cAiVt(tagParams.get(i).getM505cAiVt());

					String estVal = "";
					if("cur".equals(tagParams.get(i).getFlag()) ) {
						estVal = tagParams.get(i).getRecordMinute() + ":00";
					}else {
						estVal = "59:58";
					}
					prcsAnlyPwr.setUpdateTime(strNewDtFormat + " " + tagParams.get(i).getRecordTime() + ":" + estVal );
					prcsAnlyPwr.setFlag(tagParams.get(i).getFlag());

					session.insert(sqlId, prcsAnlyPwr);
				}

			}

			session.commit();




		return ins_chk;
	}


}
