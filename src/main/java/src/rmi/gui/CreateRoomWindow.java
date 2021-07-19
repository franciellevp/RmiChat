package src.rmi.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.rmi.Room.IRoomChat;
import src.rmi.Room.RoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.User.IUserChat;
import src.rmi.User.UserChat;
import src.rmi.main.Constants;

public class CreateRoomWindow extends JFrame {
	
	final JPanel grid = new JPanel(new FlowLayout());
	final JButton btn = new JButton("Criar");
	JTextField field = new JTextField(10);
	
	public CreateRoomWindow(IServerChat serverApi, UserChat user)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		grid.setBorder(BorderFactory.createEmptyBorder(35, 10, 10, 10));
		
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
				Create(serverApi, user);
			}
		});

		btn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Create(serverApi, user);
			}
		});
		
	}
	
	private void Create(IServerChat serverApi, UserChat user)
	{
		try {
			serverApi.createRoom(field.getText());
			new ListWindow("Lista de salas", serverApi, 300, 500, user);
			
			dispose();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
