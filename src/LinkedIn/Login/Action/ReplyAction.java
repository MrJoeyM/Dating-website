package LinkedIn.Login.Action;
/**
 * �ظ����ӵ�Servlet
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
import LinkedIn.Login.JavaBean.Frog;

public class ReplyAction extends HttpServlet {

	public ReplyAction() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String path = request.getContextPath();
		Dao dao = new Dao();
		Frog frog = new Frog();
		//ͨ������Ż�ȡ�Ի���Ļظ�
		String frogNumber = request.getParameter("frogNumber");
		frog = dao.getFrog(frogNumber);
		//��ת���ظ�����
		response.sendRedirect(path+"/ReplyPage.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}


	public void init() throws ServletException {
	}

}
