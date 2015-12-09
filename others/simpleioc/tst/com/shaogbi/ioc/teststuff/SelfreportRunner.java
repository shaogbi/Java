package com.shaogbi.ioc.teststuff;

import com.shaogbi.ioc.annotation.SimpleAutowired;
import com.shaogbi.ioc.annotation.SimpleBean;

@SimpleBean
public class SelfreportRunner {
    @SimpleAutowired
    private DigitalService service;
    
    @SimpleAutowired
    private SharedDummyObject dummy;
    
    public void selfreport() {
        service.selfreport();
    }
}
