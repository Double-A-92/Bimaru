package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Erzeugt eine HelpText klasse welche ein Window mit HelpText-Feld 
 * und Close Button erzeugt
 * 
 * @author Smelt Alexander
 */

public class HelpText implements ActionListener {
	private JFrame frmHelpwindow;	
	private JPanel panel_1;
	private JButton btnClose;
	private TextArea textArea;
	String content;
	
// Konstruktor
	public HelpText() throws IOException {
		this.content = fileLoad("/textfiles/helpText.txt");
		initialize();
	}
	
	public String fileLoad (String path) {
		URL url = getClass().getResource(path);
		File file = new File(url.getPath());
		String string = file.toString();
		String mContent="";
		try {
			mContent = readFile(string, StandardCharsets.ISO_8859_1);
		} catch (IOException except) {
			return "File problem! fileLoad()";
		}
		return mContent;
	}
	
	static String readFile(String path, Charset encoding) throws IOException {
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHelpwindow = new JFrame();
		frmHelpwindow.setIconImage(Toolkit.getDefaultToolkit().getImage(HelpText.class.getResource("/images/icon/help.png")));
		frmHelpwindow.setTitle("HelpWindow");
		frmHelpwindow.setBounds(100, 100, 725, 400);
		frmHelpwindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmHelpwindow.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(Color.DARK_GRAY);
		frmHelpwindow.getContentPane().add(panel_1);
		
		textArea = new TextArea();
		textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		textArea.setText(content);
		
		btnClose = new JButton("  Close");
		btnClose.addActionListener(this);
		btnClose.setFont(new Font("Arial", Font.BOLD, 30));
		btnClose.setIcon(new ImageIcon(HelpText.class.getResource("/com/sun/java/swing/plaf/windows/icons/Error.gif")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
							.addGap(12)
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 460, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap(467, Short.MAX_VALUE)
							.addComponent(btnClose)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnClose)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		frmHelpwindow.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frmHelpwindow.getContentPane()}));
		frmHelpwindow.setLocationRelativeTo(null);
		frmHelpwindow.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent action) {
		if(action.getSource() == this.btnClose) {
			frmHelpwindow.dispose();
		}
	}
}
