package src.rmi.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends JFrame{

	private String text;
	private boolean trigger = false;
	
	public LoginWindow() {
		
	}
	
	public String Login()
	{
		final JPanel grid = new JPanel(new FlowLayout());
		final JButton btn = new JButton("Ok");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		grid.setBorder(BorderFactory.createEmptyBorder(35, 10, 10, 10));
		JTextField field = new JTextField(10);
		setTitle("Inserir nome");
		setLocationRelativeTo(null);
		add(grid);
		grid.add(new JLabel("Nome: "));
		grid.add(field);
		grid.add(btn);
		setSize(250, 150);
		setVisible(true);

		btn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text = field.getText();
				dispose();
				trigger = true;
			}
		});
		
		do {
			setVisible(true);
		} while (text == null);

		return text;
	}

}