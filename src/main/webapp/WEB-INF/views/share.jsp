<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>投资组合</title>
<link rel="shortcut icon" href="${ctx }/resources/images/icon87.png" />
<link href="${ctx }/resources/css/share.css" rel="stylesheet" type="text/css" />
<script src="${ctx }/resources/js/jquery-1.11.0.js" type="text/javascript"></script>
<script type="text/javascript">	
	$(function(){
		var width=screen.width/16;
		var len=$('.level span').html().length;
		$('.level').css({'width':(0.5+len)+'em','left':(width/5*2.9)+'em'});
		$('.member-shade').css('background-image','url('+$('#user-image').val()+')');	
		$('.member-shade').css('background-size','4.0625em 4.0625em');
	})	
			
</script>
</head>
<body>
	<input type="hidden" id="user-image" value="${group.image }"/>
	<div class="top">
		<div class="member-pic" >
			<div class="member-shade"></div>
			<div class="member-edit"></div>										
		</div>
		<div class="level">    
  		  <span>${group.pkLevelName}</span>  
  		  <div id="triangle-down"></div> 			 
		</div>
		<div class="group-name">
			<span style="font-size: 0.9375em;letter-spacing:0.0625em;">${group.groupName }</span>
		</div>
		<div class="group-info">			
				<span>胜率:${String.valueOf(group.winRate).replace('.0', '') }% </span>
				<span>连胜:${group.pkRowWin } </span>
				<span>总场次:${group.pkTotal } </span>
				<span>多空:${String.valueOf(group.buysellRatio).replace('.0', '') }% </span>
		</div>
	</div>
	<div class="container">
		<table class="gridtable">
			<tr>
				<th>名称</th><th>持仓比例</th><th>多空</th>
			</tr>
			<c:forEach items="${group.stockGroup }" var="stock">
				<tr>
					<td><p class="stock-info">
						<span>
							<c:choose>
								<c:when test="${not empty stock.scStock}">${stock.scStock.stockName }</c:when>
								<c:when test="${not empty stock.scFutures}">${stock.scFutures.contractName }</c:when>
								<c:otherwise>${stock.stockNo}</c:otherwise>
							</c:choose>
						</span><br/>
						<span style="color:#C2C1C0;display:inline-block;margin-top:0.325em;">${stock.stockNo}</span></p>
					</td>
					<td>${String.valueOf(stock.percent).replace('.0', '')}%</td>
					<td>
						<c:choose>
							<c:when test="${stock.direct=='买'}"><span style="color:#FB4F32;">多</span></c:when>
							<c:when test="${stock.direct=='卖'}"><span style="color:#5FC77C;">空</span></c:when>
							<c:otherwise><span class="buysell">${stock.direct}</span></c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="split add"></div>

	<div class="footer">
		<div class="footer-top"><span>八爪鱼介绍</span></div>
		<div class="split"></div>
		<div class="footer-main">
			<p>&laquo;八爪鱼&raquo;是一款为投资者提供港股、美股、国际大宗商品实时行情、交易服务的APP。在这里，你可以直接开户投资，也可以随时浏览投资界各路英雄的PK组合，和资深投资高手一起竞技，更刺激更有趣，动动手指，摁住投资乐趣。</p>
		</div>
	</div>
	<div class="addplace"></div>
	<div class="download">
		<div class="detail">
			<p><span class="name">八爪鱼</span><br/>
			<span class="intro">投资、竞技，尽在此处</span></p>	
		</div>			
	</div>
	<div class="app-icon"></div>
	<div class="dbutton">
		<span>立即下载</span>
	</div>
	
</body>
</html>