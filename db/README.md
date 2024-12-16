# Database Initialization

First create a user in the mariadb:

```bash
sudo mariab -u root -p
```
```sql
CREATE USER '<user>'@'localhost' IDENTIFIED BY '<password>';
GRANT ALL PRIVILEGES ON *.* TO '<user>'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```

Then modify the `.env` file:

```bash
SERVICE_ARCHITECTURE_USER=user
SERVICE_ARCHITECTURE_PASSWORD=password
```

**And finally run the `init.sh` to create the database.**

> If you want to update the database, it is recommended to run `remove.sh` before hand.