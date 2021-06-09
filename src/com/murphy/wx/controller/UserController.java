package com.murphy.wx.controller;

import com.murphy.bean.Courier;
import com.murphy.bean.Message;
import com.murphy.bean.User;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.CourierService;
import com.murphy.util.JSONUtil;
import com.murphy.util.RandomUtil;
import com.murphy.util.SMSLogin;
import com.murphy.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            Courier c = CourierService.findBycPhone(userPhone);
            User user = new User();
            // 快递员表格查询手机号的结果
            if (c != null) {
                // 快递员
                msg.setStatus(1);
                user.setUser(false);
                msg.setResult("登录成功");
            } else {
                // 用户
                msg.setStatus(0);
                user.setUser(true);
                msg.setResult("登录成功");
            }
            user.setuPhone(userPhone);
            UserUtil.setWxUser(request.getSession(), user);
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
        User user = UserUtil.getWxUser(request.getSession());
        boolean isUser = user.isUser();
        Message msg = new Message();
        if (isUser){
            msg.setStatus(0);
        } else {
            msg.setStatus(1);
        }
        msg.setResult(user.getuPhone());
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // 1. 销毁session
        request.getSession().invalidate();
        // 2. 给客户端回复消息
        Message msg = new Message(0);
        return JSONUtil.toJSON(msg);
    }
}
