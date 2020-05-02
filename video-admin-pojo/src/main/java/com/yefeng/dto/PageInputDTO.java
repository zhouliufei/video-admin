package com.yefeng.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageInputDTO {
    @NotNull
    @Min(1)
    private int page;
    @NotNull
    @Min(10)
    private int pageSize;

}
