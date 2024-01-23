package mone.schedule.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import mone.common.conf.ConfigManager;
import mone.common.conf.ConstDef;
import mone.common.util.Utils;
import mone.db.dao.PrcsDAO;
import mone.db.dao.WeatherPrcsDAO;
import mone.db.dto.PwnCdReqDTO;
import mone.db.dto.PwnCdResDTO;
import mone.schedule.scheduler.WeatherScheduleManager;


/**
 * 특보코드조회 api 연계
 * @author iby
 *
 */
public class PwnCdJob implements Job {
	// 로그
	private static final Logger logger = LogManager.getLogger(PwnCdJob.class);

	/**
	 * constructor
	 */
	public PwnCdJob() {	 }

	/**
	 *
	 * @param list
	 * @param filePath
	 * @throws IOException
	 */
	public void loadReqParamList(List<PwnCdReqDTO> list, String filePath) throws IOException {
		BufferedReader br =null;
		FileReader fr = null;
		try {
            fr = new FileReader(new File(filePath));
            br = new BufferedReader(fr);
            String line;
            String[] tokens;
            int num = 0;

            while((line = br.readLine()) != null) {
                tokens = line.split("\\" + ConstDef.SEPARATOR);
                if(tokens.length == 2) {
                	PwnCdReqDTO item = new PwnCdReqDTO();
                	item.setAreaCd(tokens[0]);
                    item.setAreaNm(tokens[1]);
                    item.setNum(num++);

                    list.add(item);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Exception Message"  + e.getClass().getSimpleName() );
        }finally {
        	 br.close();
        	 fr.close();
        }
    }


	/**
	 *
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {


		try {
			List<PwnCdResDTO> resultList = new ArrayList<PwnCdResDTO>();

			/* api 요청 파라미터 */

			// 날짜가 바뀐 경우
			if(WeatherScheduleManager._today_yyyymmdd.equals(Utils.getDate(ConstDef.EnumDateFormat.YYYYMMDD)) == false) {
				//System.out.println(String.format("1)%s, %s, %s", Utils.getDate(ConstDef.EnumDateFormat.YYYYMMDD), WeatherScheduleManager._today_yyyymmdd, WeatherScheduleManager._yesterday_yyyymmdd));
				WeatherScheduleManager._yesterday_yyyymmdd = WeatherScheduleManager._today_yyyymmdd;
				WeatherScheduleManager._today_yyyymmdd = Utils.getDate(ConstDef.EnumDateFormat.YYYYMMDD);
				WeatherScheduleManager._uniqueKeyList = new ArrayList<String>();
				//System.out.println(String.format("2)%s, %s, %s", req_yyyymmdd, WeatherScheduleManager._today_yyyymmdd, WeatherScheduleManager._yesterday_yyyymmdd));
			}

			//logger.debug(String.format("yesterday:%s, today:%s, reqday%s", WeatherScheduleManager._yesterday_yyyymmdd, WeatherScheduleManager._today_yyyymmdd, Utils.getDate(ConstDef.EnumDateFormat.YYYYMMDD)));




			/* api 응답메시지 객체 */
			//PwnCdResDTO pwnCdResDTO = new PwnCdResDTO();

			/* api 요청 url 생성 */
	        StringBuilder urlBuilder = new StringBuilder(ConfigManager.getProps("pwncd.api.endpoint"));
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + ConfigManager.getProps("pwncd.api.key"));
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(WeatherScheduleManager._reqPageNo, "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(WeatherScheduleManager._reqNumOfRows, "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(WeatherScheduleManager._reqDataType, "UTF-8"));
	        //urlBuilder.append("&" + URLEncoder.encode("areaCode","UTF-8") + "=" + URLEncoder.encode(, "UTF-8")); /*특보구역코드*/
	        //urlBuilder.append("&" + URLEncoder.encode("fromTmFc","UTF-8") + "=" + URLEncoder.encode(req_yyyymmdd, "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("fromTmFc","UTF-8") + "=" + URLEncoder.encode("20220816", "UTF-8"));

	        BufferedReader rd = null;

	        // GET방식으로 전달 후 파라미터 받아옴
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");

	        try {
	        	 if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	 	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	 	        } else {
	 	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	 	        }

			} catch (IOException e) {
				System.err.println("whther collect occur");
			}finally {
			  rd.close();
		      conn.disconnect();
			}

	        StringBuilder sb = new StringBuilder();
 	        String line;

 	        while ((line = rd.readLine()) != null) {
 	            sb.append(line);
 	        }

	        rd.close();
	        conn.disconnect();

	        boolean isValid = parseJsonData(resultList, sb.toString());

	        if(isValid == true && resultList.size() > 0) {

	        	Map<String, Object> resultMap = new HashMap<String, Object>();
	        	resultMap.put("resultList", resultList);
	        	WeatherPrcsDAO.insertAllPwnCd(resultMap);
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
	private boolean parseJsonData(List<PwnCdResDTO> resultList, String resData) {

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

			JSONObject element;

			// 수집 건 수
			int itemCnt = parse_item.size();
			int uKeyListCnt = WeatherScheduleManager._uniqueKeyList.size();
			//System.out.println("itemCnt:"+itemCnt +", uniqueKeyListCnt:" + WeatherScheduleManager._uniqueKeyList.size());
			logger.info(String.format("[특보] 중복:%s(건), 수집:%s(건)", uKeyListCnt, itemCnt));

			if(itemCnt == uKeyListCnt) {
				isOk = false;
				return isOk;
			}

			List<String> tempUniqueKeyList = new ArrayList<String>();
			int distinctCnt = 0;
			for(int i=0; i<itemCnt; i++) {
				element = (JSONObject)parse_item.get(i);
				// 측정치
				Object objTmFc 			= element.get("tmFc");
				Object objTmSeq 		= element.get("tmSeq");
				Object objAreaCode 		= element.get("areaCode");
//				Object objAreaName 		= element.get("areaName");
				Object objWarnVar 		= element.get("warnVar");
				Object objWarnStress 	= element.get("warnStress");
				Object objCommand 		= element.get("command");
				Object objStartTime 	= element.get("startTime");
				Object objEndTime 		= element.get("endTime");
//				Object objAllEndTime 	= element.get("allEndTime");
//				Object objCancel 		= element.get("cancel");

				PwnCdResDTO resDTO = new PwnCdResDTO();

				if(objTmFc 			!= null) { resDTO.setTmFc(objTmFc.toString());  			}
				if(objTmSeq 		!= null) { resDTO.setTmSeq(objTmSeq.toString()); 			}
				if(objAreaCode 		!= null) { resDTO.setAreaCd(objAreaCode.toString()); 		}
//				if(objAreaName 		!= null) { resDTO.setAreaNm(objAreaName.toString()); 		}
				if(objWarnVar 		!= null) { resDTO.setWarnType(objWarnVar.toString()); 		}
				if(objWarnStress 	!= null) { resDTO.setWarnStress(objWarnStress.toString()); 	}
				if(objCommand 		!= null) { resDTO.setCommand(objCommand.toString()); 		}
				if(objStartTime 	!= null) { resDTO.setStartTime(objStartTime.toString()); 	}
				if(objEndTime 		!= null) { resDTO.setEndTime(objEndTime.toString()); 		}
//				if(objAllEndTime 	!= null) { resDTO.setAllEndTime(objAllEndTime.toString()); 	}
//				if(objCancel 		!= null) { resDTO.setCancel(objCancel.toString()); 			}

				// 임시 키(유니크)
				String tempKey = resDTO.getAreaCd() + resDTO.getTmSeq();

				// 중복 데이터 체크
				boolean isUnique = checkUniqueData(tempKey);

				if(isUnique == true) {
					// ok - 중복 x
					tempUniqueKeyList.add(tempKey);

					resDTO.setReqDate(Utils.getDate(ConstDef.EnumDateFormat.YYYYMMDD));
					resDTO.setReqTime(Utils.getDate(ConstDef.EnumDateFormat.HH24MM));
					resDTO.setFromTmFc(WeatherScheduleManager._today_yyyymmdd);
					resDTO.setToTmFc(WeatherScheduleManager._today_yyyymmdd);
					resultList.add(resDTO);

					// LOG : 수집 데이터 로그 출력
					/*
					logger.debug(String.format("%s%s,%s,%s,%s,%s,%s,%s,%s,%s"
							, resDTO.getReqDate(), resDTO.getReqTime(), resDTO.getAreaCd(), resDTO.getTmSeq(), resDTO.getTmFc()
							, resDTO.getCommand(), resDTO.getStartTime(), resDTO.getEndTime(), resDTO.getWarnType(), resDTO.getWarnStress()));
					*/
				} else {
					// ng - 중복 o
					distinctCnt++;
					continue;
				}
			}

			WeatherScheduleManager._uniqueKeyList.addAll(tempUniqueKeyList);
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
				//logger.debug(String.format("[특보][response NG] response data is null"));
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
				String resultCd= textNode.getNodeValue();

				//logger.debug(String.format("[특보][response NG]CD:%s, MSG:%s", resultCd.toString(), resultMsg.toString()));
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
					//logger.debug(String.format("[특보][response NG]CD:%s, MSG:%s", resultCd.toString(), resultMsg.toString()));
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Message"  + e.getClass().getSimpleName() );
		}

		return isValid;
	}

	/**
	 * 중복 데이터 체크
	 * @param chkUniqueKey
	 * @return
	 */
	private boolean checkUniqueData(String chkUniqueKey) {

		for(int i=0; i<WeatherScheduleManager._uniqueKeyList.size(); i++) {

			String uniqueKey = WeatherScheduleManager._uniqueKeyList.get(i);

			if(uniqueKey.equals(chkUniqueKey)) {
				return false;
			}
		}

		return true;
	}
}
