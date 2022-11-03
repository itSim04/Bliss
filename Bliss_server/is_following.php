<?php

require 'connection.php';

if (array_key_exists("user_id1", $_POST) && array_key_exists("user_id2", $_POST)) {

	$user_id1 = $_POST["user_id1"];
	$user_id2 = $_POST["user_id2"];

	try {

		$query = $mysqli->prepare("SELECT * FROM follows WHERE user_id1 = ? AND user_id2 = ?");
		$query->bind_param("ii", $user_id1, $user_id2);
		$query->execute();
		$result = $query->get_result();

		$output["is_authenticated"] = mysqli_num_rows($result) > 0;
		$output["success"] = true;
		$output["error"] = 0;

	} catch (Exception $e) {

		$output["is_authenticated"] = false;
		$output["success"] = false;
		$output["error"] = $e->getMessage();

	}
} else {

	$output["is_authenticated"] = false;
	$output["success"] = false;
	$output["error"]   = "Missing Attributes";
	
}
echo json_encode($output);
