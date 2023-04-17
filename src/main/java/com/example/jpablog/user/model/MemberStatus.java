package com.example.jpablog.user.model;

public enum MemberStatus {
    None, Using, Stop;

    int value;

    MemberStatus() {

    }

    public int getValue() {
        return this.value;
    }
}
