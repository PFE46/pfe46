layout 'layout.tpl', title: 'Success',
  content: contents {
    div(class:'container') {
      p('You successfully uploaded this document !')
      div(class:'pull-right') {
        a(href:link, 'Link to this document')
      }
    }
  }