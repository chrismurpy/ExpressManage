package com.murphy.wx.controller;

import com.murphy.bean.Message;
import com.murphy.bean.User;
import com.murphy.mvc.ResponseBody;
import com.murphy.mvc.ResponseView;
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

    @ResponseView("/wx/createQRCode.do")
    public String createQrcode(HttpServletRequest request, HttpServletResponse response){
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
        session.setAttribute("qrcode",qrCodeContent);
        return "/personQRcode.html";
    }

    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest request, HttpServletResponse response){
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
}
