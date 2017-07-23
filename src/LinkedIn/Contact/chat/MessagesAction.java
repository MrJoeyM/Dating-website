package LinkedIn.Contact.chat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
/**
 * �Ի���Servlet
 * @author Seavan_CC
 *
 */

public class MessagesAction extends HttpServlet {

	public MessagesAction() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("getMessages".equals(action)) {		//��XML�ļ��ж�ȡ������Ϣ
			this.getMessages(request, response);
		} else if ("sendMessage".equals(action)) {	//����������Ϣ
			this.sendMessages(request, response);
		} else if ("loginRoom".equals(action)) {	//��¼ʱ��д��ϵͳ����
			this.loginRoom(request, response);
		}
	}
	// ��ȡ����������Ϣ��XML�ļ�
	public void getMessages(HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		String fileURL = createFile(request, response); // ���ļ�������ʱ�������ļ�
		
		/*******************��ʼ���������������ݵ�XML�ļ�**********************/
		Document feedDoc =null;
		try {
			SAXBuilder builder = new SAXBuilder();
			feedDoc = builder.build(fileURL); //�õ�xml���ļ�
			Element root = feedDoc.getRootElement();			//��ȡ���ڵ�
			Element channel = root.getChild("messages");		//��ȡmessages�ڵ� ����һ��xpath����
			Iterator items = channel.getChildren("message").iterator();		//��ȡmessage�ڵ�
			String messages = "";
			// ��ȡ��ǰ�û�
			HttpSession session = request.getSession();
			String userName = "";
			if (null == session.getAttribute("username")) {
				request.setAttribute("messages", "error");		//��������Ϣ����ʾ�û��˻��Ѿ�����
			} else {
				userName = session.getAttribute("username").toString();
				DateFormat df = DateFormat.getDateTimeInstance();//�õ�ʱ��
				while (items.hasNext()) {
					Element item = (Element) items.next();
					String sendTime = item.getChildText("sendTime");	//��ȡ����ʱ��
					try {
						if (df.parse(sendTime).after(
								df.parse(session.getAttribute("loginTime").toString()))
								|| sendTime.equals(session.getAttribute("loginTime").toString())) {
							String from = item.getChildText("from");	//��ȡ������
							String face = item.getChildText("face");	//��ȡ����
							String to = item.getChildText("to");		//��ȡ������
							String content = item.getChildText("content");	//��ȡ��������
							boolean isPrivate = Boolean.valueOf(item.getChildText("isPrivate"));
							if (isPrivate) {		//��ȡ˽������
								if (userName.equals(to)
										|| userName.equals(from)) {
									messages += "<font color='red'>[˽�˶Ի�]</font><font color='blue'><b>"
											+ from
											+ "</b></font><font color='#CC0000'>"
											+ face
											+ "</font><font color='green'>["
											+ to
											+ "]</font>"
											+ "&nbsp;<font color='gray'>["
											+ sendTime + "]</font><br>"
											+ content+"<br/>";
								}
							} else if ("[ϵͳ����]".equals(from)) {	//��ȡϵͳ������Ϣ
								
							} else {		//��ȡ��ͨ������Ϣ
								messages += "<li class='send'><font color='#9AFF9A'><b>" + from
										+ "</b></font><font >"
										+ face
										+ "</font></font>"
										+ "&nbsp;&nbsp;&nbsp;<font color='#97FFFF'>["
										+ sendTime + "]</font><br>"
										+ content+"<br/></li>";
							}
						}
					} catch (Exception e) {
						System.out.println("˵����" + e.getMessage());
					}
				}
				request.setAttribute("messages", messages);		//�����ȡ��������Ϣ
			}
			//ת�������ݵ�jsp
			request.getRequestDispatcher("content.jsp").forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// ��¼ʱ��д��ϵͳ����

	public void loginRoom(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		HttpSession session = request.getSession();
		//�����ַ���������
		StringUtils su=new StringUtils();
		//ת������ĸ�ʽ
		String username=su.toGBK(request.getSession().getAttribute("stuNumber").toString());	//��õ�¼�û���
		String userip=su.toGBK(request.getParameter("ip"));		//��õ�¼IP
		UserInfo user=UserInfo.getInstance();		//���UserInfo��Ķ���
		session.setMaxInactiveInterval(600);		//����Session�Ĺ���ʱ��Ϊ10����
		Vector vector=user.getList();
		boolean flag=true;		//����Ƿ��¼�ı���
		//�ж��û��Ƿ��¼
		if(vector!=null&&vector.size()>0){
			//ѭ�������������Ƿ��к��û�����ȵ�
			for(int i=0;i<vector.size();i++){
				if(user.equals(vector.elementAt(i))){
					PrintWriter out = null;
					try {
						out = response.getWriter();
						out.println("<script language='javascript'>alert('���û��Ѿ���¼');window.location.href='LoginPage.jsp.jsp';</script>");
					} catch (IOException e) {
						e.printStackTrace();
					}
					flag=false;
					break;
				}
			}
		}
		//�����û���Ϣ
		if(flag){
			UserListener ul=new UserListener();					//����UserListener�Ķ���
			//����һ���ַ�����ȥ �����û��� ��ip �����ȥ
			ul.setUser(username+"("+userip+")");								//����û�
			user.addUser(ul.getUser());							//����û���UserInfo��Ķ�����
			session.setAttribute("user2",ul);					//��UserListener����󶨵�Session��
			session.setAttribute("username",username);			//���浱ǰ��¼���û���
			session.setAttribute("loginip",userip);				//���浱ǰ��¼��IP
			session.setAttribute("loginTime",new Date().toLocaleString());		//�����¼ʱ��
		}
		/** *******************��ʼϵͳ����********************************** */
		String fileURL = createFile(request, response); // ���ļ�������ʱ�������ļ�
		// ��ȡ��ǰ�û�
		SAXBuilder builder = new SAXBuilder();
		try {
			Document feedDoc = builder.build(fileURL);

		Element root = feedDoc.getRootElement();
		Element channel = root.getChild("messages");
		Element newNode = new Element("message");
		channel.addContent(newNode); // ����messages�ڵ�
		Element fromNode = new Element("from").setText("[ϵͳ����]");
		newNode.addContent(fromNode);
		Element faceNode = new Element("face").setText("");
		newNode.addContent(faceNode);
		Element toNode = new Element("to").setText("");
		newNode.addContent(toNode);
		Element contentNode = new Element("content")
				.setText("<font color='gray'>[" + username + "]("+userip+")��ʼ����</font>");
		newNode.addContent(contentNode);
		// ��¼ʱ��
		Element sendTimeNode = new Element("sendTime").setText(new Date()
				.toLocaleString());
		newNode.addContent(sendTimeNode);
		Element isPrivateNode = new Element("isPrivate").setText("false");
		newNode.addContent(isPrivateNode);
		//��ת����½�ɹ��Ľ���
		request.getRequestDispatcher("login_ok.jsp").forward(request, response);
		XMLOutputter xml = new XMLOutputter(Format.getPrettyFormat());
		xml.output(feedDoc, new FileOutputStream(fileURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����������Ϣ
	public void sendMessages(HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=GBK");
		StringUtils su = new StringUtils();
		//����Ķ���
		Random random = new Random();
		String from = su.toUTF8(request.getParameter("from"));			//������
		String face = su.toUTF8(request.getParameter("face"));			//����
		String to = su.toUTF8(request.getParameter("to"));				//������
		String color = request.getParameter("color");					//������ɫ
		String content = su.toUTF8(request.getParameter("content"));	
		System.out.println(content);//��������
		String isPrivate = request.getParameter("isPrivate");			//�Ƿ�Ϊ���Ļ�
		@SuppressWarnings("deprecation")
		String sendTime = new Date().toLocaleString();					//����ʱ��
		/** *******************��ʼ���������Ϣ********************************** */
		String fileURL = createFile(request, response); // ���ļ�������ʱ�������ļ�
		SAXBuilder builder = new SAXBuilder();
		Document feedDoc;
		try {
			feedDoc = builder.build(fileURL);
		
		Element root = feedDoc.getRootElement();
		Element channel = root.getChild("messages");
		Element newNode = new Element("message");
		channel.addContent(newNode); // ����messages�ڵ�
		Element fromNode = new Element("from").setText(from);
		newNode.addContent(fromNode);			//��ӷ������ӽڵ�
		Element faceNode = new Element("face").setText(face);
		newNode.addContent(faceNode);			//��ӱ����ӽڵ�
		Element toNode = new Element("to").setText(to);
		newNode.addContent(toNode);			//��ӽ������ӽڵ�
		Element contentNode = new Element("content").setText("<font color='"
				+ color + "'>" + content + "</font>");
		newNode.addContent(contentNode);			//������������ӽڵ�
		// System.out.println("���͵���Ϣ��"+from+face+to+content);
		// ����ʱ��
		Element sendTimeNode = new Element("sendTime").setText(sendTime);
		newNode.addContent(sendTimeNode);			//��ӷ���ʱ���ӽڵ�
		Element isPrivateNode = new Element("isPrivate").setText(isPrivate);
		newNode.addContent(isPrivateNode);			//����Ƿ�Ϊ���Ļ��ӽڵ�
		//ת���Ľ���
		request.getRequestDispatcher(
				"MessagesAction?action=getMessages&nocache="
						+ random.nextInt(10000)).forward(request, response);
		XMLOutputter xml = new XMLOutputter(Format.getPrettyFormat());
		xml.output(feedDoc, new FileOutputStream(fileURL));

		/** *****************��ӽ���******************************* */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// ����������������XML�ļ��������жϸ��ļ��Ƿ���ڣ���������ڽ��������ļ�
	public String createFile(HttpServletRequest request,
			HttpServletResponse response) {
		Date date = new Date();
		String newTime = new  SimpleDateFormat("yyyyMMdd").format(date);
		//�õ��ļ���·�� ������
		String fileURL = getServletContext().getRealPath("/")+"xml\\"+newTime + ".xml";
		System.err.println("�ļ���·��Ϊ----��"+fileURL);
		/** **************�ж�XML�ļ��Ƿ���ڣ�����������򴴽����ļ�********** */
		File file = new File(fileURL);
		if (!file.exists()) { // �ж��ļ��Ƿ���ڣ���������ڣ��򴴽����ļ�
			try {
				PrintWriter out =null;
				try {
					out = response.getWriter();
					out.println("<script language='javascript'>alert('��һ����¼');window.location.href='index.jsp';</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				file.createNewFile(); // �����ļ�  һ��XML�ļ�
				String dataStr = "<?xml version=\"1.0\" encoding=\"GBK\"?>\r\n";
				dataStr = dataStr + "<chat>\r\n";
				dataStr = dataStr + "<messages></messages>";
				dataStr = dataStr + "</chat>\r\n";
				byte[] content = dataStr.getBytes();
				FileOutputStream fout = new FileOutputStream(file);
				fout.write(content); // ������д�������
				fout.flush(); // ˢ�»�����
				fout.close(); // �ر������
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			PrintWriter out =null;
			try {
				out = response.getWriter();
				out.println("<script language='javascript'>alert('���ˡ�');</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return fileURL;
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
