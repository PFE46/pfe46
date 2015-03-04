var weight_data = [
        {"date":"2015-02-20","poids1":52,"poids2":50,"moyenne":51},
        {"date":"2015-02-22","poids1":51,"poids2":52,"moyenne":51.5},
        {"date":"2015-02-23","poids1":51.5,'poids2':50.5,"moyenne":51},
        {"date":"2015-02-24","poids1":51,"poids2":50,"moyenne":50.5},
        {"date":"2015-02-25","poids1":51,"moyenne":51},
        {"date":"2015-02-26","poids1":52,"poids2":51,"moyenne":51.5},
        {"date":"2015-02-27","poids1":53,"poids2":52,"moyenne":52.5},
        {"date":"2015-02-28","poids2":52,"moyenne":52},
        {"date":"2015-02-29","poids1":51,"poids2":50,"moyenne":50.5},
        {"date":"2015-03-02","poids1":51,"poids2":52,"moyenne":51.5}
    ],
    weight_chart = {
        "theme": "none",
        "type": "serial",
        "autoMargins": false,
        "marginLeft":8,
        "marginRight":8,
        "marginTop":10,
        "marginBottom":26,
        "pathToImages": "http://www.amcharts.com/lib/3/images/",
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
    }
;

function getWeight() {

    $.ajax({
        url: 'localhost:8080/weight/archives',
        type: 'GET',
        success: function (data) {
            console.log(data);
            return data;
        },
        error: function (error) {
            return weight_data;
        }
    });

}