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

		$result_temp = [];
		$result_temp["user"] = [$row];	
		$output["query_result"] = $result_temp;
		$output["success"] = true;
		$output["error"] = 0;

		$output["is_authenticated"] = mysqli_num_rows($result) > 0;

	} catch (Exception $e) {

		$output["query_result"] = null;
		$output["success"] = false;
		$output["error"] = $e->getMessage();
		$output["is_authenticated"] = false;

	}

} else {

	$output["query_result"] = null;
	$output["success"] = false;
	$output["error"]   = "Missing Attributes";
	$output["is_authenticated"] = false;
	
}



echo json_encode($output);
