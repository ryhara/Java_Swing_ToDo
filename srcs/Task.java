import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Task extends BasePage {
	public Task(BasePage page) { super(page); }
	public Task(BaseFrame frame) { super(frame); }

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("My Task"))
				page = new MyTask(Task.this);
			else if (cmd.equals("Archived Task"))
				page = new ArchivedTask(Task.this);
			else if (cmd.equals("Shared Task"))
				page = new SharedTask(Task.this);
			else
				page = new ArchivedTask(Task.this);

			if (page != null)
				Task.this._frame.changePanel(page.createPage());
		}
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.is_menu = true;
		panel.setLayout(null);

		JLabel label = panel.createLabel("Task", 0.4, 0.1, 0.2, 0.1);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);

		Action action = new Action();

		JButton my_task_add_button = panel.createButton("My Task", 0.4, 0.3, 0.2, 0.1);
		my_task_add_button.addActionListener(action);
		my_task_add_button.setFont(new Font("Arial", Font.PLAIN, 15));
		my_task_add_button.setContentAreaFilled(false);
		panel.add(my_task_add_button);

		JButton archived_task_add_button = panel.createButton("Archived Task", 0.2, 0.5, 0.2, 0.1);
		archived_task_add_button.addActionListener(action);
		archived_task_add_button.setFont(new Font("Arial", Font.PLAIN, 15));
		archived_task_add_button.setContentAreaFilled(false);
		panel.add(archived_task_add_button);

		JButton shared_task_add_button = panel.createButton("Shared Task", 0.6, 0.5, 0.2, 0.1);
		shared_task_add_button.addActionListener(action);
		shared_task_add_button.setFont(new Font("Arial", Font.PLAIN, 15));
		shared_task_add_button.setContentAreaFilled(false);
		panel.add(shared_task_add_button);

		return (panel);
	}
}