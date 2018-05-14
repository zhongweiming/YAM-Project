/**
 * 定义包的种类
 */
package com.yam.common;

public interface MessageType {
	String message_login_request = "0";//请求登录的包
	String message_login_succeed = "1";//表明登录成功
	String message_login_failed = "2";//表明登录失败
	String message_comm_mes = "3";//普通信息包
	String message_get_onLineFriend = "4";//要求在线好友的包
	String message_ret_onLineFriend = "5";//返回在线好友的包
	String message_register_request = "6";//请求注册的包
	String message_register_succeed = "7";//注册成功的包
	String message_register_failed = "8";//注册失败的包
	String message_addfriends_request = "9";//添加好友请求的包
	String message_addfriends_succeed = "10";//添加好友成功的包
	String message_addfriends_failed = "11";//添加好友失败的包
	String message_wasaddfriends_succeed = "12";//被添加好友成功的包
	String message_userexit = "13";//用户退出的包
	String message_update_friendState = "14";//更新好友状态的包
	String message_addgroup_request = "15";//建群请求的包
	String message_addgroup_succeed = "16";//建群成功的包
	String message_addgroup_failed = "17";//建群失败的包
	String message_wasaddgroup = "18";//被添加进群的包
	String message_groupmember_request = "19";//要求返回群聊成员的包
	String message_groupmember_ret = "20";//返回群聊成员的包
	String message_group_mes = "21";//发送群聊消息的包
	String message_group_trans_mes = "22";//转发的群聊消息的包

}
