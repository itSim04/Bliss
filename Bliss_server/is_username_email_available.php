<?php

require 'connection.php';

if (array_key_exists("username", $_POST) && array_key_exists("username", $_POST)) {

	try {

		$query = $mysqli->prepare("SELECT * FROM users WHERE username = ?");
		$query->bind_param("s", $_POST["username"]);
		$query->execute();
		$result = $query->get_result();

		$row = mysqli_fetch_assoc($result);
		$username_available = mysqli_num_rows($result) <= 0;

		$query = $mysqli->prepare("SELECT * FROM users WHERE email = ?");
		$query->bind_param("s", $_POST["email"]);
		$query->execute();
		$result = $query->get_result();

		$row = mysqli_fetch_assoc($result);
		$email_available = mysqli_num_rows($result) <= 0;

		$is_available = [];
		$is_available["username"] = $username_available;
		$is_available["email"] = $email_available;
		$output["is_available"] = $is_available;
		
		$output["success"] = true;
		$output["error"] = 0;

	} catch (Exception $e) {

		$output["success"] = false;
		$output["error"] = $e->getMessage();
		$output["is_available"] = false;

	}
	
} else {

	$output["success"] = false;
	$output["error"] = "Missing Entries";
	$output["is_available"] = false;

}



echo json_encode($output);
