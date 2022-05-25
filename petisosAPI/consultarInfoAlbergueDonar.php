<?php
include 'conexion.php';
$c_idAlbergue=$_GET['idAlbergue'];

$json = array();
        $consulta="CALL consultar_albergue_donacion(".$c_idAlbergue.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["albDesc"]=$registro['albDesc'];
            $result["albCell"]=$registro['albCell'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>