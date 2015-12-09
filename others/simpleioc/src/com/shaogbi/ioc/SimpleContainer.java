package com.shaogbi.ioc;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
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
     * key is class name, value is singleton instance.
     */
    private Map<String, Object> beans;
    
    public SimpleContainer() {
        beans = new ConcurrentHashMap<String, Object>();
    }
    
    /**
     * @param clazz, class name
     * @return singleton instance of the specific class name (return null if not exists)
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        String name = clazz.getName();
        Object bean = beans.get(name);
        return Optional.ofNullable((T) bean).orElse(null);
    }
    
    /**
     * @param clazz, class name
     */
    public void registerBean(Class<?> clazz) {
        if(clazz.isAnnotationPresent(SimpleBean.class)) {
            String name = clazz.getName();
            recursiveAssembleBean(name);
        }
    }
    
    /**
     * @param name, class name with full path.
     * @return Object
     */
    private Object recursiveAssembleBean(String name) {
        Object rawBean = ReflectUtil.newInstance(name);
        return recursiveAssembleBean(name, rawBean);
    }
    
    // TODO: how to deal with primitive type like String, Integer
    private Object recursiveAssembleBean(String name, Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(SimpleAutowired.class)) {
                // create or get autowired bean
                String className = field.getType().getCanonicalName();
                Object autowiredObj = beans.getOrDefault(className, recursiveAssembleBean(className));
                
                // set attribute for this field
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    field.set(object, autowiredObj);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                field.setAccessible(accessible);
            }
        }
        beans.put(name, object);
        return object;
    }
}
