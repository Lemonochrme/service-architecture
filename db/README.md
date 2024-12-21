# Database Initialization

### Install mariadb:

#### On Arch-Linux
```
pacman -S mariadb
sudo mariadb-install-db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
sudo chown -R mysql:mysql /var/lib/mysql
sudo systemctl enable mariadb
sudo systemctl start mariadb
```

### Create a user

```bash
sudo mariadb -u root -p
```
```sql
CREATE USER '<user>'@'localhost' IDENTIFIED BY '<password>';
GRANT ALL PRIVILEGES ON *.* TO '<user>'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```

### Run the `init.sh` to create the database.

> If you want to update the database, it is recommended to run `remove.sh` before hand.