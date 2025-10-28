<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/includes/Bootstrap.jsp" %>

<c:choose>
  <c:when test="${not empty param.locale}">
    <c:set var="locale" scope="session" value="${param.locale}" />
  </c:when>
  <c:when test="${empty sessionScope.locale}">
    <c:set var="locale" scope="session" value="${pageContext.request.locale.language}" />
  </c:when>
</c:choose>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="me.jmll.i18n.app" var="bundle" />

<!DOCTYPE html>
<html>
<head>
  <title><fmt:message key="app.title" bundle="${bundle}"/></title>
</head>
<body>
<div class="container">

  
  <div class="pull-right mb-3">
    <a href="?locale=es" class="btn btn-sm btn-outline-secondary">Español</a>
    <a href="?locale=en" class="btn btn-sm btn-outline-secondary">English</a>
  </div>

  <h3 class="text-center mb-4"><fmt:message key="app.title" bundle="${bundle}"/></h3>

  
  <form action="${pageContext.request.contextPath}/List.do" method="get">
    <input type="hidden" name="locale" value="${sessionScope.locale}" />

    <div class="form-group">
      <label><fmt:message key="form.path" bundle="${bundle}"/></label>
      <input type="text" name="path" class="form-control" required placeholder="C:\Users\atrej\OneDrive\Escritorio" />
    </div>

    <button type="submit" class="btn btn-primary mt-2">
      <span class="glyphicon glyphicon-search"></span>
      <fmt:message key="form.button" bundle="${bundle}"/>
    </button>
  </form>

  
  <c:if test="${not empty errores}">
    <div class="alert alert-danger mt-3">
      <ul>
        <c:forEach var="e" items="${errores}">
          <li>${e}</li>
        </c:forEach>
      </ul>
    </div>
  </c:if>

</div>

<%@ include file="/WEB-INF/includes/BootstrapJS.jsp" %>
</body>
</html>
