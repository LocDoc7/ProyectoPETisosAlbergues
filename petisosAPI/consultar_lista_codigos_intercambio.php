<?php
include 'conexion.php';
$c_id_persona=$_GET['id_persona'];

$json = array();
        $consulta="CALL consultar_lista_codigo_intercambio(".$c_id_persona.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["IdVenta"]=$registro['IdVenta'];
            $result["proImagen"]=$registro['proImagen'];
            $result["proNombre"]=$registro['proNombre'];
            $result["albNombre"]=$registro['albNombre'];
            $result["venFecha"]=$registro['venFecha'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>