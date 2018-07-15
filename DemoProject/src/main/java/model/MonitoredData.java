package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MonitoredData {
	private String startTime;
	private String endTime;
	private String activity;

	public MonitoredData(String startTime, String endTime, String activity) {
		super();
		this.startTime = startTime.trim();
		this.endTime = endTime.trim();
		this.activity = activity.trim();
		// System.out.println(this);
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Long getDuration() {
		Date date1;
		Date date2;
		long diff = 0;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
			date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
			diff = date2.getTime() - date1.getTime();
			//System.out.println(date1+" "+date2+" "+TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonitoredData other = (MonitoredData) obj;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MonitoredData [startTime=" + startTime + ", endTime=" + endTime + ", activity=" + activity + "]";
	}

}
