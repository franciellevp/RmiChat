package src.rmi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import src.rmi.Room.IRoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.Server.ServerChat;
import src.rmi.utils.Constants;

// exibe a lista de salas do servidor

@SuppressWarnings("serial")
public class ServerWindow  extends JFrame {

	private JList<Object> list;
	private JLabel content = new JLabel();
	private JPanel panel = new JPanel();
	private JPanel underPanel= new JPanel();
	
	public JButton closeBtn = new JButton("Fechar sala");
	public JButton refreshBtn = new JButton("Atualizar lista");
	public JButton createBtn = new JButton("Criar sala");
	private String[] array;
	
	public int index = 0;
	
	public ServerWindow(String title, IServerChat serverApi, int width, int height, ServerChat server) throws RemoteException {
		array = serverApi.getRooms().toArray(new String[0]);
		list = new JList<Object>();
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

		// listener do botao de fechar sala
		closeBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI + Constants.SERVER); // instancia do serverApi
			    	String roomname = serverApi.getRooms().get(index); // pega a referencia do nome da sala
			        IRoomChat roomApi = (IRoomChat) Naming.lookup(Constants.URI + roomname); // instancia do roomApi
			        
		            server.removeRoom(roomname); // retira a sala da roomList do server
			        roomApi.closeRoom(); // fecha a sala
			        refresh(serverApi, server); // atualiza a lista de salas
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// listener do botao de atualizar lista de salas
		refreshBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					refresh(serverApi, server);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// listener do botao de criar uma sala
		createBtn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// abre a janela de insercao de nome de sala
				new CreateRoomServerWindow(serverApi, server);
				dispose();
			}
		});
		
		// listener de selecao de sala
		list.addListSelectionListener(e -> {
			index = list.getSelectedIndex();
			String selected = array[index];

			// atualiza o nome da sala selecionada na UI
			content.setText(selected);
			// exibe o nome da sala e o botao de fechar sala
			panel.add(content);
			panel.add(closeBtn);

			setVisible(true);
		});

	}

	public void refresh(IServerChat serverApi, ServerChat server) throws RemoteException
	{
		// abre a janela novamente, exibindo uma lista atualizada
		new ServerWindow("Controle do Servidor", serverApi, 300, 500, server);
		
		dispose();
	}
	
}
