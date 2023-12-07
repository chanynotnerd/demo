<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@ page import="com.ssamz.demo.security.UserDetailsImpl" %>

<div class="container mt-3">
	<form>
		<input type="hidden" id="id" value="${principal.user.id}">
		<div class="mb-3">
			<label for="uname">Username:</label> <input type="text" class="form-control" id="username" placeholder="Enter username" name="username" value="${principal.user.username}">
		</div>
		<%
		// principal.user.oauth 값을 가져오기 위함.
		UserDetailsImpl userDetails = (UserDetailsImpl) pageContext.findAttribute("principal");
		String oauth = userDetails.getUser().getOauth().toString();
		if ("JBLOG".equals(oauth)) {
		%>
		<div class="mb-3">
			<label for="pwd">Password:</label> <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
		</div>
		<%
		}
		%>
		<div class="mb-3 mt-3">
			<label for="email">Email:</label> <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" value="${principal.user.email}">
		</div>
	</form>

	<button id="btn-update" class="btn btn-secondary">회원 정보 수정</button>
</div>
<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>
