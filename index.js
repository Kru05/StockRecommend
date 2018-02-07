/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var express=require("express");
var bodyParser=require('body-parser');
var app = express();
var authenticateController=require('./controllers/authenticate-controller');
var registerController=require('./controllers/register-controller');
app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());
/* route to handle login and registration */
app.post('/api/register',registerController.register);
app.post('/api/authenticate',authenticateController.authenticate);
app.listen(8012);
