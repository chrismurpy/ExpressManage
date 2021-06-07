package com.murphy.controller;

import com.murphy.bean.BootStrapTableCourier;
import com.murphy.bean.Courier;
import com.murphy.bean.Message;
import com.murphy.bean.ResultData;
import com.murphy.exception.DuplicateCodeException;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.CourierService;
import com.murphy.util.DateFormatUtil;
import com.murphy.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Courier 控制器
 *
 * @author murphy
 * @since 2021/6/5 10:19 下午
 */
public class CourierController {
    /**
     * 总用户数统计
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/cConsole.do")
    public String cConsole(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Integer>> list = CourierService.cConsole();
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
    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        // 1. 获取查询数据的起始索引值
        Integer offset = Integer.parseInt(request.getParameter("offset"));
        // 2. 获取当前页要查询的数据量大小
        Integer pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        // 3. 进行分页查询
        List<Courier> list = CourierService.findAll(true, offset, pageNumber);
        List<BootStrapTableCourier> list1 = new ArrayList<>();
        for (Courier c : list) {
            String cinTime = DateFormatUtil.format(c.getCinTime());
            String lastLogin = c.getLastLogin() == null ? "-" : DateFormatUtil.format(c.getLastLogin());
            BootStrapTableCourier c1 = new BootStrapTableCourier(c.getcId(),c.getcName(),c.getcPhone(),c.getIdNumber(),
                    c.getPassword(),c.getcNumber(),cinTime,lastLogin);
            list1.add(c1);
        }
        List<Map<String, Integer>> cConsole = CourierService.cConsole();
        Integer total = cConsole.get(0).get("c1_Total");
        // 4. 将集合封装为 bootstrap-table 识别的格式
        ResultData<BootStrapTableCourier> data = new ResultData<>();
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
    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        String cPhone = request.getParameter("cPhone");
        Courier c = CourierService.findBycPhone(cPhone);
        Message msg = new Message();
        if (c == null) {
            msg.setStatus(-1);
            msg.setResult("快递员信息不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(c);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 快递员信息录入
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        String cName = request.getParameter("cName");
        String cPhone = request.getParameter("cPhone");
        String idNumber = request.getParameter("idNumber");
        String password = request.getParameter("password");

        Courier c = new Courier(cName,cPhone,idNumber,password);
        boolean flag = CourierService.insert(c);
        Message msg = new Message();
        if (flag) {
            // 录入成功
            msg.setStatus(0);
            msg.setResult("快递员信息录入成功！");
        } else {
            // 录入失败
            msg.setStatus(-1);
            msg.setResult("快递员信息录入失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 修改快递员信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        Integer cId = Integer.parseInt(request.getParameter("cId"));
        String cName = request.getParameter("cName");
        String cPhone = request.getParameter("cPhone");
        String idNumber = request.getParameter("idNumber");
        String password = request.getParameter("password");

        Courier newCourier = new Courier();
        newCourier.setcName(cName);
        newCourier.setcPhone(cPhone);
        newCourier.setIdNumber(idNumber);
        newCourier.setPassword(password);

        boolean flag = CourierService.update(cId, newCourier);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("修改成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("修改失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 删除快递员信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        Integer cId = Integer.parseInt(request.getParameter("cId"));
        boolean flag = CourierService.delete(cId);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("删除成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
