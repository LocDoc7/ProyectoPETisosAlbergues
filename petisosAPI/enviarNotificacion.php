<?php
    function enviarPush($to = '', $data = array()){
        $apiKey = "AAAAM4xvNBg:APA91bGD-TpAlAj4qzIvgL1TWGAvdrXWupEnJy81DrdV2oo_wWtDHIj_iWXMlSFkvMO1WJkQA0NApLYHAAcTrtadk8mmaW1goTP5Mv__m0s8xnca4SfCDwZvOiLfEdon2vSzbNZ3DIVN";

        $fields = array(
            'to' => $to,
            'data' => $data
        );
        $headers = array('Authorization: key='.$apiKey, 'Content-Type:application/json');
        $url = 'https://fcm.googleapis.com/fcm/send';

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

        echo json_encode($fields);
        echo "<br><br>RESPUESTA FCM: ";

        $result = curl_exec($ch);
        curl_close($ch);

        return json_decode($result,true);
    }

        //ENVIANDO TEMA

        $to = "/topics/dispositivos";

        $data = array(
            'title' => 'BIENVENIDO A PETISOS',
            'body' => 'Muchas gracias por apoyar este proyecto <3'
        );

        print_r(enviarPush($to,$data));
?>