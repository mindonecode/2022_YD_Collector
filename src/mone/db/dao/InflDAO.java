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
public class InflDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(InflDAO.class);

	/**
	 * constructor
	 */
	public InflDAO() {
	}


	public static void execPrcsAnlyInfl(List<MariaResDTO.prcs_anly_infl> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_INFL  );

		systMngmAgtStt.setProcsCnt(insertInfl(tagResultList));
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}

	public static int insertInfl(List<MariaResDTO.prcs_anly_infl> tagParams) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_ANLY_INFL);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.prcs_anly_infl infl  = new  PostgresResDTO.prcs_anly_infl();

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
				 	infl.setFi102AiFl(tagParams.get(i).getFi102AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI102.AI_FL", tagParams.get(i).getFi102AiFl());

				 	infl.setFi804AiFl(tagParams.get(i).getFi804AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI804.AI_FL", tagParams.get(i).getFi804AiFl());

				 	infl.setPFi102AiFl(tagParams.get(i).getPFi102AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.PFI102.AI_FL", tagParams.get(i).getPFi102AiFl());

				 	infl.setPFi101AiFl(tagParams.get(i).getPFi101AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.PFI101.AI_FL", tagParams.get(i).getPFi101AiFl());

				 	infl.setFi201AiFl(tagParams.get(i).getFi201AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI201.AI_FL", tagParams.get(i).getFi201AiFl());

				 	infl.setLt201AiLev(tagParams.get(i).getLt201AiLev());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.LT201.AI_LEV", tagParams.get(i).getLt201AiLev());

				 	infl.setFte101AiFl(tagParams.get(i).getFte101AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.LT101.AI_LEV", tagParams.get(i).getFte101AiFl());

				 	infl.setFi101AiFl(tagParams.get(i).getFi101AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI101.AI_FL", tagParams.get(i).getFi101AiFl());

				 	infl.setFi301AiFl(tagParams.get(i).getFi301AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI301.AI_FL", tagParams.get(i).getFi301AiFl());

				 	infl.setLt101AiLev(tagParams.get(i).getLt101AiLev());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.LT101.AI_LEV", tagParams.get(i).getLt101AiLev());

				 	infl.setFtn101AiFl(tagParams.get(i).getFtn101AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI601.AI_FL", tagParams.get(i).getFtn101AiFl());

				 	infl.setFtn102AiFl(tagParams.get(i).getFtn102AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FTN102.AI_FL", tagParams.get(i).getFtn102AiFl());

				 	infl.setFi601AiFl(tagParams.get(i).getFi601AiFl());
				 	//PrcsDAO.chk_alram_condtion(updtTm, tagParams.get(i).getFlag(), "영동군.영동하수.FI601.AI_FL", tagParams.get(i).getFi601AiFl());

				 	infl.setFi701AiFl(tagParams.get(i).getFi701AiFl());

				 	infl.setUpdateTime(updtTm);

				 	infl.setFlag(tagParams.get(i).getFlag());
					session.insert(sqlId, infl);
				}
			}

			session.commit();

		return ins_chk;
	}


}
