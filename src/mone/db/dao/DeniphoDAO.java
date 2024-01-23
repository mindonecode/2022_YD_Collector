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
public class DeniphoDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(DeniphoDAO.class);

	/**
	 * constructor
	 */
	public DeniphoDAO() {
	}


	public static void execPrcsAnlyDenipho(List<MariaResDTO.prcs_anly_denipho> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_PRCS_ANLY_DENIPHO  );

		systMngmAgtStt.setProcsCnt(insertDenipho(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}

	public static int insertDenipho(List<MariaResDTO.prcs_anly_denipho> tagParams) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_PRCS_ANLY_DENIPHO);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_new_denipho denipho  = new  PostgresResDTO.prcs_anly_new_denipho();

			//logger.debug("::::::::::::::::::: " + tagParams );

			for(int i=0; i<tagParams.size(); i++) {

				if(tagParams.get(i).getLt101AiLev() > 0) {
					String strDate = tagParams.get(i).getRecordDate();
					SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date formatDate = dtFormat.parse(strDate);
					String strNewDtFormat = newDtFormat.format(formatDate);

					String estVal = "";
					if("cur".equals(tagParams.get(i).getFlag()) ) {
						estVal = tagParams.get(i).getRecordMinute() + ":00";
					}else {
						estVal = "59:58";
					}
					String updtTm = strNewDtFormat + " " + tagParams.get(i).getRecordTime() + ":" + estVal ;

						denipho.setNh4Dnsty(tagParams.get(i).getNh4Dnsty());
						denipho.setNh4Tmp(tagParams.get(i).getNh4Tmp());
						denipho.setNo3Dnsty(tagParams.get(i).getNo3Dnsty());
						denipho.setNo3Tmp(tagParams.get(i).getNo3Tmp());

						denipho.setUpdateTime(updtTm);

						denipho.setFlag(tagParams.get(i).getFlag());
						session.insert(sqlId, denipho);
				}
			}

			session.commit();


		return ins_chk;
	}


}
