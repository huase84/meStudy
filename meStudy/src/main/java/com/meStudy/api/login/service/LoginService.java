package com.meStudy.api.login.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meStudy.api.login.entity.EditPassword;
import com.meStudy.api.login.entity.LoginUser;
import com.meStudy.api.user.entity.User;
import com.meStudy.api.user.mapper.UserMapper;
import com.meStudy.core.base.Constants;
import com.meStudy.core.response.ResultMsg;
import com.meStudy.core.toolkit.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    @Resource
    private UserMapper userMapper;

    final HttpServletRequest request;

    public LoginService(HttpServletRequest request) {
        this.request = request;
    }

    public String login(HttpSession session, User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", user.getPhone());
        User login = this.userMapper.selectOne(wrapper);
        if (null == login) {
//            throw new AdviceException(Constants.USER_CREDENTIALS_ERROR, "该用户不存在");
            return ResultMsg.fail("该用户不存在");
        } else {

            if (!MD5Utils.judge(login.getPassword(),user.getPassword())) {
//                throw new AdviceException(Constants.USER_CREDENTIALS_ERROR, "密码错误");
                return ResultMsg.fail("密码错误");
            }
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(login, loginUser);

        // 保存用户当前登录的角色;
        session.setAttribute(Constants.SESSION_KEY, loginUser);
//        LoginUser loginUser2 = (LoginUser) session.getAttribute(Constants.SESSION_KEY);
        return ResultMsg.success("success", loginUser);
    }

    /*
     * 添加密码
     *
     * @return
     * @author xth
     * @date 2022/1/10 19:52
     */
    public String changeCPassword(EditPassword editPassword){
        User user = new User();
        user.setUserId(editPassword.getUserId());
        user.setPassword(MD5Utils.encryptPassword(editPassword.getPassword()));
        userMapper.updateById(user);
        return ResultMsg.success("success");
    }


    /*
     * 修改密码
     *
     * @return
     * @author xth
     * @date 2022/1/10 20:06
     */
    public String changePassword(EditPassword changePassword){
        // 查询当前用户的角色
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(Constants.SESSION_KEY);
        if (null == loginUser) {
            return ResultMsg.fail("获取用户失败");
        }
//        System.out.println(loginUser);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone",loginUser.getPhone());
        User user = this.userMapper.selectOne(userQueryWrapper);
        user.setPassword(MD5Utils.encryptPassword(changePassword.getPassword()));
//        System.out.println(user);
        this.userMapper.updateById(user);
        return ResultMsg.success("success");
    }

    /**
     * 校验旧密码
     * @param changePassword
     * @return
     */
    public String checkOldPassword(EditPassword changePassword) {
        // 查询当前用户的角色
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(Constants.SESSION_KEY);
        if (null == loginUser) {
            return ResultMsg.fail("获取用户信息失败");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone", loginUser.getPhone());
        User user = this.userMapper.selectOne(userQueryWrapper);
        if (null != changePassword.getPassword()){
            if (MD5Utils.judge(user.getPassword(),changePassword.getPassword())){
                return ResultMsg.success("success",true);
            }else {
                return ResultMsg.success("success",false);
            }
        }else {
            return ResultMsg.success("旧密码为空。");
        }
    }
}

