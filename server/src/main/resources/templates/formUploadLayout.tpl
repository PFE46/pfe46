html {
  head {
    title(title)
  }
  body {
      h1(title)
      div(class:'pull-right') {
            a(href:'/', 'Go back to home')
      }
      div (class:'container') {
            form (method:'post', enctype:'multipart/form-data', action:action) {
              p('File to upload :')
              input (name:'file', type:'file')
              p('Name :')
              input (name:'filename', type="text")
              p('Press here to upload the file')
              div (class:'form-actions') {
                input (type:'submit', value:'Upload')
              }
            }
      }
    }
}