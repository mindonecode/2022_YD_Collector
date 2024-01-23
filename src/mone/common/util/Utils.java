package mone.common.util;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mone.common.conf.ConfigManager;
import mone.common.conf.ConstDef;
import mone.db.dao.PrcsDAO;

/**
 *
 * @author iby
 *
 */
public class Utils {

	private static final Logger logger = LogManager.getLogger(PrcsDAO.class);
	/**
	 * 본 프로세스의 pid & hostname 취득
	 * @return pid@hostname
	 */
	public static String getJVMName() {
		String result = "";
		result = ManagementFactory.getRuntimeMXBean().getName();
		return result;
	}

	/**
	 *
	 * @param format
	 * @return
	 */
	public static String getDate(ConstDef.EnumDateFormat format) {

		SimpleDateFormat result;

		switch (format) {
			case YYYY_MM_DD_HH24_MM_SS:
				result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case YYYYMMDDHH24MMSS:
				result = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case YYYYMMDD:
				result = new SimpleDateFormat("yyyyMMdd");
				break;
			case HH24MM:
				result = new SimpleDateFormat("HHmm");
				break;
			case HH12MM:
				result = new SimpleDateFormat("hhmm");
				break;
			case HH24MMSS:
				result = new SimpleDateFormat("HHmmss");
				break;
			default:
				result = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
		}

		return result.format(new Date());
	}

	/**
	 *
	 * @param pastDays
	 * @return
	 */
	public static String getDate(int pastDays) {

		SimpleDateFormat  sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, pastDays);

		return sdf.format(cal.getTime());
	}

	/**
	 *
	 * @param target
	 * @return
	 */
	public static boolean chkStrNullOrEmpty(String target) {
		boolean result = true;
		if(target == null || target.isEmpty() == true) {
			result = false;
		}
		return result;
	}

	public static Float toFloat(String str) {
	    return toFloat(str, (float) 0);
	}

	public static Float toFloat(String str, Float defaultValue) {
	    if (str == null) {
	        return defaultValue;
	    } else {
	        try {
	            return Float.parseFloat(str);
	        } catch (NumberFormatException var3) {
	            return defaultValue;
	        }
	    }
	}

	/* 감시 알림 태그 내용을 저장하기 위한 메시지 함수 */
	public static String tagAlramMsg(String tagName, double resultMaxVal, double resultminVal, Float chkVal) {
	    String msg = "[" + tagName + "]" + "측정값 알림 \r\n" +
    			"측정값: " + 	chkVal + "\r\n" +
    			"기준값(min): " + resultMaxVal +  "\r\n" +
    			"기준값(max): " + resultminVal +  "\r\n" ;
		return msg;
	}


	public static int Bof() {

		Calendar now = Calendar.getInstance();
		int startMinute = (now.get(Calendar.MINUTE) < 2) ? 0 : now.get(Calendar.MINUTE)-2;

		return startMinute;

	}



	 public static long diffTime(String fromTm, String toTm ) throws ParseException{
		 long sec = 0;
		 SimpleDateFormat f = new SimpleDateFormat("YYYYMMddHHmm", Locale.KOREA);
			Date d1;
			Date d2;

				d1 = f.parse(fromTm);
				d2 = f.parse(toTm);
			long diff = d1.getTime() - d2.getTime();
			sec = diff / 60000;


			return sec;
	 }



	 public static Map<String, Object> BeginFrom(String finalChkDt) throws ParseException{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Calendar now = Calendar.getInstance();

			String nowRecordDate =  Utils.getDate(ConstDef.EnumDateFormat.YYYYMMDD) ;
			String nowReordTime = String.format("%02d", now.get(Calendar.HOUR_OF_DAY));
			String nowMinute = String.format("%02d", now.get(Calendar.MINUTE));

	        String finRecordDate = finalChkDt.substring(0, 8);
	        String finRecordTime = finalChkDt.substring(8, 10);
	        String finRecordMinute = finalChkDt.substring(10, 12);

	        String recordDate="";
	        String recordTime="";

	    	int endMinute = 0;
			int startMinute = 0;

			String nmTm = nowRecordDate + nowReordTime + nowMinute;

			int scdaSelectCnt  = Integer.parseInt(ConfigManager.getProps("scada.select.count"));


			if( now.get(Calendar.MINUTE) >= 0
					&& now.get(Calendar.MINUTE) <= 2
					&& Double.parseDouble(nmTm)  - Double.parseDouble(finalChkDt) < 30
			){
				recordDate = finRecordDate;
				recordTime = String.format("%02d", now.get(Calendar.HOUR_OF_DAY) - 1) ;

				startMinute = 55;
				endMinute = 59;

			} else {


				if( finalChkDt.substring(8, 12).equals("2359")) {

					Calendar cal = Calendar.getInstance();
				    DateFormat df = new SimpleDateFormat("yyyyMMdd");
				    Date date  =  df.parse(finRecordDate);
				    cal.setTime(date);
				    cal.add(Calendar.DATE, 1);

				    recordDate = df.format(cal.getTime());
				    recordTime = "00";
				    finRecordMinute = "0";

				} else if(finalChkDt.substring(10, 12).equals("59") ) {

					recordDate = finRecordDate;
					recordTime = String.format("%02d", (Integer.parseInt(finRecordTime) + 1))  ;
					finRecordMinute = "0";

				} else {

					recordDate = finRecordDate;
					recordTime = finRecordTime;
				}


				startMinute = ( Integer.parseInt(finRecordMinute) - 2 < 0) ? 0 : Integer.parseInt(finRecordMinute) - 2 ;

				if(now.get(Calendar.MINUTE) > scdaSelectCnt && Double.parseDouble(nmTm) - Double.parseDouble(finalChkDt)  >  scdaSelectCnt ) {
					endMinute = ( Integer.parseInt(finRecordMinute) + scdaSelectCnt < 59) ?  Integer.parseInt(finRecordMinute) + scdaSelectCnt : 59;
				}else {
					endMinute =  ( now.get(Calendar.MINUTE) < 59 ) ? now.get(Calendar.MINUTE) - 1  : 59  ;
				}

			}

			resultMap.put("recordDate", recordDate);
			resultMap.put("recordTime", recordTime);

			resultMap.put("startMinute", startMinute);
			resultMap.put("endMinute", endMinute);


			return resultMap;
		}



}
