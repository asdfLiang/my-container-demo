package com.lagou.edu.servlet;

import com.lagou.edu.service.TransferService;
import com.lagou.factory.annotation.WebApplicationContextUtil;
import com.lagou.factory.support.AnnotationConfigApplicationContext;

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
        // 转账
        TransferService transferService = WebApplicationContextUtil.getApplicationContext().getBean(TransferService.class);
        transferService.transfer();

        // 响应
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print("{\"status\":\"200\",\"data\": \"do transfer\"}");
    }
}