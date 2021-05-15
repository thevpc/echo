package net.thevpc.echo.api.peers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntSupplier;

public class PeerAction {
    private String action;
    private Map<String,Object> args=new HashMap<>();

    public PeerAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public PeerAction set(String name, Object value){
        args.put(name,value);
        return this;
    }

    public int getInt(String name){
        return (Integer) getRequired(name);
    }

    public int getInt(String name, IntSupplier whenNotFound){
        if(!args.containsKey(name)){
            return (int) args.get(name);
        }
        return whenNotFound.getAsInt();
    }

    public String getString(String name){
        return (String) getRequired(name);
    }

    public Object get(String name){
        return args.get(name);
    }

    public Object getRequired(String name){
        if(!args.containsKey(name)){
            return args.get(name);
        }
        throw new IllegalArgumentException("required "+name+" not found");
    }
}
