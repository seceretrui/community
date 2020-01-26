package com.ruihe.community.dto;

import lombok.Data;

/**
 * Created by seceretrui 2020/01/25/19:38
 */
@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
