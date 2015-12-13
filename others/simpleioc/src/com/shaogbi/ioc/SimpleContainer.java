package com.shaogbi.ioc;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.shaogbi.ioc.annotation.SimpleAutowired;
import com.shaogbi.ioc.annotation.SimpleBean;
import com.shaogbi.ioc.util.ReflectUtil;

/**
 * Usage:<br>
 * SimpleContainer container = new SimpleContainer();<br>
 * container.registerBean(Book.class); // add @SimpleBean annotation to class Book<br>
 * Book book = container.getBean(Book.class);
 */
public class SimpleContainer {
    /**
     * Store bean names which are registered.
     */
    private Set<String> registeredBeans;
    /**
     * Key is class name, value is singleton instance.
     * This map store and cache registered beans, along with their dependencies.
     */
    private Map<String, Object> beans;
    
    public SimpleContainer() {
        registeredBeans = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
        beans = new ConcurrentHashMap<String, Object>();
    }
    
    /**
     * @param clazz, class name
     * @return singleton instance of the specific class name (return null if not exists)
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        String name = clazz.getName();
        if(registeredBeans.contains(name)) {
            return (T) beans.get(name);
        } else {
            return null;
        }
    }
    
    /**
     * @param clazz, class name
     */
    public <T> void registerBean(Class<T> clazz) {
        if(clazz.isInterface()) {
            throw new RuntimeException("Register interface is not allowed!");
        }
        if(!clazz.isAnnotationPresent(SimpleBean.class)) {
            throw new RuntimeException("Registered bean must be with @SimpleBean annotation!");
        }
        String name = clazz.getName();
        if(!registeredBeans.contains(name)) {
            try {
                recursiveAssembleBean(name);
                registeredBeans.add(name);
            } catch (Exception e) {
                System.err.print("Register bean failed: ");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @param name, class name with full path.
     * @return Object
     * @throws Exception 
     */
    private Object recursiveAssembleBean(String name) throws Exception {
        Object rawBean = ReflectUtil.newInstance(name);
        return recursiveAssembleBean(name, rawBean);
    }
    
    // TODO: how to handle primitive type like String, Integer; how to handle beans with name
    private Object recursiveAssembleBean(String name, Object object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(SimpleAutowired.class)) {
                // create, or get autowired bean from cache
                String className = field.getType().getCanonicalName();
                Object autowiredObj = beans.getOrDefault(className, recursiveAssembleBean(className));
                
                // set attribute for this field
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(object, autowiredObj);
                field.setAccessible(accessible);
            }
        }
        beans.put(name, object);
        return object;
    }
}
