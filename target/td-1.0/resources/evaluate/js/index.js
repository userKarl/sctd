$(function() {
	$('#button').html('提交');
	$('#button').click(
			function() {
				var exception = '系统异常，请联系管理员!';
				var username = $('#username').val();
				var sex = $('#sex').val();
				var birth = $('#birth').val();
				var mobile = $('#mobile').val();
				var email = $('#email').val();
				if (username == null || username == '') {
					new TipBox({
						type : 'error',
						str : '姓名不能为空!',
						hasBtn : true
					});
					return false;
				}
				if (sex == null || sex == '') {
					new TipBox({
						type : 'error',
						str : '性别不能为空!',
						hasBtn : true
					});
					return false;
				}
				if (birth == null || birth == '') {
					new TipBox({
						type : 'error',
						str : '出生日期不能为空!',
						hasBtn : true
					});
					return false;
				}
				if (mobile == null || mobile == '') {
					new TipBox({
						type : 'error',
						str : exception,
						hasBtn : true
					});
					return false;
				}
				if (mobile.length > 11) {
					new TipBox({
						type : 'error',
						str : exception,
						hasBtn : true
					});
					return false;
				}
				// if (email == null || email == '') {
				// new TipBox({type:'error',str:'邮箱不能为空!',hasBtn:true});
				// return false;
				// }
				if (!checkemail(email)) {
					return false;
				}
				$('#button').html('提交中<dot>...</dot>');
				$.post(getRootPath() + '/evaluate/do?username=' + username
						+ '&sex=' + sex + '&birth=' + birth + '&mobile='
						+ mobile + '&email=' + email, function(data) {
					if (data != null && data.result == 'success') {
						window.location.href = data.eva_address
								+ '&path=mobile/instructions';
					} else {
						new TipBox({
							type : 'error',
							str : exception,
							hasBtn : true
						});
						$('#button').html('提交');
					}
				});
			});
});
function checkemail(email_address) {
	var regex = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
	if (regex.test(email_address)) {
		return true;
	} else {
		new TipBox({
			type : 'error',
			str : '邮箱格式不合法!',
			hasBtn : true
		});
		return false;
	}
}