<?php

require 'connection.php';

try {

	$query = $mysqli->prepare("SELECT * FROM users WHERE email = ?");
	$query->bind_param("s", $_POST["email"]);
	$query->execute();
	$result = $query->get_result();

	$row = mysqli_fetch_assoc($result);

	$output["success"] = true;
	$output["error"] = 0;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["error"] = $e->getMessage();
}

$output["available"] = mysqli_num_rows($result) <= 0;


echo json_encode($output);