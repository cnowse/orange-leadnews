package com.orange.utils.thread;

import com.orange.model.wemedia.pojo.WmUser;

/**
 * 后台登录用户存储
 *
 * @author Jeong Geol
 */
public class WmThreadLocalUtil {

    private WmThreadLocalUtil() {}

    private static final ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(WmUser user) {
        WM_USER_THREAD_LOCAL.set(user);
    }

    public static WmUser getUser() {
        return WM_USER_THREAD_LOCAL.get();
    }

    public static void removeUser() {
        WM_USER_THREAD_LOCAL.remove();
    }

}
