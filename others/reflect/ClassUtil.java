package others.reflect;

import java.lang.reflect.*;
import others.cache.CacheObject;

public class ClassUtil {
  public static void main(String[] args) {
    ClassUtil.printClassInfo(new CacheObject(null));
    ClassUtil.printClassInfo(1);
  }
  public static void printClassInfo(Object obj) {
    Class<? extends Object> c = obj.getClass();
    System.out.println(c.getName());
    // all public methods, including super class's methods
    // c.getDeclaredMethods() only returns self methods
    Method[] methods = c.getMethods();
    for(Method m: methods) {
      Class<?> currentType = m.getReturnType();
      System.out.printf("%s(", currentType.getName());
      Class<?>[] paramTypes = m.getParameterTypes();
      for(Class<?> paramType: paramTypes) {
        System.out.printf("%s,", paramType.getName());
      }
      System.out.println(")");
    }
    // Field[] fields = c.getFields();
    Field[] fields = c.getDeclaredFields();
    for(Field field: fields) {
      Class<?> fieldType = field.getType();
      String typeName = fieldType.getName();
      String fieldName = field.getName();
      System.out.printf("%s %s\n", typeName, fieldName);
    }
  }
}
