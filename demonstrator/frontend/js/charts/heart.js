var heart_data = [
        {"date":"2015-02-15","frequence":60},
        {"date":"2015-02-15","frequence":90},
        {"date":"2015-02-16","frequence":85},
        {"date":"2015-02-17","frequence":75},
        {"date":"2015-02-17","frequence":65},
        {"date":"2015-02-18","frequence":62},
        {"date":"2015-02-19","frequence":70},
        {"date":"2015-02-20","frequence":68},
        {"date":"2015-02-20","frequence":65},
        {"date":"2015-02-21","frequence":63}
    ],
    heart_chart = {
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
                "balloonText": "<b>Frequence cardiaque : [[value]] bpm</b>",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletBorderColor": "#FFFFFF",
                "hideBulletsCount": 50,
                "lineThickness": 2,
                "lineColor": "#f44336",
                "valueField": "frequence",
                title: 'Frequence'
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