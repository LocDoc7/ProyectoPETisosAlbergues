<?php
    function enviarPush($to = '', $data = array()){
        $apiKey = "AAAAqUTxBNk:APA91bFFrwkK5O_Pe5nIJbW6uLFt53sGcHpKhVRW430dRl6su4qti1KpAh0NUTMQEBna3WWRzKqUbYyOYCVok7GyYfIRSR1SSWD9dvGUazqvoKvzFeg2kKAJtH0Qa6MFPLmK09tvVwBN";

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
            'title' => 'Mensaje de Prueba',
            'body' => 'Tiene un nuevo mensaje'
        );

        print_r(enviarPush($to,$data));
?>