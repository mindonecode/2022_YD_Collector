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
public class StngTankDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(StngTankDAO.class);

	/**
	 * constructor
	 */
	public StngTankDAO() {
	}


	public static void execPrcsAnlyStngTank(List<MariaResDTO.prcs_anly_stng_tank> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_STNG_TANK);

		systMngmAgtStt.setProcsCnt(insertPrcsAnlyStngTank(tagResultList ));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);

	}

	public static int insertPrcsAnlyStngTank(List<MariaResDTO.prcs_anly_stng_tank> tagParams) throws Exception {
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_STNG_TANK);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_stng_tank prcsAnlyStngTank  = new  PostgresResDTO.prcs_anly_stng_tank();

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


					prcsAnlyStngTank.setLt501AiLev(tagParams.get(i).getLt501AiLev());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.LT501.AI_LEV", tagParams.get(i).getLt501AiLev());

					prcsAnlyStngTank.setFte203aAiFl(tagParams.get(i).getFte203aAiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FTE203A.AI_FL", tagParams.get(i).getFte203aAiFl());

					prcsAnlyStngTank.setFte203aAiFlsm(tagParams.get(i).getFte203aAiFlsm());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FTE203A.AI_FLSM", tagParams.get(i).getFte203aAiFlsm());

					prcsAnlyStngTank.setDt501AiPh(tagParams.get(i).getDt501AiPh());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.DT501.AI_PH", tagParams.get(i).getDt501AiPh());

					prcsAnlyStngTank.setFi805aAiFl(tagParams.get(i).getFi805aAiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI805A.AI_FL", tagParams.get(i).getFi805aAiFl());

					prcsAnlyStngTank.setFq1003AiFlsm(tagParams.get(i).getFq1003AiFlsm());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FQ1003.AI_FLSM", tagParams.get(i).getFq1003AiFlsm());

					prcsAnlyStngTank.setFi1003AiFl(tagParams.get(i).getFi1003AiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI1003.AI_FL", tagParams.get(i).getFi1003AiFl());

					prcsAnlyStngTank.setFq1003AiFlsm03(tagParams.get(i).getFq1003AiFlsm03());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FQ1003.AI_FLSM03", tagParams.get(i).getFq1003AiFlsm03());

					prcsAnlyStngTank.setPei1003AiFl(tagParams.get(i).getPei1003AiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.PEI1003.AI_FL", tagParams.get(i).getPei1003AiFl());

					prcsAnlyStngTank.setFi501AlFl(tagParams.get(i).getFi501AlFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI501.AL_FL", tagParams.get(i).getFi501AlFl());

					prcsAnlyStngTank.setFi502AlFl(tagParams.get(i).getFi502AlFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI502.AL_FL", tagParams.get(i).getFi502AlFl());

					prcsAnlyStngTank.setFi501AiFl(tagParams.get(i).getFi501AiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI501.AI_FL", tagParams.get(i).getFi501AiFl());

					prcsAnlyStngTank.setFi502AiFl(tagParams.get(i).getFi502AiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI502.AI_FL", tagParams.get(i).getFi502AiFl());

					prcsAnlyStngTank.setFi1002AiFl(tagParams.get(i).getFi1002AiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI1002.AI_FL", tagParams.get(i).getFi1002AiFl());

					prcsAnlyStngTank.setFi1004AiFl(tagParams.get(i).getFi1004AiFl());
					//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI1004.AI_FL", tagParams.get(i).getFi1004AiFl());

					prcsAnlyStngTank.setSlt1001AiPh(tagParams.get(i).getSlt1001AiPh());

					prcsAnlyStngTank.setUpdateTime(updtTm);
					prcsAnlyStngTank.setFlag(tagParams.get(i).getFlag());
					session.insert(sqlId, prcsAnlyStngTank);
				}
			}

			session.commit();


		return ins_chk;
	}


}
