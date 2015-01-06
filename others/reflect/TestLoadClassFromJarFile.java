package others.reflect;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import java.util.jar.*;

public class TestLoadClassFromJarFile {
  public static void main(String[] args) {
    final String CLASS_SUFFIX = ".class";
    try(
      // JarFile must be closed
      JarFile jarFile = new JarFile("C:\\Users\\shaogbi\\Desktop\\test.jar");  
    ) {
      Enumeration<JarEntry> jarEntries = jarFile.entries();
      while(jarEntries.hasMoreElements()) {
        System.out.println("================================================");
        JarEntry entry = (JarEntry)jarEntries.nextElement();
        String name = entry.getName();
        System.out.printf("Entry/File name is: %s\n", name);
        if(entry.getName().endsWith(CLASS_SUFFIX)) {
          name = name.replaceAll("/", ".").substring(0, name.lastIndexOf("."));
          System.out.printf("Class path is %s\n", name);
          Class<?> klass = Class.forName(name);
          // klass.getMethods() will display all methods include super class's
          Method[] methods = klass.getDeclaredMethods();
          System.out.println("Declared methods:");
          for(Method method: methods) {
            System.out.println("  =========");
            System.out.printf("  Method name is %s.\n", method.getName());
            System.out.printf("  Return type is %s.\n", method.getReturnType().getName());
            Parameter[] parameters = method.getParameters();
            if(parameters.length == 0) {
              System.out.println("  This method has no parameter.");
            } else {
              System.out.println("  Parameters are:");
              for(Parameter parameter: parameters) {
                System.out.printf("  %s\n", parameter.getType().getName());
              }
            }
          }
          // if you know which class the jar contains is, you can get specific instances
          // ex: IOperator operator = (IOperator)klass.newInstance();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
