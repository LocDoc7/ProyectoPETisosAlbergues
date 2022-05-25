<?php
include 'conexion.php';
$c_idAlbergue=$_GET['idAlbergue'];

$json = array();
        $consulta="CALL consultar_medio_yape(".$c_idAlbergue.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["donImg"]=$registro['donImg'];
            $result["donCellYape"]=$registro['donCellYape'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>