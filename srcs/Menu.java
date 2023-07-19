import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends BasePage {
	public Menu(BaseFrame frame) { super(frame); }

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("My Info"))
				page = new MyInfo(Menu.this);
			else if (cmd.equals("Task"))
				page = new Task(Menu.this);
			else if (cmd.equals("Users"))
				page = new Users(Menu.this);
			else if (cmd.equals("Logout"))
				page = new Login(Menu.this);
			else if (cmd.equals("Exit"))
				System.exit(0);
			else
				page = new Error(Menu.this);

			if (page != null)
				Menu.this._frame.changePanel(page.createPage());
		}
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(_frame);

		Action action = new Action();

		JButton button = new JButton("My Info");
		button.addActionListener(action);
		button.setContentAreaFilled(false);
		panel.add(button);

		JButton button2 = new JButton("Task");
		button2.addActionListener(action);
		button2.setContentAreaFilled(false);
		panel.add(button2);

		JButton button3 = new JButton("Users");
		button3.addActionListener(action);
		button3.setContentAreaFilled(false);
		panel.add(button3);

		JButton button4 = new JButton("Logout");
		button4.addActionListener(action);
		button4.setContentAreaFilled(false);
		panel.add(button4);

		JButton button5 = new JButton("Exit");
		button5.addActionListener(action);
		button5.setContentAreaFilled(false);
		button5.setForeground(Color.RED);
		panel.add(button5);

		return (panel);
	}
}