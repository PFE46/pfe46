var	margin = {top: 30, right: 40, bottom: 100, left: 50}, 
	width = 700 - margin.left - margin.right,
	height = 400 - margin.top - margin.bottom;

var	parseDate = d3.time.format("%H-%d-%b-%y").parse;
		var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth();
	var yyyy = today.getFullYear();

var	x = d3.time.scale().range([0, width]);
var	y0 = d3.scale.linear().range([height, 0]);
/*var	y1 = d3.scale.linear().range([height, 0]);
var	y2 = d3.scale.linear().range([height, 0]);*/		

var	xAxis = d3.svg.axis().scale(x)
	.orient("bottom").ticks(10);

var	yAxisLeft = d3.svg.axis().scale(y0)
	.orient("left").ticks(10);

/*var	yAxisRight = d3.svg.axis().scale(y1)
.orient("right").ticks(5);	*/

var	valueline = d3.svg.line()
	.x(function(d) { return x(d.date); })
	.y(function(d) { return y0(d.frequence); });
  
var	svg = d3.select("body")
	.append("svg")
		//.attr("width", width + margin.left + margin.right)
		.attr("width", width + margin.left + margin.right)
		.attr("height", height + margin.top + margin.bottom)
	.append("g")
		.attr("transform", 
		      "translate(" + margin.left + "," + margin.top + ")");

// Get the data

var data;

d3.json("dataCoeur.json", function(json) {
  data = json;
  visualizeit();
});

function visualizeit(){
	data.forEach(function(d) {
	d.date = parseDate(d.date);
	d.frequence = +d.frequence;
		});
	
// Scale the range of the data
x.domain(d3.extent(data, function(d) { return d.date; }));
y0.domain([55, d3.max(data, function(d) { 
	return Math.max(d.frequence) + 5; })]);


svg.append("path")
	.attr("class", "line")
	.attr("id", "blueLine")
	.style("stroke", "red")
	.attr("d", valueline(data));

svg.append("g")	
	.attr("class", "x axis")
	.attr("transform", "translate(0," + height + ")")
	.call(xAxis);
	
svg.append("text")
.attr("class", "xaxis_label")
.attr("text-anchor", "middle") // this makes it easy to centre the text as the transform is applied to the anchor
.attr("transform", "translate("+ -25 +","+(height/2)+")rotate(-90)") // text is drawn off the screen top left, move down and out and rotate
.text("Frequence (bpm)");
	

// edit the Y Axis Left
svg.append("g")	
	.attr("class", "y axis")
	.style("fill", "black")
	.attr("id", "blueAxis")
	.call(yAxisLeft);
	
svg.append("text")
.attr("class", "yaxis_label")
.attr("text-anchor", "middle") // this makes it easy to centre the text as the transform is applied to the anchor
.attr("transform", "translate("+ (width/2) +","+(height + 30)+")") // text is drawn off the screen top left, move down and out and rotate
.text("Date");
}
	
 function rescaleDay() {
parseDate = d3.time.format("%H-%d-%b-%y").parse;

d3.json("dataCoeur.json", function(json) {
  data = json;

       	data.forEach(function(d) {
	    	d.date = parseDate(d.date);
	    	d.frequence = +d.frequence;
	    });
		
		//A modifier dynamiquement
		/*var mindate = new Date(yyyy,mm,dd,0),
		maxdate = new Date(yyyy,mmn,dd,23);*/

		 var mindate = new Date(yyyy,mm,dd,0),
		 maxdate = new Date(yyyy,mm,dd,23);

    	// Scale the range of the data again 
    	x.domain([mindate,maxdate]).clamp(true);
	    //y.domain([0, d3.max(data, function(d) { return d.close; })]);

    // Select the section we want to apply our changes to
    var svg = d3.select("body").transition();

    // Make the changes
        svg.select("#blueLine")   // change the line
            .duration(750)
            .attr("d", valueline(data));
        svg.select(".x.axis") // change the x axis
            .duration(750)
            .call(xAxis);
	
	});}
	
	
function rescaleWeek() {
parseDate = d3.time.format("%H-%d-%b-%y").parse;

d3.json("dataCoeur.json", function(json) {
  data = json;

       	data.forEach(function(d) {
	    	d.date = parseDate(d.date);
	    	d.frequence = +d.frequence;
	    });
		//A modifier dynamiquement
		 /*var mindate = new Date(2012,3,7,0),
		 maxdate = new Date(2012,3,15,23); */
		 var mindate = new Date(yyyy,mm,20,0),
		 maxdate = new Date(yyyy,mm,27,23);

    	// Scale the range of the data again 
    	x.domain([mindate,maxdate]).clamp(true);
	    //y.domain([0, d3.max(data, function(d) { return d.close; })]);

    // Select the section we want to apply our changes to
    var svg = d3.select("body").transition();

    // Make the changes
        svg.select("#blueLine")   // change the line
            .duration(750)
            .attr("d", valueline(data));
        svg.select(".x.axis") // change the x axis
            .duration(750)
            .call(xAxis);

    });
} 

function rescaleMonth() {
parseDate = d3.time.format("%H-%d-%b-%y").parse;

d3.json("dataCoeur.json", function(json) {
  data = json;

       	data.forEach(function(d) {
	    	d.date = parseDate(d.date);
	    	d.frequence = +d.frequence;
	    });
		//A modifier dynamiquement
		var previousMonth = mm - 1;
		 var mindate = new Date(yyyy,previousMonth,dd),
			maxdate = new Date(yyyy,mm,dd); 

    	// Scale the range of the data again 
    	x.domain([mindate,maxdate]).clamp(true);
	    //y.domain([0, d3.max(data, function(d) { return d.close; })]);

    // Select the section we want to apply our changes to
    var svg = d3.select("body").transition();

    // Make the changes
        svg.select("#blueLine")   // change the line
            .duration(750)
            .attr("d", valueline(data));
        svg.select(".x.axis") // change the x axis
            .duration(750)
            .call(xAxis);
		svg.select("x.axis")
    });
} 