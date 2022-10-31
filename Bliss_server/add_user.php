<?php

require 'connection.php';

if (array_key_exists("username", $_POST) && array_key_exists("password", $_POST) && array_key_exists("email", $_POST) && array_key_exists("birthday", $_POST) && array_key_exists("gender", $_POST) && array_key_exists("picture", $_POST) && array_key_exists("banner", $_POST)) {

	$username = $_POST["username"];
	$password = $_POST["password"];
	$email = $_POST["email"];
	$birthday = $_POST["birthday"];
	$gender = $_POST["gender"];
	$picture = $_POST["picture"];
	$banner = $_POST["banner"];

	if (strlen($username) < 2) {

		$output["inserted_id"] = -1;
		$output["success"] = false;
		$output["error"]   = "Username too short";

	} else if (strlen($password) < 5) {

		$output["inserted_id"] = -1;
		$output["success"] = false;
		$output["error"]   = "Password too weak";

	} else if (!str_contains($email, "@") || !str_contains($email, ".")) {

		
		$output["inserted_id"] = -1;
		$output["success"] = false;
		$output["error"]   = "Invalid Email";

	} else if (preg_match("/[a-z]/i", $birthday) || strlen($birthday) < 10) {

		$output["inserted_id"] = -1;
		$output["success"] = false;
		$output["error"]   = "Missing Birthday";

	} else {

		try {

			$query = $mysqli->prepare("INSERT INTO users (user_id, username, password, email, birthday, gender, picture, banner) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)");
			$query->bind_param("sssssss", $username, $password, $email, $birthday, $gender, $picture, $banner);
			$query->execute();
			$output["inserted_id"] = $mysqli->insert_id;
			$output["success"] = true;
			$output["error"] = 0;
		} catch (Exception $e) {

			$output["inserted_id"] = -1;
			$output["success"] = false;
			$output["error"] = $e->getMessage();
		}
	}
} else {

	$output["inserted_id"] = -1;
	$output["success"] = false;
	$output["error"]   = "Missing Attributes";
}
echo json_encode($output);
