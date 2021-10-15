# Assumptions

`Filter` example:

{"startDateString":"2019-12-02",
"endDateString":"2019-12-04",
"sourceCurrency":"USD",
"targetCurrency":"GBP",
"amount":0.00}

"startDateString" before "endDateString" OR "startDateString" == "endDateString"

## Third party libraries

`Guava` - to make a map and list immutable (easier than with JDK) 

`OpenCSV` - to read CSV (easier than with JDK)
