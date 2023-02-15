<%@page import="dto.MemberDto"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.BbsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	BbsDto dto = (BbsDto)request.getAttribute("dto");
	MemberDto login = (MemberDto)session.getAttribute("login");
%>

<div align="center">

<h2>수정글</h2>

<form action="bbs?param=updateAf" method="post">
<input type="hidden" name="seq" value="<%=dto.getSeq() %>">

<table border="1">
<col width="200"><col width="500">
<tr>
	<th>아이디</th>
	<td>
		<input type="text" name="id" size="50" readonly="readonly" value="<%=login.getId() %>">
	</td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" name="title" size="50" value="<%=dto.getTitle() %>">
	</td>
</tr>
<tr>
	<th>내용</th>
	<td>
		<textarea rows="10" cols="50" name="content"><%=dto.getContent() %></textarea>
	</td>
</tr>
<tr>
	<td colspan="2" align="center">
		<input type="submit" value="수정완료">
	</td>
</tr>
</table>
</form>
</div>

</body>
</html>