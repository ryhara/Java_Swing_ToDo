public class GUI {
	private static final String _title = "ToDo";
	private static final int _win_width = 900;
	private static final int _win_height = 600;

	public static void main(String[] args) {
		SQL.getLog();
		BaseFrame frame = new BaseFrame(GUI._title, GUI._win_width, GUI._win_height);
		BasePage page = new Login(frame);
		BasePanel component = page.createPage();
		frame.changePanel(component);
		frame.setVisible(true);
	}
}
