import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Login extends BasePage {
	private JTextField username_field;
	private JPasswordField password_field;
	private JLabel username_error;
	private JLabel password_error;
	private JLabel flash_message;
	private String message = "";

	public Login(BasePage page) { super(page); }
	public Login(BasePage page, String message) {
		super(page);
		this.message = message;
	}
	public Login(BaseFrame frame) { super(frame); }

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Go to SignUp"))
				page = new SignUp(Login.this);
			else if (cmd.equals("Login"))
				page = checkLogin();
			else
				page = new Error(Login.this);
			if (page != null)
				Login.this._frame.changePanel(page.createPage());
		}

		public BasePage checkLogin() {
			String username = username_field.getText();
			String password = new String(password_field.getPassword());
			resetNamePass();

			if (!isValid(username, password))
				return (null);
			else if (checkUser(username, password)) {
				this.setMemberId(username);
				return (new MyInfo(Login.this));
			}
			else {
				flash_message.setText("Wrong username or password!");
				flash_message.setForeground(Color.RED);
				return (null);
			}
		}

		public boolean checkUser(String username, String password) {
			ArrayList<ArrayList<String>> info = SQL.select("SELECT password FROM member WHERE name=?", 1, username);
			if (info.size() == 0)
				return (false);
			ArrayList<String> str_list = info.get(0);
			if (password.equals(str_list.get(0)))
				return (true);
			else
				return (false);
		}

		public boolean isValid(String username, String password) {
			boolean is_valid = true;
			if (username.equals("")) {
				username_error.setText("Please input username");
				is_valid = false;
			}
			else
				username_error.setText("");
			if (password.equals("")) {
				password_error.setText("Please input password");
				is_valid = false;
			}
			else
				password_error.setText("");
			return (is_valid);
		}

		public void resetNamePass() {
			flash_message.setText("");
			username_field.setText("");
			password_field.setText("");
		}

		public void setMemberId(String username) {
			ArrayList<ArrayList<String>> info = SQL.select("select id from member where name=?", 1, username);
			ArrayList<String> str_list = info.get(0);
			String id = str_list.get(0);
			Login.this._frame.setLoginId(id);
		}
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.setLayout(null);

		flash_message = panel.createLabel(this.message,0.4, 0.05, 0.2, 0.05);
		flash_message.setForeground(Color.GREEN);
		flash_message.setHorizontalAlignment(JLabel.CENTER);
		JLabel label = panel.createLabel("Login", 0.35, 0.1, 0.3, 0.08);
		label.setFont(new Font("Arial Black", Font.PLAIN, 30));
		label.setHorizontalAlignment(JLabel.CENTER);

		JLabel username_label = panel.createLabel("Username: ", 0.05, 0.3, 0.15, 0.05);
		username_field = panel.createTextField("", 0.2, 0.3, 0.6, 0.05);
		username_error = panel.createLabel("",0.2, 0.35, 0.6, 0.05);
		username_error.setForeground(Color.RED);
		JLabel password_label = panel.createLabel("password: ", 0.05, 0.4, 0.15, 0.05);
		password_field = panel.createPasswordField("", 0.2, 0.4, 0.6, 0.05);
		password_error = panel.createLabel("",0.2, 0.45, 0.6, 0.05);
		password_error.setForeground(Color.RED);

		panel.add(flash_message);
		panel.add(label);
		panel.add(username_label);
		panel.add(username_field);
		panel.add(username_error);
		panel.add(password_label);
		panel.add(password_field);
		panel.add(password_error);

		Action action = new Action();

		JButton button = panel.createButton("Login", 0.55, 0.6, 0.2, 0.1);
		button.addActionListener(action);
		button.setContentAreaFilled(false);
		button.setForeground(Color.BLUE);
		panel.add(button);

		JButton button2 = panel.createButton("Go to SignUp", 0.25, 0.6, 0.2, 0.1);
		button2.addActionListener(action);
		button2.setContentAreaFilled(false);
		panel.add(button2);

		return (panel);
	}
}
