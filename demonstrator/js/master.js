var chart;

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

function makeChart(chart) {

    return AmCharts.makeChart("chartdiv", chart);
}

chart = makeChart(weight_chart);

chart.addListener('rendered', function (event) {
    populateLegend();
});

/* Events */

$('a[data-action="toggle-menu"]').on('click', function () {

    var $me = $(this),
        $parent = $me.parent();

    if (!$parent.hasClass('active')) {

        $('#chartdiv').empty();

        var parent_id = $parent.attr('id'),
            $graph_container = $('#graph-container'),
            $objects_container = $('#objects-container');

        if (parent_id == 'objects-button') {

            $graph_container.hide();
            $objects_container.show();

            populateObjects();
        }
        else {

            if (!$graph_container.is(':visible')) {
                $graph_container.show();
                $objects_container.hide();
            }

            if (parent_id == 'weight-button') {
                chart = makeChart(weight_chart);
            }
            else if (parent_id == 'bpm-button') {
                chart = makeChart(heart_chart);
            }
            else if (parent_id == 'grease-button') {
                chart = makeChart(grease_chart);
            }
            populateLegend();

        }

        $('#action-buttons').find('div.active').removeClass('active');
        $parent.addClass('active');

    }

});

$('a[data-action^="display-"]').on('click', function () {

    var $me = $(this);

    if (!$me.hasClass('active')) {

        $me
            .addClass('active')
            .siblings().removeClass('active')
        ;

        var end_date = new Date(),
            start_date = new Date(),
            action = $me.data('action'),
            offset;

        if (action == 'display-day') {
            offset = 2;
        }
        else if (action == 'display-week') {
            offset = 7;
        }
        else if (action == 'display-month') {
            offset = 30;
        }

        start_date.setDate(end_date.getDate() - offset);
        end_date.setDate(end_date.getDate() + 1);

        chart.zoomToDates(start_date, end_date);

    }

});

/* Functions */

function populateLegend() {
    // populate our custom legend when chart renders
    chart.customLegend = document.getElementById('legend');

    $('#legend-buttons').empty();

    chart.graphs.forEach(function (graph, i) {

        var $togglebutton = $('<div class="togglebutton"><label><input type="checkbox" ' + (graph.hidden ? '' : 'checked') + '> ' + graph.title + '</label></div>');

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
}

function hexToRgb(hex) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
    } : null;
}


function toggleChart(idx) {

    var graph = chart.graphs[idx];

    if (graph.hidden) {
        chart.showGraph(chart.graphs[idx]);
    }
    else {
        chart.hideGraph(chart.graphs[idx]);
    }

}
