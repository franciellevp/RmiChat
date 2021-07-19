package src.rmi.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.rmi.Server.IServerChat;
import src.rmi.User.UserChat;

public class CreateRoomWindow extends JFrame {
	
	public CreateRoomWindow(IServerChat serverApi, UserChat user)
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
		
		field.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serverApi.createRoom(field.getText());
					new ListWindow("Lista de salas", serverApi, 300, 500, user);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});

		btn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
	}
	
}
