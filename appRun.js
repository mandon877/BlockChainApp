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
var mysql = require('mysql');
app.engine('.html', require('ejs').__express);
app.set('views', __dirname + '/views');
app.set('view engine', 'html');
app.use(express.static(path.join(__dirname, 'public')));

/////////////////////////////////////////////////////////////////////////////
// START DB Connection                                                     //
/////////////////////////////////////////////////////////////////////////////
// mysql-ctl cli
// use protoss;
// show tables;
// SELECT * FROM USERPROFILE;
///////////////////////////////////////////////////////////////////////////////
// var mysql = require("mysql");
// var connection = mysql.createConnection({
//     host : "localhost",
//     port : 3306,
//     user : "root",
//     password : "apmsetup",
//     database : "nodejs"
// });
// var sqlQuery = "INSERT INTO member SET ?";
// var post = {id : "kim3", pw : "1333", name : "noname"};
// 
// function callback(err,result){
//     if(err){
//         throw err
//     }
//     console.log("Insert Complete!");
//     console.log(query.sql);
// }
// 
// connection.connect();
// var query = connection.query(sqlQuery, post, callback);
// connection.end();
//
// var mysql = require("mysql");
// var connection = mysql.createConnection({
//     host : "localhost",
//     port : 3306,
//     user : "root",
//     password : "apmsetup",
//     database : "nodejs"
// });
// var sqlQuery = "SELECT * FROM member";
// 
// function callback(err,rows, fields){
//     if(err){
//         throw err
//     }    
//     for(var i=0; i<rows.length;i++){
//         console.log(rows[i].id+" | "+rows[i].pw+" | "+rows[i].name);
//     }
// }
// 
// connection.connect();
// connection.query(sqlQuery, callback);
// connection.end();
//////////////////////////////////////////////////////////////////////////////////////
var connection = mysql.createConnection({
    host     :  process.env.IP,
    user     : 'mandon877',
    password : '1', 
    port     : 3306,
    database : 'protoss'
});

// connection.connect();
// connection.query('SELECT USERID, USERNAME, USERGROUPID, PASSWORD FROM USERPROFILE', function(err, rows, fields) {
//     if(!err)
//         console.log('The solution is : ', rows);
//     else
//         console.log('Error while performing Query.', err);
// });
    
// connection.end();
/////////////////////////////////////////////////////////////////////////////
// END DB Connection                                                       //
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
// login Start                                                             //
/////////////////////////////////////////////////////////////////////////////
app.use(session({
   secret: '@#@$MYSIGN#@$#$',
   saveUninitialized: false,
   resave: true
}));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

function simulatedDB() {
    const data = {
       User: [
         //   { id: 0,  username: 'mandon877@gmail.com',   password: '1234567891' },
         //   { id: 1,  username: 'mandon877@hotmail.com', password: '1234567891' },
       ]
    };
    
    var sqlQuery = "SELECT USERID, PASSWORD FROM USERPROFILE";
    
    function callback(err,rows,fields) {
         if(err) {
             throw err
         }

         for(var i=0; i<rows.length; i++) {
             var newUser = {
                id: i,
                username: rows[i].USERID,
                password: rows[i].PASSWORD
             };
             data.User.push(newUser);

             //  data.User[i].id = i;
             //  data.User[i].username = rows[i].USERID;
             //  data.User[i].password = rows[i].PASSWORD;
             //  console.log('The solution is : ', rows);
             //  console.log('data.User[' + i + '].id : ' +  data.User[i].id);
             //  console.log('data.User[' + i + '].username : ' +  data.User[i].username);
             //  console.log('data.User[' + i + '].password : ' +  data.User[i].password);
     }
    }
    
    connection.connect();
    connection.query(sqlQuery,callback);
    connection.end();
    
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
//   const user = req.session && req.session.user && req.session.user.username || 'Unauthenticated';
   const user = req.session.key || 'Unauthenticated';
   console.log(req.method, req.path, "<",user,">") ;
   next();
 });

app.post('/login', function(req, res, next) {
    const body = req.body;
    console.log('Login request received:', body);
    return Models.User
      .login(req.body.username, req.body.password)
      .then(user => {
        req.session.key=user.username;
        console.log("Logged in as username : ", req.session.key);
        return res.send({ok:'ok'})
      })
      .catch(err => {
          console.log(err);
          res.status(400).send(err);
      });
})

app.get('/logout', function(req, res){
    req.session.destroy(function(err){
        if(err)
            console.log(err);
        else
            res.redirect('/login');
    });
});
///////////////////////////////////////////////////////////////////////////////
// login End                                                                 //
///////////////////////////////////////////////////////////////////////////////
app.get('/login', function(req, res){
    if (req.session.key)
       res.render('home'); 
    else
       res.render('login'); 
});

app.get('/', function(req, res){
    if (req.session.key)
        res.render('home'); 
    else
       res.render('login'); 
});

app.get('/home', function(req, res){
    if (req.session.key)
        res.render('home');
    else 
        res.render('login'); 
});

app.get('/Simple', function(req, res){
    if (req.session.key)
        res.render('SimpleStorage'); 
    else
        res.render('login'); 
});

app.get('/Car', function(req, res){
    if (req.session.key)
         res.render('redirectCar'); 
    else
        res.render('login');
});

app.get('/CodePen', function(req, res){
    if (req.session.key)
        res.render('CodePenUrl'); 
    else
        res.render('login'); 
});

app.get('/Angular', function(req, res){
    if (req.session.key)
        res.render('AngularJsTest'); 
    else
        res.render('login');
});

app.get('/MiningMechine', function(req, res){
    if (req.session.key)
        res.render('MiningMechine'); 
    else
        res.render('login');
});

app.get('/Ethereum', function(req, res){
    if (req.session.key)
        res.render('EthereumGit'); 
    else
        res.render('login');
});

app.get('/Stratis', function(req, res){
    if (req.session.key)
        res.render('StratisGit'); 
    else
        res.render('login');
});

app.get('/PdfView', function(req, res){
    if (req.session.key)
        res.render('PdfView'); 
    else
        res.render('login');
});

app.get('/DomainResponse', function(req, res){
    if (req.session.key)
        res.render('DomainResponse'); 
    else
        res.render('login');
});

app.get('/Bithumb', function(req, res){
    if (req.session.key)
        res.render('BithumbUrl'); 
    else
        res.render('login');
});

app.get('/Top40', function(req, res){
    if (req.session.key)
        res.render('Top40Url'); 
    else
        res.render('login');
});

app.get('/CryptoWatch', function(req, res){
    if (req.session.key)
        res.render('CryptoWatchUrl'); 
    else
        res.render('login');
});

app.get('/Ens', function(req, res){
    if (req.session.key)
        res.render('EnsUrl'); 
    else
        res.render('login');
});

app.get('/CoinMarCap', function(req, res){
    if (req.session.key)
        res.render('CoinMarCapUrl'); 
    else
        res.render('login');
});

app.get('/ContackUs', function(req, res){
    if (req.session.key)
        res.render('ContactUs'); 
    else
        res.render('login');
});
app.listen(process.env.PORT, process.env.IP);
sys.puts('server running ' + 'now ' + Date.now());
