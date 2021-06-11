package com.murphy.wx.controller;

import com.murphy.bean.*;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.ExpressService;
import com.murphy.util.DateFormatUtil;
import com.murphy.util.JSONUtil;
import com.murphy.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 微信端快递展示
 *
 * @author murphy
 * @since 2021/6/8 4:59 下午
 */
public class ExpressController {

    /**
     * 根据手机号查询快递信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/findExpressByuPhone.do")
    public String findByuPhone(HttpServletRequest request, HttpServletResponse response) {
        Object wxUser = UserUtil.getWxUser(request.getSession());
        String uPhone = "";
        if (wxUser instanceof User) {
            uPhone = ((User) wxUser).getuPhone();
        } else if (wxUser instanceof Courier) {
            uPhone = ((Courier) wxUser).getcPhone();
        }
        List<Express> list = ExpressService.findByUserPhone(uPhone);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),
                    e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }

        Message msg = new Message();
        if (list.size() == 0){
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
            // 简单处理数据集合 - 遍历集合并过滤 / true - 保留 ｜ false - 去除
            // 记录已取件的快递
            Stream<BootStrapTableExpress> status0Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("待取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);
            });
            // 记录未取件的快递
            Stream<BootStrapTableExpress> status1Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("已取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getOutTime());
                long o2Time = DateFormatUtil.toTime(o2.getOutTime());
                return (int) (o1Time - o2Time);
            });
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            Map data = new HashMap();
            data.put("status0",s0);
            data.put("status1",s1);

            msg.setData(data);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 快递员端扫用户二维码获取快件信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),
                    e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }

        Message msg = new Message();
        if (list.size() == 0) {
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(list2);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 根据订单 查找快递
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/findExpressByNumber.do")
    public String findExpressByNumber(HttpServletRequest request, HttpServletResponse response) {
        String number = request.getParameter("expressNum");
        Express e = ExpressService.findByNumber(number);

        Message msg = new Message();
        if (e == null) {
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");

            HttpSession session = request.getSession();
            session.setAttribute("searchExpress",e);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 取信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/getExpressInfo.do")
    public String getExpressInfo(HttpServletRequest request, HttpServletResponse response) {
        Express e = (Express) request.getSession().getAttribute("searchExpress");
        Message msg = new Message();
        if (e != null) {
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),
                    e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        } else {
            msg.setStatus(-1);
            msg.setResult("未查到相关信息");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
