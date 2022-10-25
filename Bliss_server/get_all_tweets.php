<?php

require 'connection.php';

$query = $mysqli->prepare("SELECT * FROM `tweets`");
$query->execute();
$result = $query->get_result();

$tweets = [];
while($row = mysqli_fetch_assoc($result)) {
	$tweets[] = $row;
}


print_r($tweets);