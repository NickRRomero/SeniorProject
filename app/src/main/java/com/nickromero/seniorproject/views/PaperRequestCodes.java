package com.nickromero.seniorproject.views;

/**
 * Created by nickromero on 2/5/17.
 */

public enum PaperRequestCodes {
    DELETE_PAPER(5), MOVE_TO_SAVED(6);

    private final int val;

    PaperRequestCodes(int val) {this.val = val;}

    public int getVal() {
        return val;
    }
}
