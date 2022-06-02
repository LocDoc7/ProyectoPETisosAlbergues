<?php
include 'conexion.php';
$c_idvisita=$_POST['idvisita'];

$consulta="CALL cancelar_visita(".$c_idvisita.")";
mysqli_query($conexion,$consulta) or die (mysqli_error($conexion));
mysqli_close($conexion);

?>