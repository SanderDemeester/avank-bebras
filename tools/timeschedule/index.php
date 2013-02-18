<?php require_once('config.php'); ?>
<?php
if(isset($_REQUEST['username']) && isset($_REQUEST['password']) && !isset($_COOKIE['authkey'])) {
//SIGNING IN
	if($users[$_REQUEST['username']]==$_REQUEST['password']) {
		setcookie('authkey', $_REQUEST['username'].','.md5($_REQUEST['username'].$key));
		draw_loggedin($username, false);
	}
	else {
		draw_error("Jammer, het is mis...");
	}
}
elseif ($_COOKIE['authkey']) {
	list($username,$hash) = split(',',$_COOKIE['authkey']);
	if (md5($username.$key) == $hash) {
        // SUCCESFULLY SIGNED IN
		if(isset($_REQUEST['datum']) && isset($_REQUEST['begin']) && isset($_REQUEST['eind'])) {
			draw_loggedin($username, true, $_REQUEST['datum'], $_REQUEST['begin'], $_REQUEST['eind'], $_REQUEST['taak']);
		}
		else {
			draw_loggedin($username, false);
		}
    } else {
		setcookie ("authkey", "", time() - 3600);
        draw_error("Fout bij het inloggen");
    }
//AUTH	
}
else
{
//NOT AUTH
	draw_login();
}

/**
 * ----- FUNCTIES -----
**/

function get_schedule($username) {
	$file="raw_schedules/".$username.".csv";
	$fh = fopen($file, 'r');
	return fread($fh, filesize($file));
}

function draw_loggedin($username, $insert, $datum="", $begin="", $eind="", $taak="") {
	if($insert) {
		$file="raw_schedules/".$username.".csv";
		$fh = fopen($file, 'a');
		fwrite($fh, $datum.",".$begin.",".$eind.",\"".$taak."\"\n");
	}
	$content="Nieuwe entry: <br />";
	$content.='
<script type="text/javascript"> 

function stopRKey(evt) { 
  var evt = (evt) ? evt : ((event) ? event : null); 
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
} 

document.onkeypress = stopRKey; 

</script>
<form method="post" action="">
Datum: <input type="date" name="datum">
Begin: <input type="time" name="begin">
Einde: <input type="time" name="eind">
Beschrijving: <input type="text" name="taak" size="100"><br />
<input type="submit" value="Voeg Toe">
</form>
<hr />
';
	$content.="Jouw huidig tijdsschema: <br />";
	$content.=str_replace("\n","<br />",get_schedule($username));
	$content.="<hr />";
	$content.="Als je iets kapot hebt gemaakt kan je het bestand handmatig aanpassen op deze locatie: <b>/home/rtaelman/public_html/documents/timeschedules/setup/raw_schedules/".$username.".csv</b>";
	draw_page($content);
}

function draw_error($error) {
	draw_page($error." <a href='index.php'>nieuwe poging</a>");
}

function draw_login() {
	$content='
<form method="post" action="">
Naam: <input type="text" name="username"> <br>
Wachtwoord: <input type="password" name="password"> <br>
<input type="submit" value="Log In">
</form>';
	draw_page($content);
}

function draw_page($content) {
	?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Tijdschema</title>
</head>

<body>
<?php echo $content;?>
</body>
</html>
	<?php
}
?>