package com.shaogbi.ioc.teststuff;

import com.shaogbi.ioc.annotation.SimpleBean;

@SimpleBean
public class SharedDummyObject {
    private String name = "[DEFAULT]";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
