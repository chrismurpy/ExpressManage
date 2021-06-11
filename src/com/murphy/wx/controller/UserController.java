package com.murphy.wx.controller;

import com.murphy.bean.Courier;
import com.murphy.bean.Message;
import com.murphy.bean.User;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.CourierService;
import com.murphy.service.UserService;
import com.murphy.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 用户登录
 *
 * @author murphy
 * @since 2021/6/8 11:29 上午
 */
public class UserController {

    /**
     * 模拟短信发送
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/loginSms.do")
    public String sendSms(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        String code = RandomUtil.getCode() + "";
        boolean flag = SMSLogin.loginSMS(userPhone,code);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("模拟验证码已发送！请查收");
        } else {
            msg.setStatus(1);
            msg.setResult("模拟验证码发送失败，请检查手机号再试试！");
        }
        UserUtil.setLoginSms(request.getSession(), userPhone, code);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 微信端用户/快递员登录
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        String userCode = request.getParameter("code");
        String sysCode = UserUtil.getLoginSms(request.getSession(), userPhone);
        Message msg = new Message();
        if (sysCode == null) {
            // 手机号未获取短信
            msg.setStatus(-1);
            msg.setResult("手机号码未获取短信");
        } else if (sysCode.equals(userCode)) {
            // 手机号与验证码一致 / 登录成功
            User u = UserService.findByuPhone(userPhone);
            Courier c = CourierService.findBycPhone(userPhone);
            if (c == null && u == null) {
                // 判断是快递员还是用户，如果都不是则默认注册为用户
                msg.setStatus(0);
                User user = new User();
                user.setuPhone(userPhone);
                UserService.insert(user);
                UserService.updateLoginTime(userPhone);
                UserUtil.setWxUser(request.getSession(), user);
                msg.setResult("注册成功");
            } else if (c != null && u != null) {
                // 判断是快递员还是用户，如果都是则默认登录为快递员
                msg.setStatus(1);
                CourierService.updateLoginTime(userPhone);
                UserUtil.setWxUser(request.getSession(), c);
                msg.setResult("登录成功");
            } else if (c == null && u != null) {
                // 用户登录
                msg.setStatus(0);
                UserService.updateLoginTime(userPhone);
                UserUtil.setWxUser(request.getSession(), u);
                msg.setResult("登录成功");
            } else if (c != null && u == null) {
                // 快递员登录
                msg.setStatus(1);
                CourierService.updateLoginTime(userPhone);
                UserUtil.setWxUser(request.getSession(), c);
                msg.setResult("登录成功");
            }
        } else {
            // 验证码不一致 / 登录失败
            msg.setStatus(-2);
            msg.setResult("验证码不一致，请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 用户/快递员 主页面展示
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response) {
        Object loginUser = UserUtil.getWxUser(request.getSession());
        boolean isUser = loginUser instanceof User;
        Message msg = new Message();
        if (isUser){
            msg.setStatus(0);
            msg.setResult(((User) loginUser).getuPhone());
        } else {
            msg.setStatus(1);
            msg.setResult(((Courier) loginUser).getcPhone());
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // 1. 销毁session
        request.getSession().invalidate();
        // 2. 给客户端回复消息
        Message msg = new Message(0);
        return JSONUtil.toJSON(msg);
    }
}
