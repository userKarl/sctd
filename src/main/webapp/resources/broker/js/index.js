$(function() {
	var height=screen.height/16;
	var width=screen.width/16;
	$('.content').css({'height':height+'rem','background-size':'100% '+window.screen.height/16+'rem'});
	$('.entrance').css({'background-size':'5.8rem 2.3rem','top':height/10*6.5+'rem'});
	$('.entrance').click(function(){
		window.location.href='';
	});
});
