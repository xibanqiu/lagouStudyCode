package com.lagou.servlet;

import com.lagou.pojo.Result;
import com.lagou.service.TransferService;
import com.lagou.utils.JsonUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "transferServlet" , urlPatterns = "/transferServlet")  // 声明servlet
public class TransferServlet extends HttpServlet {

    // 1 实例化 service 对象
//    private TransferService transferService = new TransferServiceImpl();

    private TransferService transferService ;


    @Override
    public void init() throws ServletException {

        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

        transferService = (TransferService)webApplicationContext.getBean("transferService");

    }

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

        // 声明一个 返回结果 处理类
        Result result = new Result();

        try{

            transferService.transfer(fromCardNo,toCardNo,money);
            result.setStatus("200");

        }catch (Exception e){
            e.printStackTrace();
            result.setStatus("500");
            result.setMessage(e.getMessage());

        }

        resp.setContentType("application/json;charset=utf-8");  // 设置返回的 数据类型
        resp.getWriter().print(JsonUtils.object2Json(result));  // 调用工具类 。将返回结果处理类，转成json

    }
}
