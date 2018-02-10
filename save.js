var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "admin",
  database: "citi"
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
  var sql = "INSERT INTO History(Userid,Code,Date,Close) VALUES ("+parseInt(process.argv[2])+",'"+process.argv[3]+"','"+process.argv[4]+"',"+process.argv[5]+")";
console.log(sql);
con.query(sql,function (err, result,fields) {
  if (err) throw err;
    console.log("1 record inserted");
  });
});