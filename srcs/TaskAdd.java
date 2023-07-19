import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TaskAdd extends BasePage {
	private static String page_from = "MyTask";
	private JTextField _name_field;
	private JLabel _name_error;
	private JTextField _description_field;
	private JLabel _description_error;

	public TaskAdd(BasePage page) { super(page); }
	public TaskAdd(BaseFrame frame) { super(frame); }
	public TaskAdd(BasePage page, String page_from) {
		super(page);
		TaskAdd.page_from = page_from;
	}

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Create"))
				page = checkField();
			else if (cmd.equals("Back to Task"))
				page = getPreviousPage();
			else
				page = new Error(TaskAdd.this);

			if (page != null)
				TaskAdd.this._frame.changePanel(page.createPage());
		}

		public BasePage getPreviousPage() {
			if (page_from.equals("MyTask"))
				return (new MyTask(TaskAdd.this));
			else if (page_from.equals("ArchivedTask"))
				return (new ArchivedTask(TaskAdd.this));
			else if (page_from.equals("SharedTask"))
				return (new SharedTask(TaskAdd.this));
			return (null);
		}

		public BasePage checkField() {
			String name = _name_field.getText();
			String description = _description_field.getText();
			if (!isValid(name, description))
				return (null);
			addTask(name, description, false);
			return (getPreviousPage());
		}

		public void addTask(String name, String description, boolean is_archive) {
			SQL.insert("insert into task (owner, name, description, is_archive) VALUES(?, ?, ?, ?)", String.valueOf(TaskAdd.this._frame.getLoginId()), name, description, is_archive ? "1" : "0");
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

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.is_menu = true;
		panel.setLayout(null);

		JLabel label = panel.createLabel("TaskAdd", 0.4, 0.1, 0.2, 0.05);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		panel.add(label);

		JLabel name_label = panel.createLabel("Name: ", 0.05, 0.2, 0.15, 0.05);
		this._name_field = panel.createTextField("", 0.2, 0.2, 0.6, 0.05);
		this._name_error = panel.createLabel("",0.2, 0.25, 0.6, 0.05);
		this._name_error.setForeground(Color.RED);
		JLabel description_label = panel.createLabel("description: ", 0.05, 0.3, 0.15, 0.05);
		this._description_field = panel.createTextField("", 0.2, 0.3, 0.6, 0.05);
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

		JButton button2 = panel.createButton("Create", 0.55, 0.5, 0.2, 0.1);
		button2.addActionListener(action);

		panel.add(button);
		panel.add(button2);

		return (panel);
	}
}
