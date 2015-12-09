package com.shaogbi.ioc.teststuff;

import com.shaogbi.ioc.annotation.SimpleBean;

@SimpleBean
public class FilmDao {
    private String name = "[FilmDao]";
    private SharedDummyObject dummy = new SharedDummyObject();
    
    public String getName() {
        return name;
    }
    
    public SharedDummyObject getSharedDummyObject() {
        return dummy;
    }
}
