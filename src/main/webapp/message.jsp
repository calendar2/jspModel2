<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
// 웹에 메세지를 띄우는 작업은 컨트롤러가 아닌 jsp로
String message = (String)request.getAttribute("message");
if(message != null && !message.equals("")){
	if(message.equals("MEMBER_YES")) {
		%>
		<script type="text/javascript">
		alert("성공적으로 가입되었습니다");
		location.href = "member?param=login";
		</script>
		<%
	}else{
		%>
		<script type="text/javascript">
		alert("가입되지 않았습니다 다시 가입해주세요");
		location.href = "member?param=regi";
		</script>
		<%
	}
}

String bbswrite = (String)request.getAttribute("bbswrite");
if(bbswrite != null && !bbswrite.equals("")){
	if(bbswrite.equals("BBS_ADD_OK")){
		%>
		<script type="text/javascript">
		alert("작성 완료");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}else{
		%>
		<script type="text/javascript">
		alert("작성 실패");
		location.href = "bbs?param=bbswrite";
		</script>
		<%
	}
}

String bbsAns = (String)request.getAttribute("bbsAns");

if(bbsAns != null && !bbsAns.equals("")) {
	if(bbsAns.equals("BBS_ANS_OK")){
		%>
		<script type="text/javascript">
		alert("답글 작성 완료");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}else{
		int seq = (Integer)request.getAttribute("seq");
		%>
		<script type="text/javascript">
		alert("작성 실패");
		let seq = "<%=seq %>";
		location.href = "bbs?param=bbsdetail&seq=" + seq;
		</script>
		<%
	}
}

String bbsUp = (String)request.getAttribute("bbsUp");

if(bbsUp != null && !bbsUp.equals("")) {
	if(bbsUp.equals("BBS_UP_OK")){
		%>
		<script type="text/javascript">
		alert("답글 수정 완료");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}else{
		int seq = (Integer)request.getAttribute("seq");
		%>
		<script type="text/javascript">
		alert("수정 실패");
		let seq = "<%=seq %>";
		location.href = "bbs?param=bbsdetail&seq=" + seq;
		</script>
		<%
	}
}

String bbsDel = (String)request.getAttribute("bbsDel");

if(bbsDel != null && !bbsDel.equals("")) {
	if(bbsDel.equals("BBS_DEL_OK")){
		%>
		<script type="text/javascript">
		alert("답글 삭제 완료");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}else{
		int seq = (Integer)request.getAttribute("seq");
		%>
		<script type="text/javascript">
		alert("삭제 실패");
		let seq = "<%=seq %>";
		location.href = "bbs?param=bbsdetail&seq=" + seq;
		</script>
		<%
	}
}
%>


