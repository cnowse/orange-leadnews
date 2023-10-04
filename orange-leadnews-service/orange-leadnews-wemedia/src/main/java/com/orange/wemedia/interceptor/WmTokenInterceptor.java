package com.orange.wemedia.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.orange.model.wemedia.pojo.WmUser;
import com.orange.utils.thread.WmThreadLocalUtil;

public class WmTokenInterceptor implements HandlerInterceptor {

    /**
     * 得到 header 中的用户数据，存入到当前线程中。
     *
     * @author Jeong Geol
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        String userId = request.getHeader("userId");
        if (StringUtils.hasText(userId)) {
            WmUser wmUser = new WmUser();
            wmUser.setId(Integer.valueOf(userId));
            WmThreadLocalUtil.setUser(wmUser);
        }
        return true;
    }

    /**
     * 清理线程中的数据
     *
     * @author Jeong Geol
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler, Exception ex) {

        WmThreadLocalUtil.removeUser();
    }

}
