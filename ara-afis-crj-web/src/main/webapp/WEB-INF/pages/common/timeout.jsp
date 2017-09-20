<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
  <head>   
  	<script type="text/javascript">
  		window.top.location="<%=basePath%>/welcome.jsp";
  	</script>
  </head>
<body>
</body>
</html>
