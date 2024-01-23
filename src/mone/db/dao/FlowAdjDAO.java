package mone.db.dao;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
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
import mone.db.dto.PostgresResDTO.prcs_anly_flow_adj;
import mone.db.dto.SystMngmAgtSttDTO;

/**
 * db 데이터 취득 및 갱신
 * @author iby
 *
 */
public class FlowAdjDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(FlowAdjDAO.class);

	/**
	 * constructor
	 */
	public FlowAdjDAO() {
	}


	public static void execPrcsAnlyFlowAdj(List<MariaResDTO.prcs_anly_flow_adj> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_FLOW_ADJ);

		systMngmAgtStt.setProcsCnt(insertPrcsAnlyFlowAdj(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}

	public static int insertPrcsAnlyFlowAdj(List<MariaResDTO.prcs_anly_flow_adj> tagParams) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_FLOW_ADJ);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_flow_adj prcsAnlyFlowAdj  = new  PostgresResDTO.prcs_anly_flow_adj();

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

					prcsAnlyFlowAdj.setLte101aAiLev(tagParams.get(i).getLte101aAiLev());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.LTE101A.I_LEV", tagParams.get(i).getLte101aAiLev());

					prcsAnlyFlowAdj.setLte101bAiLev(tagParams.get(i).getLte101bAiLev());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.LTE101B.AI_LEV", tagParams.get(i).getLte101bAiLev());

					prcsAnlyFlowAdj.setFi201AiFl(tagParams.get(i).getFi201AiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI201.AI_FL", tagParams.get(i).getFi201AiFl());

					prcsAnlyFlowAdj.setFq201AiFlsm(tagParams.get(i).getFq201AiFlsm());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FQ201.AI_FLSM", tagParams.get(i).getFq201AiFlsm());

					prcsAnlyFlowAdj.setLt1001AiLev(tagParams.get(i).getLt1001AiLev());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.LT1001.AILEV", tagParams.get(i).getLt1001AiLev());

					prcsAnlyFlowAdj.setUpdateTime(updtTm);
					prcsAnlyFlowAdj.setFlag(tagParams.get(i).getFlag());

					//logger.debug( i +  "   <<<<<<<<<<<<<<>>>>>>>>>>>>>>   " + prcsAnlyFlowAdj.getUpdateTime()  + "     "   + prcsAnlyFlowAdj.getLte101aAiLev());


					session.insert(sqlId, prcsAnlyFlowAdj);
				}

			}

			session.commit();



		return ins_chk;
	}


}
