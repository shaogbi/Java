package others.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    private final Object delegate;
    
    public MyInvocationHandler(Object obj) {
        this.delegate = obj;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.printf("[Proxy] Will call method: %s\n", method.getName());
        // this will cause definite loop!
        // Object result = method.invoke(proxy, args);
        Object result = method.invoke(delegate, args);
        System.out.println("[Proxy] After method call.");
        return result;
    }
}
