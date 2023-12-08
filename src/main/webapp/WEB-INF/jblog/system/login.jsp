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
    <button id="btn-login" class="btn btn-secondary">로그인</button>
    </form>
</div>

<%@ include file="../layout/footer.jsp" %>