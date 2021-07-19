package src.rmi.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import src.rmi.Room.IRoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.User.UserChat;

public class ChatWindow extends JFrame{

	public ChatWindow(IServerChat serverApi, IRoomChat roomApi, UserChat user) {
		final JPanel panel = new JPanel();
		final JButton exitBtn = new JButton("Sair");
		final JTextField field = new JTextField(10);
		
		List<String> messages = new ArrayList<>(100);
		
		final JList<String> list = new JList();
		
		messages.add("Bem-vindo " + user.getUsername() + "!");
		
		list.setListData(messages.toArray(new String[messages.size()]));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setBorder(BorderFactory.createEmptyBorder(0, 0, 500, 10));
		panel.add(scrollPane);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		setTitle("Chat");
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		exitBtn.setSize(40, 10);
		exitBtn.setAlignmentY(BOTTOM_ALIGNMENT);
		exitBtn.setAlignmentX(CENTER_ALIGNMENT);

		field.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
		field.setSize(700, 20);
		panel.add(field);
		
		panel.add(new JLabel(" "));
		panel.add(exitBtn);
		panel.add(new JLabel(" "));
		
		add(panel);

		setSize(700, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
		
		field.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					roomApi.sendMsg(user.getUsername(), field.getText());
					messages.add(user.getUsername() + ": " +  field.getText());
					list.setListData(messages.toArray(new String[messages.size()]));
					field.setText("");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		exitBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					roomApi.leaveRoom(user.getUsername());
					dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		
	}

}
