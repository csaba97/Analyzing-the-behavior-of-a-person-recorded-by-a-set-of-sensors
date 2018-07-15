package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Model {
	private static Date smallestDate = new Date();
	private static List<MonitoredData> monitoredData = null;

	public Model() {
		String fileName = ".\\Activities.txt";
		List<MonitoredData> data = null;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			data = stream.map(line -> line.split("\\s{2,}")).map(p -> new MonitoredData(p[0], p[1], p[2]))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			smallestDate = new SimpleDateFormat("yyyy-MM-dd").parse(data.get(0).getStartTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.monitoredData = data;
		dayNumber(data);
		pelda(data);
		activityHowManyTimes(data);
		activityEachDayHowManyTimes(data);
		activityDuration(data);
		activityLessThanFiveMinutes(data);
	}

	public static List<MonitoredData> getMonitoredData() {
		return monitoredData;
	}

	public static int dayNumber(List<MonitoredData> data) {
		File file = new File(".\\res1.txt");
		file.getParentFile().mkdirs();
		// ****************************
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		List<LocalDate> localDates = data.stream().map(p -> LocalDate.parse(p.getStartTime(), formatter)).distinct()
				.collect(Collectors.toList());
		// ****************************
		try (PrintWriter writer = new PrintWriter(file)) {
			for (LocalDate date : localDates) {
				writer.println(date);
			}
			writer.println(localDates.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return localDates.size();
	}

	public static Map<String, Integer> activityHowManyTimes(List<MonitoredData> data) {
		File file = new File(".\\res2.txt");
		file.getParentFile().mkdirs();
		// ****************************
		Map<String, List<MonitoredData>> map2 = data.stream().collect(Collectors.groupingBy(x -> x.getActivity()));
		Map<String, Integer> map = map2.entrySet().stream()
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue().size()));
		// ****************************
		try (PrintWriter writer = new PrintWriter(file)) {
			map.forEach((k, v) -> {
				writer.println(k + " " + v);
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, Integer> pelda(List<MonitoredData> data) {
		Map<String, Integer> map = data.stream()
				.collect(Collectors.groupingBy(p -> p.getActivity(), Collectors.summingInt(p -> 1)));


		System.out.println(map);
		return map;
	}

	public static Map<Integer, Map<String, Integer>> activityEachDayHowManyTimes(List<MonitoredData> data) {
		File file = new File(".\\res3.txt");
		file.getParentFile().mkdirs();
		// ****************************
		Map<String, Map<String, Integer>> map = data.stream()
				.collect(Collectors.groupingBy(e -> e.getStartTime().split(" ")[0],
						Collectors.groupingBy(MonitoredData::getActivity, Collectors.summingInt(e -> 1))));

		Map<Integer, Map<String, Integer>> map3 = new HashMap<Integer, Map<String, Integer>>();
		map.forEach((key, value) -> {
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(key);
				long diff = date.getTime() - smallestDate.getTime();
				int dif = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				map3.put(dif + 1, value);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		// ****************************
		try (PrintWriter writer = new PrintWriter(file)) {
			map3.forEach((k, v) -> {
				writer.println(k + " " + v);
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// System.out.println(map);
		// System.out.println(map3);
		return map3;
	}

	public static Map<String, Long> activityDuration(List<MonitoredData> data) {
		File file = new File(".\\res4.txt");
		file.getParentFile().mkdirs();
		// ****************************
		Map<String, Long> map = new HashMap<String, Long>();
		Map<String, Long> map2 = new HashMap<String, Long>();

		map = data.stream()
				.collect(Collectors.groupingBy(p -> p.getActivity(), Collectors.summingLong(p -> p.getDuration())));

		map.forEach((key, value) -> {
			map2.put(key, TimeUnit.HOURS.convert(value, TimeUnit.MILLISECONDS));
		});

		map = map2.entrySet().stream().filter(p -> p.getValue() >= 10)
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		// ****************************
		// System.out.println(map);

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static List<String> activityLessThanFiveMinutes(List<MonitoredData> data) {
		File file = new File(".\\res5.txt");
		file.getParentFile().mkdirs();
		// ****************************
		List<String> list = new ArrayList<String>();
		Map<String, Integer> total = activityHowManyTimes(data);

		Map<String, List<MonitoredData>> map2 = data.stream()
				.filter(p -> TimeUnit.MINUTES.convert(p.getDuration(), TimeUnit.MILLISECONDS) < 5)
				.collect(Collectors.groupingBy(x -> x.getActivity()));

		Map<String, Integer> map = map2.entrySet().stream()
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue().size()));

		total.forEach((key, value) -> {
			map.forEach((key2, value2) -> {
				if (key2.equals(key)) {
					if ((float) value2 / value >= 0.9)
						list.add(key);
				}
			});
		});
		// ****************************
		// System.out.println(list);

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(list);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

}
