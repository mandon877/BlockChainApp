var mysql = require("mysql");
var connection = mysql.createConnection({
    host     :  process.env.IP,
    user     : 'mandon877',
    password : '1', 
    port     : 3306,
    database : 'protoss'
});

    connection.connect();
    
    connection.query('SELECT USERID, USERNAME, USERGROUPID, PASSWORD FROM USERPROFILE', function(err, rows, fields) {
        if(!err)
            console.log('The solution is : ', rows);
        else
            console.log('Error while performing Query.', err);
    });
    
    connection.end();