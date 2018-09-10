import java.util.Map;
import java.util.PriorityQueue;

import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.ToString;

public class LFUCache {
    @Getter
    @ToString
    private class Entry {
        private Object key;
        private Object val;
        private int accessedTimes;

        public Entry(Object key, Object val) {
            this.key= key;
            this.val = val;
            this.accessedTimes = 1;
        }
    }

    private Map<Object, Entry> entriesMap;
    private PriorityQueue<Entry> entries;
    private final int capacity;

    public LFUCache(int capacity) {
        this.entriesMap = Maps.newHashMap();
        this.entries = new PriorityQueue<Entry>(capacity, (a,b) -> a.getAccessedTimes() - b.getAccessedTimes());
        this.capacity = capacity;
    }

    public Object get(Object key) {
        if (entriesMap.containsKey(key)) {
            Entry entry = entriesMap.get(key);
            entry.accessedTimes++;
            return entry.getVal();
        } else {
            return null;
        }
    }

    public void put(Object key, Object val) {
        if (entriesMap.containsKey(key)) {
            entriesMap.get(key).accessedTimes++;
        } else {
            if (entries.size() == capacity) {
                Entry toBeRemovedeEntry = entries.remove();
                entriesMap.remove(toBeRemovedeEntry.getKey());
            }
            Entry newEntry = new Entry(key, val);
            entriesMap.put(key, newEntry);
            entries.add(newEntry);
            
        }
    }
}
