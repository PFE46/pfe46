layout 'layout.tpl', title: 'Documents : View all',
  content: contents {
    div(class:'container') {
      div(class:'pull-right') {
        a(href:'/database/document', 'Upload Document')
      }
      table(class:'table table-bordered table-striped') {
        thead {
          tr {
            td 'Name'
            td 'Link'
          }
        }
        tbody {
          if (documents.empty) { tr { td(colspan:'3', 'No Document' ) } }
          documents.each { document ->
            tr {
              td document.filename
              td {
                a(href:"/database/document/${document.filename}", 'Download')
              }
            }
          }
        }
      }
    }
  }