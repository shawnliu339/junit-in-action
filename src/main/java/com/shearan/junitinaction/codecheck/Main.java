package com.shearan.junitinaction.codecheck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		List<String> list = new LinkedList<String>();

		while ((line = br.readLine()) != null) {
			if (line.isEmpty()) {
				break;
			}
			list.add(line);
		}

		// リクエストを対応のコントローラーに渡す。
		WorkingOverTimeDetailDTO result = Controller.workingOvertimeDetail(list);
		
		// 返すデータを出力する
		Field[] details = result.getClass().getFields();
		for (Field detail : details) {
			System.out.println(detail.getInt(result));
		}
	}

}
