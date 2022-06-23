<?php
include 'conexion.php';
$c_id_persona=$_POST['id_persona'];
$c_scoremaximo=$_POST['per_score'];
$c_collares=$_POST['per_collares'];

$consulta="CALL actualizar_score_collares_usuario(".$c_id_persona.",".$c_collares.",".$c_scoremaximo.")";
mysqli_query($conexion,$consulta) or die (mysqli_error($conexion));

mysqli_close($conexion);