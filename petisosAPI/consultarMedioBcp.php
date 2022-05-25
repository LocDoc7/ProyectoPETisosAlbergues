<?php
include 'conexion.php';
$c_idAlbergue=$_GET['idAlbergue'];

$json = array();
        $consulta="CALL consultar_medio_bcp(".$c_idAlbergue.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["donNumCuenta"]=$registro['donNumCuenta'];
            $result["donCci"]=$registro['donCci'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>