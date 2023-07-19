import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class UserInfo extends BasePage {
	private String user_id;
	private String username;
	private String email;
	private String introduction;
	private String is_public;

	public UserInfo(BaseFrame frame) { super(frame); }
	public UserInfo(BasePage page) { super(page); }
	public UserInfo(BasePage page, String user_id) {
		super(page);
		this.user_id = user_id;
	}

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Back to Users"))
				page = new Users(UserInfo.this);
			else
				page = new Error(UserInfo.this);

			if (page != null)
				UserInfo.this._frame.changePanel(page.createPage());
		}
	}

	public void setUserInfo() {
		ArrayList<ArrayList<String>> info = SQL.select("select name, email, introduction, is_public from member where id=?", 4, user_id);
		ArrayList<String> str_list = info.get(0);
		this.username = str_list.get(0);
		this.email = str_list.get(1);
		this.introduction = str_list.get(2);
		this.is_public = str_list.get(3);
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.is_menu = true;
		panel.setLayout(null);
		this.setUserInfo();

		JLabel label = panel.createLabel(this.username + "'s Information", 0.35, 0.1, 0.3, 0.05);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label.setHorizontalAlignment(JLabel.CENTER);
		JLabel username_label = panel.createLabel("Username: " + this.username, 0.3, 0.2, 0.15, 0.05);
		JLabel email_label = panel.createLabel("email: " + this.email, 0.3, 0.3, 0.5, 0.05);
		JLabel introduction_label = panel.createLabel("introduction: " + this.introduction, 0.3, 0.4, 0.6, 0.05);

		String is_public_str = "Public";
		if (is_public.equals("0"))
			is_public_str = "Private";
		JLabel public_label = panel.createLabel("setting: " + is_public_str, 0.3, 0.5, 0.3, 0.05);

		panel.add(label);
		panel.add(username_label);
		panel.add(email_label);
		panel.add(public_label);
		panel.add(introduction_label);

		JButton button = panel.createButton("Back to Users", 0.4, 0.6, 0.2, 0.05);
		Action action = new Action();
		button.addActionListener(action);
		panel.add(button);

		return (panel);
	}
}
