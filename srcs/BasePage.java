import java.awt.event.*;

abstract public class BasePage {
	protected BaseFrame _frame;

	public BasePage(BasePage page) { this._frame = page.getFrame(); }
	public BasePage(BaseFrame frame) { this._frame = frame; }

	public BaseFrame getFrame() { return (this._frame); };

	public abstract class Action implements ActionListener {
		public abstract void actionPerformed(ActionEvent e);
	}

	public abstract BasePanel createPage();
}
