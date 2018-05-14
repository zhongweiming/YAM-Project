/**
 * 我的好友列表，包括分组
 */
package com.yam.client.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import com.yam.client.tools.*;
import com.yam.common.Message;
import com.yam.common.MessageType;
public class YamFriendList extends JFrame implements ActionListener,MouseListener,WindowListener{
	//处理最北部所需组件
	JButton jb1,jb2;
	JPanel jp_north;
	
	//处理第一张卡片所需组件
	JPanel jphy1,jphy2,jphy3;
	JButton jphy_jb1,jphy_jb2,jphy_jb3,jphy_jb4;
	JScrollPane jsp1;
	JLabel[] jlbs = new JLabel[100] ;//假设最多100好友
	
	//处理第二张卡片所需组件
	JPanel jpmsr1,jpmsr2,jpmsr3,jpmsr4;
	JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3,jpmsr_jb4;
	JScrollPane jsp2;
	JLabel[] jlbs2 ;
	
	//处理第三张卡片所需组件
	JPanel jphmd1,jphmd2,jphmd3;
	JButton jphmd_jb1,jphmd_jb2,jphmd_jb3,jphmd_jb4;
	JScrollPane jsp3;
	JLabel[] jlbs3;
	
	//处理第四张卡片所需组件
	JPanel jpql1,jpql2,jpql3;
	JButton jpql_jb1,jpql_jb2,jpql_jb3,jpql_jb4;
	JScrollPane jsp4;
	JLabel[] jlbs4;
	
	//把一个大的JPanel设置成CardLayout布局
	CardLayout cl;
	JPanel jp;
	//参数
	String ownerId;
	//好友数量
	int friendNum;
	//群聊数量
	int groupNum;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//YamFriendList qqFriend = new YamFriendList("1");

	}
	//更新好友情况
	public void updateFriendList(Message m) {
		String friend = m.getSender();
		if(m.getCon().equals("I'm on")){
			for(int i = 0;i < this.friendNum;i++) {
				if(jlbs[i].getText().equals(friend)) {
					jlbs[i].setEnabled(true);
					break;
				}
			}
		}else if(m.getCon().equals("I'm off")) {
			for(int i = 0;i < this.friendNum;i++) {
				if(jlbs[i].getText().equals(friend)) {
					jlbs[i].setEnabled(false);
					break;
				}
			}
		}
		
	}
	//添加好友后立马更新
	public void addFriendUpdate(String friendIdState) {
		String[] str = friendIdState.split(",");
		String friendId = str[0];
		String state = str[1];
		jlbs[this.friendNum] = new JLabel(friendId,new ImageIcon("images/touxiang.jpeg"),JLabel.LEFT);
		jlbs[this.friendNum].addMouseListener(this);
		this.jphy2.add(jlbs[this.friendNum]);
		if(state.equals("true")) {
			jlbs[this.friendNum].setEnabled(true);
		}else {
			jlbs[this.friendNum].setEnabled(false);
		}
		this.friendNum ++;//每成功添加一个好友，好友数量加1
	}
	//建群成功后后立马更新
		public void addGroupUpdate(String groupName) {
			jlbs4[this.groupNum] = new JLabel(groupName,new ImageIcon("images/touxiang.jpeg"),JLabel.LEFT);
			jlbs4[this.groupNum].addMouseListener(this);
			this.jpql2.add(jlbs4[this.groupNum]);
			jlbs4[this.groupNum].setEnabled(true);
			this.groupNum ++;//每成功创建一个群，群数量加1
		}
	
	public YamFriendList(String ownerId,Message m) {
		this.addWindowListener(this);
		this.ownerId = ownerId;
		String friends[] = m.getCon().split(",");
		String allFriends[] = friends[0].split(" ");
		this.friendNum = allFriends.length;
		String onlineFriends[] = friends[1].split(" ");
		//处理第一张卡片（显示好友列表）
		jphy_jb1 = new JButton("我的好友");
		jphy_jb2 = new JButton("陌生人");
		jphy_jb2.addActionListener(this);
		jphy_jb3 = new JButton("黑名单");
		jphy_jb3.addActionListener(this);
		jphy_jb4 = new JButton("群聊");
		jphy_jb4.addActionListener(this);
		//处理panel2
		//假定最多有100个好友
		jphy2 = new JPanel(new GridLayout(100,1,4,4));//4,4为行列间距
		//初始化好友
        for(int j = 0;j < allFriends.length;j++) {
        	jlbs[j] = new JLabel(allFriends[j],new ImageIcon("images/touxiang.jpeg"),JLabel.LEFT);
        	jlbs[j].setEnabled(false);
        	jlbs[j].addMouseListener(this);
        	jphy2.add(jlbs[j]);
        }
        for(int i = 0;i < allFriends.length;i++) {
        	for(int j = 0;j < onlineFriends.length;j++) {
        		if(allFriends[i].equals(onlineFriends[j])) {
        			jlbs[i].setEnabled(true);
        		}
        	}
        }
        //将第二个panel添加到滚动框中
        jsp1 = new JScrollPane(jphy2);
        
        //处理panel3
        jphy3 = new JPanel(new GridLayout(3,1));
		//把三个按钮加入jphy3
		jphy3.add(jphy_jb2);
		jphy3.add(jphy_jb3);
		jphy3.add(jphy_jb4);
		//处理panel1
		jphy1 = new JPanel(new BorderLayout());
		//将三部分添加到panel1
		jphy1.add(jphy_jb1,BorderLayout.NORTH);//直接add(..,"SOUTH")会报错，BorderLayout还是要这样BorderLayout.NORTH这样
		jphy1.add(jsp1,BorderLayout.CENTER);
		jphy1.add(jphy3,BorderLayout.SOUTH);
		/*第一张卡片完成*/
		
		//处理第二张卡片(陌生人)
		jpmsr_jb1 = new JButton("我的好友");
		jpmsr_jb1.addActionListener(this);
		jpmsr_jb2 = new JButton("陌生人");
		jpmsr_jb3 = new JButton("黑名单");
		jpmsr_jb3.addActionListener(this);
		jpmsr_jb4 = new JButton("群聊");
		jpmsr_jb4.addActionListener(this);
		//处理panel2
		//假定有20个陌生人
		jpmsr2 = new JPanel(new GridLayout(20,1,4,4));//4,4为行列间距
		//初始化20好友
        jlbs2 = new JLabel[20];/*
        for(int j = 0;j < jlbs2.length;j++) {
        	jlbs2[j] = new JLabel(j+1+"",new ImageIcon("images/touxiang.jpeg"),JLabel.LEFT);
        	jlbs2[j].setEnabled(false);
        	if(jlbs2[j].getText().equals(ownerId)) {
        		jlbs2[j].setEnabled(true);
        	}
        	jlbs2[j].addMouseListener(this);
        	jpmsr2.add(jlbs2[j]);
        }*/
        //将第二个panel添加到滚动框中
        jsp2 = new JScrollPane(jpmsr2);
        
        //处理panel3
        jpmsr3 = new JPanel(new GridLayout(2,1));
		//把两个按钮加入jpmsr3
		jpmsr3.add(jpmsr_jb1);
		jpmsr3.add(jpmsr_jb2);
		//处理panel4
		jpmsr4 = new JPanel(new GridLayout(2,1));
		jpmsr4.add(jpmsr_jb3);
		jpmsr4.add(jpmsr_jb4);
		//处理panel1
		jpmsr1 = new JPanel(new BorderLayout());
		//将三部分添加到panel1
		jpmsr1.add(jpmsr3,BorderLayout.NORTH);//直接add(..,"SOUTH")会报错，BorderLayout还是要这样BorderLayout.NORTH这样
		jpmsr1.add(jsp2,BorderLayout.CENTER);
		jpmsr1.add(jpmsr4,BorderLayout.SOUTH);
		/*第二张卡片完成*/
		
		//处理第三张卡片（黑名单）
		jphmd_jb1 = new JButton("我的好友");
		jphmd_jb1.addActionListener(this);
		jphmd_jb2 = new JButton("陌生人");
		jphmd_jb2.addActionListener(this);
		jphmd_jb3 = new JButton("黑名单");
		jphmd_jb4 = new JButton("群聊");
		jphmd_jb4.addActionListener(this);
		//处理panel2
		//假定有10个黑名单
		jphmd2 = new JPanel(new GridLayout(10,1,4,4));//4,4为行列间距
		//初始化20好友
        jlbs3 = new JLabel[10];/*
        for(int j1 = 0;j1 < jlbs3.length;j1++) {
        	jlbs3[j1] = new JLabel(j1+1+"",new ImageIcon("images/touxiang.jpeg"),JLabel.LEFT);
        	jlbs3[j1].setEnabled(false);
        	if(jlbs3[j1].getText().equals(ownerId)) {
        		jlbs3[j1].setEnabled(true);
        	}
        	jlbs3[j1].addMouseListener(this);
        	jphmd2.add(jlbs3[j1]);
        }*/
        //将第二个panel添加到滚动框中
        jsp3 = new JScrollPane(jphmd2);
        
        //处理panel3
        jphmd3 = new JPanel(new GridLayout(3,1));
		//把三个按钮加入jphmd3
		jphmd3.add(jphmd_jb1);
		jphmd3.add(jphmd_jb2);
		jphmd3.add(jphmd_jb3);
		
		//处理panel1
		jphmd1 = new JPanel(new BorderLayout());
		//将两部分添加到panel1
		jphmd1.add(jphmd3,BorderLayout.NORTH);//直接add(..,"SOUTH")会报错，BorderLayout还是要这样BorderLayout.NORTH这样
		jphmd1.add(jsp3,BorderLayout.CENTER);
		jphmd1.add(jphmd_jb4,BorderLayout.SOUTH);
		/*第三张卡片完成*/
		
		//获取第四张卡片所需信息
		String[] allGroups = m.getCon2().split(" ");
		this.groupNum = allGroups.length;
		//处理第四张卡片（显示群聊列表）
		jpql_jb1 = new JButton("我的好友");
		jpql_jb1.addActionListener(this);
		jpql_jb2 = new JButton("陌生人");
		jpql_jb2.addActionListener(this);
		jpql_jb3 = new JButton("黑名单");
		jpql_jb3.addActionListener(this);
		jpql_jb4 = new JButton("群聊");
		//处理panel2
		//假定最多有20个群聊
		jpql2 = new JPanel(new GridLayout(20,1,4,4));//4,4为行列间距
		jlbs4 = new JLabel[20];
		for(int i = 0;i < allGroups.length;i++) {
			jlbs4[i] = new JLabel(allGroups[i],new ImageIcon("images/touxiang.jpeg"),JLabel.LEFT);
			jlbs4[i].setEnabled(true);
			jlbs4[i].addMouseListener(this);
			jpql2.add(jlbs4[i]);
		}
		//将第二个panel添加到滚动框中
		jsp4 = new JScrollPane(jpql2);
		        
		//处理panel3
		jpql3 = new JPanel(new GridLayout(4,1));
		//把四个按钮加入jphy3
		jpql3.add(jpql_jb1);
		jpql3.add(jpql_jb2);
		jpql3.add(jpql_jb3);
		jpql3.add(jpql_jb4);
		
		//处理panel1
		jpql1 = new JPanel(new BorderLayout());
		//将三部分添加到panel1
		jpql1.add(jpql3,BorderLayout.NORTH);//直接add(..,"SOUTH")会报错，BorderLayout还是要这样BorderLayout.NORTH这样
		jpql1.add(jsp4,BorderLayout.CENTER);
		/*第四张卡片完成*/
				
		
		
		//处理最北部
		jb1 = new JButton("添加好友");
		jb2 = new JButton("建立群聊");
		jp_north = new JPanel();
		jp_north.add(jb1);
		jp_north.add(jb2);
		this.getContentPane().add(BorderLayout.NORTH,jp_north);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		//处理好友列表整个布局
		cl = new CardLayout();
		jp = new JPanel();
		jp.setLayout(cl);
//		this.setLayout(cl);
//		this.add(jphy1,"1");
//		this.add(jpmsr1,"2");
//		this.add(jphmd1, "3");
		jp.add(jphy1,"1");
		jp.add(jpmsr1,"2");
		jp.add(jphmd1, "3");
		jp.add(jpql1,"4");
		this.getContentPane().add(BorderLayout.CENTER,jp);
		
		this.setSize(300, 600);
		this.setVisible(true);
		this.setTitle(ownerId);
		//this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jphy_jb2||e.getSource()==jphmd_jb2||e.getSource()==jpql_jb2) {
			cl.show(jp, "2");
		}else if(e.getSource()==jpmsr_jb1||e.getSource()==jphmd_jb1||e.getSource()==jpql_jb1) {
			cl.show(jp, "1");
		}else if(e.getSource()==jphy_jb3||e.getSource()==jpmsr_jb3||e.getSource()==jpql_jb3) {
			cl.show(jp, "3");
		}else if(e.getSource()==jphy_jb4||e.getSource()==jpmsr_jb4||e.getSource()==jphmd_jb4) {
			cl.show(jp, "4");
		}else if(e.getSource()==jb1) {
			AddFriends ad = new AddFriends(this.ownerId);
			ManageAddFriends.add(this.ownerId, ad);
		}else if(e.getSource()==jb2) {
			AddGroup ag = new AddGroup(this.ownerId);
			ManageAddGroup.add(this.ownerId, ag);
		}
	}
	public void mouseEntered(MouseEvent e) {
		JLabel j1 = (JLabel)e.getSource();
		j1.setForeground(Color.RED);
	}
	public void mouseExited(MouseEvent e) {
		JLabel j1 = (JLabel)e.getSource();
		j1.setForeground(Color.BLACK);
	}
	@SuppressWarnings("unused")
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//响应用户双击事件，先判断点击的是用户还是群聊(这里先假设群聊名和用户名不同)
		
		if(e.getClickCount()==2) {
		
			boolean judge = false;
			String unknownName = ((JLabel)e.getSource()).getText();
			for(int i = 0;i<this.groupNum;i++) {
				if(unknownName.equals(jlbs4[i].getText())) {
					judge = true;
					break;
				}
			}
			if(judge) {
				//如果是群聊
				//得先向服务器发送请求，返回群聊成员和其状态
				Message m = new Message();
				m.setMesType(MessageType.message_groupmember_request);
				m.setSender(this.ownerId);
				m.setCon(unknownName);
				try {
					new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(this.ownerId).getS().getOutputStream()).writeObject(m);;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}else {
				//如果是普通聊天
				System.out.println("你希望和 "+unknownName+"聊天");
				//以下实现了对同一个好友只能打开一个聊天界面
				HashMap<String, YamChat> hm = ManageYamChat.getHm();
				Iterator it = hm.keySet().iterator();
				int test = 1;
				if(hm==null) {
					YamChat yc = new YamChat(this.ownerId,unknownName);//用this明确成员变量更好点，虽然不用也行
					//把聊天界面加入管理类
					ManageYamChat.addYamChat(this.ownerId+" "+unknownName, yc);
				}
				else {
					while(it.hasNext()) {
						if(it.next().toString().equals(this.ownerId+" "+unknownName)) {
							test = 0;
							break;
						}
					}
					if(test==1) {
						YamChat yc = new YamChat(this.ownerId,unknownName);
						//把聊天界面加入管理类
						ManageYamChat.addYamChat(this.ownerId+" "+unknownName, yc);
					}
				}
			}
			
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//退出之前先给服务器发送一个消息自己退出了。
		Message m = new Message();
		m.setMesType(MessageType.message_userexit);
		m.setSender(ownerId);
		try {
			new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream()).writeObject(m);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ManageClientConServerThread.remove(ownerId);
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
