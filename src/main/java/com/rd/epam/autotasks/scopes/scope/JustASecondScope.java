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
public class JustASecondScope implements Scope {
    private final Map<String, Object> scopedObjects
            = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Runnable> destructionCallbacks
            = Collections.synchronizedMap(new HashMap<>());

    private long lastTime = System.currentTimeMillis();
    private long creationTime;
    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        long currentTime = System.currentTimeMillis();
        calculateCreationTime(currentTime);
        long secondInMillis = 1000L;
        if (creationTime >= secondInMillis) {
            scopedObjects.clear();
            creationTime = 0L;
        }
        if(!scopedObjects.containsKey(s)) {
            scopedObjects.put(s, objectFactory.getObject());
        }
        lastTime = System.currentTimeMillis();
        return scopedObjects.get(s);
    }

    private void calculateCreationTime(long currentTime) {
        creationTime += currentTime - lastTime;
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
        return "justASecond";
    }
}
