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
import mone.common.util.Utils;
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
public class NewFcltDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(NewFcltDAO.class);

	/**
	 * constructor
	 */
	public NewFcltDAO() {
	}


	public static void execPrcsAnlyNewFclt(List<MariaResDTO.prcs_anly_new_fclt> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_NEW_FCLT  );

		systMngmAgtStt.setProcsCnt(insertNewFclt(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}

	public static int insertNewFclt(List<MariaResDTO.prcs_anly_new_fclt> tagParams) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_NEW_FCLT);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_new_fclt newFclt  = new  PostgresResDTO.prcs_anly_new_fclt();

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
					newFclt.setToc301AiToc(tagParams.get(i).getToc301AiToc());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.TOC301.AI_TOC", tagParams.get(i).getToc301AiToc());

					newFclt.setDo1002AiDo(tagParams.get(i).getDo1002AiDo());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.DO1002.AI_DO", tagParams.get(i).getDo1002AiDo());

					newFclt.setNh1002AiNh4(tagParams.get(i).getNh1002AiNh4());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.NH1002.AI_NH4", tagParams.get(i).getNh1002AiNh4());

					newFclt.setNo1002AiNo3(tagParams.get(i).getNo1002AiNo3());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.NO1002.AI_NO3", tagParams.get(i).getNo1002AiNo3());

					newFclt.setOrp402aAiOrp(tagParams.get(i).getOrp402aAiOrp());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.ORP402A.AI_ORP", tagParams.get(i).getOrp402aAiOrp());

					newFclt.setOrp402bAiOrp(tagParams.get(i).getOrp402bAiOrp());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.ORP402B.AI_ORP", tagParams.get(i).getOrp402bAiOrp());

					newFclt.setOrp402cAiOrp(tagParams.get(i).getOrp402cAiOrp());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.ORP402C.AI_ORP", tagParams.get(i).getOrp402cAiOrp());

					newFclt.setSs201AiSs(tagParams.get(i).getSs201AiSs());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.SS201.AI_SS", tagParams.get(i).getSs201AiSs());

					newFclt.setSs501AiSs(tagParams.get(i).getSs501AiSs());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.SS501.AI_SS", tagParams.get(i).getSs501AiSs());

					newFclt.setSs1002AiSs(tagParams.get(i).getSs1002AiSs());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.SS1002.AI_SS", tagParams.get(i).getSs1002AiSs());

					newFclt.setSlt502aAiSlt(tagParams.get(i).getSlt502aAiSlt());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.SLT502A.AI_SLT", tagParams.get(i).getSlt502aAiSlt());

					newFclt.setSlt502bAiSlt(tagParams.get(i).getSlt502bAiSlt());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.SLT502B.AI_SLT", tagParams.get(i).getSlt502bAiSlt());

					newFclt.setSlt502cAiSlt(tagParams.get(i).getSlt502cAiSlt());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.지능화.계측기.SLT502C.AI_SLT", tagParams.get(i).getSlt502cAiSlt());

					newFclt.setUpdateTime(updtTm);

					newFclt.setFlag(tagParams.get(i).getFlag());
					session.insert(sqlId, newFclt);
				}
			}

			session.commit();


		return ins_chk;
	}


}
