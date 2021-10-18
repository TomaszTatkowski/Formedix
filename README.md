# Notes
"N/A" value from CSV file represents as 0

# Request

`Filter (body)` example:

{"startDateString":"2019-12-02",
"endDateString":"2019-12-04",
"sourceCurrency":"USD",
"targetCurrency":"GBP",
"amount":0.00}

"startDateString" before "endDateString" OR "startDateString" == "endDateString"

## Endpoints
/all-exchange-by-date

/conversion-by-date

/highest-conversion-by-date

/avg-conversion-by-date

/filter-example

/available-currency


example request in comments above mapping

## Third party libraries

`Guava` - to make a map and list immutable (easier than with JDK) 

`OpenCSV` - to read CSV (easier than with JDK)

## Things to do
Request validation - 'BAD REQUEST' response when fail (e.g String date validation)

Enable Swagger

Logs

More unit tests - obviously
