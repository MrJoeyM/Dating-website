package LinkedIn.Login.Action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import LinkedIn.Login.Dao.Dao;
import LinkedIn.Login.JavaBean.Frog;
import LinkedIn.Login.JavaBean.FrogReply;
/**
 * ��ȡ����ϸ�ڵ�Servlet
 * @author Seavan_CC
 *
 */
public class FrogDetailAction extends HttpServlet {

	public FrogDetailAction() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		request.setCharacterEncoding("gbk");
		String path = request.getContextPath();
		Dao dao = new Dao();
		Frog frog = new Frog();
		//��ȡ�����
		String frogNumber = new String(request.getParameter("frogNumber").getBytes("gbk"),"gbk");;
		request.getSession().setAttribute("frogNumber", frogNumber);
		frog = dao.getFrog(frogNumber);
		int count = dao.replyCount(frogNumber);
		frog.setReplyCount(count);
		//��session�����д洢��ȡ�Ļ���
		request.getSession().setAttribute("searchFrog", frog);
		ArrayList<FrogReply> frogReplies = dao.getReply(frogNumber);
		request.getSession().setAttribute("replyList", frogReplies);
		response.sendRedirect(path+"/ReplyPage.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}


	public void init() throws ServletException {

	}

}
