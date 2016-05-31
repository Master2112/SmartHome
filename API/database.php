<?php 

include_once "ApiCollection.php";

$dbHeroes = new DatabaseInfo("db.timfalken.com", "md300889db318287", "heroes", "md300889db318287", "Jm8QvV4w");
$dbLocations = new DatabaseInfo("db.timfalken.com", "md300889db318287", "locations", "md300889db318287", "Jm8QvV4w");
$dbUsers = new DatabaseInfo("db.timfalken.com", "md300889db318287", "users", "md300889db318287", "Jm8QvV4w");

$heroes = new ApiCollection($dbHeroes, new ParamsLayout(["ownerId", "name", "stats", "inventory", "currentHealth", "maxHealth", "locationId", "lastUpdate", "alive", "currentAction", "money", "log"]));
$locations = new ApiCollection($dbLocations, new ParamsLayout(["name", "northLocationId", "eastLocationId", "southLocationId", "westLocationId", "allowedActions", "description", "animals", "items", "sleepPrice", "shop"]));
$users = new ApiCollection($dbUsers, new ParamsLayout(["userName", "email", "password", "level"]));
