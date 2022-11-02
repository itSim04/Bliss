<?php

require 'connection.php';

if (array_key_exists("user_id", $_POST)) {


	try {

		$user_id = $_POST["user_id"];

		$query = $mysqli->prepare("SELECT *, CASE WHEN (SELECT EXISTS(SELECT * FROM diamonds WHERE diamonds.user_id = ? && diamonds.gem_id = gems.gem_id)) THEN true ELSE false END as is_diamonded FROM gems");
		$query->bind_param("i", $_POST["user_id"]);
		$query->execute();
		$result = $query->get_result();

		$subquery = "SELECT * FROM users WHERE";
		$gems = [];
		$users = [];

		while ($row = mysqli_fetch_assoc($result)) {

			$gems[] = $row;
			$subquery .= " user_id = " . $row["owner_id"] . " ||";
		}

		$subquery = substr($subquery, 0, -3);

		$query = $mysqli->prepare($subquery);
		$query->execute();
		$result = $query->get_result();

		$users = [];

		while ($row = mysqli_fetch_assoc($result)) {

			$users[] = $row;
		}

		$output["success"] = true;
		$output["error"] = 0;

		$result = [];
		$result["user"] = $users;
		$result["gem"] = $gems;
		$output["query_result"] = $result;
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
