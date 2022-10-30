<?php

require 'connection.php';

if (array_key_exists("email", $_POST)) {

	try {

		$query = $mysqli->prepare("SELECT * FROM users WHERE email = ?");
		$query->bind_param("s", $_POST["email"]);
		$query->execute();
		$result = $query->get_result();

		$row = mysqli_fetch_assoc($result);

		$output["success"] = true;
		$output["error"] = 0;
		$output["available"] = mysqli_num_rows($result) <= 0;

	} catch (Exception $e) {

		$output["success"] = false;
		$output["error"] = $e->getMessage();
		$output["is_available"] = false;

	}
} else {

	$output["success"] = false;
	$output["query_result"] = null;
	$output["error"] = "Missing User ID";

}


echo json_encode($output);
