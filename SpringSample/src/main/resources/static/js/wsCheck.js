function check() {
	if (!(document.getElementsByClassName("form-control")[0].value)) {
		alert("遅刻理由を入力してください。");
		return false;
	}
}