<?php

require 'connection.php';

try {

	$query = $mysqli->prepare("SELECT * FROM gems");
	$query->execute();
	$result = $query->get_result();
	
	$subquery = "SELECT * FROM users WHERE";
	$gems = [];
	$users = [];

	while($row = mysqli_fetch_assoc($result)) {

		
		$gems[] = $row;
		$subquery .= " user_id = " . $row["owner_id"] . " ||";

	}

	$subquery = substr($subquery, 0, -3);
   
	$query = $mysqli->prepare($subquery);
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
	$result["gem"] = $gems;
	$output["query_result"] = $result;

}

catch(Exception $e) {
	$output["success"] = false;
	$output["query_result"] = 0;
	$output["error"] = $e->getMessage();
}

echo json_encode($output);