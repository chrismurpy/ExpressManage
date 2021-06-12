package com.murphy.wx.controller;

import com.murphy.bean.*;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.CourierService;
import com.murphy.service.ExpressService;
import com.murphy.service.UserService;
import com.murphy.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
     * 修改个人信息验证码
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/updateSms.do")
    public String sendUpdateSms(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        String code = RandomUtil.getCode() + "";
        boolean flag = SMSLogin.updateSMS(userPhone, code);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("模拟验证码已发送！请查收");
        } else {
            msg.setStatus(-1);
            msg.setResult("模拟验证码发送失败，请检查手机号再试试！");
        }
        UserUtil.setUpdateSms(request.getSession(), userPhone, code);
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
     * 个人中心 - 信息更改
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/editUserInfo.do")
    public String editUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String newName = request.getParameter("newName");
        String password = request.getParameter("password");
        String newPhone = request.getParameter("newPhone");
        String newID = request.getParameter("newID");
        String verify = request.getParameter("verify");

        String sysCode = UserUtil.getUpdateSms(request.getSession(), newPhone);
        Message msg = new Message();
        if (sysCode == null) {
            msg.setStatus(-1);
            msg.setResult("手机号码未获取到短信");
        } else if (sysCode.equals(verify)) {
            Object wxUser = UserUtil.getWxUser(request.getSession());
            if (wxUser instanceof User) {
                User newUser = UserService.findByuPhone(((User) wxUser).getuPhone());
                int id = newUser.getuId();
                newUser.setuName(newName);
                newUser.setPassword(password);
                newUser.setuPhone(newPhone);
                newUser.setIdNumber(newID);
                boolean flag = UserService.update(id, newUser);
                if (flag) {
                    msg.setStatus(0);
                    msg.setResult("修改成功");
                    UserUtil.setWxUser(request.getSession(), newUser);
                } else {
                    msg.setStatus(-2);
                    msg.setResult("修改失败");
                }
            } else if (wxUser instanceof Courier) {
                Courier c = CourierService.findBycPhone(((Courier) wxUser).getcPhone());
                int id = c.getcId();
                c.setcName(newName);
                c.setPassword(password);
                c.setcPhone(newPhone);
                c.setIdNumber(newID);
                boolean flag = CourierService.update(id, c);
                if (flag) {
                    msg.setStatus(0);
                    msg.setResult("修改成功");
                    UserUtil.setWxUser(request.getSession(), c);
                } else {
                    msg.setStatus(-2);
                    msg.setResult("修改失败");
                }
            } else {
                // 验证码不一致
                msg.setStatus(-3);
                msg.setResult("验证码错误，请检查");
            }
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
     * 用户/快递员 信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/findInfo.do")
    public String findInfo(HttpServletRequest request, HttpServletResponse response) {
        Object wxUser = UserUtil.getWxUser(request.getSession());
        boolean isUser = wxUser instanceof User;
        Message msg = new Message();
        if (isUser) {
            msg.setStatus(0);
            msg.setResult("用户信息获取成功");
            msg.setData((User) wxUser);
        } else {
            msg.setStatus(1);
            msg.setResult("快递员信息获取成功");
            msg.setData((Courier) wxUser);
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

    /**
     * 个人中心 - 昵称显示
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/showName.do")
    public String showUsername(HttpServletRequest request, HttpServletResponse response) {
        Object wxUser = UserUtil.getWxUser(request.getSession());
        boolean isUser = wxUser instanceof User;
        Message msg = new Message();
        if (isUser) {
            msg.setStatus(0);
            msg.setResult(((User) wxUser).getuName());
        } else {
            msg.setStatus(1);
            msg.setResult(((Courier) wxUser).getcName());
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 懒人排行榜 总榜
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/lazyBoardTotal.do")
    public String lazyBoardTotal(HttpServletRequest request, HttpServletResponse response){
        List<User> users = UserService.findAll(false,0,0);
        List<LazyBoard> result = new ArrayList<>();

        for (User user : users) {
            List<Express> expressList = ExpressService.findByUserPhone(user.getuPhone());
            LazyBoard la = new LazyBoard(user.getuPhone(), user.getuName(), expressList.size());
            result.add(la);
        }

        Collections.sort(result);
        Message msg = new Message();
        if (!result.isEmpty()) {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(result);
        } else {
            msg.setStatus(-1);
            msg.setResult("查询失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 懒人排行榜 年榜
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/lazyBoardYear.do")
    public String lazyBoardYear(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = UserService.findAll(false,0,0);
        List<LazyBoard> result = new ArrayList<>();
        for (User user : users) {
            List<Express> expressList = ExpressService.findAllAmongYearByPhone(user.getuPhone());
            LazyBoard la = new LazyBoard(user.getuPhone(), user.getuName(), expressList.size());
            result.add(la);
        }

        Collections.sort(result);
        Message msg = new Message();
        if (!result.isEmpty()) {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(result);
        } else {
            msg.setStatus(-1);
            msg.setResult("查询失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 懒人排行榜 月榜
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/lazyBoardMonth.do")
    public String lazyBoardMonth(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = UserService.findAll(false,0,0);
        List<LazyBoard> result = new ArrayList<>();
        for (User user : users) {
            List<Express> expressList = ExpressService.findAllAmongMonthByPhone(user.getuPhone());
            LazyBoard la = new LazyBoard(user.getuPhone(), user.getuName(), expressList.size());
            result.add(la);
        }

        Collections.sort(result);
        Message msg = new Message();
        if (!result.isEmpty()) {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(result);
        } else {
            msg.setStatus(-1);
            msg.setResult("查询失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
