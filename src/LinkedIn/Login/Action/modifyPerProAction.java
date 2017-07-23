package LinkedIn.Login.Action;
/**
 * �޸ĸ�����Ϣ��Servlet
 * @author Seavan_CC
 *
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import LinkedIn.Login.Dao.Dao;
import LinkedIn.Login.JavaBean.User;

public class modifyPerProAction extends HttpServlet {

	public modifyPerProAction() {
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
		String path = request.getContextPath();
		Dao dao = new Dao();
		//��ȡ�޸ĸ�����Ϣ���û���ѧ��
		String stuNumber = request.getSession().getAttribute("stuNumber").toString();
		User user = new User();
		//��ȡ�޸ĸ�����Ϣ���û�
		User user1 = (User)request.getSession().getAttribute("user");
		user.setStuName(user1.getStuName());
		System.out.println(user1.getStuName());
		//���޸ĺ����Ϣ���õ�user����
		user.setHeadPostrait(user1.getHeadPostrait());
		user.setSex(new String(request.getParameter("info_sex").getBytes("gbk"),"gbk"));
		user.setAge(Integer.valueOf(request.getParameter("info_age")));
		user.setMajor(new String(request.getParameter("info_major").getBytes("gbk"),"gbk"));
		user.setGrade(new String(request.getParameter("info_grade").getBytes("gbk"),"gbk"));
		user.setHobby(new String(request.getParameter("info_hobby").getBytes("gbk"),"gbk"));
		request.getSession().setAttribute("new_User", user);
		//�����ݿ��и����û���Ϣ
		boolean result = dao.modifyPerProfile(user, stuNumber);
		if(result)
		{
			//�޸ĸ�����Ϣ�ɹ�����ת��������ҳ
			response.sendRedirect(path+"/ModifyPage.jsp");
		}else{
			
		}
	}

	public void init() throws ServletException {
	}

}
