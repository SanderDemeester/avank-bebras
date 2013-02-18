#!/bin/bash
# Dit zal automatisch via een cronjob opgeroepen worden en van iedereen het
# tijdsschema automatisch samenvoegen en berekenen naar het voorbeeld op twizz

# zip files and mail
zip timeschedule.zip *.csv
echo "http://avank.ugent.be/~rtaelman/documents/timeschedules/setup/raw_schedules/timeshedule.zip" | mail -s "[VOP] Wekelijkse tijdsschemas" rtaelman@gmail.com

# empty files
> eddy.csv
> felix.csv
> jens.csv
> kevin.csv
> ruben.csv
> sander.csv
> thomas.csv 

