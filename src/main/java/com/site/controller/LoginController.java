package com.site.controller;


import com.site.util.CheckCode;
import com.site.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zdsoft on 14-7-16.
 */

@Controller
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping("/login")
    public String login(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) throws IOException{
        modelMap.addAttribute("action", "登录Action");
        return "login";
    }


    /**
     * 验证码
     * @param req
     * @param response
     */
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletRequest req, HttpServletResponse response){
        try {
            HttpSession session = req.getSession();
            String str = new CheckCode().getCertPic(0, 0, response.getOutputStream());
            session.setAttribute(Constant.KEY_CHECK_CODE, str);
        } catch (IOException e){
            logger.error("生成验证码失败:" + e.getMessage(), e);
        }

    }


}
