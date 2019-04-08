<?php
  $filename = $_POST[source];
  $myfile = fopen("./javasource/".$filename.".java","w") or die("Unable to open file!");
  $txt = $_POST[content];
  fwrite($myfile, $txt);
  fclose($myfile);
  $output;
  exec("$HOME/pmd-bin-6.13.0/bin/run.sh pmd -d ./javasource/".$filename.".java -f text -R rulesets/java/quickstart.xml > ./javaresult/".$filename.".txt", $output, $error);
  echo "<pre>$output</pre>";
?>
