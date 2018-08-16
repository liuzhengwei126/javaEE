package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ：请叫我伟哥.
 * @Date ：Created in 11:03 2018/8/14 0014
 * @Descriptio{description}
 * @Modified By：
 * @Version: $version$
 */
@RestController
@RequestMapping("/showlogin")
public class LoginController {

    @RequestMapping("/name")
    public Map name(){
        //后面要从数据库中取出的
        String name = SecurityContextHolder.getContext ().getAuthentication ().getName ();
        Map  map = new HashMap();
        map.put ("loginName",name);
        return map;
    }

}
