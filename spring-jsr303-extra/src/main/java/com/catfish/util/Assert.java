package com.catfish.util;

/**
 * Created by A on 2017/4/12.
 */
public class Assert {

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
