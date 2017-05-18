var express = require('express');
var app = express();
var fs = require('fs');

app.listen (process.env.PORT, function() {
    console.log('Server Start!!');
});

app.get('/', function(req, res) {
    //fs.readFile('./SimpleStorage.html', function(error, data) {
    //fs.readFile('./mainTest.html', function(error, data) {
    fs.readFile('./cssDropdown.html', function(error, data) {
        if(error) {
            console.log(error);
        } else {
            res.writeHead(200, {'Content-Type': 'text/html'});
            res.end(data);
        }
    });
});