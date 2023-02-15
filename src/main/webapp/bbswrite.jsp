<%@page import="dto.MemberDto"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	MemberDto login = (MemberDto)session.getAttribute("login");
	if(login == null){
		%>
		<script>
		alert('로그인 해주세요');
		location.href = "login.jsp";
		</script>
		<%
	}
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>글쓰기</h1>

<div align="center">
<form action="bbs?param=bbswriteAf" method="post">
<table border="1">
<col width="200"><col width="400">

<tr>
	<th>id</th>
	<td>
		<input type="text" name="id" size="50px" value="<%=login.getId() %>" readonly="readonly">
	</td>
</tr>


<tr>
	<th>제목</th>
	<td>
		<input type="text" name="title" size="50px" placeholder="제목기입">
	</td>
</tr>
<tr>
	<th>내용</th>
	<td>
		<textarea cols="50px" rows="20" name="content" placeholder="내용기입"></textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
		<!-- <input type="submit" value="글쓰기"> -->
		<button type="submit">글쓰기</button>
	</td>
</tr>

</table>
</form>
</div>
</body>
</html>