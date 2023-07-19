import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class SignUp extends BasePage {
	private JTextField username_field;
	private JTextField email_field;
	private JPasswordField password_field;
	private JPasswordField confirm_field;
	private JLabel username_error;
	private JLabel email_error;
	private JLabel password_error;
	private JLabel confirm_error;
	private JLabel flash_message;

	public SignUp(BasePage page) { super(page); }
	public SignUp(BaseFrame frame) { super(frame); }

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Register"))
				page = checkSignUp();
			else if (cmd.equals("Back to Login"))
				page = new Login(SignUp.this);
			else
				page = new Error(SignUp.this);

			if (page != null)
				SignUp.this._frame.changePanel(page.createPage());
		}

		public static void insertUser(String name, String email, String password) {
			SQL.insert("insert into member (name, email, password) VALUES(?, ?, ?)", name, email, password);
		}

		public BasePage checkSignUp() {
			String username = username_field.getText();
			String email = email_field.getText();
			String password = new String(password_field.getPassword());
			String confirm = new String(confirm_field.getPassword());
			resetAllField();

			if (!isValid(username, email, password, confirm))
				return (null);
			if (!checkDuplication(username, email)) {
				return (null);
			}
			else {
				insertUser(username, email, password);
				return (new Login(SignUp.this, "User create!"));
			}
		}

		public boolean checkDuplication(String username, String email) {
			boolean check_name = true;
			boolean check_email = true;

			ArrayList<ArrayList<String>> info = SQL.select("SELECT * FROM member WHERE name=?", 1, username);
			if (info.size() == 0)
				check_name = true;
			else {
				username_error.setText("This username has already used");
				check_name = false;
			}
			info = SQL.select("SELECT * FROM member WHERE email=?", 1, email);
			if (info.size() == 0)
				check_email = true;
			else {
				email_error.setText("This email has already used");
				check_email = false;
			}
			return (check_email && check_name);
		}

		public boolean isValid(String username, String email, String password, String confirm) {
			boolean is_valid = true;
			if (username.equals("")) {
				username_error.setText("Please input username");
				is_valid = false;
			}
			else
				username_error.setText("");
			if (email.equals("")) {
				email_error.setText("Please input email");
				is_valid = false;
			}
			else
				email_error.setText("");
			if (password.equals("")) {
				password_error.setText("Please input password");
				is_valid = false;
			}
			else
				password_error.setText("");
			if (confirm.equals("")) {
				confirm_error.setText("Please input confirm password");
				is_valid = false;
			}
			else
				confirm_error.setText("");
			if (!confirm.equals(password)) {
				is_valid = false;
				flash_message.setText("Confirm password is not equal to password!");
			}
			return (is_valid);
		}

		public void resetAllField() {
			flash_message.setText("");
			username_field.setText("");
			email_field.setText("");
			password_field.setText("");
			confirm_field.setText("");
		}
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(_frame);
		panel.setLayout(null);

		flash_message = panel.createLabel("", 0.35, 0.05, 0.3, 0.05);
		flash_message.setForeground(Color.RED);
		flash_message.setHorizontalAlignment(JLabel.CENTER);
		JLabel label = panel.createLabel("SignUp", 0.35, 0.1, 0.3, 0.1);
		label.setFont(new Font("Arial Black", Font.PLAIN, 30));
		label.setHorizontalAlignment(JLabel.CENTER);
		JLabel username_label = panel.createLabel("Username: ", 0.05, 0.3, 0.15, 0.05);
		username_field = panel.createTextField("", 0.2, 0.3, 0.6, 0.05);
		username_error = panel.createLabel("", 0.2, 0.35, 0.5,0.05);
		username_error.setForeground(Color.RED);

		JLabel email_label = panel.createLabel("email: ", 0.05, 0.4, 0.15, 0.05);
		email_field = panel.createTextField("", 0.2, 0.4, 0.6, 0.05);
		email_error = panel.createLabel("", 0.2, 0.45, 0.5, 0.05);
		email_error.setForeground(Color.RED);

		JLabel password_label = panel.createLabel("password: ", 0.05, 0.5, 0.15, 0.05);
		password_field = panel.createPasswordField("", 0.2, 0.5, 0.6, 0.05);
		password_error = panel.createLabel("", 0.2, 0.55, 0.5, 0.05);
		password_error.setForeground(Color.RED);

		JLabel confirm_label = panel.createLabel("confirm password: ", 0.05, 0.6, 0.15, 0.05);
		confirm_field = panel.createPasswordField("", 0.2, 0.6, 0.6, 0.05);
		confirm_error = panel.createLabel("", 0.2, 0.65, 0.5, 0.05);
		confirm_error.setForeground(Color.RED);

		panel.add(label);
		panel.add(flash_message);
		panel.add(username_label);
		panel.add(username_error);
		panel.add(username_field);
		panel.add(email_label);
		panel.add(email_error);
		panel.add(email_field);
		panel.add(password_label);
		panel.add(password_error);
		panel.add(password_field);
		panel.add(confirm_label);
		panel.add(confirm_error);
		panel.add(confirm_field);

		Action action = new Action();

		JButton button = panel.createButton("Register", 0.55, 0.75, 0.2, 0.1);
		button.addActionListener(action);
		button.setContentAreaFilled(false);
		button.setForeground(Color.GREEN);
		panel.add(button);

		JButton button2 = panel.createButton("Back to Login", 0.25, 0.75, 0.2, 0.1);
		button2.addActionListener(action);
		button2.setContentAreaFilled(false);
		panel.add(button2);

		return (panel);
	}
}
