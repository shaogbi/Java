package com.shaogbi.ioc.teststuff;

import com.shaogbi.ioc.annotation.SimpleAutowired;
import com.shaogbi.ioc.annotation.SimpleBean;

@SimpleBean
public class DigitalService {
    @SimpleAutowired
    private BookDao bookDao;
    
    @SimpleAutowired
    private FilmDao filmDao;
    
    @SimpleAutowired
    private SharedDummyObject dummy;
    
    private FilmDao filmDao2;
    
    private String name = "[BookService]";
    
    public void selfreport() {
        System.out.println(name + " depends on several Daos:");
        System.out.println(bookDao.getName() + " and " + filmDao.getName() + " are dependency injected.");
        System.out.println(filmDao2);
    }
}
