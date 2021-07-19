package src.rmi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import src.rmi.Room.IRoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.User.UserChat;
import src.rmi.main.Constants;

import java.rmi.server.UnicastRemoteObject;

public class ListWindow  extends JFrame {

	private JList list;
	private JLabel content = new JLabel();
	private JPanel panel = new JPanel();
	private JPanel underPanel= new JPanel();
	
	private JButton joinBtn = new JButton("Entrar");
	private JButton createBtn = new JButton("Criar sala");
	
	private int index = 0;
	
	public ListWindow(String title, ArrayList<String> rooms, int width, int height, UserChat user) {
		String[] array = rooms.toArray(new String[0]);
		list = new JList(array);
		list.setSize(width / 2, height);
		
		panel.setSize(50,50);
		panel.setBorder(BorderFactory.createEmptyBorder(130, 20, 20, 50));
		
		underPanel.setBackground(Color.gray);
		underPanel.setSize(width, 50);
		underPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		underPanel.add(createBtn);
		
		add(BorderLayout.WEST, list);
		add(BorderLayout.CENTER, panel);
		add(BorderLayout.SOUTH, underPanel);
		
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);

		joinBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					user.userJoin(index);
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		createBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CreateRoomWindow();
			}
		});
		
		list.addListSelectionListener(e -> {
			index = list.getSelectedIndex();
			String selected = array[index];

			content.setText(selected);
			//panel.setBackground(Color.gray);
			panel.add(content);
			panel.add(joinBtn);
		});

	}

}
