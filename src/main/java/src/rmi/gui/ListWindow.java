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
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import src.rmi.Room.IRoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.User.IUserChat;
import src.rmi.User.UserChat;
import src.rmi.main.Constants;

import java.rmi.server.UnicastRemoteObject;

public class ListWindow  extends JFrame {

	private JList list;
	private JLabel content = new JLabel();
	private JPanel panel = new JPanel();
	private JPanel underPanel= new JPanel();
	
	public JButton joinBtn = new JButton("Entrar");
	public JButton refreshBtn = new JButton("Atualizar lista");
	public JButton createBtn = new JButton("Criar sala");
	private String[] array;
	
	public int index = 0;
	
	public ListWindow(String title, IServerChat serverApi, int width, int height, UserChat user) throws RemoteException {
		array = serverApi.getRooms().toArray(new String[0]);
		list = new JList();
		list.setListData(array);
		list.setSize(width / 2, height);
		
		panel.setSize(50,50);
		panel.setBorder(BorderFactory.createEmptyBorder(130, 20, 20, 50));
		
		underPanel.setBackground(Color.gray);
		underPanel.setSize(width, 70);
		underPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 50, 40));
		underPanel.add(refreshBtn);
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
					dispose();
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		createBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CreateRoomWindow(serverApi, user);
				dispose();
			}
		});
		
		refreshBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					refresh(serverApi, user);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		list.addListSelectionListener(e -> {
			index = list.getSelectedIndex();
			String selected = array[index];

			content.setText(selected);
			//panel.setBackground(Color.gray);
			panel.add(content);
			panel.add(joinBtn);

			setVisible(true);
		});

	}

	public void refresh(IServerChat serverApi, UserChat user) throws RemoteException
	{
		new ListWindow("Lista de salas", serverApi, 300, 500, user);
		
		dispose();
	}
	
}
