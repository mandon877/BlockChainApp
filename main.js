var io   = require('socket.io'),
    url  = require('url'),
    sys  = require('sys'),
    express = require('express'),
    http = require('http'),
    path = require('path'),
    bodyParser = require('body-parser');
    
var app = express();
var server = http.createServer(app);
var socket = io.listen(server);
var session = require('express-session');
var loggedIn = false;
app.engine('.html', require('ejs').__express);
app.set('views', __dirname + '/views');
app.set('view engine', 'html');
app.use(express.static(path.join(__dirname, 'public')));
/////////////////////////////////////////////////////////////////////////////
// login Start                                                             //
/////////////////////////////////////////////////////////////////////////////
app.use(session({
 secret: '@#@$MYSIGN#@$#$',
 resave: false,
 saveUninitialized: true
}));
app.use(bodyParser.json());

function simulatedDB() {
    const data = {
      User: [
        { id: 0,  username: 'mandon877@gmail.com',   password: '1234567891' },
        { id: 1,  username: 'mandon877@hotmail.com', password: '1234567891' }
      ]
    };
    this.User = {
      find: id => data.User[id],
      findByUsername: username => data.User.find(o => o.username === username),
      login: (username, password) => {
          return Promise.resolve()
          .then(() => {
            console.log("  2. username / passowrd : " + username + ' / ' + password);
            if (!username || !password) throw new Error("Invalid Parameters")
            const user = this.User.findByUsername(username);
            if (!user || user.password !== password) throw new Error("Invalid Credentials");
            console.log("  3. return user : " + user);
            console.log("     return [user.id] .username / .password : [" + user.id + "] " + user.username + " / " + user.password );
            return user;
          });
      }
    };
};

var Models = new simulatedDB();

app.use((req, _, next) => {
  console.log("A. ===================================>  const user : " 
              + req.session + " , "
              + req.session.user ); //+ " , " 
             // + req.session.user.username + "  ");
  const user = req.session && req.session.user && req.session.user.username || 'Unauthenticated';
  console.log("  0. " + req.method, req.path, "<",user,">") ;
  next();
});

app.post('/login', function(req, res, next) {
    const body = req.body;
    console.log('  1. Login request received:', body);
    return Models.User
      .login(req.body.username, req.body.password)
      .then(user => {
        req.session.user = user;
        console.log("  4. Logged in as: ", user.username);
        console.log("     req.session.user: ", req.session.user);
        res.redirect('/')
        //return res.send({ok:'ok'})
      })
      .catch(err => {
          console.log(err);
          res.status(400).send(err);
      });
})

// app.get('/login/:loggedIn', function(req, res){
//     loggedIn = req.params.loggedIn;
//     if (loggedIn) res.redirect('/');
// });


app.get('/logout', function(req, res){
    var sess = req.session;
    console.log("100. sess.username : " + sess.username);
    if(sess.username){
        req.session.destroy(function(err){
            if(err){
                console.log(err);
            }else{
                res.redirect('/login');
            }
        })
    }else{
        res.redirect('/login');
    }
});

///////////////////////////////////////////////////////////////////////////////
// login End                                                                 //
///////////////////////////////////////////////////////////////////////////////

app.get('/', function(req, res){
    console.log(">> Home(/) req.session.user : " + req.session.user);

    if (req.session.user)
        res.render('home'); 
    else
        res.render('login'); 
});

app.get('/login', function(req, res){
    res.render('login'); 
});

app.get('/Simple', function(req, res){
    // if (loggedIn)
    //     res.render('SimpleStorage'); 
    // else
    //     res.render('login'); 
    res.render('SimpleStorage'); 
});

app.get('/Car', function(req, res){
    // if (loggedIn)
    //      res.render('redirectCar'); 
    // else
    //     res.render('login');
    res.render('redirectCar'); 
});

app.get('/CodePen', function(req, res){
    // if (loggedIn)
    //     res.render('CodePenUrl'); 
    // else
    //     res.render('login'); 
    res.render('CodePenUrl'); 
});

app.get('/Angular', function(req, res){
    // if (loggedIn)
    //     res.render('AngularJsTest'); 
    // else
    //     res.render('login');
    res.render('AngularJsTest'); 
});

app.get('/MiningMechine', function(req, res){
    // if (loggedIn)
    //     res.render('MiningMechine'); 
    // else
    //     res.render('login');
    res.render('MiningMechine'); 
});

app.get('/Ethereum', function(req, res){
    // if (loggedIn)
    //     res.render('EthereumGit'); 
    // else
    //     res.render('login');
    res.render('EthereumGit'); 
});

app.get('/Stratis', function(req, res){
    // if (loggedIn)
    //     res.render('StratisGit'); 
    // else
    //     res.render('login');
    res.render('StratisGit'); 
});

app.get('/PdfView', function(req, res){
    // if (loggedIn)
    //     res.render('PdfView'); 
    // else
    //     res.render('login');
    res.render('PdfView'); 
});

app.get('/DomainResponse', function(req, res){
    // if (loggedIn)
    //     res.render('DomainResponse'); 
    // else
    //     res.render('login');
    res.render('DomainResponse'); 
});

app.get('/Bithumb', function(req, res){
    // if (loggedIn)
    //     res.render('BithumbUrl'); 
    // else
    //     res.render('login');
    res.render('BithumbUrl'); 
});

app.get('/Top40', function(req, res){
    // if (loggedIn)
    //     res.render('Top40Url'); 
    // else
    //     res.render('login');
    res.render('Top40Url'); 
});

app.get('/CryptoWatch', function(req, res){
    // if (loggedIn)
    //     res.render('CryptoWatchUrl'); 
    // else
    //     res.render('login');
    res.render('CryptoWatchUrl'); 
});

app.get('/Ens', function(req, res){
    // if (loggedIn)
    //     res.render('EnsUrl'); 
    // else
    //     res.render('login');
     res.render('EnsUrl'); 
});

app.get('/CoinMarCap', function(req, res){
    // if (loggedIn)
    //     res.render('CoinMarCapUrl'); 
    // else
    //     res.render('login');
    res.render('CoinMarCapUrl'); 
});

app.get('/ContackUs', function(req, res){
    // if (loggedIn)
    //     res.render('ContactUs'); 
    // else
    //     res.render('login');
    res.render('ContactUs'); 
    
});
app.listen(process.env.PORT, process.env.IP);
sys.puts('server running ' + 'now ' + Date.now());