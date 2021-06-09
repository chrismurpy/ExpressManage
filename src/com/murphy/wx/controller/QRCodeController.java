package com.murphy.wx.controller;

import com.murphy.bean.BootStrapTableExpress;
import com.murphy.bean.Express;
import com.murphy.bean.Message;
import com.murphy.bean.User;
import com.murphy.mvc.ResponseBody;
import com.murphy.mvc.ResponseView;
import com.murphy.service.ExpressService;
import com.murphy.util.DateFormatUtil;
import com.murphy.util.JSONUtil;
import com.murphy.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 二维码
 *
 * @author murphy
 * @since 2021/6/8 9:45 下午
 */
public class QRCodeController {

    /**
     * 展示快递信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseView("/wx/createQRCode.do")
    public String createQrcode(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        // express | user
        String type = request.getParameter("type");
        String userPhone = null;
        String qrCodeContent = null;
        if ("express".equals(type)) {
            // 快递二维码：被扫后，展示单个快递的信息
            // code
            qrCodeContent = "express_" + code;
        } else {
            // 用户二维码：被扫后，柜子端展示用户所有快递
            // userPhone
            User wxUser = UserUtil.getWxUser(request.getSession());
            userPhone = wxUser.getuPhone();
            qrCodeContent = "userPhone_" + userPhone;
        }
        HttpSession session = request.getSession();
        session.setAttribute("qrcode", qrCodeContent);
        return "/personQRcode.html";
    }

    /**
     * 存取二维码信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String qrcode = (String) session.getAttribute("qrcode");
        Message msg = new Message();
        if (qrcode == null) {
            msg.setStatus(-1);
            msg.setResult("取件码获取出错，请用户重新操作");
        } else {
            msg.setStatus(0);
            msg.setResult(qrcode);
        }
        return JSONUtil.toJSON(msg);
    }

    /**
     * 扫码取件操作
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/updateStatus.do")
    public String updateExpressStatus(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        boolean flag = ExpressService.updateStatus(code);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("取件成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("取件码不存在，请用户更新二维码");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 扫码查询单个快递信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        Express e = ExpressService.findByCode(code);
        BootStrapTableExpress e2 = null;
        if (e != null) {
            e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername(),
                    e.getUserPhone(), e.getCompany(), e.getCode(), DateFormatUtil.format(e.getInTime()),
                    e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime()),
                    e.getStatus() == 0 ? "待取件" : "已取件", e.getSysPhone());
        }
        Message msg = new Message();
        if (e == null) {
            msg.setStatus(-1);
            msg.setResult("取件码不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
