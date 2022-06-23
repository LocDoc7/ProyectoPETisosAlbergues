<?php
include 'conexion.php';
$c_id_persona=$_GET['id_persona'];;

$json = array();
        $consulta="CALL consultar_notificaciones(".$c_id_persona.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["notTitulo"]=$registro['notTitulo'];
            $result["notDescripcion"]=$registro['notDescripcion'];
            $result["notFechaHora"]=$registro['notFechaHora'];
            $result["albImgLogo"]=$registro['albImgLogo'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>