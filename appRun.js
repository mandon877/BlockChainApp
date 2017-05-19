var io   = require('socket.io'),
    url  = require('url'),
    sys  = require('sys'),
    express = require('express'),
    http = require('http'),
    path = require('path');

var app = express();
var server = http.createServer(app);
var socket = io.listen(server);

app.engine('.html', require('ejs').__express);
app.set('views', __dirname + '/views');
app.set('view engine', 'html');

app.get('/', function(req, res){
    res.render('home'); 
});

app.get('/Simple', function(req, res){
      res.render('SimpleStorage'); 
});

app.use(express.static(path.join(__dirname, 'public')));

app.listen(process.env.PORT);
sys.puts('server running ' + 'now ' + Date.now());