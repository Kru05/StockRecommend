var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "admin",
  database: "citi"
});

con.connect(function(err) {
  if (err) throw err;
  con.query("SELECT * FROM stocks", function (err, result, fields) {
    if (err) throw err;
    console.log(result);
var fs = require('fs');
fs.writeFile ("fetchHistory.json", JSON.stringify(result), function(err) {
    if (err) throw err;
    console.log('complete');
    }
);

  });
});
