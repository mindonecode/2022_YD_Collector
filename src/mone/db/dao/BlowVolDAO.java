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
public class BlowVolDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(BlowVolDAO.class);

	/**
	 * constructor
	 */
	public BlowVolDAO() {
	}


	public static void execPrcsAnlyBlowVol(List<MariaResDTO.prcs_anly_blow_vol> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_BLOW_VOL);

		systMngmAgtStt.setProcsCnt(insertPrcsAnlyBlowVol(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}

	public static int insertPrcsAnlyBlowVol(List<MariaResDTO.prcs_anly_blow_vol> tagParams) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_BLOW_VOL);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_blow_vol prcsAnlyBlowVol  = new  PostgresResDTO.prcs_anly_blow_vol();




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

					//logger.debug("::::::::::::::: => " +  updtTm );

					if(tagParams.get(i).getLt101AiLev() > 0) {
					 	prcsAnlyBlowVol.setFi1001AiFl(tagParams.get(i).getFi1001AiFl());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI1001.AI_FL", tagParams.get(i).getFi1001AiFl());

					 	prcsAnlyBlowVol.setFi402AiFl(tagParams.get(i).getFi402AiFl());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI402.AI_FL", tagParams.get(i).getFi402AiFl());

					 	prcsAnlyBlowVol.setFi401aAiFl(tagParams.get(i).getFi401aAiFl());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI401A.AI_FL", tagParams.get(i).getFi401aAiFl());

					 	prcsAnlyBlowVol.setFi401bAiFl(tagParams.get(i).getFi401bAiFl());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI401B.AI_FL", tagParams.get(i).getFi401bAiFl());

					 	prcsAnlyBlowVol.setFi401cAiFl(tagParams.get(i).getFi401cAiFl());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI401C.AI_FL", tagParams.get(i).getFi401cAiFl());

					 	prcsAnlyBlowVol.setFi401dAiFl(tagParams.get(i).getFi401dAiFl());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI401D.AI_FL", tagParams.get(i).getFi401dAiFl());

					 	prcsAnlyBlowVol.setDo401aAiDo(tagParams.get(i).getDo401aAiDo());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.DO401A.AI_DO", tagParams.get(i).getDo401aAiDo());

					 	prcsAnlyBlowVol.setDo401bAiDo(tagParams.get(i).getDo401bAiDo());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.DO401B.AI_DO", tagParams.get(i).getDo401bAiDo());

					 	prcsAnlyBlowVol.setDo401cAiDo(tagParams.get(i).getDo401cAiDo());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.DO401C.AI_DO", tagParams.get(i).getDo401cAiDo());

					 	prcsAnlyBlowVol.setDo401dAiDo(tagParams.get(i).getDo401dAiDo());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.DO401D.AI_DO", tagParams.get(i).getDo401dAiDo());

					 	prcsAnlyBlowVol.setOrp402aAiOrp(tagParams.get(i).getOrp402aAiOrp());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.ORP402A.AI_ORP", tagParams.get(i).getOrp402aAiOrp());

					 	prcsAnlyBlowVol.setOrp402bAiOrp(tagParams.get(i).getOrp402bAiOrp());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.ORP402B.AI_ORP", tagParams.get(i).getOrp402bAiOrp());

					 	prcsAnlyBlowVol.setOrp402cAiOrp(tagParams.get(i).getOrp402cAiOrp());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.ORP402C.AI_ORP", tagParams.get(i).getOrp402cAiOrp());

					 	prcsAnlyBlowVol.setMlss403aAiMlss(tagParams.get(i).getMlss403aAiMlss());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.MLSS403A.AI_MLSS", tagParams.get(i).getMlss403aAiMlss());

					 	prcsAnlyBlowVol.setMlss403bAiMlss(tagParams.get(i).getMlss403bAiMlss());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.MLSS403B.AI_MLSS", tagParams.get(i).getMlss403bAiMlss());

					 	prcsAnlyBlowVol.setDo1001AiDo(tagParams.get(i).getDo1001AiDo());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.DO1001.AI_DO", tagParams.get(i).getDo1001AiDo());

					 	prcsAnlyBlowVol.setOrp1002AiOrp(tagParams.get(i).getOrp1002AiOrp());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.ORP1002.AI_ORP", tagParams.get(i).getOrp1002AiOrp());

					 	prcsAnlyBlowVol.setMlss1003AiMlss(tagParams.get(i).getMlss1003AiMlss());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.MLSS1003.AI_MLSS", tagParams.get(i).getMlss1003AiMlss());

					 	prcsAnlyBlowVol.setSlt1001AiPh(tagParams.get(i).getSlt1001AiPh());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.SLT1001.AI_PH", tagParams.get(i).getSlt1001AiPh());

					 	prcsAnlyBlowVol.setDt404AiPh(tagParams.get(i).getDt404AiPh());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.DT404.AI_PH", tagParams.get(i).getDt404AiPh());

					 	prcsAnlyBlowVol.setPlt101AiLev(tagParams.get(i).getPlt101AiLev());
					 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.PLT101.AI_LEV", tagParams.get(i).getPlt101AiLev());

					 	prcsAnlyBlowVol.setM405aAoHzs02(tagParams.get(i).getM405aAoHzs02());
					 	prcsAnlyBlowVol.setM405bAoHzs02(tagParams.get(i).getM405aAoHzs02());

					 	prcsAnlyBlowVol.setUpdateTime(updtTm);

					 	prcsAnlyBlowVol.setFlag(tagParams.get(i).getFlag());

					 	session.insert(sqlId, prcsAnlyBlowVol);
					}

			}

			session.commit();


		return ins_chk;
	}


}
