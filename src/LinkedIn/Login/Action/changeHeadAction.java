package LinkedIn.Login.Action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import LinkedIn.Login.Dao.Dao;
import LinkedIn.Login.JavaBean.User;

import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
/**
 * ����ͷ���Servlet
 * @author Seavan_CC
 *
 */
public class changeHeadAction extends HttpServlet {

	public changeHeadAction() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		request.setCharacterEncoding("gbk");

		String path = request.getContextPath();
		JspFactory fac = JspFactory.getDefaultFactory();   
		//��ȡ�����Ķ���
		PageContext pageContext = fac.getPageContext(this, request,response,   
		                       null, false, JspWriter.DEFAULT_BUFFER, true);  
		SmartUpload su=new SmartUpload();
		if(pageContext == null)
		{
			System.out.println("��");
		}else{
			su.initialize(pageContext);
		}
		//����֧�ֵ�ͼƬ��ʽ
		su.setAllowedFilesList("jpg,bmp,gif,png,PNG,JPG");
		try {
			su.upload();
		} catch (SmartUploadException e1) {
			e1.printStackTrace();
		}
		try {
			//���û��ϴ���ͼƬ��ֹ����Ӧ���ļ�����
			su.save("/upload", SmartUpload.SAVE_VIRTUAL);
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
		String pictureUrl="upload/"+su.getFiles().getFile(0).getFileName();
		User user = new User();
		String stuNumber = request.getSession().getAttribute("stuNumber").toString();
		Dao dao = new Dao();
		//�����ݿ��и����û�ͷ��
		dao.modifyHead(stuNumber, pictureUrl);
		if((User)request.getSession().getAttribute("user")!=null)
		{
			//����user��ͷ������
			user = (User)request.getSession().getAttribute("user");
			user.setHeadPostrait(pictureUrl);
			request.getSession().setAttribute("user",user);
		}
		if((User)request.getSession().getAttribute("new_User") !=null)
		{
			user=(User)request.getSession().getAttribute("new_User");
			user.setHeadPostrait(pictureUrl);
			request.getSession().setAttribute("new_User", user);
		}
		String entry = request.getParameter("entry");
		System.out.println(entry);
		//�ж��û������ĸ�ҳ�����ͷ�񣬸��������ת����Ӧҳ��
		if(entry.equals("blog")){
			response.sendRedirect(path+"/BlogPage.jsp");
		}else if(entry.equals("modify"))
		{
			response.sendRedirect(path+"/ModifyPage.jsp");
		}
	}

	public void init() throws ServletException {
		
	}

}
