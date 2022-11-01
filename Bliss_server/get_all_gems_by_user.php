<?php

require 'connection.php';

echo json_encode($_POST);
if(array_key_exists("owner_id", $_POST)) {
$owner_id = $_POST["owner_id"];
try {

	$query = $mysqli->prepare("SELECT * FROM gems WHERE owner_id = ?");
	$query->bind_param("i", $owner_id);
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