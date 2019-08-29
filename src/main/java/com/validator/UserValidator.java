package com.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        //判断是否是要校验的类,这里是User
        return Users.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users u = (Users) o;
        if (null == u.getPassword() || "".equals(u.getPassword())){
            //此方法可以加四个参数,第一个表单域field,
            //区分是哪个表单出错,第二个errorCode错误码,
            //第三个制定了资源文件中占位符,第四个具体错误返回信息
            //简写版可以把2,3参数去掉
            errors.rejectValue("password",null,null,"password is null");
        }
    }
}
