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
	<link rel="stylesheet" type="text/css" href="${ctx }/resources/evaluate/css/register-login.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/resources/evaluate/css/mdialog.css">
	<link href="${ctx }/resources/mobiledate/css/mobiscroll.frame.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }/resources/mobiledate/css/mobiscroll.frame.android-holo.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }/resources/mobiledate/css/mobiscroll.scroller.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }/resources/mobiledate/css/mobiscroll.android-holo-light.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${ctx }/resources/evaluate/css/jquery-labelauty.css">
    <style>
		ul { list-style-type: none;text-align: center;}
		li { display: inline-block;}
		li { margin: 10px 0;}
		input.labelauty + label { font: 12px "Microsoft Yahei";}
	</style>
    <script src='${ctx }/resources/js/common.js' type="text/javascript"></script>
</head>
<body>
<div id="box"></div>
<div class="cent-box">
	<div class="cent-box-header">		
		<h2 class="sub-title">请准确填写您的个人信息</h2>
		<h4 style="color:red;">带 *为必填项</h4>
	</div>

	<div class="cont-main clearfix">
		

		<div class="login form">
			<div class="group">
				<div class="group-ipt username">
					<input type="text" name="username" id="username" class="ipt" placeholder="* 姓名" required>
				</div>
				<div class="group-ipt sex">
					<ul class="dowebok">
						<li><input type="radio" name="sex" value="man" data-labelauty="男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" checked></li>
						<li><input type="radio" name="sex" value="woman" data-labelauty="女&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></li>						
					</ul>
				</div>
				<div class="group-ipt birth">
					<input type="text" name="birth" id="birth"  class="ipt" placeholder="* 出生日期" required>
				</div>
				<div class="group-ipt mobile">
					<input type="hidden" name="mobile" id="mobile" class="ipt" maxlength="11" value="${mobile }"
							onkeyup="value=value.replace(/[^\d]/g,'')" 
							onkeydown="if(event.keyCode==13)event.keyCode=9"
							onkeypress="if (event.keyCode<48 || event.keyCode>57)event.returnValue=false"
							placeholder="* 手机号" required>
				</div>
				<div class="group-ipt email">
					<input type="text" name="email" id="email" class="ipt" onchange="return checkemail(this.value);"
						placeholder="邮箱" required>
				</div>
			</div>
		</div>

		<div class="button">
			<button type="submit" class="login-btn register-btn" id="button">提交</button>
		</div>
	</div>
	<div class="logo">
		<div class="logo1"><img style="width:60%;height:100%;" src="${ctx }/resources/images/logo-01.png"></div>
		<div class="logo2"><img style="width:50%;height:100%;" src="${ctx }/resources/images/logo-02.png"></div>
	</div>
</div>
<script src="${ctx }/resources/evaluate/js/jquery-1.8.3.min.js"></script>
<script src="${ctx }/resources/evaluate/js/jquery-labelauty.js"></script>
<script>
$(function(){
	$(':input').labelauty();
});
</script>
<script src='${ctx }/resources/evaluate/js/particles.js' type="text/javascript"></script>
<script src='${ctx }/resources/evaluate/js/background.js' type="text/javascript"></script>
<script src='${ctx }/resources/evaluate/js/layer/layer.js' type="text/javascript"></script>
<script src='${ctx }/resources/evaluate/js/index.js' type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/zepto.min.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/mobiscroll.zepto.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/mobiscroll.core.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/mobiscroll.frame.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/mobiscroll.scroller.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/mobiscroll.util.datetime.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/mobiscroll.datetimebase.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/mobiscroll.datetime.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/mobiscroll.android-holo-light.js" type="text/javascript"></script>
<script src="${ctx }/resources/mobiledate/js/i18n/mobiscroll.i18n.zh.js" type="text/javascript"></script>
<script src="${ctx }/resources/evaluate/js/mdialog.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$('#birth').mobiscroll().date({
                  theme: "android-holo-light",     
                  mode: "scroller",       
                  display: "bottom",
                  dateFormat: 'yy-mm-dd',
                  lang: "zh"
			
              });
	});
	
</script>
</body>
</html>