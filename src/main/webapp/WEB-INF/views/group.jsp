<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="${ctx}">
<!-- <base target="_blank"> -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>战队总收益</title>
<link rel="shortcut icon" href="${ctx }/resources/images/icon.png" />
<script src="${ctx }/resources/js/jquery-1.11.0.js" type="text/javascript"></script>
<script type="text/javascript">	
$(function(){
	$('#search').click(function(){
		$("#searchForm").submit();
	});
})
</script>
<style>
.wrap{margin:0 auto;width:1024px;height: auto;border:#CCC 1px solid;}
.title{width: 100%;height: 30px;background-color: #EEE;vertical-align: center;line-height: 
	30px;
}
.search-div{float: right;margin-right: 15px;}
.split{width: 100%;border-top: #CCC 1px solid;
}
.form-data{width: 100%;height: auto;
}
	table.gridtable {
	   	width:100%;	
		font-size:0.8em;
		color:#333333;
		border-width: 1px;
		border-color: #B2B2B2;
		border-collapse: collapse;
		}
	table.gridtable th {	
		padding: 8px;	
		background-color: #F4F4F4;
	}
	table.gridtable td {
		width:165px;
		text-align: center;
		border-top: 1px solid #B2B2B2;
		padding: 8px;	
		line-height: 20px;		
	}
	.info{width: 100%;height: 30px;line-height: 30px;vertical-align: center;}
</style>
</head>
<body>
	<div class="wrap">
		<div class="title">
			<div class="search-div">
				<form id="searchForm" method="post" action="${ctx }/web/group/search">
					<span>请输入战队ID：</span><input type="text" name="groupId" id="groupId">&nbsp;
					开始日期：<input type="text" name="startDate" value="${startDate }">&nbsp;
					<input type="button" id="search" value="查询">
				</form>
			</div>
		</div>
		
		<div class="split"></div>
		<div class="form-data">
			<div class="info">
				<div style="margin-left:15px;float:left;">战队名称：<span style="color:red;">${groupName }</span></div>
				<div style="margin-left:25px;float:left;">总收益：<span style="color:red;">${totalProfit }</span></div>
			</div>
			<div class="split"></div>
				<table class="gridtable">
					<tr>
						<th>交易所</th><th>产品代码</th><th>产品名称</th><th>产品类型</th><th>投资比例</th><th>下单量</th><th>多空</th><th>开盘价</th><th>收盘价</th><th>盈利</th>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>	
				</table>
		</div>
	</div>
</body>
</html>
