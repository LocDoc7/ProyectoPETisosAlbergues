<?php
include 'conexion.php';
$c_id_persona=$_GET['id_persona'];

$json = array();
        $consulta="CALL consultar_visitas_registradas(".$c_id_persona.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["IdVisita"]=$registro['IdVisita'];
            $result["albNombre"]=$registro['albNombre'];
            $result["IdCanino"]=$registro['IdCanino'];
            $result["canNombre"]=$registro['canNombre'];
            $result["canImg"]=$registro['canImg'];
            $result["visFecha"]=$registro['visFecha'];
            $result["visHora"]=$registro['visHora'];
            $result["visEstado"]=$registro['visEstado'];
            $json['consulta'][]=$result;
        }
        mysqli_close($conexion);
        echo json_encode($json);
?>