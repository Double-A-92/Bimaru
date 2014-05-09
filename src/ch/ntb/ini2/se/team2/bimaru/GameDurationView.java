package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Zeigt die, seit dem ersten Klick, vergangene Zeit an.
 * Über einen Button kann das Spiel pausiert und wieder fortgesetzt werden.
 * 
 * @author Amedeo Amato
 */
public class GameDurationView extends JPanel implements Runnable {
	private static final long serialVersionUID = -4644192325470903384L;
	private BimaruGame game;
	private JLabel durationDisplay;
	private JDialog durationDialog;
	private boolean updateDisplay = true;

	/**
	 * Erstellt einen neues Panel zur Anzeige der Spieldauer,
	 * welches in einem JDialog dargestellt werden muss.
	 * 
	 * @param bimaruGame aktuelles Spiel-Objekt
	 * @param gameDurationDialog JDialog indem dieses Panel eingebunden wird.
	 */
	public GameDurationView(BimaruGame bimaruGame, JDialog gameDurationDialog) {
		game = bimaruGame;
		durationDialog = gameDurationDialog;

		//Label für Zeitanzeige initialisieren
		durationDisplay = new JLabel("00:00");
		durationDisplay.setFont(new Font(getFont().getName(), Font.BOLD, 20));
		add(durationDisplay);

		//Pause-Button erstellen und einrichten
		final JButton pauseButton = new JButton("Pause");
		pauseButton.setPreferredSize(new Dimension(100, 26));
		pauseButton.addActionListener(new ActionListener() {
			private long pauseStartTime;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (updateDisplay) { // Wenn pausiert wird
					pauseStartTime = System.nanoTime();
					updateDisplay = false;
					pauseButton.setText("Fortsetzen");
					durationDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					durationDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
				} else { // Wenn fortgesetzt wird
					if (game.getGGM().getStartTime() != 0) {
						// Dauer der Pause kompensieren indem sie zu der Startzeit addiert wird.
						game.getGGM().setStartTime(game.getGGM().getStartTime() + System.nanoTime() - pauseStartTime);
					}
					updateDisplay = true;
					pauseButton.setText("Pause");
					durationDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
					durationDialog.setModalityType(JDialog.ModalityType.MODELESS);
				}
				
				// Dialog aktualisieren (sperrend / nicht sperrend)
				durationDialog.setVisible(false);
				durationDialog.setVisible(true);
			}
		});
		add(pauseButton);

		(new Thread(this)).start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				//Aktualisiert die Dauer-Anzeige (aktuelle Zeit - Startzeit)
				if (updateDisplay && game.getGGM().getStartTime() != 0) {
					long startTime = game.getGGM().getStartTime();
					long currentTime = System.nanoTime();
					long duration = currentTime - startTime;

					String formattedDuration = String.format(
							"%02d:%02d",
							TimeUnit.NANOSECONDS.toMinutes(duration),
							TimeUnit.NANOSECONDS.toSeconds(duration)
									- TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(duration)));

					durationDisplay.setText(formattedDuration);
				} else if (game.getGGM().getStartTime() == 0) {
					durationDisplay.setText("00:00");
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
