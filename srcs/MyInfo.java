import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class MyInfo extends BasePage {
	private String username;
	private String email;
	private String introduction;
	private String is_public;
	private JLabel flash_message;
	private String message = "";

	public MyInfo(BaseFrame frame) { super(frame); }
	public MyInfo(BasePage page) { super(page); }
	public MyInfo(BasePage page, String message) {
		super(page);
		this.message = message;
	}

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;
			flash_message.setText("");
			if (cmd.equals("Go to Edit"))
				page = new MyInfoEdit(MyInfo.this);
			else
				page = new Error(MyInfo.this);

			if (page != null)
				MyInfo.this._frame.changePanel(page.createPage());
		}

		public boolean checkPass(String password) {
			String id = MyInfo.this._frame.getLoginId();
			ArrayList<ArrayList<String>> info = SQL.select("SELECT password FROM member WHERE id=?", 1, id);
			if (info.size() == 0)
				return (false);
			ArrayList<String> str_list = info.get(0);
			if (password.equals(str_list.get(0)))
				return (true);
			else
				return (false);
		}
	}

	public void setMyInfo() {
		String id = MyInfo.this._frame.getLoginId();
		ArrayList<ArrayList<String>> info = SQL.select("select name, email, introduction, is_public from member where id=?", 4, id);
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
		this.setMyInfo();

		flash_message = panel.createLabel(this.message, 0.4, 0.05, 0.2, 0.05);
		if (this.message.equals("User update!"))
			flash_message.setForeground(Color.GREEN);
		flash_message.setHorizontalAlignment(JLabel.CENTER);
		JLabel label = panel.createLabel("My Information", 0.35, 0.1, 0.3, 0.05);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label.setHorizontalAlignment(JLabel.CENTER);
		JLabel username_label = panel.createLabel("Username: " + this.username, 0.3, 0.2, 0.5, 0.05);
		JLabel email_label = panel.createLabel("email: " + this.email, 0.3, 0.3, 0.5, 0.05);
		JLabel introduction_label = panel.createLabel("introduction: " + this.introduction, 0.3, 0.4, 0.6, 0.05);

		String is_public_str = "Public";
		if (is_public.equals("0"))
			is_public_str = "Private";
		JLabel public_label = panel.createLabel("setting: " + is_public_str, 0.3, 0.5, 0.3, 0.05);

		panel.add(flash_message);
		panel.add(label);
		panel.add(username_label);
		panel.add(email_label);
		panel.add(introduction_label);
		panel.add(public_label);

		JButton button = panel.createButton("Go to Edit", 0.4, 0.6, 0.2, 0.05);
		Action action = new Action();
		button.addActionListener(action);
		panel.add(button);

		return (panel);
	}
}
