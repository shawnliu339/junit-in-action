package com.shearan.junitinaction.codecheck;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Model {

	public LocalDate date;
	public List<WorkingTime> times = new LinkedList<WorkingTime>();
	
	public Model() {
	}
	
	public Model(LocalDate date, List<WorkingTime> times) {
		this.date = date;
		this.times = times;
	}

	@Override
	public String toString() {
		return "Model [date=" + date + ", times=" + times + "]";
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
