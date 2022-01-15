package com.meStudy.core.toolkit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author wultn
 * @Date 16:21 2020/2/25
 * @Param
 * @return
 **/
public class ChartUtils {
	/**
	 * @Title getEveryDayData
	 * @Description 确定每一次运行属于哪一天
	 * @param startTime 起始时间
	 * @param stopTime 结束时间
	 * @param resultList 递归的返回值
	 * @return List<Long>
	 */
	public static List<Long> getEveryDayData(Long startTime, Long stopTime, List<Long> resultList, Long value)
			throws ParseException {
		List<Long> timeList = new ArrayList<Long>();
		for (int i = -6; i <= 1; i++) {
			Calendar instance = Calendar.getInstance();
			instance.add(Calendar.DATE, i);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateFormat = simpleDateFormat.format(instance.getTime());
			Long time = simpleDateFormat.parse(dateFormat).getTime();
			timeList.add(time);
		}
		Integer startDay = -1;
		Integer stopDay = -1;
		for (int i = 0; i <= 6; i++) {
			if (timeList.get(i) < startTime && startTime < timeList.get(i + 1)) {
				startDay = i;
			}
			if (timeList.get(i) < stopTime && stopTime < timeList.get(i + 1)) {
				stopDay = i;
			}
		}
		if (startDay != -1 && stopDay != -1) {
			if ((stopDay - startDay) >= 0) {
				for (int i = startDay; i <= stopDay; i++) {
					resultList.set(i, resultList.get(i) + value);
				}
			} else {
				resultList.set(startDay, value);
			}
		}
		return resultList;
	}
}
