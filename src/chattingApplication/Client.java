package chattingApplication;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Client  implements ActionListener
{
	JTextField text;
	JButton send;
	static JPanel p1;
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream dout;
	
	static JFrame f = new JFrame();
	public Client()
	{
		
		f.setLayout(null);
		JPanel j1 = new JPanel();
		j1.setBackground(new Color(123,97,208));
		j1.setBounds(0,0,450,70);
		j1.setLayout(null);
		f.add(j1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("2.png"));
		Image i2 = i1.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel ji = new JLabel(i3);
		ji.setBounds(5,20,28,28);
		j1.add(ji);
		ji.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent me)
					{
						f.setVisible(false);
					}
				});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("zoro.gif"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(45,12,50,50);
		j1.add(profile);
		
		ImageIcon vid = new ImageIcon(ClassLoader.getSystemResource("video.png"));
		Image i = vid.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon ii = new ImageIcon(i);
		JLabel video = new JLabel(ii);
		video.setBounds(300,20,30,30);
		j1.add(video);
		
		ImageIcon v1 = new ImageIcon(ClassLoader.getSystemResource("phone.png"));
		Image v2 = v1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon v3 = new ImageIcon(v2);
		JLabel call = new JLabel(v3);
		call.setBounds(350,20,30,30);
		j1.add(call);
		
		ImageIcon a1 = new ImageIcon(ClassLoader.getSystemResource("3icon.png"));
		Image a2 = a1.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon a3 = new ImageIcon(a2);
		JLabel icon = new JLabel(a3);
		icon.setBounds(400,23,10,25);
		j1.add(icon);
		
		JLabel name = new JLabel("ZORO");
		name.setForeground(Color.white);
		name.setFont(new Font("Raleway",Font.BOLD,20));
		name.setBounds(110,20,100,22);
		j1.add(name);
		
		JLabel active= new JLabel("active now");
		active.setForeground(Color.white);
		active.setFont(new Font("Raleway",Font.ITALIC,12));
		active.setBounds(110,43,100,22);
		j1.add(active);
		
		p1 = new JPanel();
		p1.setBounds(5,75,440,570);
		p1.setBackground(Color.white);
		f.add(p1);
		
		text = new JTextField();
		text.setBounds(5,650,310,40);
		text.setFont(new Font("Raleway",Font.TRUETYPE_FONT,18));
		f.add(text);
		
		send = new JButton("Send");
		send.setBounds(320,650,123,40);
		send.setFont(new Font("Raleway",Font.BOLD,18));
		send.setForeground(Color.white);
		send.setBackground(new Color(48, 31, 102));
		send.addActionListener(this);
		f.add(send);
		
		f.setBounds(800,50,450,700);
		f.setUndecorated(true);
		f.getContentPane().setBackground(Color.white);
		f.setVisible(true);
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		String out = text.getText();
		JPanel z1 = formatLabel(out);
		
		p1.setLayout(new BorderLayout());
		JPanel right = new JPanel(new BorderLayout());
		right.add(z1,BorderLayout.LINE_END);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		
		p1.add(vertical,BorderLayout.PAGE_START);
		try
		{
			dout.writeUTF(out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		text.setText("");
		
		f.repaint();
		f.invalidate();
		f.validate();
	}
	
	public static JPanel formatLabel(String out)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		JLabel msg = new JLabel("<html><p sytle = \"widht:150px\">" +out+"</p></html>");	
		msg.setFont(new Font("Raleway",Font.PLAIN,15));
		msg.setOpaque(true);
		msg.setBackground(new Color(197,180,252));
		msg.setBorder(new EmptyBorder(15,15,15,50));
		
		panel.add(msg);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		return panel;
		
	}
	
		
	public static void main(String[] args) 
	{
	   new Client();	
	   try 
	   {
		Socket s = new Socket("127.0.0.1",6001);
		DataInputStream din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		
		while(true)
		{
			p1.setLayout(new BorderLayout());
			String msg = din.readUTF();
			JPanel panel = formatLabel(msg);
			
			JPanel left = new JPanel(new BorderLayout());				
			left.add(panel,BorderLayout.LINE_START);
			
			vertical.add(Box.createVerticalStrut(15));
			p1.add(panel,BorderLayout.LINE_START);
			
			vertical.add(left);
			f.validate();
			
		}
	   } 
	   catch (Exception e) 
	   {
		e.printStackTrace();
	   }
	}

}
