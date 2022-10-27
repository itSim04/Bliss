<?php

require 'connection.php';

try {
	$query = $mysqli->prepare("SELECT * FROM users WHERE user_id = ?");
	$query->bind_param("i", $_POST["user_id"]);
	$query->execute();
	$result = $query->get_result();

	$row = mysqli_fetch_assoc($result);
	
	$output["success"] = true;
	$output["error"] = 0;
	$output["query_result"] = $row;
}

catch(Exception $e) {
	$output["success"] = false;
	$output["query_result"] = 0;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);