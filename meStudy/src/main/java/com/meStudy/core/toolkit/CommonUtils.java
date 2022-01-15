package com.meStudy.core.toolkit;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.io.File;

/**
 * @Author wultn
 * @Date 16:20 2020/2/25
 * @Param
 * @return
 **/
public class CommonUtils {
	public static boolean IsNoneBlank(Object... params) {
		for (Object param : params) {
			if (null == param)
				return false;
			if (param instanceof Integer) {
				if ((int) param < 0)
					return false;
			}
			if (param instanceof String) {
				if (StringUtils.isBlank(String.valueOf(param)))
					return false;
			}
		}
		return true;
	}

	/**
	 * @Author wultn
	 * @Description 据系统环境修改文件目录
	 * @Date 17:25 2020/2/27
	 * @Param [path]
	 * @return java.lang.String
	 **/
	public static String FromSlash(String path) {
		// 这里处理的逻辑是,无论是正斜杠还是反斜杠全部都刚换成File.separator
		String resultPath = path.replace("/", File.separator);
		return resultPath.replace("\\", File.separator);
	}

}
