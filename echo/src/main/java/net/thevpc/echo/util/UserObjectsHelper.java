package net.thevpc.echo.util;

public class UserObjectsHelper {
    public static final String CONST(Class clazz, String member){
        return "$CONST/"+clazz.getName()+"::"+member;
    }
}
