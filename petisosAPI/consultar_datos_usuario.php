<?php
include 'conexion.php';
$c_id_persona=$_GET['id_persona'];

$json = array();
        $consulta="CALL consultar_usuario_datos(".$c_id_persona.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["perImgPerfil"]=$registro['perImgPerfil'];
            $result["perApellidos"]=$registro['perApellidos'];
            $result["perNombres"]=$registro['perNombres'];
            $result["perDescripcion"]=$registro['perDescripcion'];
            $result["ubiLatitud"]=$registro['ubiLatitud'];
            $result["ubiLongitud"]=$registro['ubiLongitud'];
            $result["ubiDescripcion"]=$registro['ubiDescripcion'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>