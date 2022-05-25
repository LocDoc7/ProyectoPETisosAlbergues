<?php
$hostname='127.0.0.1';
$username ='user_comon';
$password='udJQh8ii9JjuR4/F';
$database ='dbpetisos';

$conexion = mysqli_connect($hostname,$username,$password,$database);

if( mysqli_connect_errno() ){
    echo "Conexion fallida: " . mysqli_connect_error();
}else{
    
}



?>