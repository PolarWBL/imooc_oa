package com.ctgu.oa.filiter;

import com.ctgu.oa.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //获取请求路径
        String path = req.getRequestURI();
        System.out.println(path);
        System.out.println(path.contains("/login.html"));
        System.out.println(path.contains("/login_check"));
        //判断请求的 路径中是否包含了 登录页面的请求
        //如果包含了，那么不过滤 继续执行操作
        if (path.contains("/login.html") || path.contains("/login_check")) {
            chain.doFilter(req, resp);
        } else {
            User user = (User) req.getSession().getAttribute("login_user");
            System.out.println(user);
            if (user == null) {
                //注销或未登录
                System.out.println("重定向");
                resp.sendRedirect(req.getContextPath()+"/login.html");
            }else {
                chain.doFilter(req, resp);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
