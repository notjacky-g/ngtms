var code;
var oriUrl = location.href;
var uuid = "";

function keyDown() {
	if (event.keyCode == 13) {
		login();
	}
}

function createCode() {
	$.ajax({
		type: 'POST',
		url: location.protocol + "//" + window.location.hostname + ":" + window.location.port + "/first/retrieveSpecificIpAndPort",
		dataType: 'json',
		async: true,
	    success: function (response) {
	       genValidateCode(location.protocol + "//" + response.first + ":" + response.second);
		}
	});
    MM_preloadImages('/images/login_button_on.png');
}

function login() {
	$.ajax({
		type: 'POST',
		url: location.protocol + "//" + window.location.hostname + ":" + window.location.port + "/first/retrieveSpecificIpAndPort",
		dataType: 'json',
		async: true,
		success: function (response) {
		  loginValidate(location.protocol + "//" + response.first + ":" + response.second);
		}
	});
}

function genValidateCode(fmIpAndPort) {
	$.ajax({
		async: true,
	    type: 'GET',
	    url: fmIpAndPort + "/api/validation/genValidateCode",
	    dataType: 'text',
	    success: function (response) {
	      var src = response.split(";")[1];
	      uuid = response.split(";")[0];
	      document.getElementById("validateCode").src = "data:image/png;base64, " + src ;
	    }
	});
}

function loginValidate(fmIpAndPort) {
	var inputCode = document.getElementById("inputCode").value;
	var username = document.getElementById("username").value;
	if (inputCode == undefined || inputCode == null || inputCode == "") {
	  genValidateCode(fmIpAndPort);
	  document.getElementById("errorMsg").innerHTML = "<span style='color: red;'>登入驗證資料錯誤</span>";
	} else {
		$.ajax({
			async: true,
		    type: 'GET',
		    url: fmIpAndPort + "/api/validation/validateCodeCheck/" + uuid + "/" + inputCode,
		    dataType: 'text',
		    success: function (response) {
		      if (response == "true") {
		    	document.cookie = "name=" + username;
		    	document.forms["loginForm"].submit();
		      } else {
		    	genValidateCode(fmIpAndPort);
		    	document.getElementById("errorMsg").innerHTML = "<span style='color: red;'>登入驗證資料錯誤</span>";
		    	document.getElementById("inputCode").value = "";
		      }
		    }
		});	
	}
}

function MM_swapImgRestore() { // v3.0
	var i, x, a = document.MM_sr;
	for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
		x.src = x.oSrc;
}
function MM_preloadImages() { // v3.0
	var d = document;
	if (d.images) {
		if (!d.MM_p)
			d.MM_p = new Array();
		var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
		for (i = 0; i < a.length; i++)
			if (a[i].indexOf("#") != 0) {
				d.MM_p[j] = new Image;
				d.MM_p[j++].src = a[i];
			}
	}
}

function MM_findObj(n, d) { // v4.01
	var p, i, x;
	if (!d)
		d = document;
	if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
		d = parent.frames[n.substring(p + 1)].document;
		n = n.substring(0, p);
	}
	if (!(x = d[n]) && d.all)
		x = d.all[n];
	for (i = 0; !x && i < d.forms.length; i++)
		x = d.forms[i][n];
	for (i = 0; !x && d.layers && i < d.layers.length; i++)
		x = MM_findObj(n, d.layers[i].document);
	if (!x && d.getElementById)
		x = d.getElementById(n);
	return x;
}

function MM_swapImage() { // v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
	document.MM_sr = new Array;
	for (i = 0; i < (a.length - 2); i += 3)
		if ((x = MM_findObj(a[i])) != null) {
			document.MM_sr[j++] = x;
			if (!x.oSrc)
				x.oSrc = x.src;
			x.src = a[i + 2];
		}
}