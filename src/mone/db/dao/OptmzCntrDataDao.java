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
public class OptmzCntrDataDao {

	// 로그
	private static final Logger logger = LogManager.getLogger(OptmzCntrDataDao.class);

	/**
	 * constructor
	 */
	public OptmzCntrDataDao() {
	}




	public static int execHmiOptmzCntrData(List<MariaResDTO.optmz_cntr_data> tagParams, String optType) throws Exception{
		SqlSession session_post = null;
		String sqlId_PSBR  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_OPTMZ_CNTR_DATA_PSBR);
		String sqlId_DENIPHO  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_OPTMZ_CNTR_DATA_DENIPHO);
		String sqlId_PAC  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_OPTMZ_CNTR_DATA_PAC);
		String sqlId_PUMP  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_OPTMZ_CNTR_DATA_PUMP);
		String sqlId_MLSS  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_INSERT_OPTMZ_CNTR_DATA_MLSS);

		SqlSession session_maria = null;
		String sqlId_MARK  = String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_UPDATE_OPTMZ_CNTR_DATA);

		int ins_chk = tagParams.size();

		try {

			session_post= DbManager.getSqlSessionPostgres();
			session_maria= DbManager.getSqlSessionMaria();

			PostgresResDTO.optmz_cntr_data optmz_post  = new  PostgresResDTO.optmz_cntr_data();
			MariaResDTO.optmz_cntr_data optmz_maria  = new  MariaResDTO.optmz_cntr_data();


			if("pump".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_post.setPumpFlag(tagParams.get(i).getPumpFlag());
					optmz_post.setPumpMark(tagParams.get(i).getDeniphoMark());
					optmz_post.setPumpRecdDt(tagParams.get(i).getPumpRecdDt());
					optmz_post.setPumpMark(tagParams.get(i).getPumpMark());
					optmz_post.setM117aOrdr(tagParams.get(i).getM117aOrdr());
					optmz_post.setM117bOrdr(tagParams.get(i).getM117bOrdr());
					optmz_post.setM117cOrdr(tagParams.get(i).getM117cOrdr());
					optmz_post.setM118Ordr(tagParams.get(i).getM118Ordr());
					optmz_post.setH5Hz(tagParams.get(i).getH5Hz());
					optmz_post.setH4Hz(tagParams.get(i).getH4Hz());
					optmz_post.setH3Hz(tagParams.get(i).getH3Hz());
					optmz_post.setH2Hz(tagParams.get(i).getH2Hz());
					optmz_post.setH1Hz(tagParams.get(i).getH1Hz());
					optmz_post.setPumpStatus(tagParams.get(i).getPumpStatus());

					if(!"".equals(tagParams.get(i).getPumpFlag())) {
						session_post.insert(sqlId_PUMP, optmz_post);

						/*
						optmz_maria.setChkTablenm("optmz_cntr_pump");
						optmz_maria.setRecdDt(tagParams.get(i).getPumpRecdDt());
						session_maria.update(sqlId_MARK, optmz_maria);
						*/
					}
				}

			}else if("denipho".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_post.setDeniphoFlag(tagParams.get(i).getDeniphoFlag());
					optmz_post.setDeniphoMark(tagParams.get(i).getDeniphoMark());
					optmz_post.setDeniphoRecdDt(tagParams.get(i).getDeniphoRecdDt());
					optmz_post.setDeniphoMark(tagParams.get(i).getDeniphoMark());
					optmz_post.setDeniphoFcltKnd(tagParams.get(i).getDeniphoFcltKnd());
					optmz_post.setDeniphoStngVal(tagParams.get(i).getDeniphoStngVal());
					optmz_post.setDeniphoStatus(tagParams.get(i).getDeniphoStatus());
					if(!"".equals(tagParams.get(i).getDeniphoFlag())) {
						session_post.insert(sqlId_DENIPHO, optmz_post);

						/*
						optmz_maria.setChkTablenm("optmz_cntr_denipho");
						optmz_maria.setRecdDt(tagParams.get(i).getDeniphoRecdDt());
						session_maria.update(sqlId_MARK, optmz_maria);
						*/
					}
				}

			}else if("psbr".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_post.setPsbrFlag(tagParams.get(i).getPsbrFlag());
					optmz_post.setPsbrMark(tagParams.get(i).getPsbrMark());
					optmz_post.setPsbrRecdDt(tagParams.get(i).getPsbrRecdDt());
					optmz_post.setPsbrMark(tagParams.get(i).getPsbrMark());
					optmz_post.setM1002aOptmz(tagParams.get(i).getM1002aOptmz());
					optmz_post.setM1002bOptmz(tagParams.get(i).getM1002bOptmz());
					optmz_post.setPm1009aOptmz(tagParams.get(i).getPm1009aOptmz());
					optmz_post.setPm1009bOptmz(tagParams.get(i).getPm1009bOptmz());
					optmz_post.setPsbrStatus(tagParams.get(i).getPsbrStatus());
					if(!"".equals(tagParams.get(i).getPsbrFlag())) {
						session_post.insert(sqlId_PSBR, optmz_post);

						/*
						optmz_maria.setChkTablenm("optmz_cntr_psbr");
						optmz_maria.setRecdDt(tagParams.get(i).getPsbrRecdDt());
						session_maria.update(sqlId_MARK, optmz_maria);
						*/
					}
				}

			}else if("pac".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_post.setPacFlag(tagParams.get(i).getPacFlag());
					optmz_post.setPacMark(tagParams.get(i).getPacMark());
					optmz_post.setPacRecdDt(tagParams.get(i).getPacRecdDt());
					optmz_post.setPacMark(tagParams.get(i).getPacMark());
					optmz_post.setPacFcltKnd(tagParams.get(i).getPacFcltKnd());
					optmz_post.setPacStngVal(tagParams.get(i).getPacStngVal());
					optmz_post.setPacStatus(tagParams.get(i).getPacStatus());
					if(!"".equals(tagParams.get(i).getPacFlag())) {
						session_post.insert(sqlId_PAC, optmz_post);

						/*
						optmz_maria.setChkTablenm("optmz_cntr_pac");
						optmz_maria.setRecdDt(tagParams.get(i).getPacRecdDt());
						session_maria.update(sqlId_MARK, optmz_maria);
						*/
					}

				}
			}else if("mlss".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_post.setMlssFlag(tagParams.get(i).getMlssFlag());
					optmz_post.setMlssMark(tagParams.get(i).getMlssMark());
					optmz_post.setMlssRecdDt(tagParams.get(i).getMlssRecdDt());
					optmz_post.setMlssMark(tagParams.get(i).getMlssMark());
					optmz_post.setMlssOpenStngVal(tagParams.get(i).getMlssOpenStngVal());
					optmz_post.setMlssCloseStngVal(tagParams.get(i).getMlssCloseStngVal());
					optmz_post.setMlssStatus(tagParams.get(i).getMlssStatus());
					if(!"".equals(tagParams.get(i).getMlssFlag())) {
						session_post.insert(sqlId_MLSS, optmz_post);

						/*
						optmz_maria.setChkTablenm("optmz_cntr_mlss");
						optmz_maria.setRecdDt(tagParams.get(i).getMlssRecdDt());
						session_maria.update(sqlId_MARK, optmz_maria);
						*/
					}
				}
			}





		}catch (Exception e) {
			logger.error(" read maria & mark error" + e.toString());
		}
		return ins_chk;
	}




	public static int execOptOptmzCntrData(List<PostgresResDTO.optmz_cntr_data> tagParams, String optType) throws Exception{
		SqlSession session_post = null;
		String sqlId_PSBR  		= String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_INSERT_OPTMZ_CNTR_DATA_PSBR);
		String sqlId_DENIPHO  	= String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_INSERT_OPTMZ_CNTR_DATA_DENIPHO);
		String sqlId_PAC  		= String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_INSERT_OPTMZ_CNTR_DATA_PAC);
		String sqlId_PUMP  		= String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_INSERT_OPTMZ_CNTR_DATA_PUMP);
		String sqlId_MLSS  		= String.format("%s.%s", ConstDef.MariaDB.NAMESPACE, ConstDef.MariaDB.SQLID_INSERT_OPTMZ_CNTR_DATA_MLSS);

		SqlSession session_maria = null;
		String sqlId_MARK  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_UPDATE_OPTMZ_CNTR_DATA);

		int ins_chk = tagParams.size();
		try {
			session_post= DbManager.getSqlSessionPostgres();
			session_maria= DbManager.getSqlSessionMaria();

			MariaResDTO.optmz_cntr_data optmz_maria  = new  MariaResDTO.optmz_cntr_data();
			PostgresResDTO.optmz_cntr_data optmz_post  = new  PostgresResDTO.optmz_cntr_data();


			if("pump".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_maria.setPumpFlag(tagParams.get(i).getPumpFlag());
					optmz_maria.setPumpMark(tagParams.get(i).getPumpMark());
					optmz_maria.setPumpRecdDt(tagParams.get(i).getPumpRecdDt());
					optmz_maria.setPumpMark(tagParams.get(i).getPumpMark());
					optmz_maria.setM117aOrdr(tagParams.get(i).getM117aOrdr());
					optmz_maria.setM117bOrdr(tagParams.get(i).getM117bOrdr());
					optmz_maria.setM117cOrdr(tagParams.get(i).getM117cOrdr());
					optmz_maria.setM118Ordr(tagParams.get(i).getM118Ordr());
					optmz_maria.setH5Hz(tagParams.get(i).getH5Hz());
					optmz_maria.setH4Hz(tagParams.get(i).getH4Hz());
					optmz_maria.setH3Hz(tagParams.get(i).getH3Hz());
					optmz_maria.setH2Hz(tagParams.get(i).getH2Hz());
					optmz_maria.setH1Hz(tagParams.get(i).getH1Hz());
					optmz_maria.setPumpStatus(tagParams.get(i).getPumpStatus());
					if(!"".equals(tagParams.get(i).getPumpFlag())) {
						session_maria.insert(sqlId_PUMP, optmz_maria);
						/*
						optmz_post.setChkTablenm("optmz_cntr_pump");
						optmz_post.setRecdDt(tagParams.get(i).getPumpRecdDt());
						session_post.update(sqlId_MARK, optmz_post);
						*/
					}



				}



			}else if("denipho".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_maria.setDeniphoFlag(tagParams.get(i).getDeniphoFlag());
					optmz_maria.setDeniphoMark(tagParams.get(i).getDeniphoMark());
					optmz_maria.setDeniphoRecdDt(tagParams.get(i).getDeniphoRecdDt());
					optmz_maria.setDeniphoMark(tagParams.get(i).getDeniphoMark());
					optmz_maria.setDeniphoFcltKnd(tagParams.get(i).getDeniphoFcltKnd());
					optmz_maria.setDeniphoStngVal(tagParams.get(i).getDeniphoStngVal());
					optmz_maria.setDeniphoStatus(tagParams.get(i).getDeniphoStatus());
					if(!"".equals(tagParams.get(i).getDeniphoFlag())) {
						session_maria.insert(sqlId_DENIPHO, optmz_maria);
						/*
						optmz_post.setChkTablenm("optmz_cntr_denipho");
						optmz_post.setRecdDt(tagParams.get(i).getDeniphoRecdDt());
						session_post.update(sqlId_MARK, optmz_post);
						*/
					}
				}
			}else if("psbr".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_maria.setPsbrFlag(tagParams.get(i).getPsbrFlag());
					optmz_maria.setPsbrMark(tagParams.get(i).getPsbrMark());
					optmz_maria.setPsbrRecdDt(tagParams.get(i).getPsbrRecdDt());
					optmz_maria.setPsbrMark(tagParams.get(i).getPsbrMark());
					optmz_maria.setPsbrMark(tagParams.get(i).getPsbrMark());
					optmz_maria.setM1002aOptmz(tagParams.get(i).getM1002aOptmz());
					optmz_maria.setM1002bOptmz(tagParams.get(i).getM1002bOptmz());
					optmz_maria.setPm1009aOptmz(tagParams.get(i).getPm1009aOptmz());
					optmz_maria.setPm1009bOptmz(tagParams.get(i).getPm1009bOptmz());
					optmz_maria.setPsbrStatus(tagParams.get(i).getPsbrStatus());

					if(!"".equals(tagParams.get(i).getPsbrFlag())) {
						session_maria.insert(sqlId_PSBR, optmz_maria);
						/*
						optmz_post.setChkTablenm("optmz_cntr_pump");
						optmz_post.setRecdDt(tagParams.get(i).getPsbrRecdDt());
						session_post.update(sqlId_MARK, optmz_post);
						*/
					}
				}
			}else if("pac".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_maria.setPacFlag(tagParams.get(i).getPacFlag());
					optmz_maria.setPacMark(tagParams.get(i).getPacMark());
					optmz_maria.setPacRecdDt(tagParams.get(i).getPacRecdDt());
					optmz_maria.setPacMark(tagParams.get(i).getPacMark());
					optmz_maria.setPacFcltKnd(tagParams.get(i).getPacFcltKnd());
					optmz_maria.setPacStngVal(tagParams.get(i).getPacStngVal());
					optmz_maria.setPacStatus(tagParams.get(i).getPacStatus());
					if(!"".equals(tagParams.get(i).getPacFlag())) {
						session_maria.insert(sqlId_PAC, optmz_maria);
						/*
						optmz_post.setChkTablenm("optmz_cntr_pac");
						optmz_post.setRecdDt(tagParams.get(i).getPacRecdDt());
						session_post.update(sqlId_MARK, optmz_post);
						*/
					}
				}
			}else if("mlss".equals(optType)) {
				for(int i=0; i<tagParams.size(); i++) {
					optmz_maria.setMlssFlag(tagParams.get(i).getMlssFlag());
					optmz_maria.setMlssMark(tagParams.get(i).getMlssMark());
					optmz_maria.setMlssRecdDt(tagParams.get(i).getMlssRecdDt());
					optmz_maria.setMlssMark(tagParams.get(i).getMlssMark());
					optmz_maria.setMlssOpenStngVal(tagParams.get(i).getMlssOpenStngVal());
					optmz_maria.setMlssCloseStngVal(tagParams.get(i).getMlssCloseStngVal());
					optmz_maria.setMlssStatus(tagParams.get(i).getMlssStatus());
					if(!"".equals(tagParams.get(i).getMlssFlag())) {
						session_maria.insert(sqlId_MLSS, optmz_maria);
						/*
						optmz_post.setChkTablenm("optmz_cntr_mlss");
						optmz_post.setRecdDt(tagParams.get(i).getMlssRecdDt());
						session_post.update(sqlId_MARK, optmz_post);
						*/
					}
				}

			}


		}catch (Exception e) {
			logger.error(" Write postges & mark error" + e.toString());
		}

		return ins_chk;
	}


}
