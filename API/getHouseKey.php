<?php 

if(isset($_GET["houseId"]))
	echo GetHouseKey($_GET["houseId"]);

function GetHouseKey($houseId)
{
	include "database.php";
	$network = FinalizeNetwork($networks->GetRow($houseId));
	
	$ownerId = -1;
	
	for($i = 0; $i < count($network->users); $i++)
	{
		if($network->users[$i]->role == "Creator")
		{
			$ownerId = $network->users[$i]->id;
		}
	}
	
	$user = $users->GetRow($ownerId);
	
	$key = "key" . $network->name . $user->name . $user->password;
	
	return md5($key);
}

function FinalizeNetwork($raw)
{
	if($raw == null)
		return null;

	include "database.php";
	include "roles.php";
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
		$newUser->url = "http://timfalken.com/hr/smarthome/users/" . $newUser->id;
		$newUser->role = $roles[$links[$i]->role];
		array_push($raw->users, $newUser);
	}
	
	$raw->crownstones = [];
	$links = $crownstones->Where("`networkId`='$raw->id'");
	
	for($i = 0; $i < count($links); $i++)
	{
		$crownstone = new stdClass();
		
		$crownstone->id = $links[$i]->id;
		$crownstone->name = $links[$i]->name;
		$crownstone->url = "http://timfalken.com/hr/smarthome/crownstones/" . $crownstone->id;
		array_push($raw->crownstones, $crownstone);
	}
	
	return $raw;
}