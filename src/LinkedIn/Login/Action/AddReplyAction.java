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
 * ��������ظ���Servlet
 * @author Seavan_CC
 *
 */
public class AddReplyAction extends HttpServlet {

	public AddReplyAction() {
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
		//��ȡ�ظ��������û�ѧ�źͻظ�����
		String replyStuNumber = new String(request.getParameter("replyStuNumber").getBytes("gbk"),"gbk");
		String replyContent = new String(request.getParameter("replyTextArea").getBytes("gbk"),"gbk");
		java.util.Date time=new java.util.Date();
		java.sql.Timestamp timeStamp=new java.sql.Timestamp(time.getTime());
		String frogNumber = request.getSession().getAttribute("frogNumber").toString();
		String stuNumber = request.getSession().getAttribute("stuNumber").toString();
		FrogReply frogReply = new FrogReply();
		frogReply.setFrogNumber(frogNumber);
		frogReply.setReplyContent(replyContent);
		frogReply.setReplyDate(timeStamp);
		frogReply.setStuNumber(stuNumber);
		Dao dao =new Dao();
		//��ȡ����
		Frog frog = (Frog)request.getSession().getAttribute("searchFrog");
		if(dao.addReply(frogReply))
		{
			frog.setReplyCount(dao.replyCount(frogNumber));
			//���»���
			request.getSession().setAttribute("searchFrog", frog);
			ArrayList<FrogReply> frogReplies = dao.getReply(frogNumber);
			//���»ظ���
			request.getSession().setAttribute("replyList", frogReplies);
			response.sendRedirect(path+"/ReplyPage.jsp");
		}else{
			
		}
	}

	public void init() throws ServletException {
		
	}

}
