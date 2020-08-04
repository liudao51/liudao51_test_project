package com.liudao51.test.shiro.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 */
@Configuration
public class ShiroConfig {
    //1. 配置ShiroFilterFactoryBean (或 叫配置Subject)
    //---1)一个subject 对应一个应用(如：用户，客户端程序等)
    //---2)subject与securityManager进行通讯
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager secutiryManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        //设置安全管理器，用于关联安全管理器（即用于把Subject交给安全管理器管理)
        shiroFilter.setSecurityManager(secutiryManager);

        //设置过滤器filter
        /**
         * anon: 无需认证即可访问
         * authc: 需要认证才可访问
         * user: 点击“记住我”功能可访问
         * perms: 拥有权限才可以访问
         * role: 拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/index", "anon");
        filterMap.put("/user/login", "anon");
        filterMap.put("/user/doLogin", "anon");
        filterMap.put("/user/view", "authc");
        filterMap.put("/user/add", "authc,perms[user:add]");
        /*filterMap.put("/user/view", "authc,role[developer]");
        filterMap.put("/user/add", "authc,perms[user:add]");*/

        //放在最后一个，以便匹配没有配置权限的，统一需要登录后查看
        filterMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterMap);


        return shiroFilter;
    }

    //2. 配置securityManager
    //---1)securityManager与Realm进行通讯
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        /*
        //设置缓存
        securityManager.setCacheManager(redisCacheManager());
        //设置session
        securityManager.setSessionManager(sessionManager());
        //设置记住我
        securityManager.setRememberMeManager(rememberMeManager());
        */

        //设置自定义Realm，用于关联自定义Realm(这个方法必须放到最后，不然不会走授权方法)
        securityManager.setRealm(myRealm);

        return securityManager;
    }

    //3. 配置自定义Relam
    //---1)自定义Relam里面包含身份认证、授权等方法实现
    @Bean(name = "myRealm")
    public MyRealm getMyRealm() {
        return new MyRealm();
    }
}
