package com.ruihe.community.dto;

import lombok.Data;

/**
 * Created by seceretrui 2019/12/22/15:02
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
    private String tag;
}
