layout 'layout.tpl', title: 'Home',
  content: contents {
    div (class:'container') {
        div(class:'pull-right') {
            a(href:'/filesystem/document', 'Upload file in file system')
        }

        div(class:'pull-right') {
            a(href:'/database/document/list', 'Upload file in database')
        }
    }
  }