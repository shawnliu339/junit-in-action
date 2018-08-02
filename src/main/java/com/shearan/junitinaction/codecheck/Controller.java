package com.shearan.junitinaction.codecheck;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

public class Controller {

	public static WorkingOverTimeDetailDTO workingOvertimeDetail(List<String> list) {

		// データ処理
		List<Model> datas = new LinkedList<Model>();
		// 一行目の月に関するデータを捨てる
		list.remove(0);
		
		for (String item : list) {
			Model data = new Model();
			String[] details = item.split(" ");
			data.date = LocalDate.parse(details[0], DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			
			//時間をフォーマット
			for (int i = 1; i < details.length; i++) {
				String[] workingTime = details[i].split("-");
				try {
					WorkingTime time = new WorkingTime(LocalTime.parse(workingTime[0]), LocalTime.parse(workingTime[1]));
					data.times.add(time);
				} catch (DateTimeParseException e) {
					
					// 24時間を超える部分は２つの時間に分ける
					String[] time = workingTime[1].split(":");
					int hour = Integer.parseInt(time[0]) - 24;
					int minute = Integer.parseInt(time[1]);
					WorkingTime newTime1 = new WorkingTime(LocalTime.parse(workingTime[0]), LocalTime.parse("23:59"));
					WorkingTime newTime2 = new WorkingTime(LocalTime.of(0, 0), LocalTime.of(hour, minute));
					data.times.add(newTime1);
					data.times.add(newTime2);
				}
				
			}
			
			datas.add(data);
		}

		Service service = new Service();
		WorkingOverTimeDetailDTO detailDTO = new WorkingOverTimeDetailDTO();
		detailDTO.workingOverTimeWithinLegal = service.calculateWorkingOverTimeWithinLegal(datas);
		detailDTO.workingOverTimeOutOfLegal = service.calculateWorkingOverTimeOutOfLegal(datas);
		detailDTO.wrokingOverTimeAtNight = service.calculateWrokingOverTimeAtNight(datas);
		detailDTO.workingInWeekend = service.calculateWorkingInWeekend(datas);
		detailDTO.workingInHoliday = service.calculateWorkingInHoliday(datas);
		
		return detailDTO;

	}
}
