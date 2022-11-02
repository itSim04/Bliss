<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT * FROM users");
	$query->execute();
	$result = $query->get_result();

	$users = [];
	while($row = mysqli_fetch_assoc($result)) {

		$users[] = $row;
	}

	$output["success"] = true;
	$output["error"] = 0;
	
	$result = [];
	$result["user"] = $users;
	$output["query_result"] = $result;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["query_result"] = 0;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);