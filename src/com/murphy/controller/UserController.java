package com.murphy.controller;

import com.murphy.bean.BootStrapTableUser;
import com.murphy.bean.Message;
import com.murphy.bean.ResultData;
import com.murphy.bean.User;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.UserService;
import com.murphy.util.DateFormatUtil;
import com.murphy.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User 控制器
 *
 * @author murphy
 * @since 2021/6/8 8:07 上午
 */
public class UserController {
    /**
     * 总用户数统计
     * @param request
     * @return
     */
    @ResponseBody("/user/uConsole.do")
    public String uConsole(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Integer>> list = UserService.uConsole();
        Message msg = new Message();
        if (list.size() == 0) {
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
        }
        msg.setData(list);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 分页查询
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        // 1. 获取查询数据的起始索引值
        Integer offset = Integer.parseInt(request.getParameter("offset"));
        // 2. 获取当前页查询的数据量大小
        Integer pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        // 3. 进行分页查询
        List<User> list = UserService.findAll(true, offset, pageNumber);
        List<BootStrapTableUser> list1 = new ArrayList<>();
        for (User u : list) {
            String uinTime = DateFormatUtil.format(u.getUinTime());
            String lastLogin = u.getLastLogin() == null ? "-" : DateFormatUtil.format(u.getLastLogin());
            BootStrapTableUser u1 = new BootStrapTableUser(u.getuId(),u.getuName(),u.getuPhone(),u.getPassword(),
                    u.getIdNumber(),uinTime,lastLogin);
            list1.add(u1);
        }
        List<Map<String, Integer>> uConsole = UserService.uConsole();
        Integer total = uConsole.get(0).get("u1_Total");
        // 4. 将集合封装为 bootstrap-table 识别的格式
        ResultData<BootStrapTableUser> data = new ResultData<>();
        data.setRows(list1);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    /**
     * 根据手机号查找
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        String uPhone = request.getParameter("uPhone");
        User u = UserService.findByuPhone(uPhone);
        Message msg = new Message();
        if (u == null) {
            msg.setStatus(-1);
            msg.setResult("用户信息不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(u);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 用户信息录入
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        String uName = request.getParameter("uName");
        String uPhone = request.getParameter("uPhone");
        String idNumber = request.getParameter("idNumber");
        String password = request.getParameter("password");

        User u = new User(uName,uPhone,password,idNumber);
        boolean flag = UserService.insert(u);
        Message msg = new Message();
        if (flag) {
            // Success
             msg.setStatus(0);
             msg.setResult("用户信息录入成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("用户信息录入失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 修改用户信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        Integer uId = Integer.parseInt(request.getParameter("uId"));
        String uName = request.getParameter("uName");
        String uPhone = request.getParameter("uPhone");
        String password = request.getParameter("password");
        String idNumber = request.getParameter("idNumber");

        User newUser = new User();
        newUser.setuName(uName);
        newUser.setuPhone(uPhone);
        newUser.setPassword(password);
        newUser.setIdNumber(idNumber);

        boolean flag = UserService.update(uId, newUser);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("修改成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 删除用户信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        Integer uId = Integer.parseInt(request.getParameter("uId"));
        boolean flag = UserService.delete(uId);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("删除成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("删除失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
