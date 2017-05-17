var http = require('http');
var server = http.createServer(function(req, res) {
     
     res.writeHeader(200, {'Content-Type': 'text/html; charset=UTF-8' });
//   res.statusCode = 200;
//   res.statusMessage = 'OK';
//   res.setHeader('content-type','text/plain');

  res.write('<html><body><h1>Hello World</h1><p><a href="./SimpleStorage.html">Redirect</a></p></body></html>');
  res.end();
}).listen(process.env.PORT);