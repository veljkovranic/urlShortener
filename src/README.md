/shortenUrl
             
Example: 
POST Request
             
Body
{url: "http://google.com"}
             
Response:
{result: "0"}

/open/url?urlValue

Example:
GET Request 
urlValue = 0

Redirects to http://google.com

/stats 

Example:
GET Request 

JSON with values of number of redirections for endpoint /open
{"RS":2}