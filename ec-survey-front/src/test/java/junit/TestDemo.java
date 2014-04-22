package junit;

import java.util.Calendar;

import com.ecarinfo.common.utils.DateUtils;

public class TestDemo {

	public static void main(String[] args) {
		 Calendar cal = Calendar.getInstance();
		 System.out.println("今天的日期: " + cal.getTime());

		 int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		 cal.add(Calendar.DATE, -day_of_week);
		 System.out.println("本周第一天: " + cal.getTime());
		 
		 System.err.println(DateUtils.getYear());
	}
}
