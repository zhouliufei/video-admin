package com.yefeng.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BgmPageInputDTO extends PageInputDTO {

    private String author;

    private String name;

    private String startTime;

    private String endTime;

}
