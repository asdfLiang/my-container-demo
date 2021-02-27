package com.lagou.edu.servlet;

import com.lagou.edu.service.TransferService;
import com.lagou.edu.spring.factory.annotation.WebApplicationContextUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liangzj
 * @date 2021/2/21 18:25
 */
public class TransferServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String money = req.getParameter("money");

        resp.setContentType("application/json;charset=utf-8");
        try {
            // 转账
            TransferService transferService = WebApplicationContextUtil.getApplicationContext().getBean(TransferService.class);
            transferService.transfer(fromCardNo, toCardNo, Double.valueOf(money));
            resp.getWriter().print("{\"status\":\"200\",\"data\": \"transfer success\"}");
        } catch (Exception e) {
            resp.getWriter().print("{\"status\":\"500\",\"data\": \"transfer error\"}");
        }
    }
}