<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>

<style type="text/css">
.center{
	margin: auto;
	width: 60%;
	border: 1px solid #000000;
	border-radius: 1em;
	padding: 10px;
	background-color: #58FA58;
}

/* 배경에 이미지를 크기가 달라져도 잘리지 않게, 꽉 차게 */
body{
	width: 100%;

	background: url('./b_pic1.jpg') no-repeat center fixed;

	-webkit-background-size : cover;
	-moz-background-size : cover;
	-o-background-size: cover;
	background-size: cover;
	
}
</style>

</head>
<body>

<h2 style="color:white">login page</h2>

<div class="center">

<form action="member" method="post">	<!-- member라는 컨트롤러를 찾아가라 -->
<input type="hidden" name="param" value="loginAf">	<!-- param이라는 이름에 loginAf라는 값을 줘라 -->

<table border="1" style="margin-left: auto; margin-right: auto;">
<tr>
	<th>아이디</th>
	<td>
		<input type="text" id="id" name="id" size="20"><br>
		<input type="checkbox" id="chk_save_id">id 저장
	</td>
</tr>
<tr>
	<th>패스워드</th>
	<td>
		<input type="password" name="pwd" size="20">
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" value="log-in">
		<a href="member?param=regi">회원가입</a> <!-- member라는 컨트롤러를 찾아가서 param이라는 이름에 regi라는 값을 줘라 -->
	</td>
</tr>
</table>

</form>

</div>

<script type="text/javascript">
/*
   	cookie : id저장, pw저장 == String		client
   	session : login한 정보 == Object		server
*/

// 쿠키에 저장된 user_id를 user_id라는 변수에 대입
let user_id = $.cookie("user_id");

// user_id라는 변수에 쿠키값이 들어가있을 경우 if문 실행
if(user_id != null){	// 저장한 id가 있음
	$("#id").val(user_id);	// user_id의 value값을 id에 대입
	$("#chk_save_id").prop("checked", true);	// id 저장이라는 체크박스를 체크해줘라
}

// id 저장이라는 체크박스를 클릭했을 때 실행
$("#chk_save_id").click(function(){
	if($("#chk_save_id").is(":checked")){	// id 저장이라는 체크박스를 체크했을 때
		// alert('true');
		if($("#id").val().trim() == ""){	// id가 비정상적일 경우에 실행, trim은 띄어쓰기 여부를 판별
			alert('id를 입력해주세요');
			$("#chk_save_id").prop("checked", false);	// id가 비정상적이므로 체크박스 해제
		}else{	// id가 정상적일 경우에 실행
			// cookie를 저장
			$.cookie("user_id", $("#id").val().trim(), {expires:7, path:'./'})
		}
	}else{	// id 저장이라는 체크박스를 클릭해서 해제했을 경우에 실행
		// alert('false');
		$.removeCookie("user_id", {path:'./'});	// 쿠키를 삭제
	}
});
</script>

</body>
</html>