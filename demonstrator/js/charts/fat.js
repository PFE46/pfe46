var grease_data = [
        {"date":"2015-02-20","grease":50},
        {"date":"2015-02-22","grease":58},
        {"date":"2015-02-23","grease":50},
        {"date":"2015-02-24","grease":51},
        {"date":"2015-02-25","grease":51},
        {"date":"2015-02-26","grease":52},
        {"date":"2015-02-27","grease":53},
        {"date":"2015-02-28","grease":53},
        {"date":"2015-02-29","grease":51}
    ],
    grease_chart = {
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
                "balloonText": "<b>Masse grasse : [[value]]</b>",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletBorderColor": "#FFFFFF",
                "hideBulletsCount": 50,
                "lineThickness": 2,
                "lineColor": "#2196f3",
                "valueField": "grease",
                title: 'Masse grasse'
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
