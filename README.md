# activemq-plugin
ActiveMQ Plugins for authentication and authroization

# Description

This project implements ActiveMQ security plugin that allows dynamic and generci queue authorization<br>

Supose you have to autorize :

* a user 'user1' with a role 'role1' to deque from 'queue1'
* a user 'user2' with a role 'role2' to deque from 'queue2'
* and so on ...

This plugin allows to implement this type of authorization without the necesity of manually configure one authroization entry for each queue.<br>

The authorization is based on a 'pattern' that relates a role name to a queue name

This way you don't have to manually configure each queue authorization and yo can add queues dinamically withouth the need of re configuration


