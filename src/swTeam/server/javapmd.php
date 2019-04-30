<?php
  $filename = $_POST[source];
  $myfile = fopen("./javasource/".$filename.".java","w") or die("Unable to open file!");
  $txt = $_POST[content];
  fwrite($myfile, $txt);
  fclose($myfile);
  $output = shell_exec("./pmd-bin-6.13.0/bin/run.sh pmd -d ./javasource/".$filename.".java -f text -R rulesets/java/quickstart.xml");
  echo "<pre>$output</pre>";
	$write = fopen("./javaresult/".$filename.".txt","w");
	fwrite($write, $output);
	fclose($write);
?>
