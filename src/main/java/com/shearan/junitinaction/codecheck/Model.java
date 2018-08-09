package com.shearan.junitinaction.codecheck;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Model {

	public LocalDate date;
	public List<WorkingTime> times = new LinkedList<WorkingTime>();
	private WorkingTime timeInCalculating;
	
	public Model() {
	}

	public WorkingTime getTimeInCalculating() {
		return timeInCalculating;
	}

	/**
	 * 设置当前正在被计算的工作时间
	 * @param timeInCalculating 正在被计算的工作时间
	 */
	public void setTimeInCalculating(WorkingTime timeInCalculating) {
		this.timeInCalculating = timeInCalculating;
	}

	public Model(LocalDate date, List<WorkingTime> times) {
		this.date = date;

		this.times = times;
	}

	@Override
	public String toString() {
		return "Model [date=" + date + ", times=" + times + "]";
	}

	/**
	 * 获取日期对应星期的对应数字
	 * @return
	 */
	public int getDayOfWeekValue() {
		return date.getDayOfWeek().getValue();
	}

	/**
	 * 获取当日的下一天
	 * @return 返回当日下一天的日期对象
	 */
	public LocalDate getNextDay() {
		return date.plusDays(1);
	}


}

class WorkingTime {
	public LocalTime startTime;
	public LocalTime endTime;

	public WorkingTime() {
	}

	public WorkingTime(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "WorkingTime [startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
