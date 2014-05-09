package ch.ntb.ini2.se.team2.bimaru;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ToolBarView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 6118507465544237331L;
	int errorcount = 0;

	private BimaruGame bimaruGame;
	private GameGridModel ggm;
	private GameGridView view;
	private JDialog gameDurationDialog;

	JButton undo, redo, eye, refresh, check, clock, help, save, print;

	public ToolBarView(BimaruGame bimaruGame) {
		this.bimaruGame = bimaruGame;
		this.ggm = bimaruGame.getGGM();
		this.view = bimaruGame.getView();

		FlowLayout fl = new FlowLayout();
		setLayout(fl);
		addButtons();
	}

	private JButton button(String name) {
		JButton button = new JButton(createIcon("/images/icon/" + name + ".png"));
		button.addActionListener(this);
		add(button);
		return button;
	}

	private void addButtons() {
		eye = button("eye");
		refresh = button("refresh");
		check = button("check");
		clock = button("clock");
		// undo = button("undo");
		// redo = button("redo");
		// help = button("help");
		// save = button("save");
		// print = button("print");
	}

	public void actionPerformed(ActionEvent action) {
		// if(action.getSource() == this.undo) {}
		// if(action.getSource() == this.redo) {}
		// if(action.getSource() == this.help) {}
		// if(action.getSource() == this.save) {}
		// if(action.getSource() == this.print) {}

		if (action.getSource() == this.check) {
			checkFieldState();
			JOptionPane.showMessageDialog(null, "You have " + errorcount + " error(s)", "Check Result",
					JOptionPane.ERROR_MESSAGE);
			errorcount = 0;
			
		} else if (action.getSource() == this.clock) {
			if (gameDurationDialog == null) {
				gameDurationDialog = new JDialog(bimaruGame, "Spieldauer", false);
				gameDurationDialog.setContentPane(new GameDurationView(bimaruGame, gameDurationDialog));
				gameDurationDialog.setResizable(false);
				gameDurationDialog.pack();
				gameDurationDialog.setIconImage(createIcon("/images/icon/clock.png").getImage());
			}

			if (!gameDurationDialog.isShowing()) {
				gameDurationDialog.setVisible(true);
				gameDurationDialog.setLocationRelativeTo(bimaruGame.getView());
			}
			
		} else if (action.getSource() == this.eye) {
			Object[] options = { "OK", "CANCEL" };
			int eingabe = JOptionPane.showOptionDialog(null, "Showing solution will end this game", "Solution",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			if (eingabe == 0) {
				showSolution();
			}

		} else if (action.getSource() == this.refresh) {
			refresh();
		}
	}

	private void refresh() {
		for (int i = 0; i < ggm.getXSize(); i++) {
			for (int j = 0; j < ggm.getYSize(); j++) {
				ggm.setFieldState(i, j, 0);
				view.updateButton(i, j);
			}
		}
		for (int i = 0; i < ggm.getHints().size(); i++) {

			int x = ggm.getHints().get(i).getX();
			int y = ggm.getHints().get(i).getY();

			ggm.setFieldState(x, y, ggm.getRealFieldState(x, y));
		}

		ggm.setStartTime(0);
		ggm.setGameRunning(true);
	}

	private void checkFieldState() {
		for (int i = 0; i < ggm.getXSize(); i++) {
			for (int j = 0; j < ggm.getYSize(); j++) {
				if (ggm.getFieldState(i, j) != 0 && ggm.getFieldState(i, j) != ggm.getRealFieldState(i, j)) {
					errorcount++;
					ggm.setFieldState(i, j, ggm.getFieldState(i, j) + 2);
					view.updateButton(i, j);
				}
			}
		}
	}

	private void showSolution() {
		for (int i = 0; i < ggm.getXSize(); i++) {
			for (int j = 0; j < ggm.getYSize(); j++) {
				ggm.setFieldState(i, j, ggm.getRealFieldState(i, j));
				view.updateButton(i, j);
			}
		}
		ggm.setGameRunning(false);
	}

	protected static ImageIcon createIcon(String path) {
		URL imgURL = ToolBarView.class.getResource(path); 
		if (imgURL != null) {
			ImageIcon img = new ImageIcon(imgURL);
			Image newimg = img.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg);
		} else {
			System.err.println("Couldn't find image in system: " + path);
			return null;
		}
	}
}
