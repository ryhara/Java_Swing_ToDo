import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Share extends BasePage {
	private int task_id;

	public Share(BasePage page) { super(page); }
	public Share(BaseFrame frame) { super(frame); }
	public Share(BasePage page,int task_id) {
		super(page);
		this.task_id = task_id;
	}

	public class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			BasePage page = null;

			if (cmd.equals("Back to ToDo"))
				page = new ToDo(Share.this, Share.this.task_id);
			else if (cmd.equals("Share member add"))
				page = new ShareAdd(Share.this, Share.this.task_id);
			else if (cmd.startsWith("Delete_")) {
				String member_id = cmd.substring(7);
				deleteEditable(member_id, Share.this.task_id);
				page = new Share(Share.this, Share.this.task_id);
			}
			else if (cmd.startsWith("Editable_")) {
				String member_id = cmd.substring(9);
				updateEditable("1", member_id, Share.this.task_id);
				page = null;
			}
			else if (cmd.startsWith("Viewable_")) {
				String member_id = cmd.substring(9);
				updateEditable("0", member_id, Share.this.task_id);
				page = null;
			}
			else
				page = new Error(Share.this);

			if (page != null)
				Share.this._frame.changePanel(page.createPage());
		}

		public static void updateEditable(String is_editable, String member_id, int task_id) {
			SQL.update("update share set is_editable = ? where member = ? and task = ?", is_editable, member_id, String.valueOf(task_id));
		}

		public static void deleteEditable(String member_id, int task_id) {
			SQL.delete("delete from share where member = ? and task = ?", member_id, String.valueOf(task_id));
		}
	}

	public BasePanel createPage() {
		BasePanel panel = new BasePanel(this._frame);
		panel.is_menu = true;
		panel.setLayout(null);

		JLabel label = panel.createLabel("Share", 0.35, 0.1, 0.3, 0.05);
		label.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);

		Action action = new Action();

		JButton button = panel.createButton("Share member add", 0.6, 0.1, 0.2, 0.05);
		button.addActionListener(action);
		panel.add(button);

		double panel_height = 0.2;

		ArrayList<ArrayList<String>> info = SQL.select("select s.member, m.name, s.is_editable from member m, share s where s.task=? and s.member=m.id", 3, String.valueOf(this.task_id));
		for (int i = 0; i < info.size(); i++) {
			ArrayList<String> str_list = info.get(i);
			String member_id = str_list.get(0);
			String member_name = str_list.get(1);
			String editable = str_list.get(2);
			JLabel name_label = panel.createLabel(member_name, 0.2, panel_height, 0.2, 0.05);
			boolean is_editable = false;
			if (editable.equals("1"))
				is_editable = true;
			JRadioButton radio1 = panel.createRadioButton("Editable", is_editable, 0.45, panel_height, 0.1, 0.05);
			JRadioButton radio2 = panel.createRadioButton("Viewable", !is_editable, 0.6, panel_height, 0.1, 0.05);
			radio1.setActionCommand("Editable_" + member_id);
			radio1.addActionListener(action);
			radio2.setActionCommand("Viewable_" + member_id);
			radio2.addActionListener(action);
			JButton delete_button = panel.createButton("Delete", 0.75, panel_height, 0.1, 0.05);
			delete_button.setActionCommand("Delete_" + member_id);
			delete_button.addActionListener(action);
			ButtonGroup bgroup = new ButtonGroup();
			bgroup.add(radio1);
			bgroup.add(radio2);
			panel.add(radio1);
			panel.add(radio2);
			panel.add(delete_button);
			panel.add(name_label);

			panel_height = panel_height + 0.05;
			if (panel_height > 0.8)
				panel.setPreferredSize(new Dimension(900, (int)(Share.this._frame.getWinHeight() * (panel_height + 0.2))));
		}

		JButton button2 = panel.createButton("Back to ToDo", 0.4, panel_height + 0.05, 0.2, 0.05);
		button2.addActionListener(action);
		panel.add(button2);

		return (panel);
	}
}
