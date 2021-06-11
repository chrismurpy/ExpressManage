package com.murphy.wx.filter;

import com.murphy.bean.User;
import com.murphy.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 微信端 - 访问权限控制
 * @author murphy
 */
@WebFilter({"/index.html"})
public class Filter implements javax.servlet.Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        Object wxUser = UserUtil.getWxUser(session);
        if (wxUser != null) {
            chain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).sendRedirect("login.html");
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
