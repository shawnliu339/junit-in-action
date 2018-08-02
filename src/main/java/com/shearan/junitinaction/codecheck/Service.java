package com.shearan.junitinaction.codecheck;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Service {

	// 深夜残業終了時間
	public LocalTime midnightWorkingStartTime = LocalTime.parse("22:00");
	// 深夜残業終了時間
	public LocalTime midnightWorkingEndTime = LocalTime.parse("05:00");
	// 所定仕事開始時間
	public LocalTime standardStartTime = LocalTime.parse("08:00");
	// 所定仕事終了時間
	public LocalTime standardEndTime = LocalTime.parse("16:00");
	// 所定休憩時間
	public long breakTime = 1;
	// 所定仕事時間
	public long standardWorkingTime = this
			.toHours(Duration.between(this.standardStartTime, this.standardEndTime).toMinutes()) - this.breakTime;

	public static void calculate() { }

	/**
	 * 法定外残業時間数を計算する
	 * 
	 * @param datas
	 * @return
	 */
	public int calculateWorkingOverTimeOutOfLegal(List<Model> datas) {
		int workingOverTime = 0;
		int weekSum = 0;

		for (Model data : datas) {
			int daySum = 0;
			for (WorkingTime workingTime : data.times) {

				long workingDuration = Duration.between(workingTime.startTime, workingTime.endTime).toMinutes();
				workingDuration = this.toHours(workingDuration);
				int dayOfWeek = data.date.getDayOfWeek().getValue();

				// 00:00 <= working time < 05:00
				boolean isNextDay = (workingTime.startTime.isAfter(LocalTime.parse("00:00"))
						|| workingTime.startTime.equals(LocalTime.parse("00:00")))
						&& workingTime.startTime.isBefore(midnightWorkingEndTime);

				if (isNextDay && data.date.plusDays(1).getDayOfWeek().getValue() > 5) {
					continue;
				}

				if ((dayOfWeek >= 1 && dayOfWeek <= 5 && weekSum > 40)
						|| (dayOfWeek >= 1 && dayOfWeek <= 5 && daySum > 8)) {
					// 一週間40時間を超えた、または一日８時間を超えた
					workingOverTime += workingDuration;

				} else if (dayOfWeek >= 1 && dayOfWeek <= 5 && weekSum + workingDuration > 40) {
					// 一部分の仕事時間を足したら、超える
					int gap = 40 - weekSum;
					workingOverTime += workingDuration - gap;

				} else if (dayOfWeek >= 1 && dayOfWeek <= 5 && daySum + workingDuration > 8) {
					// 一部分の仕事時間を足したら、超える
					int gap = 8 - daySum;
					workingOverTime += workingDuration - gap;

				}

				daySum += workingDuration;
				weekSum += workingDuration;
			}
			if (data.date.getDayOfWeek().getValue() == 5) {
				weekSum = 0;
			}
		}
		return workingOverTime;

	}

	/**
	 * 法定内残業時間数を計算する
	 * 
	 * @param datas
	 * @return
	 */
	public int calculateWorkingOverTimeWithinLegal(List<Model> datas) {
		int weekSum = 0;
		int workingOverTime = 0;

		for (Model data : datas) {
			int daySum = 0;
			for (WorkingTime workingTime : data.times) {

				LocalTime workingStart = workingTime.startTime;
				LocalTime workingEnd = workingTime.endTime;
				long workingDuration = Duration.between(workingStart, workingEnd).toMinutes();
				workingDuration = this.toHours(workingDuration);
				int dayOfWeek = data.date.getDayOfWeek().getValue();
				
				// 週４０時間を超えたら、超えた部分を捨てる
				if (dayOfWeek >= 1 && dayOfWeek <= 5 
						&& weekSum + workingDuration > 40) {
					long gap = workingDuration + weekSum - 40;
					workingEnd = workingEnd.minusHours(gap);
					if (workingEnd.isAfter(workingStart) == false) {
						continue;
					}
					workingDuration = Duration.between(workingStart, workingEnd).toMinutes();
					workingDuration = this.toHours(workingDuration);
				}

				// まず、平日か、４０時間を超えるか、かつ８時間を超えるかを検証する
				// そして、残業開始時間を跨ぐかどうかによって、残業時間を計算する
				if (dayOfWeek >= 1 && dayOfWeek <= 5 
						&& weekSum + workingDuration <= 40 && daySum <= 8
						&& workingEnd.isAfter(standardEndTime)) {

					if (workingEnd.isAfter(standardEndTime)
							&& workingStart.isAfter(standardEndTime)) {
						
						workingOverTime += workingDuration;
						
					} 
					//仕事時間は残業開始時間を跨ぐ
					else if (workingEnd.isAfter(standardEndTime)
							&& workingStart.isBefore(standardEndTime)) {
						
						long duration = Duration.between(standardEndTime, workingEnd).toMinutes();
						workingOverTime += this.toHours(duration);
						
					}

					// 法定内残業を超えるなら、超える部分を引く
					long overTime = (daySum + workingDuration) - 8;
					if (overTime > 0) {
						workingOverTime -= overTime;
					}
				}

				daySum += workingDuration;
				weekSum += workingDuration;
			}
			if (data.date.getDayOfWeek().getValue() == 5) {
				weekSum = 0;
			}
		}
		return workingOverTime;
	}

	/**
	 * 深夜残業時間を計算する
	 * 
	 * @param datas
	 * @return
	 */
	public int calculateWrokingOverTimeAtNight(List<Model> datas) {

		int workingOverTime = 0;

		for (Model data : datas) {
			for (WorkingTime workingTime : data.times) {

				LocalTime workingStart = workingTime.startTime;
				LocalTime workingEnd = workingTime.endTime;

				long workingDuration = Duration.between(workingStart, workingEnd).toMinutes();
				workingDuration = this.toHours(workingDuration);

				if (this.isMidnightTo5(workingStart)) {
					workingOverTime += workingDuration;
				}
				else if (this.isMidnightTo5(workingEnd)) {
					workingOverTime += this.toHours(Duration.between(midnightWorkingStartTime, workingEnd).toMinutes());
				}

			}

		}
		return workingOverTime;
	}
	
	/**
	 * 休日労働時間数を計算する
	 * @param datas
	 * @param holidayList 休日リスト
	 * @return
	 */
	public int calculateWorkingInHoliday(List<Model> datas, List<Integer> holidayList) {
		int workingOverTime = 0;

		for (Model data : datas) {
			for (WorkingTime workingTime : data.times) {
				
				int dayOfWeek = data.date.getDayOfWeek().getValue();
				long workingDuration = Duration.between(workingTime.startTime, workingTime.endTime).toMinutes();
				workingDuration = this.toHours(workingDuration);
				
				// 00:00 <= working time < 05:00
				boolean isNextDay = (workingTime.startTime.isAfter(LocalTime.parse("00:00"))
						|| workingTime.startTime.equals(LocalTime.parse("00:00")))
						&& workingTime.startTime.isBefore(midnightWorkingEndTime);
				
				LocalDate nextDay = data.date.plusDays(1);
				int nextDayOfWeek = nextDay.getDayOfWeek().getValue();
				if (!isNextDay && holidayList.contains(dayOfWeek)
						|| isNextDay && holidayList.contains(nextDayOfWeek)) {
					workingOverTime += workingDuration;
				}

			}

		}
		return workingOverTime;
	}

	/**
	 * 所定休日労働時間数を計算する
	 * @param datas
	 * @return
	 */
	public int calculateWorkingInWeekend(List<Model> datas) {
		
		List<Integer> holdayList = new LinkedList<Integer>();
		holdayList.add(6);
		return calculateWorkingInHoliday(datas, holdayList);
	}

	/**
	 * 法定休日労働時間数を計算する
	 * @param datas
	 * @return
	 */
	public int calculateWorkingInHoliday(List<Model> datas) {
		List<Integer> holdayList = new LinkedList<Integer>();
		holdayList.add(7);
		return calculateWorkingInHoliday(datas, holdayList);
	}

	private long toHours(long minute) {
		return Math.round(minute / 60.0);
	}
	
	private boolean isMidnightTo5(LocalTime time) {
		return // time >= 22:00
				time.isAfter(midnightWorkingStartTime)
				|| time.equals(midnightWorkingStartTime)
				// time <= 05:00
				|| time.isBefore(midnightWorkingEndTime)
				|| time.equals(midnightWorkingEndTime);
	}

}
