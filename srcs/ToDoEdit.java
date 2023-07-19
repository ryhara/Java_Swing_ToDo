import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class ToDoEdit extends BasePage {
	private int _task_id;
	private int _todo_id;
	private String _title;
	private String _deadline;
	private int _priority;
	private JTextField _title_field;
	private JLabel _title_error;
	private JSpinner _deadline_field;
	private JLabel _deadline_error;
	private ButtonGroup _bgroup;

	public ToDoEdit(BasePage page) { super(page); }
	public ToDoEdit(BaseFrame frame) { super(frame); }
	public ToDoEdit(BasePage page, int task_id, int todo_id) {
		super(page);
		this._task_id = task_id;
		this._todo_id = todo_id;
	}

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Complete Editing"))
				page = checkField();
			else if (cmd.equals("Back to ToDo"))
				page = new ToDo(ToDoEdit.this, _task_id);
			else
				page = new Error(ToDoEdit.this);

			if (page != null)
				ToDoEdit.this._frame.changePanel(page.createPage());
		}

		public BasePage checkField() {
			String title = _title_field.getText();
			String deadline = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(_deadline_field.getValue());
			int priority = Integer.valueOf(_bgroup.getSelection().getActionCommand());
			if (!isValid(title, deadline))
				return (null);
			updateToDo(title, deadline, priority);
			return (new ToDo(ToDoEdit.this, _task_id));
		}

		public void updateToDo(String title, String deadline, int priority) {
			LocalDateTime time_now = LocalDateTime.now();
			DateTimeFormatter time_format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			String update_time = time_format.format(time_now);
			SQL.update("update todo set title = ?, deadline = ?, update_time = ?, priority = ? where id = ?", title, deadline, update_time, String.valueOf(priority), String.valueOf(_todo_id));
		}

		public boolean isValid(String title, String deadline) {
			boolean is_valid = true;
			if (title.equals("")) {
				_title_error.setText("Please input title");
				is_valid = false;
			} else
				_title_error.setText("");
			if (deadline.equals("")) {
				_deadline_error.setText("Please input deadline");
				is_valid = false;
			} else
				_deadline_error.setText("");
			return (is_valid);
		}
	}

	public void setDefaultToDo() {
		ArrayList<ArrayList<String>> info = SQL.select("select title, deadline, priority from todo where id=?", 3, String.valueOf(_todo_id));
		ArrayList<String> str_list = info.get(0);
		this._title = str_list.get(0);
		this._deadline = str_list.get(1);
		this._priority = Integer.valueOf(str_list.get(2));
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.is_menu = true;
		panel.setLayout(null);

		setDefaultToDo();

		JLabel label = panel.createLabel("ToDoEdit", 0.4, 0.1, 0.2, 0.05);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);

		JLabel title_label = panel.createLabel("Title: ", 0.05, 0.2, 0.15, 0.05);
		this._title_field = panel.createTextField(this._title, 0.2, 0.2, 0.6, 0.05);
		this._title_error = panel.createLabel("",0.2, 0.25, 0.6, 0.05);
		this._title_error.setForeground(Color.RED);

		JLabel deadline_date_label = panel.createLabel("Deadline: ", 0.05, 0.3, 0.15, 0.05);

		SpinnerDateModel model = new SpinnerDateModel();
		_deadline_field = panel.createSpinner(model, 0.2, 0.3, 0.6, 0.05);
		JComponent editor = new JSpinner.DateEditor(_deadline_field, "yyyy/MM/dd HH:mm:ss");
		_deadline_field.setEditor(editor);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			Date defaultDate = dateFormat.parse(_deadline);
			model.setValue(defaultDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this._deadline_error = panel.createLabel("",0.2, 0.35, 0.6, 0.05);
		this._deadline_error.setForeground(Color.RED);

		JLabel priority_label = panel.createLabel("Priority: ", 0.05, 0.4, 0.15, 0.05);
		JRadioButton radio1 = panel.createRadioButton("1", false, 0.2, 0.4, 0.1, 0.05);
		JRadioButton radio2 = panel.createRadioButton("2", false, 0.32, 0.4, 0.1, 0.05);
		JRadioButton radio3 = panel.createRadioButton("3", false, 0.44, 0.4, 0.1, 0.05);
		JRadioButton radio4 = panel.createRadioButton("4", false, 0.56, 0.4, 0.1, 0.05);
		JRadioButton radio5 = panel.createRadioButton("5", false, 0.68, 0.4, 0.1, 0.05);

		switch (_priority) {
			case 1:
				radio1.setSelected(true);
				break ;
			case 2:
				radio2.setSelected(true);
				break ;
			case 3:
				radio3.setSelected(true);
				break ;
			case 4:
				radio4.setSelected(true);
				break ;
			case 5:
				radio5.setSelected(true);
				break ;
		}

		_bgroup = new ButtonGroup();
		_bgroup.add(radio1);
		_bgroup.add(radio2);
		_bgroup.add(radio3);
		_bgroup.add(radio4);
		_bgroup.add(radio5);

		panel.add(title_label);
		panel.add(this._title_field);
		panel.add(this._title_error);
		panel.add(deadline_date_label);
		panel.add(this._deadline_field);
		panel.add(this._deadline_error);
		panel.add(priority_label);
		panel.add(radio1);
		panel.add(radio2);
		panel.add(radio3);
		panel.add(radio4);
		panel.add(radio5);

		Action action = new Action();

		JButton button = panel.createButton("Back to ToDo", 0.25, 0.6, 0.2, 0.1);
		button.addActionListener(action);

		JButton button2 = panel.createButton("Complete Editing", 0.55, 0.6, 0.2, 0.1);
		button2.addActionListener(action);

		panel.add(button);
		panel.add(button2);

		return (panel);
	}
}
