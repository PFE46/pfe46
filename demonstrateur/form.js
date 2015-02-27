$(document).ready(function() {
	var opt1 = $('#optionsRadios1');
		opt1.click(function(){
			document.getElementById('formSupp').style.display = 'none';
			document.getElementById('formSupp2').style.display = 'none';
			});
	var opt2 = $('#optionsRadios2');
		opt2.click(function(){
			document.getElementById('formSupp').style.display = 'block';
			document.getElementById('formSupp2').style.display = 'none';
			});
	var opt3 = $('#optionsRadios3');
		opt3.click(function(){
			document.getElementById('formSupp2').style.display = 'block';
			document.getElementById('formSupp').style.display = 'none';
	});
	var optAuth = $('#optionsRadiosAuth1');
		optAuth.click(function(){
			document.getElementById('formSupp3').style.display = 'block';
	});
	var optNoAuth = $('#optionsRadiosAuth2');
		optNoAuth.click(function(){
			document.getElementById('formSupp3').style.display = 'none';
	});
});