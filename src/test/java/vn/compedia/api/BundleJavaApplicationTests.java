package vn.compedia.api;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BundleJavaApplicationTests {
//
//	@Test
//	public void contextLoads() {
//	}
//
//}

import vn.compedia.api.util.DateUtil;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

public class BundleJavaApplicationTests {
    public static void main(String[] args) throws Exception {
        LocalDate date = DateUtil.convertDateToLocalDate(new Date()).toLocalDate();
        System.out.println(next(date, 31));
    }

    public static LocalDate next(LocalDate current, int selectedDayOfMonth) {
        return LocalDate.of(2020, 1, selectedDayOfMonth).with(YearMonth.from(current.plusMonths(1)));
    }
}
