<?php
include 'conexion.php';
$c_idpersona=$_POST['idpersona'];
$c_idcanino=$_POST['idcanino'];
$c_fecha_reg=$_POST['fecha_reg'];
$c_hora_reg=$_POST['hora_reg'];
$c_comentario=$_POST['comentario'];

$consulta="CALL reg_visita(".$c_idpersona.",".$c_idcanino.",'".$c_fecha_reg."','".$c_hora_reg."','".$c_comentario."')";
mysqli_query($conexion,$consulta) or die (mysqli_error($conexion));
mysqli_close($conexion);

?>