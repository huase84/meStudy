package com.meStudy.api.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meStudy.core.response.ResultMsg;
import com.meStudy.core.toolkit.QueryGenerator;
import com.meStudy.api.user.entity.User;
import com.meStudy.api.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/** @author <no value> */
@Slf4j
@RestController
@RequestMapping("/user/")
public class UserController {
  final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("getPage")
  public String getPage(
      User user,
      @RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size,
      HttpServletRequest request) {
    QueryWrapper<User> queryWrapper = QueryGenerator.initQueryWrapper(user, request.getParameterMap());
    Page<User> pageable = new Page<>(page, size);
    IPage<User> pageList = userService.page(pageable, queryWrapper);
    return ResultMsg.success(pageList);
  }

  @RequestMapping(value = "save", method = RequestMethod.POST)
  public String save(@RequestBody User user) {
    return ResultMsg.success(userService.save(user));
  }

  @RequestMapping(value = "edit", method = RequestMethod.POST)
  public String edit(@RequestBody User user) {
    return ResultMsg.success(userService.updateById(user));
  }

  @RequestMapping(value = "get")
  public String get(@RequestParam(name = "id") Long id) {
    return ResultMsg.success(userService.getById(id));
  }

  @RequestMapping(value = "delete", method = RequestMethod.POST)
  public String delete(@RequestBody Long id) {
    return ResultMsg.success(userService.removeById(id));
  }
}
