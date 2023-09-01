package com.rd.epam.autotasks.scopes.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Denys Parshutkin
 * @version 1.0.0
 */
public class ThreeTimesScope implements Scope {
    private final Map<String, Object> scopedObjects
            = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Runnable> destructionCallbacks
            = Collections.synchronizedMap(new HashMap<>());
    private int count = 0;
    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        if(count == 3){
            scopedObjects.clear();
            count=0;
        }
        if(!scopedObjects.containsKey(s)) {
            scopedObjects.put(s, objectFactory.getObject());
        }
        count++;
        return scopedObjects.get(s);
    }

    @Override
    public Object remove(String s) {
        destructionCallbacks.remove(s);
        return scopedObjects.remove(s);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "threeTimes";
    }
}
