package com.murphy.controller;

import com.murphy.bean.BootStrapTableCourier;
import com.murphy.bean.Courier;
import com.murphy.bean.Message;
import com.murphy.bean.ResultData;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.CourierService;
import com.murphy.service.ExpressService;
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
}
