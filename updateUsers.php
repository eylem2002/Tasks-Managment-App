<?php

    $servername = "localhost";
	$username = "root";
	$password= "12345678";
	$db_name = "Rosie";
	
	$ID = $_POST["id"];
	$Name =  $_POST["name"];
	$Pass =  $_POST["password"];
	$Email =  $_POST["email"];
	$phone = $_POST["phone"];
	
	
	$conn = new mysqli($servername,$username,$password,$db_name);
	
	if($conn->connect_error){
		
		
		//die("not connected successfully :(");
	
	}else{
		
		
		//echo "Id --> ".$ID."<br>";
		//echo "Name --> ".$Name."<br>";
		//echo "Password --> ".$Pass."<br>";
		//echo "Email --> ".$Email."<br>";
		//echo "wlc user";
		$sql = "UPDATE user SET name = '$Name', password = '$Pass', email = '$Email', phone = '$phone' WHERE id = '$ID'";
		
		if($conn ->query($sql) === TRUE){
			
			
			//echo "record updated successfully"."<br>";
			$user = array(
				
				"name" => $Name,
				"email" => $Email,
				"password" => $Pass,
				"phone" => $phone
				);
				$task["error"] = false;
				$task["message"]= "User Info updated successfully";
				$task["user"] = $user;
				
				echo json_encode($task);
		}else{
			
			//echo "something goes wrong"."<br>";
			$user = array(
				
				"name" => "no data",
				"email" => "no data",
				"password" => "no data",
				"phone" => "no data"
				);
				$task["error"] = true;
				$task["message"]= "something goes worng - User Info not updated correctly!";
				//$product["message"]= "something goes worng - User Info not updated correctly! SQL:".$conn->error." Error --> ".$conn->error;
				$task["user"] = $user;
				
				echo json_encode($task);
				
		}
	}
?>