<?php
include 'conexion.php';
$c_idalbergue=$_GET['idalbergue'];

$json = array();
        $consulta="CALL consultar_categorias_albergue(".$c_idalbergue.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["IdCategoriaCan"]=$registro['IdCategoriaCan'];
            $result["catDesc"]=$registro['catDesc'];
            $result["catRangoEdad"]=$registro['catRangoEdad'];
            $result["catImagen"]=$registro['catImagen'];
            $result["cantCanes"]=$registro['cantCanes'];
            $json['categorias'][]=$result;
        }

        mysqli_close($conexion);
        echo json_encode($json);
?>