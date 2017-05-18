var fs = require('fs');

fs.readFile('./SimpleStorage.html', 'utf-8', function(err, data) {
    if (err) {
        console.error('File Read Error : ' , err);
        return;
    }
    console.log('File : ', data);
});