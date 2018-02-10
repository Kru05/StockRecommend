var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "admin",
  database: "citi"
});

con.connect(function(err) {
  if (err) throw err;
  var userid = process.argv[2];
  var sql = 'SELECT * FROM History WHERE Userid = ?';  con.query(sql, [userid ], function (err, result) {
    if (err) throw err;
    console.log(result);
	var fs = require('fs');
	fs.writeFile('fetchHistory.json',JSON.stringify(result),function(err){
    if(err) throw err;
  })
  });
});