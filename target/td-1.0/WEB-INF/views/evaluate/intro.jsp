<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<!-- <base target="_blank"> -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>交易胜任力测评</title>
<link rel="stylesheet" type="text/css" href="${ctx }/resources/evaluate/css/intro.css">
<script src="${ctx }/resources/js/jquery-1.11.0.js" type="text/javascript"></script>
<script src='${ctx }/resources/js/common.js' type="text/javascript"></script>
<script src='${ctx }/resources/evaluate/js/intro.js' type="text/javascript"></script>
</head>
<body>
<div class="content">
	<input type="hidden" id="mobile" value="${mobile }"/>
	<div class="entrance"></div>
	<div class="contact">
		<span style="color:red;">获取测评报告</span><br>
		<span style="color:#003389;display:inline-block;margin-top:0.3125rem;">请联系客服，了解详细信息</span><br>
		<span style="color:#FFF;display:inline-block;margin-top:0.5rem;">QQ：2930544816</span>
	</div>
</div>

</body>
</html>