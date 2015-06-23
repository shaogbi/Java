package others.wrapper;

import java.util.Collection;
import java.util.Set;

// Wrapper class
public class WrapperSet<E> extends ForwardingSet<E> {
    private int addCount = 0;
    
    public WrapperSet(Set<E> s) {
        super(s);
    }
    
    public int getAddCount() {
        return addCount;
    }
    
    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }
}
