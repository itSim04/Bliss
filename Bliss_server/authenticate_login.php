<?php

require 'connection.php';

try {

	$query = $mysqli->prepare("SELECT * FROM users WHERE username = ? AND password = ?");
	$query->bind_param("ss", $_POST["username"], $_POST["password"]);
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

$output["is_authenticated"] = mysqli_num_rows($result) > 0;


echo json_encode($output);