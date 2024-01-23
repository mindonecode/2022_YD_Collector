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
public class AbnrDetDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(AbnrDetDAO.class);

	/**
	 * constructor
	 */
	public AbnrDetDAO() {
	}


	public void execPrcsAnlyAbnrDet(List<MariaResDTO.prcs_anly_abnr_det> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_ABNR_DET);

		systMngmAgtStt.setProcsCnt(insertPrcsAnlyAbnrDet(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);

	}

	public int insertPrcsAnlyAbnrDet(List<MariaResDTO.prcs_anly_abnr_det> tagParams) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_ABNR_DET);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_abnr_det prcsAnlyAbnrDet  = new  PostgresResDTO.prcs_anly_abnr_det();

			for(int i=0; i<tagParams.size(); i++) {

				String strDate = tagParams.get(i).getRecordDate();
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date formatDate = dtFormat.parse(strDate);
				String strNewDtFormat = newDtFormat.format(formatDate);

				prcsAnlyAbnrDet.setVThres1(tagParams.get(i).getVThres1());

				String estVal = "";
				if("cur".equals(tagParams.get(i).getFlag()) ) {
					estVal = tagParams.get(i).getRecordMinute() + ":00";
				}else {
					estVal = "59:58";
				}
				prcsAnlyAbnrDet.setUpdateTime(strNewDtFormat + " " + tagParams.get(i).getRecordTime() + ":" + estVal );
				prcsAnlyAbnrDet.setFlag(tagParams.get(i).getFlag());

				session.insert(sqlId, prcsAnlyAbnrDet);

			}

			session.commit();

		return ins_chk;
	}


}
