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

}
