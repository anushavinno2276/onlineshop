# Onlineshop

Monitoring the world’s espresso machines and measuring our love of coffee, in the cloud.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See **Deployment** for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software 

```

1.Java
2.Mysql
3.Apache Tomcat Server

```
### Database Table Requirments

1. **user** Table
````
id, name, password, email, address, created_timestamp, updated_timestamp, user_activation_key, active, user_type
````
2.**product** Table
````
id, name, cost, colour, imageurl, timestamp, quantity, notify, discount
````
3.**user_cart** Table
````
id, user_id, product_id, quantity, timestamp, last_notified_time
````

4.**seller_product** Table
````
user_id, product_id, quantity, added_timestamp
````
5.**recently_added_product**
````
id, product_id
````
6.**order_product**
````
id, user_id, product_id, quantity, order_timestamp
````
7.**notify_product**
````
id, user_id, product_id
````

####JNDI configaration 

In context.xml have to configure jndi by adding below tag
````
<Resource name="<jndi name>" auth="Container" type="javax.sql.DataSource"
		maxTotal="12" maxIdle="3" maxWaitMillis="20000" driverClassName="<Your Driver class name>"
		url="jdbc:mysql://localhost:3306/online_shopping" username="<Your username>"
		password="<Your password>" />

````

## Authors

* **Akshay Mehta** 




