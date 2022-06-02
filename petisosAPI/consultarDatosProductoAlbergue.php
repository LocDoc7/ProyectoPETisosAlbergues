<?php
include 'conexion.php';
$c_id_producto=$_GET['id_producto'];

$json = array();
        $consulta="CALL consultar_info_producto(".$c_id_producto.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["albNombre"]=$registro['albNombre'];
            $result["ubiLatitud"]=$registro['ubiLatitud'];
            $result["ubiLongitud"]=$registro['ubiLongitud'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>