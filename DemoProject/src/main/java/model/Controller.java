package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Controller {
	private View view;
	private Model model;
	String res = "";

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		this.view.addButtonOneListener(new ButtonOneListener());
		this.view.addButtonTwoListener(new ButtonTwoListener());
		this.view.addButtonThreeListener(new ButtonThreeListener());
		this.view.addButtonFourListener(new ButtonFourListener());
		this.view.addButtonFiveListener(new ButtonFiveListener());
	}

	class ButtonOneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String res = Integer.toString(Model.dayNumber(Model.getMonitoredData()));
			view.getField().setText(res);
		}
	}

	class ButtonTwoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Map<String, Integer> map = Model.activityHowManyTimes(Model.getMonitoredData());
			res = "";
			map.forEach((k, v) -> {
				res += (k + " " + v + "\n");
			});
			view.getField().setText(res);
		}
	}

	class ButtonThreeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Map<Integer, Map<String, Integer>> map = Model.activityEachDayHowManyTimes(Model.getMonitoredData());
			res = "";
			map.forEach((k, v) -> {
				res += (k + " " + v + "\n");
			});
			view.getField().setText(res);
		}
	}

	class ButtonFourListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Map<String, Long> map = Model.activityDuration(Model.getMonitoredData());
			res = "";
			res += map;
			view.getField().setText(res);
		}
	}

	class ButtonFiveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<String> list = Model.activityLessThanFiveMinutes(Model.getMonitoredData());
			res = "";
			res += list;
			view.getField().setText(res);
		}
	}

}
