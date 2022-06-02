<?php
include 'conexion.php';
$c_id_visita=$_GET['id_visita'];

$json = array();
        $consulta="CALL consultar_detalle_visita_registrada(".$c_id_visita.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["canSexo"]=$registro['canSexo'];
            $result["canEdad"]=$registro['canEdad'];
            $result["ubiLatitud"]=$registro['ubiLatitud'];
            $result["ubiLongitud"]=$registro['ubiLongitud'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>