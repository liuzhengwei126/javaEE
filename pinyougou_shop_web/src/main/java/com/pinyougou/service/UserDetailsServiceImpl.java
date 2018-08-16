package com.pinyougou.service;

import com.pinyougou.pojo.TbSeller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author ：请叫我伟哥.
 * @Date ：Created in 17:39 2018/8/14 0014
 * @Description：自定义认证类
 * @Modified By：
 * @Version: $version$
 */

public class UserDetailsServiceImpl implements UserDetailsService{

    //通过set方法注入sellerservice 用来查询seller
    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println ("userDetailsSericveImpl经过了");
        //构建一个角色列表  只有一个角色
        Collection <GrantedAuthority> grantAuths = new ArrayList();
        grantAuths.add (new SimpleGrantedAuthority ("ROLE_SELLER"));

        TbSeller seller = sellerService.findOne (username);
        if (null !=seller){ //如果seller存在 则对比密码
            if ("1".equals (seller.getStatus ())){
                return new User (username,seller.getPassword (),grantAuths);
            }else {
                return null;
            }

        }else {
            return null;
        }

    }
}
