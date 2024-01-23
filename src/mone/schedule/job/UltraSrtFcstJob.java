package mone.schedule.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import mone.common.conf.ConfigManager;
import mone.common.conf.ConstDef;
import mone.common.util.Utils;
import mone.db.dao.WeatherPrcsDAO;
import mone.db.dto.MariaResDTO;
import mone.db.dto.UltraSrtFcstReqDTO;
import mone.db.dto.UltraSrtFcstResDTO;
import mone.schedule.scheduler.WeatherScheduleManager;

/**
 * 초단기실황 api 연계
 * @author iby
 *
 */
public class UltraSrtFcstJob implements Job {
	// 로그
	private static final Logger logger = LogManager.getLogger(UltraSrtFcstJob.class);

	/**
	 * constructor
	 */
	public UltraSrtFcstJob() {
	}

	/*
	 * 실행
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		try {
			List<UltraSrtFcstResDTO> resultList = new ArrayList<UltraSrtFcstResDTO>();
//			Map<String, String> chkResultMap = new HashMap<String, String>();

			//logger.debug("  지역 목록 ::: " + WeatherScheduleManager._ultraSrtFcstTargetList.size());

			for (UltraSrtFcstReqDTO dto : WeatherScheduleManager._ultraSrtFcstTargetList) {

				/* api 요청 파라미터 */
				// 격자 x
				String reqNx = dto.getNx();
				// 격자 y
				String reqNy = dto.getNy();
				// api 요청일자 - 년월일
				String reqDate_yyyyMMdd = Utils.getDate(ConstDef.EnumDateFormat.YYYYMMDD);
				// api 요청시각 - 시분
				String reqTime_hhmm = Utils.getDate(ConstDef.EnumDateFormat.HH24MM);

				String reqDate_yyyy_MM_dd = Utils.getDate(ConstDef.EnumDateFormat.YYYY_MM_DD_HH24_MM_SS);

				/* api 응답메시지 객체 */
				UltraSrtFcstResDTO ultraSrtFcstDTO = new UltraSrtFcstResDTO();
				ultraSrtFcstDTO.setReqDate(reqDate_yyyy_MM_dd);
				ultraSrtFcstDTO.setRegionAreaCd(dto.getRegionAreaCd());
				ultraSrtFcstDTO.setNx(reqNx);
				ultraSrtFcstDTO.setNy(reqNy);

				/* api 요청 url 생성 */
		        StringBuilder urlBuilder = new StringBuilder(ConfigManager.getProps("ultrasrtfcst.api.endpoint"));
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + ConfigManager.getProps("ultrasrtfcst.api.key"));
		        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(WeatherScheduleManager._reqPageNo, "UTF-8"));
		        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("60", "UTF-8"));
		        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(WeatherScheduleManager._reqDataType, "UTF-8"));
		        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(reqDate_yyyyMMdd, "UTF-8"));
		        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(reqTime_hhmm, "UTF-8"));
		        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(reqNx, "UTF-8"));
		        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(reqNy, "UTF-8"));

		        // GET방식으로 전달 후 파라미터 받아옴
		        URL url = new URL(urlBuilder.toString());
		        System.out.println(url);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");
		        conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
		        System.out.println("예보 :: Response code(200 ok) : " + conn.getResponseCode());
		        BufferedReader rd;

		        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
		            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        } else {
		            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		        }

		        StringBuilder sb = new StringBuilder();
		        String line;

		        while ((line = rd.readLine()) != null) {
		            sb.append(line);
		        }

		        rd.close();
		        conn.disconnect();

	        	parseJsonData(ultraSrtFcstDTO, sb.toString());

			}

			logger.info(String.format("[초단기 :: 예보]수집:%s(건)", resultList.size()));


		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}
	}

	/**
	 *
	 * @param resDTO
	 * @param resData
	 * @throws Exception
	 */
	private List<Map<String, Object>> parseJsonData(UltraSrtFcstResDTO resDTO, String resData) throws Exception {

		//List<UltraSrtFcstResDTO> result = new ArrayList<UltraSrtFcstResDTO>();
		List<Map<String, Object>> listMapInsert = new ArrayList<Map<String, Object>>();

		boolean isOk = false;

		try {
			boolean isJson = checkResDataType(resData);
			if(isJson == false) {
				return null;
			}

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(resData);
			JSONObject parse_response = (JSONObject)obj.get("response");

			isOk = checkValidJsonData((JSONObject)parse_response.get("header"));
			if(isOk == false) {
				return null;
			}

			JSONObject parse_body = (JSONObject)parse_response.get("body");
			JSONObject parse_items = (JSONObject)parse_body.get("items");
			JSONArray parse_item = (JSONArray)parse_items.get("item");

			String category = "";

			JSONObject element;

			String day = "";
			String time = "";

			String fday = "";
			String ftime = "";

			for(int i=0; i<parse_item.size(); i++) {
				element = (JSONObject)parse_item.get(i);

				// 측정치
				Object objBaseDate = element.get("baseDate");
				Object objBaseTime = element.get("baseTime");
				Object objFcstValue = element.get("fcstValue");

				if(objBaseDate != null && day.equals(objBaseDate.toString()) == false) 	{	day = objBaseDate.toString();	}
				if(objBaseDate != null && time.equals(objBaseTime.toString()) == false) {	time = objBaseTime.toString();	}
				if(objFcstValue == null || objBaseDate == null || objBaseTime == null) 	{	continue;						}
				Object objFcstDate = element.get("fcstDate");
				Object objFcstTime = element.get("fcstTime");

				if(objFcstDate != null && fday.equals(objFcstDate.toString()) == false) {	fday = objFcstDate.toString();	}
				if(objFcstDate != null && ftime.equals(objFcstTime.toString()) == false){	ftime = objFcstTime.toString();	}
				if(objFcstValue == null || objFcstDate == null || objFcstTime == null) 	{	continue;						}

				category = (String)element.get("category");

				String val = objFcstValue.toString();

				Map<String, Object> resultMap = new HashMap<String, Object>();

				switch (category) {
					case "T1H":	resultMap.put("t1h", val);	break;
					case "RN1":	resultMap.put("rn1", val.matches("-?\\d+(\\.\\d+)?") ? val : "0");  break;
					case "SKY":	resultMap.put("sky", val);  break;
					case "UUU":	resultMap.put("uuu", val);  break;
					case "VVV":	resultMap.put("vvv", val);  break;
					case "REH":	boolean chck = Utils.chkStrNullOrEmpty(val);	if(chck == false) {	val = "0";	}	resultMap.put("reh", val);	break;
					case "PTY":	resultMap.put("pty", val);	break;
					case "LGT":	resultMap.put("lgt", val);  break;
					case "VEC":	resultMap.put("vec", val);  break;
					case "WSD":	resultMap.put("wsd", val);  break;
					default:	break;
				}

				resultMap.put("fcstClct", fday.substring(0,4) +"-"+fday.substring(4,6) +"-"+fday.substring(6,8) + " " + ftime.substring(0,2) + ":" +  ftime.substring(2,4) +":00");
				resultMap.put("reqDate",resDTO.getReqDate());
				resultMap.put("regionAreaCd",resDTO.getRegionAreaCd());
				resultMap.put("nx",resDTO.getNx());
				resultMap.put("ny",resDTO.getNy());

				listMapInsert.add(resultMap);

				//logger.debug("<<<<<<<>>>>>>>>" + resultMap);
				WeatherPrcsDAO.insertAllUltraSrtFcst(resultMap);
			}



		} catch (ParseException e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}

		return listMapInsert;
	}

	/**
	 * API 취득 데이터의 타입이 JSON인지 체크
	 * @param resData
	 * @return
	 */
	private boolean checkResDataType(String resData) {
		boolean isJson = false;

		try {
			if(resData == null || resData.isEmpty() == true) {
				logger.debug(String.format("[초단기][response NG] response data is null"));
				return isJson;
			}
			if(resData.startsWith(ConstDef.START_TAG_JSON) == true) {
				isJson = true;
			} else {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document d = db.parse(new InputSource(new StringReader(resData)));
				NodeList nodelist =  d.getElementsByTagName("returnAuthMsg");
//				Node node =  nodelist.item(0);
				Node textNode =  nodelist.item(0).getChildNodes().item(0);
				String resultMsg = textNode.getNodeValue();

				nodelist =  d.getElementsByTagName("returnReasonCode");
//				node =  nodelist.item(0);
				textNode =  nodelist.item(0).getChildNodes().item(0);
				String resultCd = textNode.getNodeValue();

				//logger.debug(String.format("[초단기][response NG]CD:%s, MSG:%s", resultCd.toString(), resultMsg.toString()));
			}
		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}

		return isJson;
	}

	/**
	 * 응답메시지 정상여부 체크
	 * @param header
	 * @return
	 */
	private boolean checkValidJsonData(JSONObject header) {

		boolean isValid = false;

		try {
			if(header != null) {
				Object resultCd = header.get("resultCode");
				Object resultMsg = header.get("resultMsg");

				if(ConstDef.ApiResultCode.NORMAL_CODE.equals(resultCd.toString()) == true) {
					isValid = true;
				} else {
					logger.debug(String.format("[초단기][response NG]CD:%s, MSG:%s", resultCd.toString(), resultMsg.toString()));
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}

		return isValid;
	}
}
