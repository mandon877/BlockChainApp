var express = require('express');
var app = express();
var path = require('path')
var fs = require('fs');

app.listen (process.env.PORT, function() {
    console.log('Server Start!!');
});

//app.use(express.static(path.join(__dirname, 'public')));

app.get('/', function(req, res) {
    //fs.readFile('./SimpleStorage.html', function(error, data) {
    if(req.url.indexOf('.html') != -1) {
        fs.readFile('./mainNavigation.html', function(error, data) {
            if(error) console.log(error);
            res.writeHead(200, {'Content-Type': 'text/html'});
            res.end(data);
        });
    }
});

