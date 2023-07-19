import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Users extends BasePage {
	private static ArrayList<ArrayList<String>> search_list;
	private JTextField username_field;
	private JLabel username_error;

	public Users(BasePage page) { super(page); }
	public Users(BaseFrame frame) { super(frame); }

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Search"))
				page = searchUser();
			else if (cmd.startsWith("User_")) {
				page = new UserInfo(Users.this, cmd.substring(5));
				search_list = null;
			}
			else
				page = new Error(Users.this);
			if (page != null)
				Users.this._frame.changePanel(page.createPage());
			else
				search_list = null;
		}

		public BasePage searchUser() {
			String username = username_field.getText();
			username_field.setText("");
			if (username.equals("")) {
				username_error.setText("Please input username");
				search_list = null;
				return (new Users(Users.this));
			}
			else {
				search_list = SQL.select_like("SELECT name, id FROM member WHERE is_public=1 and name LIKE ?", 2, username);
				if (search_list.size() == 0) {
					search_list = null;
					return (new Users(Users.this));
				}
				else
					return (new Users(Users.this));

			}
		}
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.is_menu = true;
		panel.setLayout(null);

		JLabel label = panel.createLabel("Users", 0.45, 0.1, 0.1, 0.05);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel username_label = panel.createLabel("Username Search: ", 0.05, 0.2, 0.15, 0.05);
		username_field = panel.createTextField("", 0.25, 0.2, 0.5, 0.05);
		username_error = panel.createLabel("",0.25, 0.25, 0.6, 0.05);
		username_error.setForeground(Color.RED);

		Action action = new Action();

		JButton button = panel.createButton("Search", 0.8, 0.2, 0.1, 0.05);
		button.addActionListener(action);
		panel.add(button);

		double panel_height = 0.3;
		if (search_list != null) {
			for (int i = 0; i < search_list.size(); i++) {
				ArrayList<String> str_list = search_list.get(i);
				String username = str_list.get(0);
				String user_id = str_list.get(1);
				if (user_id.equals(Users.this._frame.getLoginId()))
					continue;
				JButton search_ans = panel.createButton(username, 0.35, panel_height, 0.3, 0.05);
				search_ans.setActionCommand("User_" + user_id);
				search_ans.addActionListener(action);
				search_ans.setContentAreaFilled(false);
				panel.add(search_ans);

				panel_height = panel_height + 0.06;
				if (panel_height > 0.8)
					panel.setPreferredSize(new Dimension(900, (int)(Users.this._frame.getWinHeight() * (panel_height + 0.2))));
			}
		}

		panel.add(label);
		panel.add(username_label);
		panel.add(username_field);
		panel.add(username_error);

		return (panel);
	}
}
