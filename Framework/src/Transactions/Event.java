/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transactions;

import java.util.HashMap;

/**
 *
 * @author Equipo1
 */
public class Event {
    private String event;
    private HashMap<String, Object> params;

    public Event(String event) {
        this.event = event;
        this.params = new HashMap();
    }
    
    public void addParam(String key, Object value) {
        params.put(key, value);
    }
    
    public Object getParam(String key) {
        return params.get(key);
    }

    public String getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "Event{" + "event=" + event + ", params=" + params + '}';
    }
    
}
