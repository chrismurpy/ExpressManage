package com.murphy.controller;

import com.murphy.bean.BootStrapTableExpress;
import com.murphy.bean.Express;
import com.murphy.bean.Message;
import com.murphy.bean.ResultData;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.ExpressService;
import com.murphy.util.DateFormatUtil;
import com.murphy.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Express 控制器
 *
 * @author murphy
 * @since 2021/6/4 5:08 下午
 */
public class ExpressController {
    @ResponseBody("/express/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Integer>> data = ExpressService.console();
        Message msg = new Message();
        if (data.size() == 0) {
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        // 1. 获取查询数据的起始索引值
        Integer offset = Integer.parseInt(request.getParameter("offset"));
        // 2. 获取当前页要查询的数据量大小
        Integer pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        // 3. 进行分页查询
        List<Express> list = ExpressService.findAll(true, offset, pageNumber);
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
        List<Map<String, Integer>> console = ExpressService.console();
        Integer total = console.get(0).get("data1_Size");
        // 4. 将集合封装为 bootstrap-table 识别的格式
        ResultData<BootStrapTableExpress> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }
}
