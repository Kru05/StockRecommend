/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var connection = require('./../config');
module.exports.register=function(req,res){
    var users={
        "email":req.body.email,
        "password":req.body.password
    };
    connection.query('INSERT INTO users SET ?',users, function (error, results, fields) {
      if (error) {
        res.json({
            status:false,
            message:'there are some error with query'
        });
      }else{
          res.json({
            status:true,
            data:results,
            message:'user registered sucessfully'
        });
      }
    });
};
