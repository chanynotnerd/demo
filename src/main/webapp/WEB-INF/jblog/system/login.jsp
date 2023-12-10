<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container mt-3">
<form action="/auth/securitylogin" method="post">
  <div class="mb-3">
  <label for ="uname">
  <spring:message code="user.login.form.username" />:</label>
  <input type="text" class="form-control" id="username" placeholder="Enter username" name="username">
  </div>
    <div class="mb-3">
      <label for="pwd">
      <spring:message code="user.login.form.password" />:</label>
      <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
    </div>
    <div class="mb-3">
    <button id="btn-login" class="btn btn-secondary"><spring:message code="user.login.form.login_btn" /></button>
    <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=7726cc35fb60a4fcf57862d725f173de&redirect_uri=http://localhost:8080/oauth/kakao">
             			<img height="38px" src="/image/kakao_login_btn.png"></a>
    <a href="../oauth2/authorization/google"> <img height="38px"
    				src="/image/google_login_btn.png"></a>
    </div>
    </form>
</div>

<%@ include file="../layout/footer.jsp" %>