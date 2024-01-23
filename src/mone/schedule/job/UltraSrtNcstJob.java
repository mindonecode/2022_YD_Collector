package mone.schedule.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import mone.db.dao.PrcsDAO;
import mone.db.dao.WeatherPrcsDAO;
import mone.db.dto.UltraSrtNcstReqDTO;
import mone.db.dto.UltraSrtNcstResDTO;
import mone.schedule.scheduler.WeatherScheduleManager;

/**
 * 초단기실황 api 연계
 * @author iby
 *
 */
public class UltraSrtNcstJob implements Job {
	// 로그
	private static final Logger logger = LogManager.getLogger(UltraSrtNcstJob.class);

	/**
	 * constructor
	 */
	public UltraSrtNcstJob() {
	}

	/*
	 * 실행
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		try {
			List<UltraSrtNcstResDTO> resultList = new ArrayList<UltraSrtNcstResDTO>();
//			Map<String, String> chkResultMap = new HashMap<String, String>();

			for (UltraSrtNcstReqDTO dto : WeatherScheduleManager._ultraSrtNcstTargetList) {

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
				UltraSrtNcstResDTO ultraSrtNcstDTO = new UltraSrtNcstResDTO();
				ultraSrtNcstDTO.setReqDate(reqDate_yyyy_MM_dd);
				ultraSrtNcstDTO.setRegionAreaCd(dto.getRegionAreaCd());
				ultraSrtNcstDTO.setNx(reqNx);
				ultraSrtNcstDTO.setNy(reqNy);

				/* api 요청 url 생성 */
		        StringBuilder urlBuilder = new StringBuilder(ConfigManager.getProps("ultrasrtncst.api.endpoint"));
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + ConfigManager.getProps("ultrasrtncst.api.key"));
		        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(WeatherScheduleManager._reqPageNo, "UTF-8"));
		        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(WeatherScheduleManager._reqNumOfRows, "UTF-8"));
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
		        conn.setRequestProperty("Content-type", "application/json");
		        System.out.println("Response code(200 ok) : " + conn.getResponseCode());
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

		        boolean isValid = parseJsonData(ultraSrtNcstDTO, sb.toString());
		        if(isValid == true) {
		        	resultList.add(ultraSrtNcstDTO);
		        }
			}

			logger.info(String.format("[초단기 :: 실황]수집:%s(건)", resultList.size()));

			if(resultList.size() > 0) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("resultList", resultList);
				WeatherPrcsDAO.insertAllUltraSrtNcst(resultMap);
			}
		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}
	}

	/**
	 *
	 * @param resDTO
	 * @param resData
	 */
	private boolean parseJsonData(UltraSrtNcstResDTO resDTO, String resData) {

		boolean isOk = false;

		try {
			// 데이터타입 체크
			boolean isJson = checkResDataType(resData);
			if(isJson == false) {
				return isOk;
			}

			// json 변환 객체
			JSONParser parser = new JSONParser();
			//
			JSONObject obj = (JSONObject)parser.parse(resData);
			//
			JSONObject parse_response = (JSONObject)obj.get("response");

			/* 헤더 체크 */
			isOk = checkValidJsonData((JSONObject)parse_response.get("header"));
			if(isOk == false) {
				return isOk;
			}


			//
			JSONObject parse_body = (JSONObject)parse_response.get("body");
			//
			JSONObject parse_items = (JSONObject)parse_body.get("items");
			//
			JSONArray parse_item = (JSONArray)parse_items.get("item");


			String category = "";

			JSONObject element;

			String day = "";
			String time = "";

			for(int i=0; i<parse_item.size(); i++) {
				element = (JSONObject)parse_item.get(i);
				// 측정치
				Object objBaseDate = element.get("baseDate");
				Object objBaseTime = element.get("baseTime");
				Object objObsrValue = element.get("obsrValue");

				category = (String)element.get("category");

				if(objBaseDate != null && day.equals(objBaseDate.toString()) == false) {
					day = objBaseDate.toString();
				}
				if(objBaseDate != null && time.equals(objBaseTime.toString()) == false) {
					time = objBaseTime.toString();
					//System.out.println(day + " " + time);
				}
				if(objObsrValue == null || objBaseDate == null || objBaseTime == null) {
					continue;
				}

				String val = objObsrValue.toString();

				switch (category) {
					case "T1H":	// 기온
						resDTO.setT1h(val);
						break;
					case "RN1":	// 1시간 강수량
						resDTO.setRn1(val);
						break;
					case "UUU":	// 동서바람성분
						resDTO.setUuu(val);
						break;
					case "VVV":	// 남북바람성분
						resDTO.setVvv(val);
						break;
					case "REH":	// 습도
						boolean chck = Utils.chkStrNullOrEmpty(val);
						if(chck == false) {
							val = "0";
							//logger.debug("REH(습도) is null");
						}
						resDTO.setReh(val);
						break;
					case "PTY":	// 강수형태
						resDTO.setPty(val);
						break;
					case "VEC":	// 풍향
						resDTO.setVec(val);
						break;
					case "WSD":	// 풍속
						resDTO.setWsd(val);
						break;
					default:
						break;
				}

				resDTO.setBaseDate(day);
				resDTO.setBaseTime(time);
			}

			// LOG : 수집 데이터 로그 출력
			/*
			logger.debug(String.format("%s%s,%s,%s,%s,%s,%s,%s,%s"
					, resDTO.getReqDate(), resDTO.getReqTime(), resDTO.getRegionAreaCd()
					, resDTO.getT1h(), resDTO.getRn1(), resDTO.getReh(), resDTO.getVec(), resDTO.getWsd(), resDTO.getPty()));
			*/
		} catch (ParseException e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}

		return isOk;
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
				//logger.debug(String.format("[초단기][response NG] response data is null"));
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
					//logger.debug(String.format("[초단기][response NG]CD:%s, MSG:%s", resultCd.toString(), resultMsg.toString()));
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}

		return isValid;
	}
}
