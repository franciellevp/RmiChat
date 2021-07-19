package src.rmi.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonMessage extends JFrame {
	
	private boolean trigger = false;

	public ButtonMessage(String text, int width, int height) {
		final JPanel panel = new JPanel();
		final JLabel label = new JLabel(text);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JButton btn = new JButton("Valeu!");
		btn.setSize(40, 10);
		
		panel.add(label);
		panel.add(new JLabel(" "));
		panel.add(btn);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		add(panel);

		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
		
		btn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				trigger = true;
				dispose();
			}
		});
		
		do {
			setVisible(true);
		} while (!trigger);
	}

}
