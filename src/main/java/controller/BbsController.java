package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;
import db.DBConnection;
import dto.BbsDto;
import dto.MemberDto;

@WebServlet("/bbs")
public class BbsController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	
	public void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DBConnection.initConnection();
		
		req.setCharacterEncoding("utf-8");
		
		String param = req.getParameter("param");
		
		if(param.equals("bbslist")) {
			// 추가
			String choice = req.getParameter("choice");
			String search = req.getParameter("search");
			if(choice == null || choice.equals("") || search == null) {
				choice = "";
				search = "";
			}
			
			BbsDao dao = BbsDao.getInstance();
			// List<BbsDto> list = dao.getBbsSearchList(choice, search);
			// 페이지 넘버
			String sPageNumber = req.getParameter("pageNumber");
			int pageNumber = 0;
			if(sPageNumber != null && !sPageNumber.equals("")) {
				pageNumber = Integer.parseInt(sPageNumber);
			}
			
			//글의 총수
			int count = dao.getAllBbs(choice, search);

			// 페이지의 수
			int pageBbs = count / 10;		// 10개씩 총글의 수를 나눔
			if((count % 10) > 0) {
				pageBbs = pageBbs + 1;
			}
			
			List<BbsDto> list = dao.getBbsPageList(choice, search, pageNumber);
			
			req.setAttribute("pageNumber", pageNumber);
			req.setAttribute("pageBbs", pageBbs);
			req.setAttribute("bbslist", list);		// 짐싸!
			req.setAttribute("choice", choice);
			req.setAttribute("search", search);
			forward("bbslist.jsp", req, resp);	// 잘가!
		}
		else if(param.equals("bbswrite")) {
			
			resp.sendRedirect("bbswrite.jsp");
		}
		else if(param.equals("bbswriteAf")) {
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			BbsDao dao = BbsDao.getInstance();
			boolean isS = dao.writeBbs(new BbsDto(id, title, content));
			String bbswrite = "";
			if(isS) {
				bbswrite = "BBS_ADD_OK";
			}else {
				bbswrite = "BBS_ADD_NO";
			}
			
			req.setAttribute("bbswrite", bbswrite);
			forward("message.jsp", req, resp);
		}
		else if(param.equals("bbsdetail")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			BbsDao dao = BbsDao.getInstance();
			dao.readcount(seq); // 카운트 증가
			BbsDto dto = dao.getBbsInfo(seq);
			
			req.setAttribute("dto", dto);		// 짐싸!
			forward("bbsdetail.jsp", req, resp);
		}
		else if(param.equals("answer")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			BbsDto dto = BbsDao.getInstance().getBbsInfo(seq);
			
			req.setAttribute("dto", dto);		// 짐싸!
			forward("answer.jsp", req, resp);
		}
		else if(param.equals("answerAf")) {
			int seq = Integer.parseInt(req.getParameter("seq"));

			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");

			BbsDao dao = BbsDao.getInstance();

			boolean isS = dao.answer(seq, new BbsDto(id, title, content));
			String bbsAns = "";
			if(isS) {
				bbsAns = "BBS_ANS_OK";
			}else {
				bbsAns = "BBS_ANS_NO";
			}
			
			req.setAttribute("bbsAns", bbsAns);
			req.setAttribute("seq", seq);
			forward("message.jsp", req, resp);
		}
		else if(param.equals("updateBbs")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			BbsDto dto = BbsDao.getInstance().getBbsInfo(seq);
			req.setAttribute("dto", dto);
			forward("updateBbs.jsp", req, resp);
		}
		else if(param.equals("updateAf")) {
			int seq = Integer.parseInt(req.getParameter("seq"));

			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");

			BbsDao dao = BbsDao.getInstance();

			boolean isS = dao.update(seq, new BbsDto(id, title, content));
			String bbsUp = "BBS_UP_NO";
			if(isS) {
				bbsUp = "BBS_UP_OK";
			}
			req.setAttribute("seq", seq);
			req.setAttribute("bbsUp", bbsUp);
			forward("message.jsp", req, resp);
		}
		else if(param.equals("deleteBbs")) {
			int seq = Integer.parseInt(req.getParameter("seq"));

			BbsDao dao = BbsDao.getInstance();

			boolean isS = dao.delete(seq);
			String bbsDel = "BBS_DEL_NO";
			if(isS) {
				bbsDel = "BBS_DEL_OK";
			}
			req.setAttribute("seq", seq);
			req.setAttribute("bbsDel", bbsDel);
			forward("message.jsp", req, resp);
		}
	}
	
	public void forward(String linkName, HttpServletRequest req, HttpServletResponse resp) {
		RequestDispatcher dispatcher = req.getRequestDispatcher(linkName);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
