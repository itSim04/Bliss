<?php

require 'connection.php';

if (array_key_exists("username", $_POST) && array_key_exists("password", $_POST)) {
	$username = $_POST["username"];
	$password =  $_POST["password"];
	try {


		$query = $mysqli->prepare("SELECT * FROM users WHERE username = ? AND password = ?");
		$query->bind_param("ss", $username, $password);
		$query->execute();
		$result = $query->get_result();

		$row = mysqli_fetch_assoc($result);

		$output["success"] = true;
		$output["error"] = 0;
	} catch (Exception $e) {

		$output["success"] = false;
		$output["error"] = $e->getMessage();
	}
} else {
	$output["success"] = false;
	$output["error"]   = "Missing Attributes";
}

$output["is_authenticated"] = mysqli_num_rows($result) > 0;


echo json_encode($output);
