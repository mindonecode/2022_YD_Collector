package mone.db.dto;

import lombok.Data;

/**
 * maria db
 * @author iby
 *
 */
@Data
public class MariaResDTO {

	@Data
	public static class hm_minute_trend_history {

		private String 	t				 ;
		private String 	status			 ;
		private String 	tagId			 ;
		private String 	tagGroupName	 ;
		private String 	tagName          ;
		private String 	recordDate       ;
		private String 	recordTime       ;
		private String 	t00              ;
		private String 	t01              ;
		private String 	t02              ;
		private String 	t03              ;
		private String 	t04              ;
		private String 	t05              ;
		private String 	t06              ;
		private String 	t07              ;
		private String 	t08              ;
		private String 	t09              ;
		private String 	t10              ;
		private String 	t11              ;
		private String 	t12              ;
		private String 	t13              ;
		private String 	t14              ;
		private String 	t15              ;
		private String 	t16              ;
		private String 	t17              ;
		private String 	t18              ;
		private String 	t19              ;
		private String 	t20              ;
		private String 	t21              ;
		private String 	t22              ;
		private String 	t23              ;
		private String 	t24              ;
		private String 	t25              ;
		private String 	t26              ;
		private String 	t27              ;
		private String 	t28              ;
		private String 	t29              ;
		private String 	t30              ;
		private String 	t31              ;
		private String 	t32              ;
		private String 	t33              ;
		private String 	t34              ;
		private String 	t35              ;
		private String 	t36              ;
		private String 	t37              ;
		private String 	t38              ;
		private String 	t39              ;
		private String 	t40              ;
		private String 	t41              ;
		private String 	t42              ;
		private String 	t43              ;
		private String 	t44              ;
		private String 	t45              ;
		private String 	t46              ;
		private String 	t47              ;
		private String 	t48              ;
		private String 	t49              ;
		private String 	t50              ;
		private String 	t51              ;
		private String 	t52              ;
		private String 	t53              ;
		private String 	t54              ;
		private String 	t55              ;
		private String 	t56              ;
		private String 	t57              ;
		private String 	t58              ;
		private String 	t59              ;
		private String 	t00Status        ;
		private String 	t01Status        ;
		private String 	t02Status        ;
		private String 	t03Status        ;
		private String 	t04Status        ;
		private String 	t05Status        ;
		private String 	t06Status        ;
		private String 	t07Status        ;
		private String 	t08Status        ;
		private String 	t09Status        ;
		private String 	t10Status        ;
		private String 	t11Status        ;
		private String 	t12Status        ;
		private String 	t13Status        ;
		private String 	t14Status        ;
		private String 	t15Status        ;
		private String 	t16Status        ;
		private String 	t17Status        ;
		private String 	t18Status        ;
		private String 	t19Status        ;
		private String 	t20Status        ;
		private String 	t21Status        ;
		private String 	t22Status        ;
		private String 	t23Status        ;
		private String 	t24Status        ;
		private String 	t25Status        ;
		private String 	t26Status        ;
		private String 	t27Status        ;
		private String 	t28Status        ;
		private String 	t29Status        ;
		private String 	t30Status        ;
		private String 	t31Status        ;
		private String 	t32Status        ;
		private String 	t33Status        ;
		private String 	t34Status        ;
		private String 	t35Status        ;
		private String 	t36Status        ;
		private String 	t37Status        ;
		private String 	t38Status        ;
		private String 	t39Status        ;
		private String 	t40Status        ;
		private String 	t41Status        ;
		private String 	t42Status        ;
		private String 	t43Status        ;
		private String 	t44Status        ;
		private String 	t45Status        ;
		private String 	t46Status        ;
		private String 	t47Status        ;
		private String 	t48Status        ;
		private String 	t49Status        ;
		private String 	t50Status        ;
		private String 	t51Status        ;
		private String 	t52Status        ;
		private String 	t53Status        ;
		private String 	t54Status        ;
		private String 	t55Status        ;
		private String 	t56Status        ;
		private String 	t57Status        ;
		private String 	t58Status        ;
		private String 	t59Status        ;
		private String 	minVal           ;
		private String 	minValTime       ;
		private String 	maxVal           ;
		private String 	maxValTime       ;
		private String 	avgVal           ;
		private String 	sumVal           ;



	}

	@Data
	public static class hm_tag_dic {
		//태그 데이터 코드 정의
	  //private String 	tagId          	 ;
		private String 	nodeId           ;
	  //private String 	tagName        	 ;
		private String 	groupName        ;
		private String 	tagType          ;
		private String 	tagDescription   ;
		private String 	indirectTagYn    ;
		private String 	runTagYn         ;
		private String 	dataType         ;
		private String 	dataLength       ;
		private String 	digitalInitVal   ;
		private String 	digitalOnString  ;
		private String 	digitalOffString ;
		private String 	alarmTagYn       ;
		private String 	analogInitVal    ;
		private String 	analogMinVal     ;
		private String 	analogMaxVal     ;
		private String 	analogDecimalPoint;
		private String 	analogUnit       ;
		private String 	stringInitVal    ;
		private String 	lastStatusStoreYn;
		private String 	hardwareTagYn    ;
		private String 	group            ;
		private String 	name             ;
		private String 	node             ;
		private String 	description      ;
		private String 	minTrendGroup    ;



	}


	@Data
    public static class prcs_anly_abnr_det {
    	private String  id;
    	private String  vData;
    	private String  vThres1;
    	private String  vThres2;
    	private String  aPtime;
    	private String  updateTime;
    	private String  flag;

    	private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
	}

	@Data
    public static class prcs_anly_flow_adj {
    	private  String  id;
    	private  double   lte101aAiLev;
    	private  double   lte101bAiLev;
    	private  double  fi201AiFl;
    	private  double  fq201AiFlsm;
    	private  double  lt1001AiLev;
    	private  String  preBatFe;
    	private  String  preSedFe;
    	private  String  updateTime;
    	private  String  flag;
    	private  String  recordDate;
    	private  String  recordTime;
    	private  String  recordMinute;
    	private double lt101AiLev;

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
    	private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
    	private double  fi1002AiFl;

    	private double po4p403aAiPo4p;
    	private double po4p403bAiPo4p;
    	private double lt101AiLev;

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
		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
    	private double  fi501AiFl;
    	private double  fi502AiFl;
    	private double  fi1002AiFl;
    	private double  fi1004AiFl;
    	private double  slt1001AiPh;
    	private double lt101AiLev;
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
    	private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
    	private double  plt101AiLev;
    	private double  m405aAoHzs02;
    	private double  m405bAoHzs02;
    	private double lt101AiLev;

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
    	private double lt101AiLev;

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
		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
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

		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
    	private double lt101AiLev;
	}



	@Data
    public static class prcs_anly_denipho {
		private double nh4Dnsty;
		private double no3Dnsty;
		private double no3Tmp;
		private double nh4Tmp;

		private String  flag;

		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
    	private double lt101AiLev;
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

		private double m117aAiAr ;
		private double m117aAiAs ;
		private double m117aAiAt ;
		private double m117bAiAr ;
		private double m117bAiAs ;
		private double m117bAiAt ;
		private double m117cAiAr ;
		private double m117cAiAs ;
		private double m117cAiAt ;
		private double m118AiAr  ;
		private double m118AiAs  ;
		private double m118AiAt  ;
		private double m405aAiAr ;
		private double m405aAiAs ;
		private double m405aAiAt ;
		private double m405bAiAr ;
		private double m405bAiAs ;
		private double m405bAiAt ;
		private double m1002aAiAr;
		private double m1002aAiAs;
		private double m1002aAiAt;
		private double m1002bAiAr;
		private double m1002bAiAs;
		private double m1002bAiAt;
		private double m820aAiAr ;
		private double m820aAiAs ;
		private double m820aAiAt ;
		private double m820bAiAr ;
		private double m820bAiAs ;
		private double m820bAiAt ;
		private double m805aAiAr ;
		private double m805aAiAs ;
		private double m805aAiAt ;
		private double m805bAiAr ;
		private double m805bAiAs ;
		private double m805bAiAt ;
		private double m505aAiAr ;
		private double m505aAiAs ;
		private double m505aAiAt ;
		private double m505cAiAr ;
		private double m505cAiAs ;
		private double m505cAiAt ;

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

		private double lt101AiLev;
	}

	@Data
    public static class prcs_anly_vbrt {

		private String  flag;
		private String  recordDate;
    	private String  recordTime;
    	private String  recordMinute;
    	private String  updateTime;
		private double   m1002aAiKx;
		private double   m1002bAiKx;
		private double   m1007aAiKx;
		private double   m1007bAiKx;
		private double   m1201aAiKx;
		private double   m1201bAiKx;
		private double   m1207aAiKx;
		private double   m1207bAiKx;
		private double   m405aAiKx;
		private double   m405bAiKx;
		private double   m505aAiKx;
		private double   m505bAiKx;
		private double   m805aAiKx;
		private double   m805bAiKx;
		private double   m820aAiKx;
		private double   m820bAiKx;

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
		private double lt101AiLev;
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
