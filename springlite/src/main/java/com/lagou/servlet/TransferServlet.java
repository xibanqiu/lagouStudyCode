package com.lagou.servlet;

import com.lagou.factory.BeanFactory;
import com.lagou.pojo.Result;
import com.lagou.service.TransferService;
import com.lagou.service.impl.TransferServiceImpl;
import com.lagou.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.VarHandle;


@WebServlet(name = "transferServlet" , urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

    // 实例化 transferService 对象
//    private TransferService transferService = new TransferServiceImpl();

    // 使用工厂  实例化 transferService 对象
    private TransferService transferService = (TransferService) BeanFactory.getBean("transferService");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置请求体的 字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try{

            transferService.transfer(fromCardNo,toCardNo,money);
            result.setStatus("200");

        }catch (Exception e){
            e.printStackTrace();
            result.setStatus("500");
            result.setMessage(e.getMessage());

        }

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));

    }
}
