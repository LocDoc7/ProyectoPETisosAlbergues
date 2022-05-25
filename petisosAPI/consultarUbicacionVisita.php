<?php
include 'conexion.php';
$c_id_canino=$_GET['id_canino'];

$json = array();
        $consulta="CALL consulta_ubicacion_visita(".$c_id_canino.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["ubiLatitud"]=$registro['ubiLatitud'];
            $result["ubiLongitud"]=$registro['ubiLongitud'];
            $result["ubiDescripcion"]=$registro['ubiDescripcion'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>