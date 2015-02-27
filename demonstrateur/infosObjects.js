$(document).ready(function() {
	var data;

d3.json("WiiBoardInfos.json", function(json) {
  data = json;
  visualizeBoard();
});
d3.json("SmartBodyInfos.json", function(json) {
  data = json;
  visualizeSB();
});

function visualizeBoard(){
	data.forEach(function(d) {
	d.id = d.id;
	d.status = d.status;
	d.batteryLevel = d.batteryLevel;
	document.getElementById("id2").innerHTML=d.id;
	document.getElementById("statut2").innerHTML=d.status;
	document.getElementById("batterie").innerHTML=d.batteryLevel;
	document.getElementById("progress").setAttribute("style", "width : 75%;");
	document.getElementById("progress").setAttribute("aria-valuenow", "75");
		});
}

function visualizeSB(){
	data.forEach(function(d) {
	d.id = d.id;
	d.status = d.status;
	document.getElementById("id1").innerHTML=d.id;
	document.getElementById("statut1").innerHTML=d.status;
	
		});
}

});