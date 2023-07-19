import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Error extends BasePage {
	public Error(BasePage page) { super(page); }
	public Error(BaseFrame frame) { super(frame); }

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Back to Login"))
				page = new Login(Error.this);
			else
				page = new Error(Error.this);

			if (page != null)
				Error.this._frame.changePanel(page.createPage());
		}
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.setLayout(null);

		JLabel label = panel.createLabel("Error", 0.45, 0.2, 0.1, 0.05);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label.setForeground(Color.RED);
		panel.add(label);

		JButton button = panel.createButton("Back to Login", 0.4, 0.5, 0.2, 0.1);
		Action action = new Action();
		button.addActionListener(action);
		panel.add(button);

		return (panel);
	}
}
