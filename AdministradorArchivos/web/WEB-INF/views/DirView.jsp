<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  <title><fmt:message key="view.title" bundle="${bundle}"/></title>
</head>
<body>
<div class="container">

  
  <div class="pull-right mb-3">
    <a href="?locale=es&path=${param.path}" class="btn btn-sm btn-outline-secondary">Español</a>
    <a href="?locale=en&path=${param.path}" class="btn btn-sm btn-outline-secondary">English</a>
  </div>

  <h3 class="text-center mb-4"><fmt:message key="view.title" bundle="${bundle}"/></h3>

  <c:if test="${fn:length(paths) lt 1}">
    <div class="alert alert-info text-center">
      <fmt:message key="table.empty" bundle="${bundle}"/>
    </div>
  </c:if>

  <c:if test="${fn:length(paths) ge 1}">
    <table class="table table-bordered table-hover">
      <thead style="background-color:#000; color:white;">
        <tr>
          <th><fmt:message key="table.name" bundle="${bundle}"/></th>
          <th><fmt:message key="table.path" bundle="${bundle}"/></th>
          <th><fmt:message key="table.size" bundle="${bundle}"/></th>
          <th><fmt:message key="table.download" bundle="${bundle}"/></th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="p" items="${paths}">
          <tr>
            <td>${p.fileName}</td>
            <td>${p}</td>
            <td><c:out value="${sizes[p.toString()]}"/></td>
            <td>
              <a href="${pageContext.request.contextPath}/Download.do?file=${fn:escapeXml(p.toString())}"
                 class="btn btn-sm btn-success">
                <span class="glyphicon glyphicon-download-alt"></span>
                <fmt:message key="table.download" bundle="${bundle}"/>
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:if>

  <div class="text-center mt-3">
    <a href="${pageContext.request.contextPath}/Admin.do" class="btn btn-dark">
      <span class="glyphicon glyphicon-arrow-left"></span>
      <fmt:message key="nav.back" bundle="${bundle}"/>
    </a>
  </div>
</div>

<%@ include file="/WEB-INF/includes/BootstrapJS.jsp" %>
</body>
</html>
