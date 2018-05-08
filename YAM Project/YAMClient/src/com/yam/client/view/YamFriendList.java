/**
 * 我的好友列表，包括分组
 */
package com.yam.client.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;

import com.yam.client.tools.*;
import com.yam.common.Message;
public class YamFriendList extends JFrame implements ActionListener,MouseListener{
	//处理最北部所需组件
	JButton jb1;
	
	//处理第一张卡片所需组件
	JPanel jphy1,jphy2,jphy3;
	JButton jphy_jb1,jphy_jb2,jphy_jb3;
	JScrollPane jsp1;
	JLabel[] jlbs;
	
	//处理第二张卡片所需组件
	JPanel jpmsr1,jpmsr2,jpmsr3;
	JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3;
	JScrollPane jsp2;
	JLabel[] jlbs2;
	
	//处理第三张卡片所需组件
	JPanel jphmd1,jphmd2,jphmd3;
	JButton jphmd_jb1,jphmd_jb2,jphmd_jb3;
	JScrollPane jsp3;
	JLabel[] jlbs3;
	//把一个大的JPanel设置成CardLayout布局
	CardLayout cl;
	JPanel jp;
	//参数
	String ownerId;
	//好友数量
	int num;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//YamFriendList qqFriend = new YamFriendList("1");

	}
	//更新在线好友情况
	public void updateFriendList(String onlineFriend) {
		for(int i = 0;i < this.num;i++) {
			if(jlbs[i].getText().equals(onlineFriend)) {
				jlbs[i].setEnabled(true);
				break;
			}
		}
	}
	//添加好友后立马更新
	public void addFriendUpdate(String friendId) {
		JLabel jlb = new JLabel(friendId,new ImageIcon("images/touxiang.jpeg"),JLabel.LEFT);
		this.jphy2.add(jlb);
		jlb.setEnabled(false);
		//判断该好友是否在线
		HashMap<String,YamFriendList> hm = ManageYamFriendList.getHm();
		Iterator<String> it = hm.keySet().iterator();
		while(it.hasNext()) {
			String str = it.next().toString();
			System.out.println("测试："+str);
			if(str.equals(friendId)){
				jlb.setEnabled(true);
				break;
			}
		}
	}
	
	public YamFriendList(String ownerId,Message m) {
		this.ownerId = ownerId;
		String friends[] = m.getCon().split(",");
		String allFriends[] = friends[0].split(" ");
		this.num = allFriends.length;
		String onlineFriends[] = friends[1].split(" ");
		//处理第一张卡片（显示好友列表）
		jphy_jb1 = new JButton("我的好友");
		jphy_jb2 = new JButton("陌生人");
		jphy_jb2.addActionListener(this);
		jphy_jb3 = new JButton("黑名单");
		jphy_jb3.addActionListener(this);
		//处理panel2
		//假定最多有100个好友
		jphy2 = new JPanel(new GridLayout(100,1,4,4));//4,4为行列间距
		//初始化好友
        jlbs = new JLabel[allFriends.length];
        for(int j = 0;j < jlbs.length;j++) {
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
        jphy3 = new JPanel(new GridLayout(2,1));
		//把两个按钮加入jphy3
		jphy3.add(jphy_jb2);
		jphy3.add(jphy_jb3);
		
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
		
		//处理panel1
		jpmsr1 = new JPanel(new BorderLayout());
		//将三部分添加到panel1
		jpmsr1.add(jpmsr3,BorderLayout.NORTH);//直接add(..,"SOUTH")会报错，BorderLayout还是要这样BorderLayout.NORTH这样
		jpmsr1.add(jsp2,BorderLayout.CENTER);
		jpmsr1.add(jpmsr_jb3,BorderLayout.SOUTH);
		/*第二张卡片完成*/
		
		//处理第三张卡片（黑名单）
		jphmd_jb1 = new JButton("我的好友");
		jphmd_jb1.addActionListener(this);
		jphmd_jb2 = new JButton("陌生人");
		jphmd_jb2.addActionListener(this);
		jphmd_jb3 = new JButton("黑名单");
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
		/*第三张卡片完成*/
		
		//处理最北部
		jb1 = new JButton("添加好友");
		this.getContentPane().add(BorderLayout.NORTH,jb1);
		jb1.addActionListener(this);
		
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
		this.getContentPane().add(BorderLayout.CENTER,jp);
		
		this.setSize(300, 600);
		this.setVisible(true);
		this.setTitle(ownerId);
		//this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jphy_jb2||e.getSource()==jphmd_jb2) {
			cl.show(jp, "2");
		}else if(e.getSource()==jpmsr_jb1||e.getSource()==jphmd_jb1) {
			cl.show(jp, "1");
		}else if(e.getSource()==jphy_jb3||e.getSource()==jpmsr_jb3) {
			cl.show(jp, "3");
		}else if(e.getSource()==jb1) {
			new AddFriends(this.ownerId);
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
		//响应用户双击事件，并获得好友编号
		if(e.getClickCount()==2) {
			//得到好友的编号
			String friendNum = ((JLabel)e.getSource()).getText();
			System.out.println("你希望和 "+friendNum+"聊天");
			//以下实现了对同一个好友只能打开一个聊天界面
			HashMap<String, YamChat> hm = ManageYamChat.getHm();
			Iterator it = hm.keySet().iterator();
			int test = 1;
			if(hm==null) {
				YamChat yc = new YamChat(this.ownerId,friendNum);//用this明确成员变量更好点，虽然不用也行
				//把聊天界面加入管理类
				ManageYamChat.addYamChat(this.ownerId+" "+friendNum, yc);
			}
			else {
				while(it.hasNext()) {
					if(it.next().toString().equals(this.ownerId+" "+friendNum)) {
						test = 0;
						break;
					}
				}
				if(test==1) {
					YamChat yc = new YamChat(this.ownerId,friendNum);
					//把聊天界面加入管理类
					ManageYamChat.addYamChat(this.ownerId+" "+friendNum, yc);
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

}
