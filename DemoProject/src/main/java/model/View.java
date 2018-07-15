package model;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * The Class View. Contains a table and several buttons for changing the table
 * content and buttons for different operations like: insert/delete data etc.
 */
public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private Container content;
	private JPanel panel;
	private JPanel panel2;
	private JPanel panel3;
	private JButton btn1 = new JButton("Distinct Days");
	private JButton btn2 = new JButton("Occurrences");
	private JButton btn3 = new JButton("Count for each day");
	private JButton btn4 = new JButton("Total duration");
	private JButton btn5 = new JButton("90% of the monitoring samples");
	JTextArea field=new JTextArea(30,70);
	public View() {
		content = getContentPane();
		content.setLayout((new BorderLayout()));
		panel = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel(new GridLayout(20, 1));

		
		panel3.add(btn1);
		panel3.add(btn2);
		panel3.add(btn3);
		panel3.add(btn4);
		panel3.add(btn5);
		panel2.add(field);
		content.add(panel2, BorderLayout.NORTH);
		content.add(panel3, BorderLayout.CENTER);
		this.setSize(400, 500);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setName("SENSORS");
		this.setVisible(true);
	}
	
	public JTextArea getField() {
		return field;
	}

	public void setField(JTextArea field) {
		this.field = field;
	}


	public void addButtonOneListener(ActionListener mal) {
		btn1.addActionListener(mal);
	}
	
	public void addButtonTwoListener(ActionListener mal) {
		btn2.addActionListener(mal);
	}

	public void addButtonThreeListener(ActionListener mal) {
		btn3.addActionListener(mal);
	}

	public void addButtonFourListener(ActionListener mal) {
		btn4.addActionListener(mal);
	}

	public void addButtonFiveListener(ActionListener mal) {
		btn5.addActionListener(mal);
	}
}
