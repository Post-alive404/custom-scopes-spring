package com.rd.epam.autotasks.scopes.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Denys Parshutkin
 * @version 1.0.0
 */
public class ThreadScope implements Scope {
    private final ThreadLocal<Map<String, Object>> threadScope = new NamedThreadLocal<>("ThreadScope") {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };
    private final Map<String, Runnable> destructionCallbacks
            = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {

        if(!threadScope.get().containsKey(s)) {
            threadScope.get().put(s, objectFactory.getObject());
        }
        return threadScope.get().get(s);
    }

    @Override
    public Object remove(String s) {
        destructionCallbacks.remove(s);
        return threadScope.get().remove(s);
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
        return "thread";
    }
}
