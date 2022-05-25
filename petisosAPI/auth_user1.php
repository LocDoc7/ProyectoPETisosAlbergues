<?php
include 'conexion.php';
//$user_usuario = $_POST['usuario'];
//$user_password = $_POST['password'];
$user_usuario="luchini";
$user_password="123456";

//$consulta="CALL auth_user('".$user_usuario."','".$user_password."')";
$consulta="CALL verificar_usuario('".$user_usuario."','".$user_password."')";
$resultado = mysqli_query($conexion,$consulta);

if($fila = mysqli_fetch_array($resultado)){
    echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}

mysqli_close($conexion);


?>