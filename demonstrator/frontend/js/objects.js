var objects = [
    {id:'789DEF012', name: 'Smart Body Analyser'},
    {id:'123ABC456', name: 'Wii Balance Board', status:"ON", batteryLevel: 75}
];

function populateObjects(objects) {

    objects.forEach(function (object) {

        var div = $('<div class="col-xs-12 col-lg-6">'),
            $div = $(div),
            panel = $('<div class="panel panel-material-blue-grey"><div class="panel-heading"></div><div class="panel-body"><div class="list-group"></div></div></div>'),
            $panel = $(panel)
        ;

        $panel.find('.panel-heading').html('<h3 class="panel-title">' + object.name + '</h3>');

        var id_item = buildObjectInfoItem('mdi-action-assignment', 'Id', '<p class="list-group-item-text">' + object.id + '</p>');

        $panel.find('.panel-body .list-group').append(id_item);
        $panel.find('.panel-body .list-group').append('<div class="list-group-separator"></div>');

        if (object.status) {
            var status_icon = object.status == 'ON' ? 'mdi-navigation-check device-on' : 'mdi-navigation-close device-off',
                status_item = buildObjectInfoItem(status_icon, 'Statut', '<p class="list-group-item-text">' + object.status + '</p>');

            $panel.find('.panel-body .list-group').append(status_item);
            $panel.find('.panel-body .list-group').append('<div class="list-group-separator"></div>');
        }

        if (object.batteryLevel) {

            var battery_level = object.batteryLevel,
                progress_class = battery_level < 50 ? (battery_level <= 33 ? 'danger' : 'warning') : 'success';

            var progress_bar = '<div class="progress"><div class="progress-bar progress-bar-' + progress_class + '" style="width: ' + battery_level +'%"></div></div>',
                battery_item = buildObjectInfoItem('mdi-device-battery-full', 'Batterie', progress_bar, '<div class="least-content">' + battery_level + '%</div>');

            $panel.find('.panel-body .list-group').append(battery_item);
            $panel.find('.panel-body .list-group').append('<div class="list-group-separator"></div>');
        }

        $div.append($panel);

        $('#objects-details').append($div);

    });

}

function buildObjectInfoItem(icon_class, title, text_content, text_label) {

    var item = $('<div class="list-group-item"></div>'),
        $item = $(item),
        action = $('<div class="row-action-primary"><i class="' + icon_class + '"></i></div>'),
        $action = $(action),
        content = $('<div class="row-content"><h4 class="list-group-item-heading">' + title + '</h4></div>'),
        $content = $(content);

    $content.append(text_content);

    if (text_label) {
        $content.prepend(text_label);
    }

    $item
        .append($action)
        .append($content)
    ;

    return $item;

}

function getObjectsInfos() {

    $.ajax({
        url: 'http://localhost:8080/objects_infos/archives',
        type: 'GET',
        success: function (data) {
            populateObjects(data);
        },
        error: function (error) {
            populateObjects(objects);
        }
    });

}