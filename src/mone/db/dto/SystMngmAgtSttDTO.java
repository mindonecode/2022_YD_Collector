package mone.db.dto;

import lombok.Data;

/**
 * 프로세스 상태 현황
 * @author iby
 *
 */
@Data
public class SystMngmAgtSttDTO {

    private String systMngmAgtSttId;
    private String colctDt;
    private String procsSttusCd;
    private int procsCnt;
    private String procsType;

}



