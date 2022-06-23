<?php
include 'conexion.php';
$c_usuario=$_GET['usuario'];
$c_contrasenia=$_GET['contrasenia'];
$c_token=$_GET['token'];

$json = array();
        $consulta="CALL verificar_usuario('".$c_usuario."','".$c_contrasenia."','".$c_token."')";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["IdPersona"]=$registro['IdPersona'];
            $result["perCantDonaciones"]=$registro['perCantDonaciones'];
            $result["perCantCollares"]=$registro['perCantCollares'];
            $result["perScore"]=$registro['perScore'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>