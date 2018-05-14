/**
 * You And Me 客户端登录界面
 */
package com.yam.client.view;
import javax.swing.*;

import com.yam.client.model.YamClientConServer;
import com.yam.client.tools.ManageClientConServerThread;
import com.yam.client.tools.ManageYamFriendList;
import com.yam.common.Message;
import com.yam.common.MessageType;
import com.yam.common.User;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
public class YamClientLogin extends JFrame implements ActionListener,KeyListener{
	/**
	 * 
	 */
	//一个内部类专门用于键盘监听(更清晰的一种方式)

	private static final long serialVersionUID = 1L;
	//框架所需组件
	JLabel jl1;
	ImageIcon img;
	//定义北边需要组件
	JLabel jl2;
	
	//定义中部需要组件
	JPanel jp2;
	JLabel jp2_jl1,jp2_jl2,jp2_jl3,jp2_jl4;
	JCheckBox jp2_jcb1,jp2_jcb2;
	JTextField jp2_jtf;
	JPasswordField jp2_jpf;
	JButton jp2_jb1;
	
	//定义南部需要组件
	JPanel jp1;
	JButton jp1_jb1,jp1_jb2,jp1_jb3;
	
	public static void main(String[] args) {
		YamClientLogin clientLogin = new YamClientLogin();
	}
	public YamClientLogin() {
		//处理框架
		this.setBounds(400, 200, 400, 400);
		this.setTitle("You And Me");
		/*//设置背景图
		img  = new ImageIcon("images/background.jpg");
		jl1 = new JLabel(img);
		this.jl1.setBounds(0, 0, 500, 300);
		this.getLayeredPane().add(jl1,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);
		*/
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//处理北部
		jl2 = new JLabel(new ImageIcon("images/800.jpg"));
		this.getContentPane().add(BorderLayout.NORTH, jl2);
		
		//处理南部
		jp1 = new JPanel();
		jp1_jb1 = new JButton("注册");
		//相应用户点击注册
		jp1_jb1.addActionListener(this);
		jp1_jb2 = new JButton("登录");
		//响应用户点击登录
	    jp1_jb2.addActionListener(this);
		jp1_jb3 = new JButton("关于YAM");
		jp1.add(jp1_jb3);
		jp1.add(jp1_jb1);
		jp1.add(jp1_jb2);
		this.getContentPane().add(BorderLayout.SOUTH,jp1);
		//jp1_jb1.setBounds(200, 240, 30, 20);
		//jp1_jb2.setBounds(300, 240, 30, 20);
		
		//处理中部
		jp2 = new JPanel(new GridLayout(3,3));
		jp2_jl1 = new JLabel("用户名/手机/邮箱：",JLabel.RIGHT);//设置JLabel对齐方式
		jp2_jl2 = new JLabel("密码：",JLabel.RIGHT);
		jp2_jl3 = new JLabel("忘记密码",JLabel.CENTER);
		jp2_jl3.setForeground(Color.RED);
		jp2_jl4 = new JLabel("     ");
		jp2_jtf = new JTextField();
		jp2_jtf.addKeyListener(this);//如果只监听用户名这文本框，只有光标移到这里才能回车有效，要想放到密码那里也行，得对密码框也进行监听
		jp2_jpf = new JPasswordField();
		jp2_jpf.addKeyListener(this);//这也印证了为什么做键盘监听时监听对象不能是JFrame,因为JFrame不是输入这些后停留的地方。
		jp2_jcb1 = new JCheckBox("记住密码");
		jp2_jcb2 = new JCheckBox("自动登录");
		jp2_jb1 = new JButton("清除");
		jp2_jb1.addActionListener(this);
		jp2.add(jp2_jl1);
		jp2.add(jp2_jtf);
		jp2.add(jp2_jb1);
		jp2.add(jp2_jl2);
		jp2.add(jp2_jpf);
		jp2.add(jp2_jl3);
		jp2.add(jp2_jcb1);
		jp2.add(jp2_jcb2);
		jp2.add(jp2_jl4);
		this.getContentPane().add(BorderLayout.CENTER, jp2);
		//this.setAlwaysOnTop(true);//设置一直在最前面

		
		this.setVisible(true);//必须放在最后，对控件也有影响
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//如果用户点击登录
		if(e.getSource()==jp1_jb2) {
			User u = new User();
			u.setUserId(jp2_jtf.getText().trim());//trim()忽略两边空格
			u.setPasswd(new String(jp2_jpf.getPassword()));//因为.getPassword()返回字符数组类型
			
			if(new YamClientConServer().sendLoginInfoToServer(u)) {
				//YamFriendList yamFriendList = new YamFriendList(u.getUserId());
				//ManageYamFriendList.addYamFriendList(u.getUserId(), yamFriendList);
				//发送一个要求返回在线好友的请求包}
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().getOutputStream());
					//做一个message包
					Message m = new Message();
					m.setMesType(MessageType.message_get_onLineFriend);
					//指明要哪个号的好友列表
					m.setSender(u.getUserId());
					oos.writeObject(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//同时关闭登录界面
				this.dispose();//不关闭的话可以new出很多个好友列表，哈哈
			}else {
				//弹出对话框
				JOptionPane.showMessageDialog(this, "用户名或密码错误或者该用户已经在线");
			}
		}else if(e.getSource()==jp2_jb1) {
			jp2_jtf.setText("");
		}else if(e.getSource()==jp1_jb1) {
			new YamUserRegister();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			User u = new User();
			u.setUserId(jp2_jtf.getText().trim());//trim()忽略两边空格
			u.setPasswd(new String(jp2_jpf.getPassword()));//因为.getPassword()返回字符数组类型
			
			if(new YamClientConServer().sendLoginInfoToServer(u)) {
				//YamFriendList yamFriendList = new YamFriendList(u.getUserId());
				//ManageYamFriendList.addYamFriendList(u.getUserId(), yamFriendList);
				//发送一个要求返回在线好友的请求包
				//禁止重复登录
				HashMap hm = ManageClientConServerThread.getHm();
				Iterator it = hm.keySet().iterator();
				String str = it.next().toString();
				while(it.hasNext()) {
					if(str.equals(it.next())) {
						JOptionPane.showMessageDialog(this,"不能重复登录");
					}
				}
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().getOutputStream());
					//做一个message包
					Message m = new Message();
					m.setMesType(MessageType.message_get_onLineFriend);
					//指明要哪个号的好友列表
					m.setSender(u.getUserId());
					oos.writeObject(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//同时关闭登录界面
				this.dispose();//不关闭的话可以new出很多个好友列表，哈哈
			}else {
				//弹出对话框
				JOptionPane.showMessageDialog(this, "用户名或密码错误或者该用户已经登录");
			}
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
