package mone.common.conf;

/**
 *
 * @author iby
 *
 */
public class ConstDef {

	// 프로젝트 기본 경로
	public static final String USER_DIR = System.getProperty("user.dir");

	// 설정 파일 경로
	// 로컬은 요넘으로
	//public static final String CONF_XML_PATH = "/resources/config/config.xml";
	//public static final String CONF_PROPS_PATH = "/resources/config/config.properties";
	//public static final String LOG_CONF_PATH = "/resources/config/log4j2.xml";

	//운영서버는 요넘으로
	public static final String CONF_XML_PATH = "/config/config.xml";
	public static final String CONF_PROPS_PATH = "/config/config.properties";
	public static final String LOG_CONF_PATH = "/config/log4j2.xml";

	// 프로세스 ID
	public static final String MARIA_PROCESS_ID = "00MRA00000302";
	public static final String POSTGRES_PROCESS_ID = "00PST00000902";

	// 파일 속성값 구분자 "|"
	public static final String SEPARATOR = "|";



	// json의 시작 태그 문자
	public static final String START_TAG_JSON = "{";
	// xml의 시작 태그 문자
	public static final String START_TAG_XML = "<";

	// db 재연결 시도 주기 (ms)
	public static final int db_reconn_time_ms = 1000 * 60;
	// 프로세스 정상 기동 체크 주기 (ms)
	public static final int prcs_heartbeat_tims_ms = 1000 * 30;

	/**
	 * 시간 포맷별 번호
	 * @author iby
	 *
	 */
	public enum EnumDateFormat {
		YYYY_MM_DD_HH24_MM_SS,
		YYYYMMDDHH24MMSS,
		YYYYMMDD,
		HH24MM,
		HH12MM,
		HH24MMSS,
	}

	/**
	 *
	 * @author iby
	 *
	 */
	public static class OnOff {
		public static String ON = "on";
		public static String OFF = "off";
	}

	/**
	 * 상태코드
	 * @author iby
	 *
	 */
	public static class PROCS_STTUS_CD {
		public static String ON = "001";
		public static String OFF = "002";
	}



	/**
	 * SQL.xml id 정의
	 * @author iby
	 *
	 */
	public static class MariaDB {
		public static String NAMESPACE = "MariaDB";

		public static final String SQLID_MARIA_DB_CONN_CHECK 			= "maria_db_conn_check";

		public static final String SQLID_DB_MARIA_TAG_DIC 				= "db_maria_tag_dic";
		public static final String SQLID_MINUTE_TREND_HISTORY 			= "minute_trend_history";

		public static final String SQLID_MINUTE_TREND_ALL_HISTORY		= "minute_trend_all_history";

		public static final String SQLID_SELECT_CHECKMUSTTAG 			= "select_checkMustTag";

		public static final String SQLID_SELECT_PRCS_ANLY_ABNR_DET  	= "select_prcs_anly_abnr_det";
		public static final String SQLID_SELECT_PRCS_ANLY_FLOW_ADJ      = "select_prcs_anly_flow_adj";
		public static final String SQLID_SELECT_PRCS_ANLY_PAC_INPT      = "select_prcs_anly_pac_inpt";
		public static final String SQLID_SELECT_PRCS_ANLY_STNG_TANK     = "select_prcs_anly_stng_tank";
		public static final String SQLID_SELECT_PRCS_ANLY_BLOW_VOL      = "select_prcs_anly_blow_vol";
		public static final String SQLID_SELECT_PRCS_ANLY_NEW_FCLT      = "select_prcs_anly_new_fclt";
		public static final String SQLID_SELECT_PRCS_ANLY_INFL          = "select_prcs_anly_infl";
		public static final String SQLID_SELECT_PRCS_ANLY_INFL_SECOND   = "select_prcs_anly_infl_second";
		public static final String SQLID_SELECT_PRCS_ANLY_DENIPHO   	= "select_prcs_anly_denipho";

		public static final String SQLID_SELECT_PRCS_ANLY_INFL_PUMP    	= "select_prcs_anly_infl_pump";
		public static final String SQLID_SELECT_PRCS_ANLY_PWR    		= "select_prcs_anly_pwr";
		public static final String SQLID_SELECT_PRCS_ANLY_VBRT    		= "select_prcs_anly_vbrt";

		public static final String SQLID_SELECT_PRCS_ANLY_STNG_INFL 	= "select_prcs_anly_stng_tag_clct";

		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_PUMP 		= "select_optmz_cntr_data_pump";
		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_DENIPHO 	= "select_optmz_cntr_data_denipho";
		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_PSBR 		= "select_optmz_cntr_data_psbr";
		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_PAC 		= "select_optmz_cntr_data_pac";
		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_MLSS		= "select_optmz_cntr_data_mlss";

		public static final String SQLID_UPDATE_OPTMZ_CNTR_DATA 		= "update_optmz_cntr_data";

		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_PUMP 		= "insert_optmz_cntr_data_pump";
		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_PSBR 		= "insert_optmz_cntr_data_psbr";
		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_DENIPHO 	= "insert_optmz_cntr_data_denipho";
		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_PAC 		= "insert_optmz_cntr_data_pac";
		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_MLSS 		= "insert_optmz_cntr_data_mlss";

	}

	public static class PostgresDB {
		public static String NAMESPACE = "PostgresDB";

		public static final String SQLID_PRCS_OPT_RUN					= "prcs_opt_run";

		public static final String SQLID_CHECK_FINAL_DATE 				= "check_final_date";
		public static final String SQLID_POSTGRES_DB_CONN_CHECK 		= "postgres_db_conn_check";
		public static final String SQLID_SYST_MNGM_AGT_STT 				= "insert_syst_mngm_agt_stt";

		public static final String SQLID_INSERT_PRCS_ANLY_ABNR_DET  	= "insert_prcs_anly_abnr_det";
		public static final String SQLID_INSERT_PRCS_ANLY_FLOW_ADJ      = "insert_prcs_anly_flow_adj";
		public static final String SQLID_INSERT_PRCS_ANLY_PAC_INPT      = "insert_prcs_anly_pac_inpt";
		public static final String SQLID_INSERT_PRCS_ANLY_STNG_TANK     = "insert_prcs_anly_stng_tank";
		public static final String SQLID_INSERT_PRCS_ANLY_BLOW_VOL      = "insert_prcs_anly_blow_vol";
		public static final String SQLID_INSERT_PRCS_ANLY_INFL          = "insert_prcs_anly_infl";
		public static final String SQLID_INSERT_PRCS_ANLY_NEW_FCLT      = "insert_prcs_anly_new_fclt";
		public static final String SQLID_INSERT_PRCS_ANLY_INFL_PUMP    	= "insert_prcs_anly_infl_pump";
		public static final String SQLID_INSERT_PRCS_ANLY_PWR    		= "insert_prcs_anly_pwr";
		public static final String SQLID_INSERT_PRCS_ANLY_VBRT    		= "insert_prcs_anly_vbrt";

		public static final String SQLID_INSERT_PRCS_MNTR_TAG_ALRM    	= "insert_prcs_mntr_tag_alrm";
		public static final String SQLID_INSERT_SYST_MNGM_ALRM_HIST 	= "insert_syst_mngm_alrm_hist";
		public static final String SQLID_SCDA_DATA_MINUTE_TREND_HISTORY = "scda_data_minute_trend_history";
		public static final String SQLID_SELECT_CHK_ALRAM_CONDTION 		= "select_chk_alram_condtion";
		public static final String SQLID_PRCS_ANLY_DENIPHO 				= "insert_prcs_anly_denipho";
		public static final String SQLID_INSERT_PRCS_ANLY_STNG_INFL 	= "insert_prcs_anly_stng_tag_clct";

		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_PUMP 	= "insert_optmz_cntr_data_pump";
		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_PSBR 	= "insert_optmz_cntr_data_psbr";
		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_DENIPHO = "insert_optmz_cntr_data_denipho";
		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_PAC 	= "insert_optmz_cntr_data_pac";
		public static final String SQLID_INSERT_OPTMZ_CNTR_DATA_MLSS 	= "insert_optmz_cntr_data_mlss";

		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_PUMP 	= "select_optmz_cntr_data_pump";
		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_DENIPHO = "select_optmz_cntr_data_denipho";
		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_PSBR 	= "select_optmz_cntr_data_psbr";
		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_PAC 	= "select_optmz_cntr_data_pac";
		public static final String SQLID_SELECT_OPTMZ_CNTR_DATA_MLSS	= "select_optmz_cntr_data_mlss";

		public static final String SQLID_UPDATE_OPTMZ_CNTR_DATA 		= "update_optmz_cntr_data";

	}

	public static class WeatherDB {
		public static String NAMESPACE = "Weather";
		public static final String SQLID_MERGE_TH_WTHR_PRCS_STTS 		= "merge_godt_wthr_prcs_stts";
		public static final String SQLID_SELECT_ULTRASRTNCST 			= "select_ultrasrtncst";
		public static final String SQLID_SELECT_ULTRASRTFCST 			= "select_ultrasrtfcst";
		public static final String SQLID_INSERT_SYST_MNGM_AGT_STT 		= "insert_syst_mngm_agt_stt";
		public static final String SQLID_INSERTALL_ULTRASRTNCST 		= "insertall_ultrasrtncst";
		public static final String SQLID_INSERTALL_ULTRASRTFCST 		= "insertall_ultrasrtfcst";
		public static final String SQLID_INSERTALL_PWNCD 				= "insertall_pwncd";
		public static final String SQLID_MERGEALL_PWNCD 				= "mergeall_pwncd";

	}


	/**
	 * API 결과 코드
	 * @author iby
	 *
	 */
	public static class ApiResultCode {
		// 정상
		public static String NORMAL_CODE = "00";
		// 어플리케이션 에러
		public static String APPLICATION_ERROR = "01";
		// 데이터베이스 에러
		public static String DB_ERROR = "02";
		// 데이터없음 에러
		public static String NODATA_ERROR = "03";
		// HTTP 에러
		public static String HTTP_ERROR = "04";
		// 서비스 연결실패 에러
		public static String SERVICETIMEOUT_ERROR = "05";
		// 잘못된 요청 파라메터 에러
		public static String INVALID_REQUEST_PARAMETER_ERROR = "10";
		// 필수요청 파라메터가 없음
		public static String NO_MANDATORY_REQUEST_PARAMETERS_ERROR = "11";
		// 해당 오픈API서비스가 없거나 폐기됨
		public static String NO_OPENAPI_SERVICE_ERROR = "12";
		// 서비스 접근거부
		public static String SERVICE_ACCESS_DENIED_ERROR = "20";
		// 일시적으로 사용할 수 없는 서비스 키
		public static String TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR = "21";
		// 서비스 요청제한횟수 초과에러
		public static String LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR = "22";
		// 등록되지 않은 서비스키
		public static String SERVICE_KEY_IS_NOT_REGISTERED_ERROR = "30";
		// 기한만료된 서비스키
		public static String DEADLINE_HAS_EXPIRED_ERROR = "31";
		// 등록되지 않은 IP
		public static String UNREGISTERED_IP_ERROR = "32";
		// 서명되지 않은 호출
		public static String UNSIGNED_CALL_ERROR = "33";
		// 기타에러
		public static String UNKNOWN_ERROR = "99";

	}

}
