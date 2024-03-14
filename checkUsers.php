<?php


	$servername = "localhost";
	$username = "root";
	$password= "12345678";
	$db_name = "Rosie";
	
	$Email = $_POST["email"];
	$Pass = $_POST["password"];
	
	
 
   // echo "Email >>> ".$Email."<br>";
   // echo "pass >>> ".$Pass."<br>";
	
	
	$conn = new mysqli($servername,$username,$password,$db_name);
	
	if($conn->connect_error){
		
		
		//die("not connected successfully :(");
		
	}else{
		
		
		//echo "connected successfully :)";
		$sql = "SELECT * FROM user WHERE email ='$Email' AND password = '$Pass'";
		$result = $conn->query($sql);
		if($result ->num_rows>0){
			
			//echo "some data selected";
			while($row = $result -> fetch_assoc()){
				
				$user = array(
				
				"id" => $row["id"],
				"name" => $row["name"],
				"email" => $row["email"],
				"password" => $row["password"],
				"phone" => $row["phone"]
				);
				
				$task["error"] = false;
				$task["message"]= "Loged In Successfully";
				$task["user"] = $user;
				
				
				
				
			}
			
			 echo json_encode($task);
			
			
			
		}else{
			//sd
			
				$user = array(
				
				"id" => 0,
				"name" => "No Data",
				"email" => "No Data",
				"password" => "No Data",
				"phone" => "No Data"
				);
				
				$task["error"] = true;
				$task["message"]= " Faield to Logging";
				$task["user"] = $user;
			
			echo json_encode($task);
		}
		
	}

?>