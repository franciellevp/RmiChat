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
import src.rmi.Server.ServerChat;

// janela para insercao de nome da sala pelo servidor

@SuppressWarnings("serial")
public class CreateRoomServerWindow extends JFrame {
	
	final JPanel grid = new JPanel(new FlowLayout());
	final JButton btn = new JButton("Criar");
	JTextField field = new JTextField(10);
	
	public CreateRoomServerWindow(IServerChat serverApi, ServerChat server)
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
		
		// listener do campo ao teclar enter
		field.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Create(serverApi, server);
			}
		});

		// listener do botao de criar sala
		btn.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Create(serverApi, server);
			}
		});
		
	}
	
	// cria uma nova sala
	private void Create(IServerChat serverApi, ServerChat server)
	{
		try {
			serverApi.createRoom(field.getText()); // solicita ao server uma nova sala
			new ServerWindow("Controle do Servidor", serverApi, 300, 500, server); // atualiza a tela
			
			dispose(); //fecha a janela
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
