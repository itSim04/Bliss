<?php

require 'connection.php';

$tweet_date = $_POST["tweet_date"];
$edit_date = $_POST["edit_date"];
$owner_id = $_POST["owner_id"];

$query = $mysqli->prepare("INSERT INTO `tweets` (`tweet_id`, `tweet_date`, `edit_date`, `owner_id`) VALUES (NULL, ?, ?, ?)");
$query->bind_param("sss", $tweet_date, $edit_date, $owner_id);
$query->execute();

echo "Succeeded";