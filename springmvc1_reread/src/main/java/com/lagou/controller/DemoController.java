package com.lagou.controller;


import com.lagou.pojo.QueryVo;
import com.lagou.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/demo")
public class DemoController {


    @ExceptionHandler(ArithmeticException.class)
    public void handleException(ArithmeticException e,HttpServletResponse response){

        try {
            response.getWriter().write(e.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 直接声明形参ModelMap，封装数据
     * url: http://localhost:8080/demo/handle01
     *
     *
     *  // 下面返回的结果  :================= modelmap:class org.springframework.web.servlet.ModelAndView
     * @return
     */
    @RequestMapping("/handle01")
    public ModelAndView handle01() {
        Date date = new Date();// 服务器时间
        ModelAndView mv = new ModelAndView();

        mv.addObject("date",date);
        System.out.println("=================modelmap:" + mv.getClass());

        mv.setViewName("success");
        return mv;
    }

    /**
     * 直接声明形参ModelMap，封装数据
     * url: http://localhost:8080/demo/handle011
     *
     *
     *  // 下面返回的结果  :================= modelmap:class org.springframework.web.servlet.ModelAndView
     * @return
     */
    @RequestMapping("/handle011")
    public String handle011() {
        Date date = new Date();// 服务器时间
        ModelAndView mv = new ModelAndView();

        mv.addObject("date",date);
        System.out.println("=================modelmap:" + mv.getClass());

        mv.setViewName("success");
        return "success";
    }


    /**
     * 直接声明形参ModelMap，封装数据
     * url: http://localhost:8080/demo/handle02
     *  // 下面返回的结果  :================= modelmap:class org.springframework.validation.support.BindingAwareModelMap
     */
    @RequestMapping("/handle02")
    public String handle02(ModelMap modelMap) {
        Date date = new Date();// 服务器时间
        modelMap.addAttribute("date",date);
        System.out.println("=================modelmap:" + modelMap.getClass());
        return "success";
    }

    /**
     * 直接声明形参ModelMap，封装数据
     * url: http://localhost:8080/demo/handle03
     *  // 下面返回的结果  :================= modelmap:class org.springframework.validation.support.BindingAwareModelMap
     */
    @RequestMapping("/handle03")
    public String handle03(Model model) {
        Date date = new Date();// 服务器时间
        model.addAttribute("date",date);
        System.out.println("=================model:" + model.getClass());
        return "success";
    }


    /**
     * 直接声明形参ModelMap，封装数据
     * url: http://localhost:8080/demo/handle03
     *  // 下面返回的结果  :================= modelmap:class org.springframework.validation.support.BindingAwareModelMap
     */
    @RequestMapping("/handle04")
    public String handle04(Map<String,Object> map) {
        Date date = new Date();// 服务器时间
        map.put("date",date);
        System.out.println("=================map:" + map.getClass());
        return "success";
    }

    /**
     *
     * SpringMVC 对原⽣servlet api的⽀持 url：/demo/handle05?id=1
     *
     * 如果要在SpringMVC中使⽤servlet原⽣对象，⽐如
     HttpServletRequest\HttpServletResponse\HttpSession，直接在Handler⽅法形参中声
     明使⽤即可
     *
     * @return
     */
    @RequestMapping("/handle05")
    public ModelAndView handle05(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession) {

        String id = httpServletRequest.getParameter("id");
        System.out.println(id);
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);

        modelAndView.setViewName("success");
        return modelAndView;
    }


    /*
    * SpringMVC 接收简单数据类型参数 url：/demo/handle06?id=1&flag=1
    *
    * 注意：接收简单数据类型参数，直接在handler⽅法的形参中声明即可，框架会取出参数值
    然后绑定到对应参数上
    * 要求：传递的参数名和声明的形参名称保持⼀致
    */
    @RequestMapping("/handle06")
    public ModelAndView handle06(@RequestParam("id") Integer id,@RequestParam("flag") Boolean
            flag) {
        System.out.println("id = " + id +", flag="+ flag);

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }


    @RequestMapping("/handle07")
    public ModelAndView handle07(User user) {
        System.out.println(user);

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    @RequestMapping("/handle08")
    public ModelAndView handle08(QueryVo queryVo) {
        System.out.println(queryVo);

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * http://localhost:8080/handle09?birthday=2020-10-10
     *
     * @param birthday
     * @return
     */

    @RequestMapping("/handle09")
    public ModelAndView handle09(Date birthday) {

        System.out.println(birthday);

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",birthday);
        modelAndView.setViewName("success");
        return modelAndView;
    }


    /**
     * restful 风格 的get请求
     *
     * @return
     */
    @RequestMapping(value = "/handle10/{id}",method = RequestMethod.GET)
    public ModelAndView handle10(@PathVariable("id")Long id) {

        System.out.println(id);

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * restful 风格 的post  请求
     *
     * @return
     */
    @RequestMapping(value = "/handle11",method = RequestMethod.POST)
    public ModelAndView handle11(String name) {

        System.out.println(name);

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }


    /**
     * restful 风格 的  put请求
     *
     * @return
     */
    @RequestMapping(value = "/handle12/{id}/{name}",method = RequestMethod.PUT)
    public ModelAndView handle12(@PathVariable("id")Long id,@PathVariable("name") String name) {

        System.out.println(id);
        System.out.println(name);

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * restful 风格 的  put请求
     *
     * @return
     */
    @RequestMapping(value = "/handle13/{id}",method = RequestMethod.DELETE)
    public ModelAndView handle13(@PathVariable("id")Long id) {

        System.out.println(id);

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date",date);
        modelAndView.setViewName("success");
        return modelAndView;
    }


    /**
     * Ajax 风格 的  post
     *
     * @return
     */
    @PostMapping(value = "/handle14")
    // 添加    @ResponseBody 之后，不再走 视图接续器那个流程，而是等同于 response 直接输出数据
    @ResponseBody
    public User handle14(@RequestBody User user) {

        System.out.println(user);

        user.setName("张三丰");
        return user;
    }

    /**
     * 文件上传
     *
     * @return
     */
    @PostMapping(value = "/handle15")
    // 添加    @ResponseBody 之后，不再走 视图接续器那个流程，而是等同于 response 直接输出数据
//    @ResponseBody
    public ModelAndView handle15(MultipartFile multipartFile,HttpSession httpSession) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();

        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        String newName = UUID.randomUUID().toString() + "." + ext;

        String realPath = httpSession.getServletContext().getRealPath("/uploads");

        String dataPath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        File folder = new File(realPath + "/" + dataPath);

        if(!folder.exists()){
            folder.mkdirs();
        }

        multipartFile.transferTo(new File(folder,newName));

        //
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("date",date);

        modelAndView.setViewName("success");

        return modelAndView;

    }


    @RequestMapping("/handle16")
    public ModelAndView handle16(){

        int i = 2/0;

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("date",date);

        modelAndView.setViewName("success");

        return modelAndView;

    }


    @RequestMapping("/handle00")
    public ModelAndView handle00(String name){

        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("date",date);

        modelAndView.setViewName("success");

        return modelAndView;

    }
    @RequestMapping("/handleRedirect")
    public String handleRedirect(String name, RedirectAttributes redirectAttributes){

//        return "redirect:handle00?name="+name;   拼接参数的方式，安全性，参数长度，都有局限

        redirectAttributes.addAttribute("name",name);
        return "redirect:handle00";

    }


}
