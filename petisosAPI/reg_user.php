<?php
include 'conexion.php';
$c_apellidos=$_POST['apellidos'];
$c_nombres=$_POST['nombres'];
$c_edad=$_POST['edad'];
$c_numero=$_POST['numero'];
$c_dni=$_POST['dni'];
$c_ubicacion=$_POST['ubicacion'];
$c_ubilatitud=$_POST['ubilatitud'];
$c_ubilongitud=$_POST['ubilongitud'];
$c_pathimg=$_POST['pathimg'];
$c_descripcion=$_POST['descripcion'];
$c_usuario=$_POST['usuario'];
$c_pwd1=$_POST['pwd1'];

if(empty($_POST['pathimg'])){
    $imagePath = "";
}else{
    $imagePath = "img2/$c_dni.jpg";
    //$SERVER_URL = "http://192.168.1.2/API/petisosAPI/$imagePath";
    file_put_contents($imagePath,base64_decode($c_pathimg));
}
    $consulta="CALL reg_new_user('".$c_apellidos."','".$c_nombres."',".$c_edad.",'".$c_numero."','".$c_dni."','".$c_ubicacion."','".$c_ubilatitud."','".$c_ubilongitud."','".$imagePath."','".$c_descripcion."','".$c_usuario."','".$c_pwd1."')";
    mysqli_query($conexion,$consulta) or die (mysqli_error($conexion));


mysqli_close($conexion);

// $resultInsert = $mysql->query($consulta);


//$mysql->close();
// mysqli_query($conexion,$consulta) or die (mysqli_error($conexion));
// mysqli_close($conexion);

?>