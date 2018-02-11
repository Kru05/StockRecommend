var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "admin",
  database: "citi"
});

con.connect(function(err) {
  if (err) throw err;
  var Username = process.argv[2];
  var Password = process.argv[3];
  var result=0;
  var sql = 'SELECT * FROM Users WHERE Username = ? and Password =?'; 
var flag=0;
	 con.query(sql, [Username,Password], function (err, result) {
    
	if (err) throw err;
    for (i = 0; i < result.length; i++) { 
    if(Username==result[i].Username && Password==result[i].Password)
		flag=1;
	
}
console.log(flag);
var fs = require('fs');
	fs.writeFile('login.json',JSON.stringify(flag),function(err){
if(err) throw err;
  })
  });
});
