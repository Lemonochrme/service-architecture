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


### Install MariaDB on Debian

1. Update the package list:
   ```bash
   sudo apt update
   ```

2. Install MariaDB:
   ```bash
   sudo apt install mariadb-server mariadb-client
   ```

3. Secure the installation:
   ```bash
   sudo mysql_secure_installation
   ```

4. Enable and start MariaDB:
   ```bash
   sudo systemctl enable mariadb
   sudo systemctl start mariadb
   ```

---

### Create a Database User

1. Access MariaDB as root:
   ```bash
   sudo mariadb -u root -p
   ```

2. Execute the following commands to create a new user:
   ```sql
   CREATE USER '<user>'@'localhost' IDENTIFIED BY '<password>';
   GRANT ALL PRIVILEGES ON *.* TO '<user>'@'localhost' WITH GRANT OPTION;
   FLUSH PRIVILEGES;
   ```

---

### Initialize the Database

- Run the `init.sh` script to set up the database:
  ```bash
  ./init.sh
  ```

- To clean up before reinitializing, execute:
  ```bash
  ./remove.sh
  ```
