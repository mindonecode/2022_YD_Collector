package mone.db.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConstDef;
import mone.db.conn.DbManager;
import mone.db.dto.PostgresResDTO;
import mone.db.dto.SystMngmAgtSttDTO;

/**
 * db 데이터 취득 및 갱신
 * @author iby
 *
 */
public class NewAlrmlDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(NewAlrmlDAO.class);

	/**
	 * constructor
	 */
	public NewAlrmlDAO() {
	}


	public static void execPrcsAnlyNewAlrm(String tagTbl, String tagId, String alrmDscr, Float tagVal) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_INSERT_PRCS_MNTR_TAG_ALRM  );

		//systMngmAgtStt.setProcsCnt(insertNewAlrm(tagTbl, tagId, alrmDscr, tagVal));

		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);
	}

	public static int insertNewAlrm(String updateTime, String tagTbl, String tagId, String alrmDscr, Float tagVal ) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_PRCS_MNTR_TAG_ALRM);

		int ins_chk  = 0 ;


			session = DbManager.getSqlSessionPostgres();

				PostgresResDTO.prcs_mntr_tag_alrm newAlrm  = new PostgresResDTO.prcs_mntr_tag_alrm();

				newAlrm.setTagTbl(tagTbl);
				newAlrm.setTagId(tagId);
				newAlrm.setAlrmDscr(alrmDscr);
				newAlrm.setTagVal(tagVal);
				newAlrm.setUpdateTime(updateTime);

				ins_chk = session.insert(sqlId, newAlrm);

			session.commit();




		return ins_chk;
	}

	public static int insertNewAlrmCr(String updateTime, String tagTbl, String tagId, String alrmDscr, Float tagVal ) throws Exception{
		SqlSession session = null;

		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_SYST_MNGM_ALRM_HIST);
		int ins_chk  = 0 ;


			session = DbManager.getSqlSessionPostgres();

				PostgresResDTO.syst_mngm_alrm_hist newAlrm  = new PostgresResDTO.syst_mngm_alrm_hist();

				newAlrm.setAlrmLevel("경고");
				newAlrm.setCategory("감시");
				newAlrm.setContent(alrmDscr);
				newAlrm.setUpdateTime(updateTime);
				newAlrm.setConfirmTime("");
				newAlrm.setActionDetails("");
				newAlrm.setConfirmorName("");
				newAlrm.setActionFlag("");

				ins_chk = session.insert(sqlId, newAlrm);

			session.commit();




		return ins_chk;
	}


}
