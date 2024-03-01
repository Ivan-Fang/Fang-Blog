package com.ivanfang.fangblog.utils;

public class ThreadLocalUtil {

    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    public static void set(Object data) {
        THREAD_LOCAL.set(data);
    }

    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
