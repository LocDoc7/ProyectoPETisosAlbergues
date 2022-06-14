<?php
include 'conexion.php';
$c_id_venta=$_GET['id_venta'];

$json = array();
        $consulta="CALL consultar_detalle_producto_intercambiado(".$c_id_venta.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["idModoPago"]=$registro['idModoPago'];
            $result["ubiLatitud"]=$registro['ubiLatitud'];
            $result["ubiLongitud"]=$registro['ubiLongitud'];
            $result["detCantidad"]=$registro['detCantidad'];
            $result["detPrecio"]=$registro['detPrecio'];
            $result["venEstado"]=$registro['venEstado'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>