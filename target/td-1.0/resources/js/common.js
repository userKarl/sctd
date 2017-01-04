/**
 * 根路径取得
 * 
 * @returns {String} 根路径:http://域名|ip/webName
 */
function getRootPath() {
	var pathName = window.location.pathname.substring(1);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
	return window.location.protocol + '//' + window.location.host + '/' + webName;
}