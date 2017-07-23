package LinkedIn.Contact.chat;

import javax.servlet.http.HttpSessionBindingEvent;
/**
 * �û�������
 * @author Seavan_CC
 *
 */
public class UserListener implements
javax.servlet.http.HttpSessionBindingListener{
	private String user;
	private UserInfo container = UserInfo.getInstance();
	//�޲ι��췽��
	public UserListener() {
		user = "";
	}
	// �������߼�����Ա
	public void setUser(String user) {
		this.user = user;
	}

	// ��ȡ���߼���
	public String getUser() {
		return this.user;
	}
	
	// ��Session�ж������ʱִ�еķ���
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("�����û�Ϊ"+ user);
		
	}
	// ��Session�ж����Ƴ�ʱִ�еķ���
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println(this.user+"������...");
		if (user!="") {
			container.removeUser(user);
		}
		
	}

}
