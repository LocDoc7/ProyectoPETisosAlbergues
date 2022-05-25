<?php
include 'conexion.php';
$c_idalbergue=$_GET['idalbergue'];
$c_idcategoria=$_GET['idcategoria'];

$json = array();
        $consulta="CALL consultar_caninos_por_albergue(".$c_idcategoria.",".$c_idalbergue.")";
        $resultado = mysqli_query($conexion,$consulta);
        while($registro = mysqli_fetch_array($resultado)){
            $result["IdCanino"]=$registro['IdCanino'];
            $result["canNombre"]=$registro['canNombre'];
            $result["canHistoria"]=$registro['canHistoria'];
            $result["canImg"]=$registro['canImg'];
            $result["canSexo"]=$registro['canSexo'];
            $result["canEdad"]=$registro['canEdad'];
            $result["canRaza"]=$registro['canRaza'];
            $result["canEstado"]=$registro['canEstado'];
            $json['caninos'][]=$result;
        }

        mysqli_close($conexion);
        echo json_encode($json);
?>