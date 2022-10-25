<?php

require 'connection.php';

try {

	$query = $mysqli->prepare("SELECT * FROM users WHERE username = ?");
	$query->bind_param("ss", $_POST["username"]);
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

$output["hit"] = mysqli_num_rows($result) > 0;


print_r($output);