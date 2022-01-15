package com.meStudy.core.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: ColorGeneratorUtils
 * @Description: 生成颜色工具类（用于关系图3）
 * @author wultn
 * @date 2018年8月28日 下午4:56:37
 */
public class ColorGeneratorUtils {

  private String createColor() {
    //红色
    String red;
    //绿色
    String green;
    //蓝色
    String blue;
    //生成随机对象
    Random random = new Random();
    //生成红色颜色代码
    red = Integer.toHexString(random.nextInt(256)).toUpperCase();
    //生成绿色颜色代码
    green = Integer.toHexString(random.nextInt(256)).toUpperCase();
    //生成蓝色颜色代码
    blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

    //判断红色代码的位数
    red = red.length() == 1 ? "0" + red : red;
    //判断绿色代码的位数
    green = green.length() == 1 ? "0" + green : green;
    //判断蓝色代码的位数
    blue = blue.length() == 1 ? "0" + blue : blue;
    //生成十六进制颜色值
    return "#" + red + green + blue;

  }

  public List<String> chooseColor(int n) {
    List<String> colors = new ArrayList<String>();
    for (int i = 0; i < n; i++) {
      String color = createColor();
      while (colors.contains(color)) {
        color = createColor();
      }
      colors.add(color);
    }
    return colors;
  }

}
