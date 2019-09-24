Spring_MVC_Blog deploy:

Wildfly

install: https://vitux.com/install-and-configure-wildfly-jboss-on-ubuntu/

version:
Product Name - WildFly Full
Product Version - 16.0.0.Final
Release Name
Release Version - 8.0.0.Final
Management Version - 10.0.0
Console Version - 3.1.2.Final
Operation Mode - STANDALONE


../Spring_MVC_Blog/src/main/webapp/WEB-INF/jboss-web.xml:
<?xml version="1.0" encoding="UTF-8"?>
<jboss-web>
    <context-root>/</context-root>
</jboss-web>

http://localhost:8080/
http://localhost:9990/console/index.html admin-wildfly/admin-wildfly

start/stop:
sudo service wildfly stop
sudo service wildfly start
sudo service wildfly restart

MySql

install:
sudo apt-get update
sudo apt-get install mysql-server-5.7

version:
mysql  Ver 14.14 Distrib 5.7.27, for Linux (x86_64) using  EditLine wrapper

configure:
sudo mysql -u root
use mysql;
SELECT User, Host, plugin FROM mysql.user;
+------------------+-----------+-----------------------+
| User             | Host      | plugin                |
+------------------+-----------+-----------------------+
| root             | localhost | auth_socket           |
| mysql.session    | localhost | mysql_native_password |
| mysql.sys        | localhost | mysql_native_password |
| debian-sys-maint | localhost | mysql_native_password |
+------------------+-----------+-----------------------+
UPDATE user SET plugin='mysql_native_password' WHERE User='root';
FLUSH PRIVILEGES;
exit;

fill data:
execute scripts.sql (located in project)

start/stop:
sudo service mysql stop
sudo service mysql restart
sudo service mysql start

JMX:
1. Run JConsole:
$JAVA_HOME/bin/jconsole -J-Djava.class.path=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/jconsole.jar:/opt/wildfly-8.2.0.Final/bin/client/jboss-client.jar
(../jboss-client.jar OR ../jboss-cli-client.jar)
2. Set Remote url:
service:jmx:http-remoting-jmx://localhost:9990

JMS/ActiveMQ:
/var/lib/activemq/bin/artemis run
/var/lib/activemq/bin/artemis stop
