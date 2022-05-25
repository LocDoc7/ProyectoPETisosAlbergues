<?php
include 'conexion.php';
$c_dni_user=$_GET['dni_user'];
$c_pwd_user=$_GET['pwd_user'];

$json = array();
        $consulta="CALL modify_password('".$c_dni_user."','".$c_pwd_user."')";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
        $result["retorno"]=$registro['retorno'];
        $json['resultado'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>