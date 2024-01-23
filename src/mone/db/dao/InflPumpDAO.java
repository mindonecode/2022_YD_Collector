package mone.db.dao;

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
public class InflPumpDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(InflPumpDAO.class);

	/**
	 * constructor
	 */
	public InflPumpDAO() {
	}


	public static void execPrcsAnlyInflPump(List<MariaResDTO.prcs_anly_infl_pump> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_INFL_PUMP);

		systMngmAgtStt.setProcsCnt(insertPrcsAnlyInflPump(tagResultList ));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);

	}

	public static int insertPrcsAnlyInflPump(List<MariaResDTO.prcs_anly_infl_pump> tagParams) throws Exception {
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_INFL_PUMP);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_infl_pump prcsAnlyInflPump  = new  PostgresResDTO.prcs_anly_infl_pump();

			for(int i=0; i<tagParams.size(); i++) {

				String strDate = tagParams.get(i).getRecordDate();
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date formatDate = dtFormat.parse(strDate);
				String strNewDtFormat = newDtFormat.format(formatDate);

				if(tagParams.get(i).getLt101AiLev() > 0) {
					prcsAnlyInflPump.setM117aAiW(tagParams.get(i).getM117aAiW() );
					prcsAnlyInflPump.setM117aAiWh(tagParams.get(i).getM117aAiWh());
					prcsAnlyInflPump.setM117bAiW(tagParams.get(i).getM117bAiW() );
					prcsAnlyInflPump.setM117bAiWh(tagParams.get(i).getM117bAiWh());
					prcsAnlyInflPump.setM117cAiW(tagParams.get(i).getM117cAiW() );
					prcsAnlyInflPump.setM117cAiWh(tagParams.get(i).getM117cAiWh());
					prcsAnlyInflPump.setM118AiW(tagParams.get(i).getM118AiW());
					prcsAnlyInflPump.setM118AiWh(tagParams.get(i).getM118AiWh());
					String estVal = "";
					if("cur".equals(tagParams.get(i).getFlag()) ) {
						estVal = tagParams.get(i).getRecordMinute() + ":00";
					}else {
						estVal = "59:58";
					}
					prcsAnlyInflPump.setUpdateTime(strNewDtFormat + " " + tagParams.get(i).getRecordTime() + ":" + estVal );
					prcsAnlyInflPump.setFlag(tagParams.get(i).getFlag());
					session.insert(sqlId, prcsAnlyInflPump);
				}
			}

			session.commit();


		return ins_chk;
	}


}
