package com.controller;

import com.pojo.User;
import com.service.UserService;
import com.utils.DesUtils;
import com.utils.JwtUtil;
import com.utils.MD5Utils;
import com.validator.Users;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("")
public class UserController {

    Logger logger = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;

    /**
     * 注册页面的controller
     *
     * @return
     */


    /**
     * 转向注册页面
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String register(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "pwd", required = false) String pwd


    ) throws Exception {
        User user = new User();
        DesUtils des = new DesUtils();
        MD5Utils md = new MD5Utils();
        user.setName(name);
        user.setPwd(md.getMD5Code(pwd));
        if (userService.selectByName(name)==null){
            userService.register(user);
            logger.info("注册成功，注册的用户名为："+name+","+"密码为："+pwd);
            return "success";
        }else {
            logger.info("用户名已经存在");
            return "register";
        }
    }

    /**
     * 转向登录页面
     */
    @RequestMapping("/user")
    public String login() {
        return "user";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)

    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "name", required = false) String name ,
                        @RequestParam(value = "pwd", required = false) String pwd,
                        Model model
                        ) throws Exception {
        DesUtils des = new DesUtils();
        MD5Utils md = new MD5Utils();
        User user = userService.loginCheck(name, md.getMD5Code(pwd));
        if (name.isEmpty()) {
            model.addAttribute("error", "用户名不能为空");
            return "user";
        }
        if (pwd.isEmpty()) {
            model.addAttribute("error", "密码不能为空");
            return "user";
        }
        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(24*60*60);
            logger.info("登陆成功");
            String token = JwtUtil.createToken(user.getId());
            logger.info(String.valueOf(user.getId() + System.currentTimeMillis()));
            logger.info("token=" + token);
            Cookie cookie = new Cookie("token",session.getId());
            cookie.setPath("/");
            //有效期单位为秒
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
            //将数据存储到session中
            session.setAttribute("user", user);
            return "home";
        } else {
            logger.info("登陆失败");
            model.addAttribute("error", "请输入正确的用户名和密码");
            return "user";
        }
    }

    /**
     * 用户退出
     */

    @RequestMapping(value = "/outLogin", method = RequestMethod.GET)
    public String outLogin(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    logger.info("开始清理");
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    request.getSession().removeAttribute("user");
                }
            }
        }
        return "user";
    }
}