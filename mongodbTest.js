var mongodb = require('mongodb');
function ConnectToDB(mongoUrl){
    var MongoClient = mongodb.MongoClient;
    //var url = 'mongodb://localhost:27017/my_database_name';
    var url = mongoUrl || 'mongodb://' + process.env.IP + ":27017/test";
    // Use connect method to connect to the Server
    MongoClient.connect(url, function(err, db) {
        if(err){
           console.log(err); 
        }
      console.log("Connected correctly to server");
      return db;
    });
}