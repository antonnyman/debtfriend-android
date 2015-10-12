# DebtFriend
Anton och Isak

## Dependencies
Go to https://github.com/isaklilja/Papper and clone the project to the same folder as you keep the folder for this project. For example:

/StudioProjects/DebtFriend

/StudioProjects/Papper

Add to `settings.gradle` 
```
include ':papper'
project(':papper').projectDir = new File(settingsDir, '../Papper/papper')
```

## API Endpoints
### User
`/api/register` 
**POST** username, email, password
returns status

`/api/login`
**POST** username, password
returns status, key

`/api/logout/<key>`
returns "success"

`/api/confirm/<token>`
returns static html site "account_confirmed.html"

`/api/generate/token/<email>`
*For testing only*
returns token, confirm_url

`/api/users/get/<key>`
returns user

`/api/users/get/all/<key>`
returns all users

`/api/users/delete/<id>/<key>`
returns "success"

`/api/users/find/<username>`
returns users limit 20

`/api/friend/add/<id>/<key>`
returns result

`/api/friends/all/<id>/<key>`
returns all friends of key user

### Debt
`/api/debt/add/<key>`
returns result

`/api/debt/delete/<id>/<key>`
returns result

`/api/debt/<id>/<key>`
returns debt

`/api/debt/total/<key>`
returns overview of total debts

`/api/debt/owner/all/<key>`
returns all owned debts

`/api/debt/receiver/all/<key>`
returns all recieved debts
