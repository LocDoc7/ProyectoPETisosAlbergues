<?php
include 'conexion.php';

$json = array();
        $consulta="CALL consultar_consejos_albergues()";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["albNombre"]=$registro['albNombre'];
            $result["albImgLogo"]=$registro['albImgLogo'];
            $result["tituloConsejo"]=$registro['tituloConsejo'];
            $result["descConsejo"]=$registro['descConsejo'];
            $result["imgConsejo"]=$registro['imgConsejo'];
            $result["vinculoConsejo"]=$registro['vinculoConsejo'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>