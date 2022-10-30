<?php

require 'connection.php';

if (array_key_exists("user_id", $_POST)) {

	try {

		$query = $mysqli->prepare("SELECT follows.user_id1 FROM follows WHERE follows.user_id2 = ?");
		$query->bind_param("i", $_POST["user_id"]);
		$query->execute();
		$result = $query->get_result();

		$followers = [];

		while ($row = mysqli_fetch_assoc($result)) {

			$followers[] = $row;

		}

		$output["success"] = true;
		$output["error"] = 0;
		$output["query_results"] = $followers;

	} catch (Exception $e) {

		$output["success"] = false;
		$output["query_results"] = 0;
		$output["error"] = $e->getMessage();

	}
} else {

	$output["success"] = false;
	$output["query_results"] = null;
	$output["error"] = "Missing User ID";

}

echo json_encode($output);
