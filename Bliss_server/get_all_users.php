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
	$output["result"] = $users;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["result"] = 0;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);