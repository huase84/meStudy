package com.meStudy.api.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.meStudy.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends BaseEntity { 
  /**  */
  @TableId(type = IdType.AUTO)
  private Integer userId;
  /**  */
  private String name;
  /** 0-男，1-女 */
  private Integer sex;
  /** 手机号 */
  private String phone;
  /** 密码 */
  private String password;
  /** 0-学生，1-老师，2-管理员 */
  private Integer type;
  /** 创建人名称 */
  private String creator;
}
