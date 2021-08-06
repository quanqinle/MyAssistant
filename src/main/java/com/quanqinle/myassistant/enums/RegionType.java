package com.quanqinle.myassistant.enums;

/**
 * 地区类型
 *
 * @author quanql
 * @version 2021/8/5
 */
public enum RegionType {
    /**
     * 类型：1省 2市 3区县
     */
    PROVINCE(1),
    CITY(2),
    COUNTY(3);

    private final int type;

    RegionType(int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }
}
