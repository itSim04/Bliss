<?php

require 'connection.php';

if(array_key_exists("owner_id", $_POST) && array_key_exists("user_id", $_POST)) {
$owner_id = $_POST["owner_id"];
$user_id = $_POST["user_id"];
try {

	$query = $mysqli->prepare("SELECT *, CASE WHEN (SELECT EXISTS(SELECT * FROM diamonds WHERE diamonds.user_id = ? && diamonds.gem_id = gems.gem_id)) THEN true ELSE false END as is_diamonded FROM gems WHERE owner_id = ?");
	$query->bind_param("ii", $user_id, $owner_id);
	$query->execute();
	$result = $query->get_result();
	
	$gems = [];
	$users = [];

	while($row = mysqli_fetch_assoc($result)) {

		
		$gems[] = $row;

	}


	$output["success"] = true;
	$output["error"] = 0;

	$result = [];                       
	$result["gem"] = $gems;
	$output["query_result"] = $result;

}

catch(Exception $e) {
	$output["success"] = false;
	$output["query_result"] = null;
	$output["error"] = $e->getMessage();
}} else {

$output["success"] = false;
	$output["query_result"] = null;
	$output["error"] = "Missing User ID";

}

echo json_encode($output);