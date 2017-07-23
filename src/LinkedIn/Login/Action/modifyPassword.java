package LinkedIn.Login.Action;
/**
 * �޸������Servlet
 * @author Seavan_CC
 *
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import LinkedIn.Login.Dao.Dao;

public class modifyPassword extends HttpServlet {

	public modifyPassword() {
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
		System.out.println("jinlaile");
		String path = request.getContextPath();
		Dao dao = new Dao();
		//��ȡ�޸�������û�ѧ��
		String stuNumber = request.getSession().getAttribute("stuNumber").toString();
		String oldPassword = request.getParameter("old_pass");
		String old = dao.searchPass(stuNumber);
		String newPassword = request.getParameter("new_pass");
		String message ="";
		if(!oldPassword.equals(old))
		{
//			  response.setContentType("text/html; charset=UTF-8"); //ת��
//			  PrintWriter out = response.getWriter();
//			  out.flush();
//			  out.println("<script>");
//			  out.println("alert('ԭ�����������');");
//			  out.println("</script>");
			message = "ԭ�����������";
			request.getSession().setAttribute("message", message);
			response.sendRedirect(path+"/ModifyPage.jsp");
			return;
		}
		//�޸ĸ�������
		boolean result = dao.modifyPassword(newPassword, stuNumber);
		if(result)
		{
//			response.setContentType("text/html; charset=UTF-8"); //ת��
//			PrintWriter out = response.getWriter();
//			out.flush();
//			out.println("<script>");
//			out.println("alert('�޸�����ɹ�');");
//			out.println("</script>");
			message = "�޸�����ɹ���";
			//����޸�����ɹ�����ת��������ҳ
			request.getSession().setAttribute("message", message);
			response.sendRedirect(path+"/ModifyPage.jsp");
			
		}else{
			
		}
		
	}

	public void init() throws ServletException {
		
	}

}
