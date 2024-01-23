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
public class StngDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(StngDAO.class);

	/**
	 * constructor
	 */
	public StngDAO() {
	}


	public static void execPrcsAnlyStngInfl(List<MariaResDTO.prcs_anly_stng_tag_clct> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_STNG_INFL  );

		systMngmAgtStt.setProcsCnt(insertStngInfl(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}

	public static int insertStngInfl(List<MariaResDTO.prcs_anly_stng_tag_clct> tagParams) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_STNG_INFL);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_stng_tag_clct infl  = new  PostgresResDTO.prcs_anly_stng_tag_clct();

			for(int i=0; i<tagParams.size(); i++) {

				String strDate = tagParams.get(i).getRecordDate();
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date formatDate = dtFormat.parse(strDate);
				String strNewDtFormat = newDtFormat.format(formatDate);

				if(tagParams.get(i).getLt101AiLev() > 0) {

				 	infl.setLt101AiLev(tagParams.get(i).getLt101AiLev());
				 	infl.setLt101AoLevh0s(tagParams.get(i).getLt101AoLevh0s());

				 	infl.setLt101AoLevh1s(tagParams.get(i).getLt101AoLevh1s());
				 	infl.setLt101AoLevh2s(tagParams.get(i).getLt101AoLevh2s());
				 	infl.setLt101AoLevh3s(tagParams.get(i).getLt101AoLevh3s());
				 	infl.setLt101AoLevh4s(tagParams.get(i).getLt101AoLevh4s());
				 	infl.setLt101AoLevh5s(tagParams.get(i).getLt101AoLevh5s());
				 	infl.setLt101AoLevh6s(tagParams.get(i).getLt101AoLevh6s());
				 	infl.setLt101AoLevh7s(tagParams.get(i).getLt101AoLevh7s());
				 	infl.setLt101AoLevh8s(tagParams.get(i).getLt101AoLevh8s());
				 	infl.setLt101AoLevh9s(tagParams.get(i).getLt101AoLevh9s());
				 	infl.setLt101AoLevh10s(tagParams.get(i).getLt101AoLevh10s());
				 	infl.setLt101AoLevh11s(tagParams.get(i).getLt101AoLevh11s());
				 	infl.setLt101AoLevh12s(tagParams.get(i).getLt101AoLevh12s());
				 	infl.setLt101AoLevh13s(tagParams.get(i).getLt101AoLevh13s());
				 	infl.setLt101AoLevh14s(tagParams.get(i).getLt101AoLevh14s());
				 	infl.setLt101AoLevh15s(tagParams.get(i).getLt101AoLevh15s());
				 	infl.setLt101AoLevh16s(tagParams.get(i).getLt101AoLevh16s());
				 	infl.setLt101AoLevh17s(tagParams.get(i).getLt101AoLevh17s());
				 	infl.setM117AoFqh1s(tagParams.get(i).getM117AoFqh1s());
				 	infl.setM117AoFqh2s(tagParams.get(i).getM117AoFqh2s());
				 	infl.setM117AoFqh3s(tagParams.get(i).getM117AoFqh3s());
				 	infl.setM117AoFqh4s(tagParams.get(i).getM117AoFqh4s());
				 	infl.setM117AoFqh5s(tagParams.get(i).getM117AoFqh5s());
				 	infl.setM117AoFqh6s(tagParams.get(i).getM117AoFqh6s());
				 	infl.setM117AoFqh7s(tagParams.get(i).getM117AoFqh7s());
				 	infl.setM117AoFqh8s(tagParams.get(i).getM117AoFqh8s());
				 	infl.setM117AoFqh9s(tagParams.get(i).getM117AoFqh9s());
				 	infl.setM117AoFqh10s(tagParams.get(i).getM117AoFqh10s());
				 	infl.setM117AoFqh11s(tagParams.get(i).getM117AoFqh11s());
				 	infl.setM117AoFqh12s(tagParams.get(i).getM117AoFqh12s());
				 	infl.setM117AoFqh13s(tagParams.get(i).getM117AoFqh13s());
				 	infl.setM117AoFqh14s(tagParams.get(i).getM117AoFqh14s());
				 	infl.setM117AoFqh15s(tagParams.get(i).getM117AoFqh15s());
				 	infl.setM117AoFqh16s(tagParams.get(i).getM117AoFqh16s());
				 	infl.setM117AoFqh17s(tagParams.get(i).getM117AoFqh17s());
				 	infl.setM207aAiZt(tagParams.get(i).getM207aAiZt());
				 	infl.setM207aAoSv1s(tagParams.get(i).getM207aAoSv1s());
				 	infl.setM207aAoSv2s(tagParams.get(i).getM207aAoSv2s());
				 	infl.setM207aAoSv3s(tagParams.get(i).getM207aAoSv3s());
				 	infl.setM207aAoSv4s(tagParams.get(i).getM207aAoSv4s());
				 	infl.setM207aAoSv5s(tagParams.get(i).getM207aAoSv5s());
				 	infl.setM207aAoSv6s(tagParams.get(i).getM207aAoSv6s());
				 	infl.setM207aAoSv7s(tagParams.get(i).getM207aAoSv7s());
				 	infl.setM207aAoSv8s(tagParams.get(i).getM207aAoSv8s());
				 	infl.setM207bAiZt(tagParams.get(i).getM207bAiZt());
				 	infl.setM207bAoSv1s(tagParams.get(i).getM207bAoSv1s());
				 	infl.setM207bAoSv2s(tagParams.get(i).getM207bAoSv2s());
				 	infl.setM207bAoSv3s(tagParams.get(i).getM207bAoSv3s());
				 	infl.setM207bAoSv4s(tagParams.get(i).getM207bAoSv4s());
				 	infl.setM207bAoSv5s(tagParams.get(i).getM207bAoSv5s());
				 	infl.setM207bAoSv6s(tagParams.get(i).getM207bAoSv6s());
				 	infl.setM207bAoSv7s(tagParams.get(i).getM207bAoSv7s());
				 	infl.setM207bAoSv8s(tagParams.get(i).getM207bAoSv8s());

				 	infl.setM117AoRus01(tagParams.get(i).getM117AoRus01());
				 	infl.setM117AoRus02(tagParams.get(i).getM117AoRus02());
				 	infl.setM117AoRus03(tagParams.get(i).getM117AoRus03());
				 	infl.setM117AoRus04(tagParams.get(i).getM117AoRus04());

				 	infl.setM405aAoHzs01(tagParams.get(i).getM405aAoHzs01());
				 	infl.setM405aAoHzs02(tagParams.get(i).getM405aAoHzs02());
				 	infl.setM405bAoHzs01(tagParams.get(i).getM405bAoHzs01());
				 	infl.setM405bAoHzs02(tagParams.get(i).getM405bAoHzs02());

				 	infl.setAoPcsc05(tagParams.get(i).getAoPcsc05());

				 	infl.setM405aDiRu(tagParams.get(i).getM405aDiRu());
				 	infl.setM405bDiRu(tagParams.get(i).getM405bDiRu());
				 	infl.setM405cDiRu(tagParams.get(i).getM405cDiRu());
				 	infl.setAoPcsc03(tagParams.get(i).getAoPcsc03());
				 	infl.setAoPcs03(tagParams.get(i).getAoPcs03());


				 	String estVal = "";
					if("cur".equals(tagParams.get(i).getFlag()) ) {
						estVal = tagParams.get(i).getRecordMinute() + ":00";
					}else {
						estVal = "59:58";
					}
					infl.setUpdateTime(strNewDtFormat + " " + tagParams.get(i).getRecordTime() + ":" + estVal );
					infl.setFlag(tagParams.get(i).getFlag());

					session.insert(sqlId, infl);
				}
			}


		return ins_chk;
	}


}
