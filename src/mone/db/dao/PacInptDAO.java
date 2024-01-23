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
public class PacInptDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(PacInptDAO.class);

	/**
	 * constructor
	 */
	public PacInptDAO() {
	}


	public static void execPrcsAnlyPacInpt(List<MariaResDTO.prcs_anly_pac_inpt> tagResultList ) throws Exception {
		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_PAC_INPT);

		systMngmAgtStt.setProcsCnt(insertPrcsAnlyPacInpt(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}


	public static int insertPrcsAnlyPacInpt(List<MariaResDTO.prcs_anly_pac_inpt> tagParams) throws Exception {
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_PAC_INPT);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_pac_inpt prcsAnlyPacInpt  = new  PostgresResDTO.prcs_anly_pac_inpt();

			for(int i=0; i<tagParams.size(); i++) {

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

				if(tagParams.get(i).getLt101AiLev() > 0) {


				 	prcsAnlyPacInpt.setPfi1001AiFl(tagParams.get(i).getPfi1001AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.PFI1001.AI_FL", tagParams.get(i).getPfi1001AiFl());

				 	prcsAnlyPacInpt.setPfi1001AiPo4p(tagParams.get(i).getPfi1001AiPo4p());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.PFI1001.AI_PO4P", tagParams.get(i).getPfi1001AiPo4p());

				 	prcsAnlyPacInpt.setPfi1001AiPh(tagParams.get(i).getPfi1001AiPh());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.PFI1001.AI_PH", tagParams.get(i).getPfi1001AiPh());

				 	prcsAnlyPacInpt.setPfi1003AiFl(tagParams.get(i).getPfi1003AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.PFI1003.AI_FL", tagParams.get(i).getPfi1003AiFl());

				 	prcsAnlyPacInpt.setFi1002AiFl(tagParams.get(i).getFi1002AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI1002.AI_FL", tagParams.get(i).getFi1002AiFl());

				 	prcsAnlyPacInpt.setPo4p403aAiPo4p(tagParams.get(i).getPo4p403aAiPo4p());

				 	prcsAnlyPacInpt.setPo4p403bAiPo4p(tagParams.get(i).getPo4p403bAiPo4p());

				  	prcsAnlyPacInpt.setUpdateTime(updtTm);

				 	prcsAnlyPacInpt.setFlag(tagParams.get(i).getFlag());

					ins_chk = session.insert(sqlId, prcsAnlyPacInpt);
				}

			}

			session.commit();



		return ins_chk;
	}


}
