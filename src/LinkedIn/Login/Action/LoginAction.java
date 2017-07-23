package LinkedIn.Login.Action;
/**
 * ��¼ϵͳ��Servlet
 * @author Seavan_CC
 *
 */
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import LinkedIn.Login.Dao.Dao;
import LinkedIn.Login.JavaBean.User;

public class LoginAction extends HttpServlet {

	public LoginAction() {
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

		String path = request.getContextPath();
		//��ȡ�û���������
		String stuNumber = request.getParameter("stuNumber");
		String password = request.getParameter("password");
		HttpSession session= request.getSession();
		session.setAttribute("stuNumber", stuNumber);
		Dao dao = new Dao();
		//����û���Ϊ�գ���ת����¼����
		if(stuNumber == null)
		{
			response.sendRedirect(path+"/LoginPage.jsp");
		}else{
			//�����¼��Ϣ��ƥ�䣬����ת����¼����
			if(dao.login(stuNumber) == null)
			{
				response.sendRedirect(path+"/LoginPage.jsp");
			}
			else if(!dao.login(stuNumber).getPassword().equals(password)){
				response.sendRedirect(path+"/LoginPage.jsp");
			}
			else {
				//�����¼��Ϣƥ�䣬����ת����ҳ
				session.setAttribute("user", dao.login(stuNumber));
				response.sendRedirect(path+"/homepage.jsp");
			}
		}
	}


	public void init() throws ServletException {
		super.init();
	}

}
