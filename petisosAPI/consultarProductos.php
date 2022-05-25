<?php
include 'conexion.php';

$json = array();
        $consulta="CALL consultar_productos()";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["IdProducto"]=$registro['IdProducto'];
            $result["proNombre"]=$registro['proNombre'];
            $result["proDescripcion"]=$registro['proDescripcion'];
            $result["proPrecioReal"]=$registro['proPrecioReal'];
            $result["proPrecioCollares"]=$registro['proPrecioCollares'];
            $result["proImagen"]=$registro['proImagen'];
            $result["IdAlbergue"]=$registro['IdAlbergue'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>