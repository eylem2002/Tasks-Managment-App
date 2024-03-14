<?php
    $servername = "localhost";
	$username = "root";
	$password= "12345678";
	$db_name = "Rosie";
	
	$Name = $_POST["name"];
	$Pass = $_POST["password"];
	$Email = $_POST["email"];
	$phone = $_POST["phone"];
	
	
 
    echo "Name >>> ".$Name."<br>";
    echo "Email >>> ".$Email."<br>";
    echo "pass >>> ".$Pass."<br>";
	
	
	$conn = new mysqli($servername,$username,$password,$db_name);
	
	if($conn->connect_error){
		
		
	
			die("Connection failed: " . $conn->connect_error);
	
		
	}else{
		
		echo "connected successfully : )"."<br>";
		$sql = "SELECT * FROM user WHERE name = $Name AND password = $Pass";
		$result = $conn->query($sql);
		if($result -> num_rows>0){
			
				$user = array(
				
				"name" => "no data",
				"email" => "no data",
				"password" => "no data",
				"phone" => "no data"
				);
				$task["error"] = true;
				$task["message"]= "username or email already exist";
				$task["user"] = $user;
				
				echo json_encode("some"+$task);
				
				
		}else{
			
			
			
			
			$sql2 = "INSERT INTO user (name,password,email,phone) VALUES('$Name','$Pass','$Email','$phone')";
			if($conn->query($sql2) === TRUE){
				
			
				$user = array(
				
				"name" => $Name,
				"email" => $Email,
				"password" => $Pass,
				"phone" =>$phone
				);
				$task["error"] = false;
				$task["message"]= "User Registed successfully";
				$task["user"] = $user;
				
				echo json_encode("some3"+$task);
				
				
			}else{
				
				$user = array(
				"name" => "no data",
				"email" => "no data",
				"password" => "no data",
				"phone"=> "no data"
				);
				$task["error"] = true;
				$task["message"]= "something goes wrong!";
				$task["user"] = $user;
				
				echo json_encode("some4"+$task);
				
			}
			
			
		}
	
	}

?>