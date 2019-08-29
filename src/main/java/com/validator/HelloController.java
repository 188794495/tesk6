package com.validator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class HelloController {

    //我们知道在Controller类中通过@InitBinder标记的方法只有在请求当前Controller的时候才会被执行
    //所以在这里注册校验器
    @InitBinder
    public void initBainder(DataBinder binder){
        binder.replaceValidators(new UserValidator());

    }
    //这个方法主要是跳转到登录页面
    @RequestMapping(value = "/logins",method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute(new Users());
        return "logins";
    }
    //处理登录表单
    @RequestMapping(value = "/logins",method = RequestMethod.POST)
    public String login(@Validated Users user, BindingResult br){

        if (br.hasErrors()){
            return "logins";
        }
        return "--";
    }
}
