package com.shaogbi.ioc.util;

public class ReflectUtil {
    public static Object newInstance(String className) {
        Object obj = null;
        try {
            Class<?> clazz = Class.forName(className);
            obj = clazz.newInstance();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
