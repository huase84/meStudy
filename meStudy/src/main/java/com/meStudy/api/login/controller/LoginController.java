package com.meStudy.api.login.controller;

import com.meStudy.api.login.entity.EditPassword;
import com.meStudy.api.login.entity.LoginUser;
import com.meStudy.api.login.service.LoginService;
import com.meStudy.api.user.entity.User;
import com.meStudy.core.base.Constants;
import com.meStudy.core.response.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/login/")
/**
 * 用户登陆管理
 * @author xth
 */
public class LoginController {
    @Autowired
    private LoginService loginService;
    /**
     * 用户登录
     */
    @RequestMapping(value = "userLogin",method = RequestMethod.POST)
    public String login(@RequestBody User user, HttpSession session) {
        return this.loginService.login(session, user);
    }

    /**
     * 拉取用户登录信息
     */
    @RequestMapping(value = "getLogin", method = RequestMethod.GET)
    public String getLogin(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(Constants.SESSION_KEY);
        return ResultMsg.success("success",loginUser);
    }
    /**
     * 用户登出
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        // 清除session
        session.removeAttribute(Constants.SESSION_KEY);
        return ResultMsg.success();
    }

    /**
     * 用户修改密码-校验旧密码
     */
    @RequestMapping(value = "checkOldPassword",method = RequestMethod.POST)
    public String checkOldPassword(@RequestBody EditPassword changePassword){
        return loginService.checkOldPassword(changePassword);
    }

    /**
     * 用户修改密码
     */
    @RequestMapping(value = "changePassword",method = RequestMethod.POST)
    public String changePassword(@RequestBody EditPassword changePassword){
        return loginService.changePassword(changePassword);
    }

    /**
     * 设置密码
     * @param editPassword
     * @return
     */
    @RequestMapping(value = "changeCPassword",method = RequestMethod.POST)
    public String changeCPassword(@RequestBody EditPassword editPassword){
        return loginService.changeCPassword(editPassword);
    }

    /**
     * 检查用户登录
     */
    @RequestMapping(value = "checkLogin", method = RequestMethod.GET)
    public String checkLogin(HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(Constants.SESSION_KEY);
        if (null != loginUser) {
            return ResultMsg.success();
        } else {
            return ResultMsg.fail(401, "未登录！");
        }
    }
}
