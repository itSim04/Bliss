<?php

require 'connection.php';

if (array_key_exists("user_id", $_POST)) {

	try {

		$query = $mysqli->prepare("SELECT follows.user_id2 FROM follows WHERE follows.user_id1 = ?");
		$query->bind_param("i", $_POST["user_id"]);
		$query->execute();
		$result = $query->get_result();

		$followings = [];

		while ($row = mysqli_fetch_assoc($result)) {

			$followings[] = $row;
		}

		$output["success"] = true;
		$output["error"] = 0;
		$output["query_result"] = $followings;

	} catch (Exception $e) {

		$output["success"] = false;
		$output["query_result"] = 0;
		$output["error"] = $e->getMessage();

	}
} else {

	$output["success"] = false;
	$output["query_result"] = null;
	$output["error"] = "Missing User ID";
	
}

echo json_encode($output);
