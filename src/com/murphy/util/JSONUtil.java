package com.murphy.util;

import com.google.gson.Gson;

/**
 * @author murphy
 */
public class JSONUtil {

    private static Gson g = new Gson();

    public static String toJSON(Object obj){
        return g.toJson(obj);
    }
}
