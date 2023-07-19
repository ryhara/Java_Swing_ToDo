import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.ArrayList;

public class TaskEdit extends BasePage {
	private static String page_from = "MyTask";
	private JTextField _name_field;
	private JLabel _name_error;
	private JTextField _description_field;
	private JLabel _description_error;
	private String _name = "";
	private String _description = "";
	private int _task_id;

	public TaskEdit(BasePage page) { super(page); }
	public TaskEdit(BaseFrame frame) { super(frame); }
	public TaskEdit(BasePage page, String page_from) {
		super(page);
		TaskEdit.page_from = page_from;
	}
	public TaskEdit(BasePage page, int task_id, String page_from) {
		super(page);
		this._task_id = task_id;
		TaskEdit.page_from = page_from;
	}

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Edit"))
				page = checkField();
			else if (cmd.equals("Back to Task"))
				page = getPreviousPage();
			else
				page = new Error(TaskEdit.this);

			if (page != null)
				TaskEdit.this._frame.changePanel(page.createPage());
		}

		public BasePage getPreviousPage() {
			if (page_from.equals("MyTask"))
				return (new MyTask(TaskEdit.this));
			else if (page_from.equals("ArchivedTask"))
				return (new ArchivedTask(TaskEdit.this));
			else if (page_from.equals("SharedTask"))
				return (new SharedTask(TaskEdit.this));
			return (null);
		}

		public BasePage checkField() {
			String name = _name_field.getText();
			String description = _description_field.getText();
			if (!isValid(name, description))
				return (null);
			updateTask(name, description);
			return (getPreviousPage());
		}

		public void updateTask(String name, String description) {
			SQL.update("update task set owner=?, name=?, description=? where id=?", String.valueOf(TaskEdit.this._frame.getLoginId()), name, description, String.valueOf(TaskEdit.this._task_id));
		}

		public boolean isValid(String name, String description) {
			boolean is_valid = true;
			if (name.equals("")) {
				_name_error.setText("Please input task name");
				is_valid = false;
			}
			else
				_name_error.setText("");
			if (description.equals("")) {
				_description_error.setText("Please input description");
				is_valid = false;
			}
			else
				_description_error.setText("");
			return (is_valid);
		}
	}

	public void setBeforeTask() {
		ArrayList<ArrayList<String>> info = SQL.select("select name, description from task where id=?", 2, String.valueOf(this._task_id));
		ArrayList<String> str_list = info.get(0);
		this._name = str_list.get(0);
		this._description = str_list.get(1);
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.is_menu = true;
		panel.setLayout(null);

		setBeforeTask();

		JLabel label = panel.createLabel("TaskEdit", 0.4, 0.1, 0.2, 0.05);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		panel.add(label);

		JLabel name_label = panel.createLabel("Name: ", 0.05, 0.2, 0.15, 0.05);
		this._name_field = panel.createTextField(this._name, 0.2, 0.2, 0.6, 0.05);
		this._name_error = panel.createLabel("",0.2, 0.25, 0.6, 0.05);
		this._name_error.setForeground(Color.RED);
		JLabel description_label = panel.createLabel("description: ", 0.05, 0.3, 0.15, 0.05);
		this._description_field = panel.createTextField(this._description, 0.2, 0.3, 0.6, 0.05);
		this._description_error = panel.createLabel("",0.2, 0.35, 0.6, 0.05);
		this._description_error.setForeground(Color.RED);

		panel.add(name_label);
		panel.add(this._name_field);
		panel.add(this._name_error);
		panel.add(description_label);
		panel.add(this._description_field);
		panel.add(this._description_error);

		Action action = new Action();

		JButton button = panel.createButton("Back to Task", 0.25, 0.5, 0.2, 0.1);
		button.addActionListener(action);

		JButton button2 = panel.createButton("Edit", 0.55, 0.5, 0.2, 0.1);
		button2.addActionListener(action);

		panel.add(button);
		panel.add(button2);

		return (panel);
	}
}
