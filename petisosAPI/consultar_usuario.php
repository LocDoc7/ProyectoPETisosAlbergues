<?php
include 'conexion.php';
$c_usuario=$_GET['usuario'];
$c_contrasenia=$_GET['contrasenia'];

$json = array();
        $consulta="CALL verificar_usuario('".$c_usuario."','".$c_contrasenia."')";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["IdPersona"]=$registro['IdPersona'];
            $result["perCantDonaciones"]=$registro['perCantDonaciones'];
            $result["perCantCollares"]=$registro['perCantCollares'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>