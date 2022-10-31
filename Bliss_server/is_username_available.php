<?php

require 'connection.php';

if (array_key_exists("username", $_POST)) {

	try {

		$query = $mysqli->prepare("SELECT * FROM users WHERE username = ?");
		$query->bind_param("s", $_POST["username"]);
		$query->execute();
		$result = $query->get_result();

		$row = mysqli_fetch_assoc($result);

		$output["success"] = true;
		$output["error"] = 0;
		$output["is_available"] = mysqli_num_rows($result) <= 0;

	} catch (Exception $e) {

		$output["success"] = false;
		$output["error"] = $e->getMessage();
		$output["is_available"] = false;

	}
	
} else {

	$output["success"] = false;
	$output["error"] = "Missing User ID";
	$output["is_available"] = false;

}



echo json_encode($output);
