package com.meStudy.core.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BaseEntity {
  /** 创建人 */
  @TableField(fill = FieldFill.INSERT)
  private Long createId;
  /** 创建时间 */
  @TableField(fill = FieldFill.INSERT)
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  /** 更新人 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateId;
  /** 更新时间 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;
  /** 删除状态(0-正常,1-已删除) */
  @TableField(fill = FieldFill.INSERT)
  @TableLogic
  private Integer delFlag;
}
