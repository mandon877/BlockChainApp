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

app.use(express.static(path.join(__dirname, 'public')));


app.get('/', function(req, res){
    res.render('home'); 
});

app.get('/login', function(req, res){
    res.render('login'); 
});

app.get('/Simple', function(req, res){
      res.render('SimpleStorage'); 
});

app.get('/Car', function(req, res){
      res.render('redirectCar');
});

app.get('/CodePen', function(req, res){
      res.render('CodePenUrl');
});

app.get('/Angular', function(req, res){
      res.render('AngularJsTest');
});

app.get('/MiningMechine', function(req, res){
      res.render('MiningMechine');
});

app.get('/Ethereum', function(req, res){
      res.render('EthereumGit');
});

app.get('/Stratis', function(req, res){
      res.render('StratisGit');
});

app.get('/PdfView', function(req, res){
      res.render('PdfView');
});

app.get('/DomainResponse', function(req, res){
      res.render('DomainResponse');
});

app.get('/Bithumb', function(req, res){
      res.render('BithumbUrl');
});

app.get('/Top40', function(req, res){
      res.render('Top40Url');
});

app.get('/CryptoWatch', function(req, res){
      res.render('CryptoWatchUrl');
});

app.get('/Ens', function(req, res){
      res.render('EnsUrl');
});

app.get('/CoinMarCap', function(req, res){
      res.render('CoinMarCapUrl');
});



app.get('/ContackUs', function(req, res){
      res.render('ContactUs');
});

app.listen(process.env.PORT);
sys.puts('server running ' + 'now ' + Date.now());