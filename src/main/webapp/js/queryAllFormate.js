// 序号
function indexFormatter(value, row, index) {
	return index + 1;
}
//日期
function dateFormatter(value) {
	var date;
	if (value != null) {
		var date = new Date(value).format('yyyy-MM-dd');
	}
	return date;
}