package com.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zdsoft on 14-7-24.
 */
@Controller
public class UeditorController {

    @RequestMapping("/ueditor")
    public String ueditor(){
        return "ueditor/ueditor";
    }

}
