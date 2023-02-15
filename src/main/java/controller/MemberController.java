package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import db.DBConnection;
import dto.MemberDto;
import net.sf.json.JSONObject;

@WebServlet("/member")
public class MemberController extends HttpServlet{

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
		
		if(param.equals("login")) {
			resp.sendRedirect("login.jsp");
		}
		else if(param.equals("regi")) {
			resp.sendRedirect("regi.jsp");
		}
		else if(param.equals("idcheck")) {		// ajax
			String id = req.getParameter("id");
			
			// DB로 접근
			MemberDao dao = MemberDao.getInstance();
			boolean b = dao.getId(id);
			
			String str = "NO";
			if(b == false) {
				str = "YES";
			}
			
			JSONObject obj = new JSONObject();
			obj.put("str", str);
			
			resp.setContentType("application/x-json;charset=utf-8");
			resp.getWriter().print(obj);
			
		}
		else if(param.equals("regiAf")) {	// 회원가입
			
			// parameter 값(회원가입을 할 때 입력한 정보들을 넘겨받아 변수에 저장)
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			
			// db에 저장(1. 싱글톤을 통해서, 2. 변수가 여러 개이므로 dto에 변수를 넣어줌, 3. db에 저장이 되면 T, 안 되면 F 반환)
			MemberDao dao = MemberDao.getInstance();
			
			MemberDto dto = new MemberDto(id, pwd, name, email, 0);
			boolean isS = dao.addMember(dto);
			
			// login.jsp(isS이 true면(db에 저장이 되면) 로그인 페이지로, false면(db에 저장이 안 되면) 다시 회원가입)
			String message = "";
			if(isS) {
				// 회원가입이 된 것을 화면에 알려주기 위해서
				/*
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('계정이 등록됐습니다.');</script>");
				out.flush();
				*/
				
				message = "MEMBER_YES";
				// 회원가입 됐으니 로그인 페이지로
				
			}else {
				// 회원가입이 된 것을 화면에 알려주기 위해서
				/*
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('정보를 다시 확인해주세요');</script>");
				out.flush();
				*/
				
				message = "MEMBER_NO";
				// 회원가입이 안 됐으니 다시 회원가입 페이지로
				
			}
			
			req.setAttribute("message", message);
			// req.getRequestDispatcher("");
			forward("message.jsp", req, resp);
		}
		else if(param.equals("loginAf")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			
			MemberDao dao = MemberDao.getInstance();
			MemberDto login = dao.login(id, pwd);
			
			if(login != null) {
				// session에 저장
				req.getSession().setAttribute("login", login);
				
				resp.sendRedirect("bbs?param=bbslist");
			}
			
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
