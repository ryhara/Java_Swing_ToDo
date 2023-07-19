import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class ToDoEditListener implements TableModelListener {
	private DefaultTableModel model;
	private BasePage page;
	private int task_id;
	private ArrayList<ArrayList<String>> info;

	public ToDoEditListener(DefaultTableModel model, BasePage page, int task_id, ArrayList<ArrayList<String>> info) {
		this.model = model;
		this.page = page;
		this.task_id = task_id;
		this.info = info;
	}

	public void tableChanged(TableModelEvent e) {
		int col_num = e.getColumn();
		int row_num = e.getFirstRow();
		String value = (boolean)model.getValueAt(row_num, col_num) ? "1" : "0";

		ArrayList<String> str_list = info.get(row_num);
		int todo_id = Integer.valueOf(str_list.get(6));
		SQL.update("update todo set is_done = ? where id = ?", value, String.valueOf(todo_id));

		BasePage new_page = new ToDo(page, task_id);
		page.getFrame().changePanel(new_page.createPage());
	}
}
