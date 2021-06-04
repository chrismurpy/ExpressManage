package com.murphy.controller;

import com.murphy.bean.Message;
import com.murphy.mvc.ResponseBody;
import com.murphy.service.ExpressService;
import com.murphy.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
