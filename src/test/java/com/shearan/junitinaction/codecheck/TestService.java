package com.shearan.junitinaction.codecheck;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestService {

    private Service service = new Service();

    @Before
    public void setUp() throws Exception {
    }

    // 1.无加班
    private List<Model> getCase1() {
        List<WorkingTime> times1 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("16:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-01"), times1)
        );
    }

    // 2.有加班，但未超过法定时间
    private List<Model> getCase2() {
        List<WorkingTime> times2 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("17:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-01"), times2)
        );
    }

    // 3.有加班，超过法定时间
    private List<Model> getCase3() {
        List<WorkingTime> times3 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("21:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-01"), times3)
        );
    }

    // 4.有加班，迟到，超过法定时间
    private List<Model> getCase4() {
        List<WorkingTime> times4 = Arrays.asList(
                new WorkingTime(LocalTime.parse("10:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("21:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-01"), times4)
        );
    }

    // 5.有加班，超过法定时间，深夜加班
    private List<Model> getCase5() {
        List<WorkingTime> times5 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("23:59")),
                new WorkingTime(LocalTime.parse("00:00"), LocalTime.parse("02:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-01"), times5)
        );
    }

    // 6.周末加班
    private List<Model> getCase6() {
        List<WorkingTime> times6 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("23:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-04"), times6)
        );
    }

    // 7.有加班，跨日，第二日为周末
    private List<Model> getCase7() {
        List<WorkingTime> times7 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("23:59")),
                new WorkingTime(LocalTime.parse("00:00"), LocalTime.parse("02:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-03"), times7)
        );
    }

    // 8.假日加班
    private List<Model> getCase8() {
        List<WorkingTime> times8 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("23:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-05"), times8)
        );
    }

    // 9.周末加班，跨日，第二日为假日
    private List<Model> getCase9() {
        List<WorkingTime> times9 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("23:59")),
                new WorkingTime(LocalTime.parse("00:00"), LocalTime.parse("02:00"))
        );
        return Collections.singletonList(
                new Model(LocalDate.parse("2017-02-04"), times9)
        );
    }

    // 10.多日上班数据，有加班，超过法定时间
    private List<Model> getCase10Input2() {
        // case10 input1
        List<WorkingTime> times101 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("18:00"))
        );
        List<WorkingTime> times102 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("18:00"))
        );
        List<WorkingTime> times103 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("18:00"))
        );
        List<WorkingTime> times104 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("17:00"))
        );
        // case10 input2
        List<WorkingTime> times105 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("21:00"))
        );
        return Arrays.asList(
                new Model(LocalDate.parse("2017-01-16"), times101),
                new Model(LocalDate.parse("2017-01-17"), times102),
                new Model(LocalDate.parse("2017-01-18"), times103),
                new Model(LocalDate.parse("2017-01-19"), times104),
                new Model(LocalDate.parse("2017-01-20"), times105)
        );
    }

    // 10.多日上班数据，有加班，超过法定时间
    private List<Model> getCase10Input1() {
        // case10 input1
        List<WorkingTime> times101 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("18:00"))
        );
        List<WorkingTime> times102 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("18:00"))
        );
        List<WorkingTime> times103 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("18:00"))
        );
        List<WorkingTime> times104 = Arrays.asList(
                new WorkingTime(LocalTime.parse("08:00"), LocalTime.parse("12:00")),
                new WorkingTime(LocalTime.parse("13:00"), LocalTime.parse("17:00"))
        );
        return Arrays.asList(
                new Model(LocalDate.parse("2017-01-16"), times101),
                new Model(LocalDate.parse("2017-01-17"), times102),
                new Model(LocalDate.parse("2017-01-18"), times103),
                new Model(LocalDate.parse("2017-01-19"), times104)
        );
    }

    @Test
    public void testCalculateWorkingOverTimeWithinLegal() {
        int actual = service.calculateWorkingOverTimeWithinLegal(getCase2());
        assertEquals(1, actual);
    }

    @Test
    public void testCalculateWorkingOverTimeWithinLegalHavingLate() {
        int actual = service.calculateWorkingOverTimeWithinLegal(getCase4());
        assertEquals(3, actual);
    }

    @Test
    public void testCalculateWorkingOverTimeOutOfLegal() {
        int actual = service.calculateWorkingOverTimeOutOfLegal(getCase3());
        assertEquals(4, actual);
    }

    @Test
    public void testCalculateWorkingOverTimeOutOfLegalOver40() {
        int actual = service.calculateWorkingOverTimeOutOfLegal(getCase10Input2());
        assertEquals(10, actual);
    }

    @Test
    public void testCalculateWrokingOverTimeAtNight() {
        int actual = service.calculateWrokingOverTimeAtNight(getCase5());
        assertEquals(4, actual);
    }

    @Test
    public void testCalculateWorkingInWeekend() {
        int actual = service.calculateWorkingInWeekend(getCase6());
        assertEquals(14, actual);
    }

    @Test
    public void testCalculateWorkingInWeekendCrossingDay() {
        int actual = service.calculateWorkingInWeekend(getCase7());
        assertEquals(2, actual);
    }

    @Test
    public void testCalculateWorkingInHoliday() {
        int actual = service.calculateWorkingInHoliday(getCase8());
        assertEquals(14, actual);
    }

    @Test
    public void testCalculateWorkingInHolidayCrossingDay() {
        int actual = service.calculateWorkingInHoliday(getCase9());
        assertEquals(2, actual);
    }
}
