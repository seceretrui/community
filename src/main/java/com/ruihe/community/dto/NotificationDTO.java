package com.ruihe.community.dto;

import com.ruihe.community.model.User;
import lombok.Data;

/**
 * Created by seceretrui 2019/12/19/14:16
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}