<?php

include "database.php";

if(isset($_GET["id"]))
{
	echo json_encode(FinalizeNetwork($networks->GetRow($_GET["id"])));
}
else
{
	$all = $networks->All();
	
	for($i = 0; $i < count($all); $i++)
	{
		$all[$i] = FinalizeNetwork($all[$i]);
	}
	
	echo json_encode($all);
}
	
	
function FinalizeNetwork($raw)
{
	if($raw == null)
		return null;

	include "database.php";
	$raw->users = [];
	$links = $userToNetwork->Where("`networkId`='$raw->id'");
	
	for($i = 0; $i < count($links); $i++)
	{
		$user = $users->GetRow($links[$i]->userId);
		$newUser = new stdClass();
		
		$newUser->id = $user->id;
		$newUser->name = $user->name;
		$newUser->email = $user->email;
		$newUser->phoneId = $user->phoneId;
		array_push($raw->users, $newUser);
	}
	
	return $raw;
}