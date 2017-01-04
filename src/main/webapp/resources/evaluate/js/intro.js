$(function(){
		var height=screen.availHeight/16;
		$('.content').css({'height':height+'rem','background-size':'100% '+window.screen.height/16+'rem'});
		$('.entrance').css({'background-size':'8.5rem 2.5rem','top':height/10*6.7+'rem'});
		$('.contact').css({'top':height/10*6.9+'rem','height':'6rem'});
		$('.entrance').click(function(){
			window.location.href=getRootPath()+'/evaluate/index?mobile='+$('#mobile').val();
		});
	})	