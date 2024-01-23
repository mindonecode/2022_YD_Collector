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
public class VbrtDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(VbrtDAO.class);

	/**
	 * constructor
	 */
	public VbrtDAO() {
	}


	public static void execPrcsAnlyVbrt(List<MariaResDTO.prcs_anly_vbrt> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_VBRT  );

		systMngmAgtStt.setProcsCnt(insertVbrt(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}

	public static int insertVbrt(List<MariaResDTO.prcs_anly_vbrt> tagParams) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_VBRT);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_vbrt vbrt  = new  PostgresResDTO.prcs_anly_vbrt();

			for(int i=0; i<tagParams.size(); i++) {

				String strDate = tagParams.get(i).getRecordDate();
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date formatDate = dtFormat.parse(strDate);
				String strNewDtFormat = newDtFormat.format(formatDate);

				if(tagParams.get(i).getLt101AiLev() > 0) {
				 	vbrt.setM1002aAiKx(tagParams.get(i).getM1002aAiKx());
				 	vbrt.setM1002bAiKx(tagParams.get(i).getM1002bAiKx());
				 	vbrt.setM1007aAiKx(tagParams.get(i).getM1007aAiKx());
				 	vbrt.setM1007bAiKx(tagParams.get(i).getM1007bAiKx());
				 	vbrt.setM1201aAiKx(tagParams.get(i).getM1201aAiKx());
				 	vbrt.setM1201bAiKx(tagParams.get(i).getM1201bAiKx());
				 	vbrt.setM1207aAiKx(tagParams.get(i).getM1207aAiKx());
				 	vbrt.setM1207bAiKx(tagParams.get(i).getM1207bAiKx());
				 	vbrt.setM405aAiKx(tagParams.get(i).getM405aAiKx());
				 	vbrt.setM405bAiKx(tagParams.get(i).getM405bAiKx());
				 	vbrt.setM505aAiKx(tagParams.get(i).getM505aAiKx());
				 	vbrt.setM505bAiKx(tagParams.get(i).getM505bAiKx());
				 	vbrt.setM805aAiKx(tagParams.get(i).getM805aAiKx());
				 	vbrt.setM805bAiKx(tagParams.get(i).getM805bAiKx());
				 	vbrt.setM820aAiKx(tagParams.get(i).getM820aAiKx());
				 	vbrt.setM820bAiKx(tagParams.get(i).getM820bAiKx());

				 	vbrt.setM1002aAiPvx(tagParams.get(i).getM1002aAiPvx());
					vbrt.setM1002aAiPvy(tagParams.get(i).getM1002aAiPvy());
					vbrt.setM1002aAiPvz(tagParams.get(i).getM1002aAiPvz());
					vbrt.setM1002aAiRax(tagParams.get(i).getM1002aAiRax());
					vbrt.setM1002aAiRay(tagParams.get(i).getM1002aAiRay());
					vbrt.setM1002aAiRaz(tagParams.get(i).getM1002aAiRaz());
					vbrt.setM1002bAiPvx(tagParams.get(i).getM1002bAiPvx());
					vbrt.setM1002bAiPvy(tagParams.get(i).getM1002bAiPvy());
					vbrt.setM1002bAiPvz(tagParams.get(i).getM1002bAiPvz());
					vbrt.setM1002bAiRax(tagParams.get(i).getM1002bAiRax());
					vbrt.setM1002bAiRay(tagParams.get(i).getM1002bAiRay());
					vbrt.setM1002bAiRaz(tagParams.get(i).getM1002bAiRaz());
					vbrt.setM1007aAiPvx(tagParams.get(i).getM1007aAiPvx());
					vbrt.setM1007aAiPvy(tagParams.get(i).getM1007aAiPvy());
					vbrt.setM1007aAiPvz(tagParams.get(i).getM1007aAiPvz());
					vbrt.setM1007aAiRax(tagParams.get(i).getM1007aAiRax());
					vbrt.setM1007aAiRay(tagParams.get(i).getM1007aAiRay());
					vbrt.setM1007aAiRaz(tagParams.get(i).getM1007aAiRaz());
					vbrt.setM1007bAiPvx(tagParams.get(i).getM1007bAiPvx());
					vbrt.setM1007bAiPvy(tagParams.get(i).getM1007bAiPvy());
					vbrt.setM1007bAiPvz(tagParams.get(i).getM1007bAiPvz());
					vbrt.setM1007bAiRax(tagParams.get(i).getM1007bAiRax());
					vbrt.setM1007bAiRay(tagParams.get(i).getM1007bAiRay());
					vbrt.setM1007bAiRaz(tagParams.get(i).getM1007bAiRaz());
					vbrt.setM1201aAiPvx(tagParams.get(i).getM1201aAiPvx());
					vbrt.setM1201aAiPvy(tagParams.get(i).getM1201aAiPvy());
					vbrt.setM1201aAiPvz(tagParams.get(i).getM1201aAiPvz());
					vbrt.setM1201aAiRax(tagParams.get(i).getM1201aAiRax());
					vbrt.setM1201aAiRay(tagParams.get(i).getM1201aAiRay());
					vbrt.setM1201aAiRaz(tagParams.get(i).getM1201aAiRaz());
					vbrt.setM1201bAiPvx(tagParams.get(i).getM1201bAiPvx());
					vbrt.setM1201bAiPvy(tagParams.get(i).getM1201bAiPvy());
					vbrt.setM1201bAiPvz(tagParams.get(i).getM1201bAiPvz());
					vbrt.setM1201bAiRax(tagParams.get(i).getM1201bAiRax());
					vbrt.setM1201bAiRay(tagParams.get(i).getM1201bAiRay());
					vbrt.setM1201bAiRaz(tagParams.get(i).getM1201bAiRaz());
					vbrt.setM1207aAiPvx(tagParams.get(i).getM1207aAiPvx());
					vbrt.setM1207aAiPvy(tagParams.get(i).getM1207aAiPvy());
					vbrt.setM1207aAiPvz(tagParams.get(i).getM1207aAiPvz());
					vbrt.setM1207aAiRax(tagParams.get(i).getM1207aAiRax());
					vbrt.setM1207aAiRay(tagParams.get(i).getM1207aAiRay());
					vbrt.setM1207aAiRaz(tagParams.get(i).getM1207aAiRaz());
					vbrt.setM1207bAiPvx(tagParams.get(i).getM1207bAiPvx());
					vbrt.setM1207bAiPvy(tagParams.get(i).getM1207bAiPvy());
					vbrt.setM1207bAiPvz(tagParams.get(i).getM1207bAiPvz());
					vbrt.setM1207bAiRax(tagParams.get(i).getM1207bAiRax());
					vbrt.setM1207bAiRay(tagParams.get(i).getM1207bAiRay());
					vbrt.setM1207bAiRaz(tagParams.get(i).getM1207bAiRaz());
					 vbrt.setM405aAiPvx(tagParams.get(i).getM405aAiPvx());
					 vbrt.setM405aAiPvy(tagParams.get(i).getM405aAiPvy());
					 vbrt.setM405aAiPvz(tagParams.get(i).getM405aAiPvz());
					 vbrt.setM405aAiRax(tagParams.get(i).getM405aAiRax());
					 vbrt.setM405aAiRay(tagParams.get(i).getM405aAiRay());
					 vbrt.setM405aAiRaz(tagParams.get(i).getM405aAiRaz());
					 vbrt.setM405bAiPvx(tagParams.get(i).getM405bAiPvx());
					 vbrt.setM405bAiPvy(tagParams.get(i).getM405bAiPvy());
					 vbrt.setM405bAiPvz(tagParams.get(i).getM405bAiPvz());
					 vbrt.setM405bAiRax(tagParams.get(i).getM405bAiRax());
					 vbrt.setM405bAiRay(tagParams.get(i).getM405bAiRay());
					 vbrt.setM405bAiRaz(tagParams.get(i).getM405bAiRaz());
					 vbrt.setM505aAiPvx(tagParams.get(i).getM505aAiPvx());
					 vbrt.setM505aAiPvy(tagParams.get(i).getM505aAiPvy());
					 vbrt.setM505aAiPvz(tagParams.get(i).getM505aAiPvz());
					 vbrt.setM505aAiRax(tagParams.get(i).getM505aAiRax());
					 vbrt.setM505aAiRay(tagParams.get(i).getM505aAiRay());
					 vbrt.setM505aAiRaz(tagParams.get(i).getM505aAiRaz());
					 vbrt.setM505bAiPvx(tagParams.get(i).getM505bAiPvx());
					 vbrt.setM505bAiPvy(tagParams.get(i).getM505bAiPvy());
					 vbrt.setM505bAiPvz(tagParams.get(i).getM505bAiPvz());
					 vbrt.setM505bAiRax(tagParams.get(i).getM505bAiRax());
					 vbrt.setM505bAiRay(tagParams.get(i).getM505bAiRay());
					 vbrt.setM505bAiRaz(tagParams.get(i).getM505bAiRaz());
					 vbrt.setM805aAiPvx(tagParams.get(i).getM805aAiPvx());
					 vbrt.setM805aAiPvy(tagParams.get(i).getM805aAiPvy());
					 vbrt.setM805aAiPvz(tagParams.get(i).getM805aAiPvz());
					 vbrt.setM805aAiRax(tagParams.get(i).getM805aAiRax());
					 vbrt.setM805aAiRay(tagParams.get(i).getM805aAiRay());
					 vbrt.setM805aAiRaz(tagParams.get(i).getM805aAiRaz());
					 vbrt.setM805bAiPvx(tagParams.get(i).getM805bAiPvx());
					 vbrt.setM805bAiPvy(tagParams.get(i).getM805bAiPvy());
					 vbrt.setM805bAiPvz(tagParams.get(i).getM805bAiPvz());
					 vbrt.setM805bAiRax(tagParams.get(i).getM805bAiRax());
					 vbrt.setM805bAiRay(tagParams.get(i).getM805bAiRay());
					 vbrt.setM805bAiRaz(tagParams.get(i).getM805bAiRaz());
					 vbrt.setM820aAiPvx(tagParams.get(i).getM820aAiPvx());
					 vbrt.setM820aAiPvy(tagParams.get(i).getM820aAiPvy());
					 vbrt.setM820aAiPvz(tagParams.get(i).getM820aAiPvz());
					 vbrt.setM820aAiRax(tagParams.get(i).getM820aAiRax());
					 vbrt.setM820aAiRay(tagParams.get(i).getM820aAiRay());
					 vbrt.setM820aAiRaz(tagParams.get(i).getM820aAiRaz());
					 vbrt.setM820bAiPvx(tagParams.get(i).getM820bAiPvx());
					 vbrt.setM820bAiPvy(tagParams.get(i).getM820bAiPvy());
					 vbrt.setM820bAiPvz(tagParams.get(i).getM820bAiPvz());
					 vbrt.setM820bAiRax(tagParams.get(i).getM820bAiRax());
					 vbrt.setM820bAiRay(tagParams.get(i).getM820bAiRay());
					 vbrt.setM820bAiRaz(tagParams.get(i).getM820bAiRaz());

				 	String estVal = "";
					if("cur".equals(tagParams.get(i).getFlag()) ) {
						estVal = tagParams.get(i).getRecordMinute() + ":00";
					}else {
						estVal = "59:58";
					}
				 	vbrt.setUpdateTime(strNewDtFormat + " " + tagParams.get(i).getRecordTime() + ":" + estVal );
				 	vbrt.setFlag(tagParams.get(i).getFlag());
					session.insert(sqlId, vbrt);
				}
			}

			session.commit();




		return ins_chk;
	}


}
