<?php 

include_once "ApiCollection.php";

$dbUsers = new DatabaseInfo("db.timfalken.com", "md300889db336985", "users", "md300889db336985", "j8UmuEAx");
$dbNetworks = new DatabaseInfo("db.timfalken.com", "md300889db336985", "networks", "md300889db336985", "j8UmuEAx");
$dbCrownstones = new DatabaseInfo("db.timfalken.com", "md300889db336985", "crownstones", "md300889db336985", "j8UmuEAx");
$dbUserNetworks = new DatabaseInfo("db.timfalken.com", "md300889db336985", "userToNetwork", "md300889db336985", "j8UmuEAx");

$users = new ApiCollection($dbUsers, new ParamsLayout(["name", "email", "password", "prefs", "phoneId"]));
$networks = new ApiCollection($dbLocations, new ParamsLayout(["name"]));
$crownstones = new ApiCollection($dbCrownstones, new ParamsLayout(["name", "networkId"]));
$userToNetwork = new ApiCollection($dbCrownstones, new ParamsLayout(["userId", "networkId", "role"]));
