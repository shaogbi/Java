package com.shaogbi.ioc.teststuff;

import com.shaogbi.ioc.annotation.SimpleAutowired;
import com.shaogbi.ioc.annotation.SimpleBean;

@SimpleBean
public class BookDao {
    private String name = "[BookDao]";
    
    @SimpleAutowired
    private SharedDummyObject dummy;
    
    public String getName() {
        return name;
    }
}
