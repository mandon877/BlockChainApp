var http = require('http');

var movieList = [{title:'아바타' , director:'제임스 카메론'} ];
//var movieList = [{title:'ABATA' , director:'JAMSE KAMELON'} ];

http.createServer(function (req, res) {
    if( req.method.toLocaleLowerCase() == 'post') {
        var buffer = '';
        req.on('data', function(chunk) {
            buffer += chunk;
        });

        req.on('end', function() {
            var parsed = JSON.parse(buffer);
            var titleData = parsed.title;
            var directorData = parsed.director;

            movieList.push({title:titleData, director:directorData});

            res.writeHead(200, {'Content-Type':'application/json'});
            res.end(JSON.stringify({result:'success'}));
        });
    }
    else {
        var result = {
            count : movieList.length,
            data : movieList
        };
        res.writeHead(200, {'Content-Type':'application/json'});
        res.end(JSON.stringify(result));
      }
}).listen(process.env.PORT);