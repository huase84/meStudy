package com.meStudy.core.toolkit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/** @author gongym */
@Slf4j
public class QueryGenerator {

  /**
   * 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
   *
   * @param searchObj 查询实体
   * @param parameterMap request.getParameterMap()
   * @return QueryWrapper实例
   */
  public static <T> QueryWrapper<T> initQueryWrapper(
      T searchObj, Map<String, String[]> parameterMap) {
    long start = System.currentTimeMillis();
    QueryWrapper<T> queryWrapper = new QueryWrapper<>();
    // 要求：数据库字段和实体类字段必须严格按照数据库（下划线）--》实体类（驼峰）标准
    // 只支持：字段匹配的查询条件
    // 未支持：按照字段排序
    // 未支持：字段条件为除了等于之外的判断条件
    PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(searchObj);
    for (PropertyDescriptor propertyDescriptor : origDescriptors) {
      String name = propertyDescriptor.getName();
      Object value = null;
      try {
        value = PropertyUtils.getNestedProperty(searchObj, name);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        e.printStackTrace();
      }

      if (null != value) {
        queryWrapper.eq(camelToUnderline(name), value);
      }
    }
    for (String parameter : parameterMap.keySet()) {
      if (null != parameterMap.get(parameter)[0]) {
        String field = camelToUnderline(StringUtils.substringBefore(parameter, "_"));
        String condition = StringUtils.substringAfterLast(parameter, "_");
        switch (condition) {
          case "LIKE":
            // LIKE 条件
            queryWrapper.like(field, parameterMap.get(parameter)[0]);
            break;
          case "NE":
            // NE 条件 -- 不等于
            queryWrapper.ne(field, parameterMap.get(parameter)[0]);
            break;
          case "GT":
            // GT 条件 -- 大于
            queryWrapper.gt(field, parameterMap.get(parameter)[0]);
            break;
          case "GE":
            // GE 条件 -- 大于等于
            queryWrapper.ge(field, parameterMap.get(parameter)[0]);
            break;
          case "LT":
            // LT 条件 -- 小于
            queryWrapper.lt(field, parameterMap.get(parameter)[0]);
            break;
          case "LE":
            // LE 条件 -- 小于等于
            queryWrapper.le(field, parameterMap.get(parameter)[0]);
            break;
          case "ISNULL":
            // ISNULL 条件 -- 为空
            queryWrapper.isNull(field);
            break;
          case "ISNOTNULL":
            // ISNOTNULL 条件 -- 不为空
            queryWrapper.isNotNull(field);
            break;
          case "ASC":
            // ASC 条件 -- asc排序
            queryWrapper.orderByAsc(field);
            break;
          case "DESC":
            // DESC 条件 -- desc排序
            queryWrapper.orderByDesc(field);
            break;
          case "IN":
        	  // LE 条件 -- 小于等于
        	  queryWrapper.in(field, parameterMap.get(parameter));
        	  break;
          default:
            break;
        }
      }
    }
    log.debug("---查询条件构造器初始化完成,耗时:" + (System.currentTimeMillis() - start) + "毫秒----");
    return queryWrapper;
  }

  public static String camelToUnderline(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c)) {
        sb.append("_");
      }
      sb.append(Character.toLowerCase(c));
    }
    return sb.toString();
  }
}
