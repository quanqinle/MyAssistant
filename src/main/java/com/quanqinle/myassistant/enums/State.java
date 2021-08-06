package com.quanqinle.myassistant.enums;

/**
 * @author quanql
 * @version 2021/8/6
 */
public enum State {
    /**
     * 0删除，1有效
     */
    DELETE(0),
    VALID(1);

    private final int state;

    State(int state) {
        this.state = state;
    }

    public int getValue() {
        return state;
    }
}
