<?php
include 'conexion.php';
$c_id_persona=$_POST['id_persona'];
$c_per_apellido=$_POST['per_apellido'];
$c_per_nombre=$_POST['per_nombre'];
$c_per_desc=$_POST['per_desc'];
$c_per_lat=$_POST['per_lat'];
$c_per_lng=$_POST['per_lng'];
$c_ubi_desc=$_POST['ubi_desc'];
$c_rutaimg=$_POST['rutaimg'];
$c_pathimg=$_POST['pathimg'];

if(empty($_POST['pathimg'])){
    $imagePath = "";
}else{
    //$SERVER_URL = "http://192.168.1.2/API/petisosAPI/$imagePath";
    unlink($c_rutaimg);
    file_put_contents($c_rutaimg,base64_decode($c_pathimg));
}
    $consulta="CALL actualizar_info_usuario(".$c_id_persona.",'".$c_per_apellido."','".$c_per_nombre."','".$c_per_desc."','".$c_per_lat."','".$c_per_lng."','".$c_ubi_desc."')";
    mysqli_query($conexion,$consulta) or die (mysqli_error($conexion));


mysqli_close($conexion);

// $resultInsert = $mysql->query($consulta);


//$mysql->close();
// mysqli_query($conexion,$consulta) or die (mysqli_error($conexion));
// mysqli_close($conexion);

?>