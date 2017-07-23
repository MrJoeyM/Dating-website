package LinkedIn.Login.Action;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import LinkedIn.Login.Dao.Dao;
import LinkedIn.Login.JavaBean.User;
/**
 * ע���Servlet
 * @author Seavan_CC
 *
 */
public class RegisterAction extends HttpServlet {

	public RegisterAction() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		request.setCharacterEncoding("gbk");
		//��ȡע����û�ͷ�񣬲�����ϵͳ�ļ�����
		String path = request.getContextPath();
		JspFactory fac = JspFactory.getDefaultFactory();   
		PageContext pageContext = fac.getPageContext(this, request,response,   
		                       null, false, JspWriter.DEFAULT_BUFFER, true);  
		SmartUpload su=new SmartUpload();
		if(pageContext == null)
		{
			System.out.println("��");
			response.sendRedirect(path+"/RegisterPage.jsp");
			return;
		}else{
			su.initialize(pageContext);
		}
		su.setAllowedFilesList("jpg,bmp,gif,png,PNG,JPG");
		try {
			su.upload();
		} catch (SmartUploadException e1) {
			e1.printStackTrace();
		}
		try {
			su.save("/upload", SmartUpload.SAVE_VIRTUAL);
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
		String pictureUrl="upload/"+su.getFiles().getFile(0).getFileName();
		//��ȡ�û���ע����Ϣ
		System.out.println(new String(su.getRequest().getParameter("stuNumber").getBytes("gbk"),"gbk"));
		String stuNumber = new String(su.getRequest().getParameter("stuNumber").getBytes("gbk"),"gbk");
		String stuName =new String(su.getRequest().getParameter("stuName").getBytes("gbk"),"gbk"); 
		String password =new String(su.getRequest().getParameter("password").getBytes("gbk"),"gbk");
		String major =new String(su.getRequest().getParameter("major").getBytes("gbk"),"gbk");
		String grade =new String(su.getRequest().getParameter("grade").getBytes("gbk"),"gbk"); 
		String sex =new String(su.getRequest().getParameter("sex").getBytes("gbk"),"gbk");
		int age = Integer.parseInt(su.getRequest().getParameter("age"));
		String head = new String(pictureUrl.getBytes("gbk"),"gbk");
		String hobby =new String(su.getRequest().getParameter("hobby").getBytes("gbk"),"gbk"); 
		//��ע����Ϣ����һ��user������
		User user = new User() ;
		user.setStuNumber(stuNumber);
		user.setStuName(stuName);
		user.setPassword(password);
		user.setMajor(major);
		user.setGrade(grade);
		user.setSex(sex);
		user.setAge(age);
		user.setHeadPostrait(head);
		user.setHobby(hobby);
		HttpSession session= request.getSession();
		Dao dao = new Dao();
		boolean result = dao.register(user);
		//�ж��û��Ƿ�ע��ɹ������ע��ɹ�����ת����¼����
		if(result)
		{
			response.sendRedirect(path+"/LoginPage.jsp");
			session.setAttribute("user", user);
		}
		else {
			//����û�ע��ʧ�ܣ�����ת��ע�����
			response.sendRedirect(path+"/RegisterPage.jsp");
		}
	}

	public void init() throws ServletException {
		super.init();
	}

}
