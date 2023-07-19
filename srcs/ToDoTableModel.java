import javax.swing.table.DefaultTableModel;

public class ToDoTableModel extends DefaultTableModel {
	boolean is_editable;

	ToDoTableModel(Object[][] data, Object[] columnNames, boolean is_editable) {
		super(data, columnNames);
		this.is_editable = is_editable;
	}

	public Class getColumnClass(int col) {
		return (getValueAt(0, col).getClass());
	}

	public boolean isCellEditable(int row, int column) {
		if (column == 5 && is_editable)
			return (true);
		return (false);
	}
}
