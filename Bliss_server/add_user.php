<?php

require 'connection.php';

$username = $_POST["username"];
$password = $_POST["password"];
$email = $_POST["email"];
$birthday = $_POST["birthday"];
$gender = $_POST["gender"];
$picture = $_POST["picture"];
$banner = $_POST["banner"];

$query = $mysqli->prepare("INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `birthday`, `gender`, `picture`, `banner`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)");
$query->bind_param("sssssss", $username, $password, $email, $birthday, $gender, $picture, $banner);
$query->execute();

echo "Succeeded";