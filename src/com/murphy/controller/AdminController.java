package com.murphy.controller;

import com.murphy.bean.Message;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.AdminService;
import com.murphy.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author murphy
 */
public class AdminController {

    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        // 1. 接收参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AdminService.login(username,password);

        // 2. 调用Service传参数，并获取结果
        boolean result = AdminService.login(username,password);
        // 3. 根据结果，准备不同的返回数据
        Message msg = null;
        if (result){
            // {status:0, result:"登录成功"}
            msg = new Message(0,"登录成功");
            // 登录时间 和 ip 的更新
            Date date = new Date();
            // 0:0 - 本机IP
            String ip = request.getRemoteAddr();
            AdminService.updateLoginTimeAndIP(username,date,ip);
        } else {
            // {status:-1, result:"登录失败"}
            msg = new Message(1,"登录失败");
        }
        // 4. 将数据转换为JSON
        String json = JSONUtil.toJSON(msg);
        // 5. 将JSON回复给Ajax
        return json;
    }
}
