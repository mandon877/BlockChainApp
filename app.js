var express = require('express');
var app = express();

console.log('process.env.PORT', process.env.PORT);

app.get('/', function(req, res) {
    res.send("Hello world");
})

app.listen(process.env.PORT, function(){
    console.log('Connected!');
})