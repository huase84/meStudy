package com.meStudy.api.login.entity;

import lombok.Data;

/*
 *修改密码
 *
 * @ClassName: EditPassword
 * @author: xth
 * @date: 2022/1/10 19:57
 */
@Data
public class EditPassword {
    private Integer userId;
    private String password;
    private String checkPass;
}
