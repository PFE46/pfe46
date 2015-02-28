var chart;

(function($) {

    var chartData = generatechartData();

    function generatechartData() {
        var chartData = [];
        var firstDate = new Date();
        firstDate.setDate(firstDate.getDate() - 150);

        for (var i = 0; i < 150; i++) {
            // we create date objects here. In your data, you can have date strings
            // and then set format of your dates using chart.dataDateFormat property,
            // however when possible, use date objects, as this will speed up chart rendering.
            var newDate = new Date(firstDate);
            newDate.setDate(newDate.getDate() + i);

            var visits = Math.round(Math.random() * 100 - 50);

            chartData.push({
                date: newDate,
                visits: visits
            });
        }
        return chartData;
    }

    var data = [
            {"date":"2015-02-22","poids1":58,"poids2":57,"moyenne":57.5},
            {"date":"2015-02-23","poids1":50,"poids2":48,"moyenne":49},
            {"date":"2015-02-24","poids1":51,"poids2":50,"moyenne":50.5},
            {"date":"2015-02-25","poids1":51,"poids2":50,"moyenne":50.5},
            {"date":"2015-02-26","poids1":52,"poids2":49,"moyenne":50.5},
            {"date":"2015-02-27","poids1":53,"poids2":52,"moyenne":52.5},
            {"date":"2015-02-28","poids1":53,"poids2":52,"moyenne":52.5}
        ]
        ;

    chart = AmCharts.makeChart("chartdiv", {
        "theme": "none",
        "type": "serial",
        "autoMargins": false,
        "marginLeft":8,
        "marginRight":8,
        "marginTop":10,
        "marginBottom":26,
        "pathToImages": "http://www.amcharts.com/lib/3/images/",
        "dataProvider": data,
        "valueAxes": [{
            "id":"weightAxis",
            "axisAlpha": 0,
            "inside": true
        }],
        "graphs": [
            {
                "balloonText": "<b>Poids : [[value]]</b>",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletBorderColor": "#FFFFFF",
                "hideBulletsCount": 50,
                "lineThickness": 2,
                "lineColor": "#f44336",
                "valueField": "moyenne",
                title: 'Moyenne'
            },
            {
                "balloonText": "<b>Poids : [[value]]</b>",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletBorderColor": "#FFFFFF",
                "hideBulletsCount": 50,
                "lineThickness": 2,
                "lineColor": "#4caf50",
                "valueField": "poids1",
                title: "Smart Body Analyzer"
            },{
                "balloonText": "<b>Poids : [[value]]</b>",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletBorderColor": "#FFFFFF",
                "hideBulletsCount": 50,
                "lineThickness": 2,
                "lineColor": "#2196f3",
                "valueField": "poids2",
                title: 'WiiBoard'
            }
        ],
        "chartCursor": {
            "cursorPosition": "mouse"
        },
        "categoryField": "date",
        "categoryAxis": {
            "parseDates": true,
            "axisAlpha": 0,
            "minHorizontalGap":100
        }
    });

    chart.addListener('rendered', function (event) {

        // populate our custom legend when chart renders
        chart.customLegend = document.getElementById('legend');

        $('#legend').append('<div id="legend-buttons" class="form-group"></div>');

        chart.graphs.forEach(function (graph, i) {

            var $togglebutton = $('<div class="togglebutton"><label><input type="checkbox" checked> ' + graph.title + '</label></div>');

            $('#legend-buttons').append($togglebutton);

            $.material.init();

            $togglebutton.on('change', function () {
                toggleChart(i);
            });

            var rgbColor = hexToRgb(graph.lineColor);

            $togglebutton
                .find('span.toggle').css('background-color', 'rgba('+rgbColor.r+','+rgbColor.g+','+rgbColor.b+',.5)')
            ;

        });

    });

    /* Events */
    $('a[data-action="toggle-menu"]').on('click', function () {

        var $me = $(this),
            $parent = $me.parent();

        if (!$parent.hasClass('active')) {

            $('#action-buttons').find('div.active').removeClass('active');
            $me.parent().addClass('active');

        }

    });

    /* Functions */
    function zoomChart(){
        if(chart.zoomToIndexes){
            chart.zoomToIndexes(130, chartData.length - 1);
        }
    }

    function hexToRgb(hex) {
        var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
        return result ? {
            r: parseInt(result[1], 16),
            g: parseInt(result[2], 16),
            b: parseInt(result[3], 16)
        } : null;
    }

    //chart.addListener("dataUpdated", zoomChart);
    //zoomChart();

})(jQuery);

function toggleChart(idx) {

    var graph = chart.graphs[idx];

    if (graph.hidden) {
        chart.showGraph(chart.graphs[idx]);
    }
    else {
        chart.hideGraph(chart.graphs[idx]);
    }

}
