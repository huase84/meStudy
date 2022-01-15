package com.meStudy.api.login.entity;

import com.meStudy.api.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUser extends User implements Serializable {

}
