package src.rmi.gui;

import java.awt.Color;
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

// janela de bate-papo

@SuppressWarnings("serial")
public class ChatWindow extends JFrame{
	
	final JPanel panel = new JPanel();
	final JButton sendBtn = new JButton("Enviar");
	final JButton exitBtn = new JButton("Sair da sala");
	final JTextField field = new JTextField(10);
	private List<String> messages = new ArrayList<>();
	final JList<String> list = new JList<String>();
	
	public ChatWindow(IServerChat serverApi, IRoomChat roomApi, UserChat user) {
		messages.add("Bem-vindo " + user.getUsername() + "!");
		
		list.setListData(messages.toArray(new String[messages.size()]));
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setBorder(BorderFactory.createEmptyBorder(0, 0, 500, 10));
		panel.add(scrollPane);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		setTitle("Chat");
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		sendBtn.setSize(40, 10);
		sendBtn.setAlignmentY(BOTTOM_ALIGNMENT);
		sendBtn.setAlignmentX(CENTER_ALIGNMENT);
		sendBtn.setBackground(Color.green);
		sendBtn.setOpaque(true);
		
		exitBtn.setSize(40, 10);
		exitBtn.setAlignmentY(BOTTOM_ALIGNMENT);
		exitBtn.setAlignmentX(CENTER_ALIGNMENT);

		field.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
		field.setSize(700, 20);
		panel.add(field);	
		
		panel.add(new JLabel(" "));
		panel.add(sendBtn);
		panel.add(new JLabel(" "));
		panel.add(exitBtn);
		panel.add(new JLabel(" "));
		
		add(panel);

		setSize(700, 700);
		setLocationRelativeTo(null);

		setVisible(true);

		// envia a mensagem ao teclar enter
		field.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage(roomApi, user);
			}
		});
		
		// envia a mensagem ao clicar no botao de enviar
		sendBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage(roomApi, user);
			}
		});
		
		// sai da sala ao clicar no botao de sair
		exitBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					roomApi.leaveRoom(user.getUsername()); // remove este usuario da sala
					new ListWindow("Lista de salas", serverApi, 300, 500, user); // exibe a lista de salas novamente
					dispose(); // fecha essa janela
				} catch (RemoteException e1) { // caso a sala ja tenha sido fechada pelo servidor
					try {
						new ListWindow("Lista de salas", serverApi, 300, 500, user); // apenas exibe a lista de salas novamente
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					dispose();
				}	
			}
		});
		
	}
	
	// recebe uma nova mensagem e adiciona na lista de mensagens
	public void receiveMessage(String msg)
	{
		messages.add(msg);
		list.setListData(messages.toArray(new String[messages.size()]));
		setVisible(true);
	}
	
	// envia uma mensagem
	private void sendMessage(IRoomChat roomApi, UserChat user)
	{
		if(field.getText().isBlank()) {return;} // nao envia caso nao tenha nada digitado
		try {
			roomApi.sendMsg(user.getUsername(), field.getText()); // envia a mensagem
			messages.add(user.getUsername() + ": " +  field.getText()); // adiciona a mensagem na lista
			list.setListData(messages.toArray(new String[messages.size()])); // atualiza a lista
			field.setText(""); // limpa o campo de texto
			setVisible(true); // atualiza a tela
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

}
