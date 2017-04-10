package com.epam.custom_udf;

public enum Criteria {

    OS("os"),

    DEVICE("device"),

    BROWSER("browser");

    private String param;

    Criteria(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

}
