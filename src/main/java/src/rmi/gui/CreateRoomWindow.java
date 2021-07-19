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

public class CreateRoomWindow extends JFrame {
	
	public CreateRoomWindow()
	{
		final JPanel grid = new JPanel(new FlowLayout());
		final JButton btn = new JButton("Criar");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		grid.setBorder(BorderFactory.createEmptyBorder(35, 10, 10, 10));
		JTextField field = new JTextField(10);
		setTitle("Criar sala");
		setLocationRelativeTo(null);
		add(grid);
		grid.add(new JLabel("Nome da sala: "));
		grid.add(field);
		grid.add(btn);
		setSize(200, 200);
		setVisible(true);

		btn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//serverApi.createRoom(field.getText());
				dispose();
			}
		});
		
	}
}
