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
public class AllScadaDataDAO {

	// 로그
	private static final Logger logger = LogManager.getLogger(AllScadaDataDAO.class);

	/**
	 * constructor
	 */
	public AllScadaDataDAO() {
	}


	public static void execScdaDataMinuteTrendHistory(List<hm_minute_trend_history> tagResultList) throws Exception {

		SystMngmAgtSttDTO systMngmAgtStt = new SystMngmAgtSttDTO();
		systMngmAgtStt.setProcsSttusCd(ConstDef.PostgresDB.SQLID_SYST_MNGM_AGT_STT);

		int cur = insertScdaDataMinuteTrendHistory(tagResultList, "all");
		systMngmAgtStt.setProcsType("all");
		systMngmAgtStt.setProcsCnt(cur);
		PrcsDAO.insertSystMngmAgtStt(systMngmAgtStt);

	}


	public static int insertScdaDataMinuteTrendHistory(List<MariaResDTO.hm_minute_trend_history> tagParams, String flag) throws Exception{
		SqlSession session = null;
		String sqlId  = String.format("%s.%s", ConstDef.PostgresDB.NAMESPACE, ConstDef.PostgresDB.SQLID_SCDA_DATA_MINUTE_TREND_HISTORY);

		int ins_chk = tagParams.size();

			session = DbManager.getSqlSessionPostgres();

			PostgresResDTO.scdaDataMinuteTrendHistory scdaDataMinuteTrendHistory  = new  PostgresResDTO.scdaDataMinuteTrendHistory();

			for(int i=0; i<tagParams.size(); i++) {

					scdaDataMinuteTrendHistory.setTagId(tagParams.get(i).getTagId());
					scdaDataMinuteTrendHistory.setTagGroupName(tagParams.get(i).getTagGroupName());
					scdaDataMinuteTrendHistory.setTagName(tagParams.get(i).getTagName());
					scdaDataMinuteTrendHistory.setRecordDate(tagParams.get(i).getRecordDate());
					scdaDataMinuteTrendHistory.setRecordTime(tagParams.get(i).getRecordTime());
					scdaDataMinuteTrendHistory.setT00(Utils.toFloat(tagParams.get(i).getT00()));
					scdaDataMinuteTrendHistory.setT01(Utils.toFloat(tagParams.get(i).getT01()));
					scdaDataMinuteTrendHistory.setT02(Utils.toFloat(tagParams.get(i).getT02()));
					scdaDataMinuteTrendHistory.setT03(Utils.toFloat(tagParams.get(i).getT03()));
					scdaDataMinuteTrendHistory.setT04(Utils.toFloat(tagParams.get(i).getT04()));
					scdaDataMinuteTrendHistory.setT05(Utils.toFloat(tagParams.get(i).getT05()));
					scdaDataMinuteTrendHistory.setT06(Utils.toFloat(tagParams.get(i).getT06()));
					scdaDataMinuteTrendHistory.setT07(Utils.toFloat(tagParams.get(i).getT07()));
					scdaDataMinuteTrendHistory.setT08(Utils.toFloat(tagParams.get(i).getT08()));
					scdaDataMinuteTrendHistory.setT09(Utils.toFloat(tagParams.get(i).getT09()));
					scdaDataMinuteTrendHistory.setT10(Utils.toFloat(tagParams.get(i).getT10()));
					scdaDataMinuteTrendHistory.setT11(Utils.toFloat(tagParams.get(i).getT11()));
					scdaDataMinuteTrendHistory.setT12(Utils.toFloat(tagParams.get(i).getT12()));
					scdaDataMinuteTrendHistory.setT13(Utils.toFloat(tagParams.get(i).getT13()));
					scdaDataMinuteTrendHistory.setT14(Utils.toFloat(tagParams.get(i).getT14()));
					scdaDataMinuteTrendHistory.setT15(Utils.toFloat(tagParams.get(i).getT15()));
					scdaDataMinuteTrendHistory.setT16(Utils.toFloat(tagParams.get(i).getT16()));
					scdaDataMinuteTrendHistory.setT17(Utils.toFloat(tagParams.get(i).getT17()));
					scdaDataMinuteTrendHistory.setT18(Utils.toFloat(tagParams.get(i).getT18()));
					scdaDataMinuteTrendHistory.setT19(Utils.toFloat(tagParams.get(i).getT19()));
					scdaDataMinuteTrendHistory.setT20(Utils.toFloat(tagParams.get(i).getT20()));
					scdaDataMinuteTrendHistory.setT21(Utils.toFloat(tagParams.get(i).getT21()));
					scdaDataMinuteTrendHistory.setT22(Utils.toFloat(tagParams.get(i).getT22()));
					scdaDataMinuteTrendHistory.setT23(Utils.toFloat(tagParams.get(i).getT23()));
					scdaDataMinuteTrendHistory.setT24(Utils.toFloat(tagParams.get(i).getT24()));
					scdaDataMinuteTrendHistory.setT25(Utils.toFloat(tagParams.get(i).getT25()));
					scdaDataMinuteTrendHistory.setT26(Utils.toFloat(tagParams.get(i).getT26()));
					scdaDataMinuteTrendHistory.setT27(Utils.toFloat(tagParams.get(i).getT27()));
					scdaDataMinuteTrendHistory.setT28(Utils.toFloat(tagParams.get(i).getT28()));
					scdaDataMinuteTrendHistory.setT29(Utils.toFloat(tagParams.get(i).getT29()));
					scdaDataMinuteTrendHistory.setT30(Utils.toFloat(tagParams.get(i).getT30()));
					scdaDataMinuteTrendHistory.setT31(Utils.toFloat(tagParams.get(i).getT31()));
					scdaDataMinuteTrendHistory.setT32(Utils.toFloat(tagParams.get(i).getT32()));
					scdaDataMinuteTrendHistory.setT33(Utils.toFloat(tagParams.get(i).getT33()));
					scdaDataMinuteTrendHistory.setT34(Utils.toFloat(tagParams.get(i).getT34()));
					scdaDataMinuteTrendHistory.setT35(Utils.toFloat(tagParams.get(i).getT35()));
					scdaDataMinuteTrendHistory.setT36(Utils.toFloat(tagParams.get(i).getT36()));
					scdaDataMinuteTrendHistory.setT37(Utils.toFloat(tagParams.get(i).getT37()));
					scdaDataMinuteTrendHistory.setT38(Utils.toFloat(tagParams.get(i).getT38()));
					scdaDataMinuteTrendHistory.setT39(Utils.toFloat(tagParams.get(i).getT39()));
					scdaDataMinuteTrendHistory.setT40(Utils.toFloat(tagParams.get(i).getT40()));
					scdaDataMinuteTrendHistory.setT41(Utils.toFloat(tagParams.get(i).getT41()));
					scdaDataMinuteTrendHistory.setT42(Utils.toFloat(tagParams.get(i).getT42()));
					scdaDataMinuteTrendHistory.setT43(Utils.toFloat(tagParams.get(i).getT43()));
					scdaDataMinuteTrendHistory.setT44(Utils.toFloat(tagParams.get(i).getT44()));
					scdaDataMinuteTrendHistory.setT45(Utils.toFloat(tagParams.get(i).getT45()));
					scdaDataMinuteTrendHistory.setT46(Utils.toFloat(tagParams.get(i).getT46()));
					scdaDataMinuteTrendHistory.setT47(Utils.toFloat(tagParams.get(i).getT47()));
					scdaDataMinuteTrendHistory.setT48(Utils.toFloat(tagParams.get(i).getT48()));
					scdaDataMinuteTrendHistory.setT49(Utils.toFloat(tagParams.get(i).getT49()));
					scdaDataMinuteTrendHistory.setT50(Utils.toFloat(tagParams.get(i).getT50()));
					scdaDataMinuteTrendHistory.setT51(Utils.toFloat(tagParams.get(i).getT51()));
					scdaDataMinuteTrendHistory.setT52(Utils.toFloat(tagParams.get(i).getT52()));
					scdaDataMinuteTrendHistory.setT53(Utils.toFloat(tagParams.get(i).getT53()));
					scdaDataMinuteTrendHistory.setT54(Utils.toFloat(tagParams.get(i).getT54()));
					scdaDataMinuteTrendHistory.setT55(Utils.toFloat(tagParams.get(i).getT55()));
					scdaDataMinuteTrendHistory.setT56(Utils.toFloat(tagParams.get(i).getT56()));
					scdaDataMinuteTrendHistory.setT57(Utils.toFloat(tagParams.get(i).getT57()));
					scdaDataMinuteTrendHistory.setT58(Utils.toFloat(tagParams.get(i).getT58()));
					scdaDataMinuteTrendHistory.setT59(Utils.toFloat(tagParams.get(i).getT59()));
					scdaDataMinuteTrendHistory.setT00Status(Utils.toFloat(tagParams.get(i).getT00Status()));
					scdaDataMinuteTrendHistory.setT01Status(Utils.toFloat(tagParams.get(i).getT01Status()));
					scdaDataMinuteTrendHistory.setT02Status(Utils.toFloat(tagParams.get(i).getT02Status()));
					scdaDataMinuteTrendHistory.setT03Status(Utils.toFloat(tagParams.get(i).getT03Status()));
					scdaDataMinuteTrendHistory.setT04Status(Utils.toFloat(tagParams.get(i).getT04Status()));
					scdaDataMinuteTrendHistory.setT05Status(Utils.toFloat(tagParams.get(i).getT05Status()));
					scdaDataMinuteTrendHistory.setT06Status(Utils.toFloat(tagParams.get(i).getT06Status()));
					scdaDataMinuteTrendHistory.setT07Status(Utils.toFloat(tagParams.get(i).getT07Status()));
					scdaDataMinuteTrendHistory.setT08Status(Utils.toFloat(tagParams.get(i).getT08Status()));
					scdaDataMinuteTrendHistory.setT09Status(Utils.toFloat(tagParams.get(i).getT09Status()));
					scdaDataMinuteTrendHistory.setT10Status(Utils.toFloat(tagParams.get(i).getT10Status()));
					scdaDataMinuteTrendHistory.setT11Status(Utils.toFloat(tagParams.get(i).getT11Status()));
					scdaDataMinuteTrendHistory.setT12Status(Utils.toFloat(tagParams.get(i).getT12Status()));
					scdaDataMinuteTrendHistory.setT13Status(Utils.toFloat(tagParams.get(i).getT13Status()));
					scdaDataMinuteTrendHistory.setT14Status(Utils.toFloat(tagParams.get(i).getT14Status()));
					scdaDataMinuteTrendHistory.setT15Status(Utils.toFloat(tagParams.get(i).getT15Status()));
					scdaDataMinuteTrendHistory.setT16Status(Utils.toFloat(tagParams.get(i).getT16Status()));
					scdaDataMinuteTrendHistory.setT17Status(Utils.toFloat(tagParams.get(i).getT17Status()));
					scdaDataMinuteTrendHistory.setT18Status(Utils.toFloat(tagParams.get(i).getT18Status()));
					scdaDataMinuteTrendHistory.setT19Status(Utils.toFloat(tagParams.get(i).getT19Status()));
					scdaDataMinuteTrendHistory.setT20Status(Utils.toFloat(tagParams.get(i).getT20Status()));
					scdaDataMinuteTrendHistory.setT21Status(Utils.toFloat(tagParams.get(i).getT21Status()));
					scdaDataMinuteTrendHistory.setT22Status(Utils.toFloat(tagParams.get(i).getT22Status()));
					scdaDataMinuteTrendHistory.setT23Status(Utils.toFloat(tagParams.get(i).getT23Status()));
					scdaDataMinuteTrendHistory.setT24Status(Utils.toFloat(tagParams.get(i).getT24Status()));
					scdaDataMinuteTrendHistory.setT25Status(Utils.toFloat(tagParams.get(i).getT25Status()));
					scdaDataMinuteTrendHistory.setT26Status(Utils.toFloat(tagParams.get(i).getT26Status()));
					scdaDataMinuteTrendHistory.setT27Status(Utils.toFloat(tagParams.get(i).getT27Status()));
					scdaDataMinuteTrendHistory.setT28Status(Utils.toFloat(tagParams.get(i).getT28Status()));
					scdaDataMinuteTrendHistory.setT29Status(Utils.toFloat(tagParams.get(i).getT29Status()));
					scdaDataMinuteTrendHistory.setT30Status(Utils.toFloat(tagParams.get(i).getT30Status()));
					scdaDataMinuteTrendHistory.setT31Status(Utils.toFloat(tagParams.get(i).getT31Status()));
					scdaDataMinuteTrendHistory.setT32Status(Utils.toFloat(tagParams.get(i).getT32Status()));
					scdaDataMinuteTrendHistory.setT33Status(Utils.toFloat(tagParams.get(i).getT33Status()));
					scdaDataMinuteTrendHistory.setT34Status(Utils.toFloat(tagParams.get(i).getT34Status()));
					scdaDataMinuteTrendHistory.setT35Status(Utils.toFloat(tagParams.get(i).getT35Status()));
					scdaDataMinuteTrendHistory.setT36Status(Utils.toFloat(tagParams.get(i).getT36Status()));
					scdaDataMinuteTrendHistory.setT37Status(Utils.toFloat(tagParams.get(i).getT37Status()));
					scdaDataMinuteTrendHistory.setT38Status(Utils.toFloat(tagParams.get(i).getT38Status()));
					scdaDataMinuteTrendHistory.setT39Status(Utils.toFloat(tagParams.get(i).getT39Status()));
					scdaDataMinuteTrendHistory.setT40Status(Utils.toFloat(tagParams.get(i).getT40Status()));
					scdaDataMinuteTrendHistory.setT41Status(Utils.toFloat(tagParams.get(i).getT41Status()));
					scdaDataMinuteTrendHistory.setT42Status(Utils.toFloat(tagParams.get(i).getT42Status()));
					scdaDataMinuteTrendHistory.setT43Status(Utils.toFloat(tagParams.get(i).getT43Status()));
					scdaDataMinuteTrendHistory.setT44Status(Utils.toFloat(tagParams.get(i).getT44Status()));
					scdaDataMinuteTrendHistory.setT45Status(Utils.toFloat(tagParams.get(i).getT45Status()));
					scdaDataMinuteTrendHistory.setT46Status(Utils.toFloat(tagParams.get(i).getT46Status()));
					scdaDataMinuteTrendHistory.setT47Status(Utils.toFloat(tagParams.get(i).getT47Status()));
					scdaDataMinuteTrendHistory.setT48Status(Utils.toFloat(tagParams.get(i).getT48Status()));
					scdaDataMinuteTrendHistory.setT49Status(Utils.toFloat(tagParams.get(i).getT49Status()));
					scdaDataMinuteTrendHistory.setT50Status(Utils.toFloat(tagParams.get(i).getT50Status()));
					scdaDataMinuteTrendHistory.setT51Status(Utils.toFloat(tagParams.get(i).getT51Status()));
					scdaDataMinuteTrendHistory.setT52Status(Utils.toFloat(tagParams.get(i).getT52Status()));
					scdaDataMinuteTrendHistory.setT53Status(Utils.toFloat(tagParams.get(i).getT53Status()));
					scdaDataMinuteTrendHistory.setT54Status(Utils.toFloat(tagParams.get(i).getT54Status()));
					scdaDataMinuteTrendHistory.setT55Status(Utils.toFloat(tagParams.get(i).getT55Status()));
					scdaDataMinuteTrendHistory.setT56Status(Utils.toFloat(tagParams.get(i).getT56Status()));
					scdaDataMinuteTrendHistory.setT57Status(Utils.toFloat(tagParams.get(i).getT57Status()));
					scdaDataMinuteTrendHistory.setT58Status(Utils.toFloat(tagParams.get(i).getT58Status()));
					scdaDataMinuteTrendHistory.setT59Status(Utils.toFloat(tagParams.get(i).getT59Status()));
					scdaDataMinuteTrendHistory.setMinVal(Utils.toFloat(tagParams.get(i).getMinVal()));
					scdaDataMinuteTrendHistory.setMinValTime(tagParams.get(i).getMinValTime());
					scdaDataMinuteTrendHistory.setMaxVal(Utils.toFloat(tagParams.get(i).getMaxVal()));
					scdaDataMinuteTrendHistory.setMaxValTime(tagParams.get(i).getMaxValTime());
					scdaDataMinuteTrendHistory.setAvgVal(Utils.toFloat(tagParams.get(i).getAvgVal()));
					scdaDataMinuteTrendHistory.setSumVal(Utils.toFloat(tagParams.get(i).getSumVal()));

					String reqDate_yyyy_MM_dd = Utils.getDate(ConstDef.EnumDateFormat.YYYY_MM_DD_HH24_MM_SS);
					scdaDataMinuteTrendHistory.setCollectDt(reqDate_yyyy_MM_dd  + " [" + i +"]");

					session.insert(sqlId, scdaDataMinuteTrendHistory);
			}

			session.commit();


		return ins_chk;
	}


}
