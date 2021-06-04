package com.murphy.filter;

import com.murphy.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限访问 - 过滤器
 * @author murphy
 */
@WebFilter({"/admin/index.html","/admin/views/*","/express/*"})
public class AccessControlFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String userName = UserUtil.getUserName(request.getSession());
        if (userName != null) {
            chain.doFilter(req, resp);
        } else {
            response.sendError(404,"很遗憾，权限不足");
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
