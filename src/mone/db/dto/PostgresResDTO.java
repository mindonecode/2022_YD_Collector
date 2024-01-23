package mone.db.dto;

import java.util.List;

import lombok.Data;

/**
 * postgres db
 * @author iby
 *
 */
@Data
public class PostgresResDTO {

	private String  chkTableNm;

	@Data
    public static class prcs_anly_abnr_det {
    	private String  id;
    	private String  vData;
    	private String  vThres1;
    	private String  vThres2;
    	private String  aPtime;
    	private String  updateTime;
    	private String  flag;
	}

	@Data
    public static class prcs_anly_flow_adj {
    	private String  id;
    	private double   lte101aAiLev;
    	private double   lte101bAiLev;
    	private double  fi201AiFl;
    	private double  fq201AiFlsm;
    	private double  lt1001AiLev;
    	private String  preBatFe;
    	private String  preSedFe;
    	private String  updateTime;
    	private String  flag;



   	}

	@Data
    public static class prcs_anly_pac_inpt {
    	private String  prcsAnlyPacInptId;
    	private double  pfi1001AiFl;
    	private double  pfi1001AiPo4p;
    	private double  pfi1001AiPh;
    	private double  pfi1003AiFl;
    	private String  aPtime;
    	private String  updateTime;
    	private String  flag;
    	private double  fi1002AiFl;

    	private double po4p403aAiPo4p;
    	private double po4p403bAiPo4p;


   	}

	@Data
    public static class prcs_anly_stng_tank {
       	private String  updateTime;
    	private String  flag;
    	private double  lt501AiLev;
		private double  fte203aAiFl;
		private double  fte203aAiFlsm;
		private double  dt501AiPh;
		private double  fi805aAiFl;
		private double  fq1003AiFlsm;
		private double  fi1003AiFl;
		private double  fq1003AiFlsm03;
		private double  pei1003AiFl;
		private String preQRw;
		private String preSlGen;
		private String preSlExtrac;
		private double  fi501AlFl;
		private double  fi502AlFl;
		private double  fi501AiFl;
    	private double  fi502AiFl;
    	private double  fi1004AiFl;
    	private double  fi1002AiFl;
    	private double  slt1001AiPh;
   	}

	@Data
    public static class prcs_anly_stng_tag_clct{
		private double  lt101AiLev		;
		private double  lt101AoLevh0s   ;
		private double  lt101AoLevh1s   ;
		private double  lt101AoLevh2s   ;
		private double  lt101AoLevh3s   ;
		private double  lt101AoLevh4s   ;
		private double  lt101AoLevh5s   ;
		private double  lt101AoLevh6s   ;
		private double  lt101AoLevh7s   ;
		private double  lt101AoLevh8s   ;
		private double  lt101AoLevh9s   ;
		private double  lt101AoLevh10s  ;
		private double  lt101AoLevh11s  ;
		private double  lt101AoLevh12s  ;
		private double  lt101AoLevh13s  ;
		private double  lt101AoLevh14s  ;
		private double  lt101AoLevh15s  ;
		private double  lt101AoLevh16s  ;
		private double  lt101AoLevh17s  ;
		private double  m117AoFqh1s     ;
		private double  m117AoFqh2s     ;
		private double  m117AoFqh3s     ;
		private double  m117AoFqh4s     ;
		private double  m117AoFqh5s     ;
		private double  m117AoFqh6s     ;
		private double  m117AoFqh7s     ;
		private double  m117AoFqh8s     ;
		private double  m117AoFqh9s     ;
		private double  m117AoFqh10s    ;
		private double  m117AoFqh11s    ;
		private double  m117AoFqh12s    ;
		private double  m117AoFqh13s    ;
		private double  m117AoFqh14s    ;
		private double  m117AoFqh15s    ;
		private double  m117AoFqh16s    ;
		private double  m117AoFqh17s    ;
		private double  m207aAiZt       ;
		private double  m207aAoSv1s     ;
		private double  m207aAoSv2s     ;
		private double  m207aAoSv3s     ;
		private double  m207aAoSv4s     ;
		private double  m207aAoSv5s     ;
		private double  m207aAoSv6s     ;
		private double  m207aAoSv7s     ;
		private double  m207aAoSv8s     ;
		private double  m207bAiZt       ;
		private double  m207bAoSv1s     ;
		private double  m207bAoSv2s     ;
		private double  m207bAoSv3s     ;
		private double  m207bAoSv4s     ;
		private double  m207bAoSv5s     ;
		private double  m207bAoSv6s     ;
		private double  m207bAoSv7s     ;
		private double  m207bAoSv8s     ;
		private double  m117AoRus01;
		private double  m117AoRus02;
		private double  m117AoRus03;
		private double  m117AoRus04;
		private String  flag;

		private String  updateTime;
		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;

    	private double  m405aAoHzs01;
    	private double  m405aAoHzs02;
    	private double  m405bAoHzs01;
    	private double  m405bAoHzs02;

    	private double  aoPcsc05;

    	private double  m405aDiRu;
    	private double  m405bDiRu;
    	private double  m405cDiRu;
    	private double  aoPcsc03;
    	private double  aoPcs03;
	}
	@Data
    public static class prcs_anly_blow_vol {
    	private String  id;
    	private double  fi1001AiFl;
    	private double  fi402AiFl;
    	private double  fi401aAiFl;
    	private double  fi401bAiFl;
    	private double  fi401cAiFl;
    	private double  fi401dAiFl;
    	private double  do401aAiDo;
    	private double  do401bAiDo;
    	private double  do401cAiDo;
    	private double  do401dAiDo;
    	private double  orp402aAiOrp;
    	private double  orp402bAiOrp;
    	private double  orp402cAiOrp;
    	private double  mlss403aAiMlss;
    	private double  mlss403bAiMlss;
    	private String  updateTime;
    	private String  pFi1001AiFl;
    	private String  pFi402AiFl;
    	private String  flag;

    	private double  do1001AiDo;
    	private double  orp1002AiOrp;
    	private double  mlss1003AiMlss;
    	private double  slt1001AiPh;
    	private double  dt404AiPh;
    	private double  plt101AiLev;
    	private double  m405aAoHzs02;
    	private double  m405bAoHzs02;
	}

	@Data
    public static class prcs_anly_infl {
    	private double fi102AiFl;
		private double fi804AiFl;
		private String rainFall;
		private String snowFall;
		private String  updateTime;
		private double pFi102AiFl;
		private double pFi101AiFl;
		private String  flag;

		private double fi201AiFl;
		private double lt201AiLev;
		private double fte101AiFl;
		private double fi101AiFl;
		private double fi301AiFl;
		private double lt101AiLev;
		private double ftn101AiFl;
		private double ftn102AiFl;
		private double fi601AiFl;
		private double fi701AiFl;
   	}

	@Data
    public static class prcs_anly_infl_pump {
		private double m117aAiW;
		private double m117aAiWh;
		private double m117bAiW;
		private double m117bAiWh;
		private double m117cAiW;
		private double m117cAiWh;
		private double m118AiW;
		private double m118AiWh;
		private double m118Ai;
		private String  flag;
		private String  updateTime;
	}


	@Data
    public static class WeatherApi {
		private String  procsSttusCd;
	}

	@Data
    public static class prcs_mntr_tag_alrm {
		private String  tagTbl;
		private String  tagTblColId;
		private String  tagId;
		private String  alrmDscr;
		private String  updateTime;
		private String  alrmStt;
		private String  alrmSttDscr;
		private String  alrmSttUpdateTime;
		private double  tagVal;
	}

	@Data
    public static class prcs_anly_new_fclt {
		private double  toc301AiToc;
		private double  do1002AiDo;
		private double  nh1002AiNh4;
		private double  no1002AiNo3;
		private double  orp402aAiOrp;
		private double  orp402bAiOrp;
		private double  orp402cAiOrp;
		private double  ss201AiSs;
		private double  ss501AiSs;
		private double  ss1002AiSs;
		private double  slt502aAiSlt;
		private double  slt502bAiSlt;
		private double  slt502cAiSlt;
		private String  updateTime;
		private String  flag;
		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
	}

	@Data
    public static class scdaDataMinuteTrendHistory {

		private String tagId;
		private String tagGroupName;
		private String tagName;
		private String recordDate;
		private String recordTime;
		private double  t00;
		private double  t01;
		private double  t02;
		private double  t03;
		private double  t04;
		private double  t05;
		private double  t06;
		private double  t07;
		private double  t08;
		private double  t09;
		private double  t10;
		private double  t11;
		private double  t12;
		private double  t13;
		private double  t14;
		private double  t15;
		private double  t16;
		private double  t17;
		private double  t18;
		private double  t19;
		private double  t20;
		private double  t21;
		private double  t22;
		private double  t23;
		private double  t24;
		private double  t25;
		private double  t26;
		private double  t27;
		private double  t28;
		private double  t29;
		private double  t30;
		private double  t31;
		private double  t32;
		private double  t33;
		private double  t34;
		private double  t35;
		private double  t36;
		private double  t37;
		private double  t38;
		private double  t39;
		private double  t40;
		private double  t41;
		private double  t42;
		private double  t43;
		private double  t44;
		private double  t45;
		private double  t46;
		private double  t47;
		private double  t48;
		private double  t49;
		private double  t50;
		private double  t51;
		private double  t52;
		private double  t53;
		private double  t54;
		private double  t55;
		private double  t56;
		private double  t57;
		private double  t58;
		private double  t59;
		private double  t00Status;
		private double  t01Status;
		private double  t02Status;
		private double  t03Status;
		private double  t04Status;
		private double  t05Status;
		private double  t06Status;
		private double  t07Status;
		private double  t08Status;
		private double  t09Status;
		private double  t10Status;
		private double  t11Status;
		private double  t12Status;
		private double  t13Status;
		private double  t14Status;
		private double  t15Status;
		private double  t16Status;
		private double  t17Status;
		private double  t18Status;
		private double  t19Status;
		private double  t20Status;
		private double  t21Status;
		private double  t22Status;
		private double  t23Status;
		private double  t24Status;
		private double  t25Status;
		private double  t26Status;
		private double  t27Status;
		private double  t28Status;
		private double  t29Status;
		private double  t30Status;
		private double  t31Status;
		private double  t32Status;
		private double  t33Status;
		private double  t34Status;
		private double  t35Status;
		private double  t36Status;
		private double  t37Status;
		private double  t38Status;
		private double  t39Status;
		private double  t40Status;
		private double  t41Status;
		private double  t42Status;
		private double  t43Status;
		private double  t44Status;
		private double  t45Status;
		private double  t46Status;
		private double  t47Status;
		private double  t48Status;
		private double  t49Status;
		private double  t50Status;
		private double  t51Status;
		private double  t52Status;
		private double  t53Status;
		private double  t54Status;
		private double  t55Status;
		private double  t56Status;
		private double  t57Status;
		private double  t58Status;
		private double  t59Status;
		private double  minVal;
		private String minValTime;
		private double  maxVal;
		private String maxValTime;
		private double  avgVal;
		private double  sumVal;
		private String collectDt;
	}

	@Data
    public static class chk_alram_condtion {
		private String  tableName;
		private String  columnName;
		private String  tagId;
		private double  maxVal;
		private double  minVal;
		private String  alrmDscr;
	}


	@Data
    public static class prcs_anly_pwr {
		private String  flag;

		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;

		 private double m117aAiW;
		 private double m117aAiWh;
		 private double m117aAiPer;
		 private double m117bAiW;
		 private double m117bAiWh;
		 private double m117bAiPer;
		 private double m117cAiW;
		 private double m117cAiWh;
		 private double m117cAiPer;
		 private double m118AiW;
		 private double m118AiWh;
		 private double m118AiPer;
		 private double m405aAiW;
		 private double m405aAiWh;
		 private double m405aAiPer;
		 private double m405bAiW;
		 private double m405bAiWh;
		 private double m405bAiPer;
		 private double m1002aAiW;
		 private double m1002aAiWh;
		 private double m1002aAiPer;
		 private double m1002bAiW;
		 private double m1002bAiWh;
		 private double m1002bAiPer;
		 private double m820aAiW;
		 private double m820aAiWh;
		 private double m820aAiPer;
		 private double m820bAiW;
		 private double m820bAiWh;
		 private double m820bAiPer;
		 private double m805aAiW;
		 private double m805aAiWh;
		 private double m805aAiPer;
		 private double m805bAiW;
		 private double m805bAiWh;
		 private double m805bAiPer;
		 private double m505aAiW;
		 private double m505aAiWh;
		 private double m505aAiPer;
		 private double m505cAiW;
		 private double m505cAiWh;
		 private double m505cAiPer;

		private double m117aAiAr  ;
		private double m117aAiAs  ;
		private double m117aAiAt  ;
		private double m117bAiAr  ;
		private double m117bAiAs  ;
		private double m117bAiAt  ;
		private double m117cAiAr  ;
		private double m117cAiAs  ;
		private double m117cAiAt  ;
		private double m118AiAr   ;
		private double m118AiAs   ;
		private double m118AiAt   ;
		private double m405aAiAr  ;
		private double m405aAiAs  ;
		private double m405aAiAt  ;
		private double m405bAiAr  ;
		private double m405bAiAs  ;
		private double m405bAiAt  ;
		private double m1002aAiAr ;
		private double m1002aAiAs ;
		private double m1002aAiAt ;
		private double m1002bAiAr ;
		private double m1002bAiAs ;
		private double m1002bAiAt ;
		private double m820aAiAr  ;
		private double m820aAiAs  ;
		private double m820aAiAt  ;
		private double m820bAiAr  ;
		private double m820bAiAs  ;
		private double m820bAiAt  ;
		private double m805aAiAr  ;
		private double m805aAiAs  ;
		private double m805aAiAt  ;
		private double m805bAiAr  ;
		private double m805bAiAs  ;
		private double m805bAiAt  ;
		private double m505aAiAr  ;
		private double m505aAiAs  ;
		private double m505aAiAt  ;
		private double m505cAiAr  ;
		private double m505cAiAs  ;
		private double m505cAiAt  ;

		private String  updateTime;

		private double m117aAiVr ;
		private double m117aAiVs ;
		private double m117aAiVt ;
		private double m117bAiVr ;
		private double m117bAiVs ;
		private double m117bAiVt ;
		private double m117cAiVr ;
		private double m117cAiVs ;
		private double m117cAiVt ;
		private double m118AiVr  ;
		private double m118AiVs  ;
		private double m118AiVt  ;
		private double m405aAiVr ;
		private double m405aAiVs ;
		private double m405aAiVt ;
		private double m405bAiVr ;
		private double m405bAiVs ;
		private double m405bAiVt ;
		private double m1002aAiVr;
		private double m1002aAiVs;
		private double m1002aAiVt;
		private double m1002bAiVr;
		private double m1002bAiVs;
		private double m1002bAiVt;
		private double m820aAiVr ;
		private double m820aAiVs ;
		private double m820aAiVt ;
		private double m820bAiVr ;
		private double m820bAiVs ;
		private double m820bAiVt ;
		private double m805aAiVr ;
		private double m805aAiVs ;
		private double m805aAiVt ;
		private double m805bAiVr ;
		private double m805bAiVs ;
		private double m805bAiVt ;
		private double m505aAiVr ;
		private double m505aAiVs ;
		private double m505aAiVt ;

		private double m505bAiAr ;
		private double m505bAiAs ;
		private double m505bAiAt ;

		private double m505cAiVr ;
		private double m505cAiVs ;
		private double m505cAiVt ;

	}

	@Data
    public static class prcs_anly_vbrt {

		private String  flag;
		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
    	private String  updateTime;
		private double  m1002aAiKx;
		private double  m1002bAiKx;
		private double  m1007aAiKx;
		private double  m1007bAiKx;
		private double  m1201aAiKx;
		private double  m1201bAiKx;
		private double  m1207aAiKx;
		private double  m1207bAiKx;
		private double  m405aAiKx;
		private double  m405bAiKx;
		private double  m505aAiKx;
		private double  m505bAiKx;
		private double  m805aAiKx;
		private double  m805bAiKx;
		private double  m820aAiKx;
		private double  m820bAiKx;

		private double  m1002aAiPvx;
		private double  m1002aAiPvy;
		private double  m1002aAiPvz;
		private double  m1002aAiRax;
		private double  m1002aAiRay;
		private double  m1002aAiRaz;
		private double  m1002bAiPvx;
		private double  m1002bAiPvy;
		private double  m1002bAiPvz;
		private double  m1002bAiRax;
		private double  m1002bAiRay;
		private double  m1002bAiRaz;
		private double  m1007aAiPvx;
		private double  m1007aAiPvy;
		private double  m1007aAiPvz;
		private double  m1007aAiRax;
		private double  m1007aAiRay;
		private double  m1007aAiRaz;
		private double  m1007bAiPvx;
		private double  m1007bAiPvy;
		private double  m1007bAiPvz;
		private double  m1007bAiRax;
		private double  m1007bAiRay;
		private double  m1007bAiRaz;
		private double  m1201aAiPvx;
		private double  m1201aAiPvy;
		private double  m1201aAiPvz;
		private double  m1201aAiRax;
		private double  m1201aAiRay;
		private double  m1201aAiRaz;
		private double  m1201bAiPvx;
		private double  m1201bAiPvy;
		private double  m1201bAiPvz;
		private double  m1201bAiRax;
		private double  m1201bAiRay;
		private double  m1201bAiRaz;
		private double  m1207aAiPvx;
		private double  m1207aAiPvy;
		private double  m1207aAiPvz;
		private double  m1207aAiRax;
		private double  m1207aAiRay;
		private double  m1207aAiRaz;
		private double  m1207bAiPvx;
		private double  m1207bAiPvy;
		private double  m1207bAiPvz;
		private double  m1207bAiRax;
		private double  m1207bAiRay;
		private double  m1207bAiRaz;
		private double  m405aAiPvx;
		private double  m405aAiPvy;
		private double  m405aAiPvz;
		private double  m405aAiRax;
		private double  m405aAiRay;
		private double  m405aAiRaz;
		private double  m405bAiPvx;
		private double  m405bAiPvy;
		private double  m405bAiPvz;
		private double  m405bAiRax;
		private double  m405bAiRay;
		private double  m405bAiRaz;
		private double  m505aAiPvx;
		private double  m505aAiPvy;
		private double  m505aAiPvz;
		private double  m505aAiRax;
		private double  m505aAiRay;
		private double  m505aAiRaz;
		private double  m505bAiPvx;
		private double  m505bAiPvy;
		private double  m505bAiPvz;
		private double  m505bAiRax;
		private double  m505bAiRay;
		private double  m505bAiRaz;
		private double  m805aAiPvx;
		private double  m805aAiPvy;
		private double  m805aAiPvz;
		private double  m805aAiRax;
		private double  m805aAiRay;
		private double  m805aAiRaz;
		private double  m805bAiPvx;
		private double  m805bAiPvy;
		private double  m805bAiPvz;
		private double  m805bAiRax;
		private double  m805bAiRay;
		private double  m805bAiRaz;
		private double  m820aAiPvx;
		private double  m820aAiPvy;
		private double  m820aAiPvz;
		private double  m820aAiRax;
		private double  m820aAiRay;
		private double  m820aAiRaz;
		private double  m820bAiPvx;
		private double  m820bAiPvy;
		private double  m820bAiPvz;
		private double  m820bAiRax;
		private double  m820bAiRay;
		private double  m820bAiRaz;



		private String  fcltType;
	}

	@Data
    public static class syst_mngm_alrm_hist {

		private String  alrmLevel;
		private String  category;
		private String  content;
		private String  updateTime;
		private String  confirmTime;
		private String  actionDetails;
		private String  confirmorName;
		private String  actionFlag;
		private String  alarmId;
	}

	@Data
    public static class prcs_anly_new_denipho {
		private double nh4Dnsty;
		private double no3Dnsty;
		private double no3Tmp;
		private double nh4Tmp;

		private String  flag;
		private String  updateTime;

		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
	}

	@Data
    public static class prcs_anly_denipho {

		private double  nh4NCncn;
		private double  no3NCncn;
		private double  nh4NClss;
		private double  no3NClss;
		private String  updateTime;
		private String  flag;

	}


	@Data
    public static class optmz_cntr_data {

		private String 	chkTablenm;
		private String 	recdDt;

		private String 	status;

		 private int  optmzCntrPumpId;
		 private String  pumpFlag;
		 private String  pumpRecdDt;
		 private String  pumpMark;
		 private String  m117aOrdr;
		 private String  m117bOrdr;
		 private String  m117cOrdr;
		 private String  m118Ordr;
		 private String  h5Hz;
		 private String  h4Hz;
		 private String  h3Hz;
		 private String  h2Hz;
		 private String  h1Hz;
		 private String  pumpStatus;

		 private int  optmzCntrDeniphoId;
		 private String  deniphoFlag;
		 private String  deniphoRecdDt;
		 private String  deniphoMark;
		 private String  deniphoFcltKnd;
		 private String  deniphoStngVal;
		 private String  deniphoStatus;

		 private int  optmzCntrMlssId;
		 private String  mlssFlag;
		 private String  mlssRecdDt;
		 private String  mlssMark;
		 private String  mlssOpenStngVal;
		 private String  mlssCloseStngVal;
		 private String  mlssStatus;

		 private int  optmzCntrPacId;
		 private String  pacFlag;
		 private String  pacRecdDt;
		 private String  pacMark;
		 private String  pacFcltKnd;
		 private String  pacStngVal;
		 private String  pacStatus;

		 private int  optmzCntrPsbrId;
		 private String  psbrFlag;
		 private String  psbrRecdDt;
		 private String  psbrMark;
		 private String  m1002aOptmz;
		 private String  m1002bOptmz;
		 private String  pm1009aOptmz;
		 private String  pm1009bOptmz;
		 private String  psbrStatus;
	}




}
