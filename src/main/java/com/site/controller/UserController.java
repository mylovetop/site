package com.site.controller;


import com.site.service.IUserInfoService;
import com.site.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 14-5-2.
 */
@Controller
public class UserController {

    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("/findUserInfo")
    public String findUserInfo(ModelMap model){
        UserInfoVO userInfoVO = userInfoService.findUserInfoById(1);
        model.addAttribute(userInfoVO);
        return "userInfo";
    }

    public IUserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

}
