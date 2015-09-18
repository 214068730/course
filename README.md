## Project Title ##

Course Sample Code for teaching TP2


### Introduction ###

### Project Description ###

### Domain Model ###

# Deploying the Application to OpenShift #

This is the instructions to deploy an application to  Openshift. Am deploying the course application that is on my machine in the folder

````
/home/hashcode/IdeaProjects/course/

````


To Deploy this application to OpenShift, you need the following in place

1. Make sure your application works
2. Open An account with OpenShift
3. Install OpenShift Client tools RHC  Tools
4. Add OpenShift Hooks to your application
5. Create an application for Deployment

## Make sure your application works ##

Your application should work on your local machine as expected. ALL tests must pass.
If any of your Test fail, the application will not deploy. If you have test that can't run elsewhere
other than on your machine, just disable them. I have done so on some tests in this application.

## Open An account with OpenShift ##

This should be the easy part, head to openshift.com and create an account. Once you have created the account, create a domain.
In my case my domain is kabaso so all my apps will be on appname-kabaso.rhcloud.com


## Install OpenShift Client tools RHC  Tools

Based on your OS, find out how to install client tools for your Operating Systems. I use Linux Mint based on Ubuntu 14.04
and this is how you install RHC tools

First install Ruby Gems and Git to your OS if it is not installed

```
$ sudo apt-get install ruby-full rubygems-integration git-core

```

Test your Ruby Installations and it Should echo the string "Ruby is Installed Successfully" back

```
$ ruby -e 'puts "Ruby is Installed Successfully"'

```

Next up Install the RHC clients

```
sudo gem install rhc

```

Run the setup with command below

```
$ rhc setup

```

Here is the output from my console

```

$rhc setup
OpenShift Client Tools (RHC) Setup Wizard

This wizard will help you upload your SSH keys, set your application namespace,
and check that other programs like Git are properly installed.

If you have your own OpenShift server, you can specify it now. Just hit enter to
use the server for OpenShift Online: openshift.redhat.com.
Enter the server hostname: |openshift.redhat.com|

You can add more servers later using 'rhc server'.

Login to openshift.redhat.com: boniface@kabaso.com
Password: **********

OpenShift can create and store a token on disk which allows to you to access the
server without using your password. The key is stored in your home directory and
should be kept secret.  You can delete the key at any time by running 'rhc
logout'.
Generate a token now? (yes|no) yes
Generating an authorization token for this client ... lasts 28 days

Saving configuration to /home/hashcode/.openshift/express.conf ... done

No SSH keys were found. We will generate a pair of keys for you.

    Created: /home/hashcode/.ssh/id_rsa.pub

Your public SSH key must be uploaded to the OpenShift server to access code.
Upload now? (yes|no)
yes

  default (type: ssh-rsa)
  -----------------------
    Fingerprint: 1d:77:55:18:1f:10:ba:8b:94:82:f2:04:2e:e4:42:c7

You can enter a name for your key, or leave it blank to use the default name.
Using the same name as an existing key will overwrite the old key.

Provide a name for this key: |bonifacefocus|

Uploading key 'bonifacefocus' ... done

Checking for git ... found git version 1.9.1

Checking common problems .. done

Checking for a domain ... kabaso

Checking for applications ... found 1

  course http://course-kabaso.rhcloud.com/

  You are using 1 of 3 total gears
  The following gear sizes are available to you: small

Your client tools are now configured.

```

There are instructions on how to install client on windows on Openshift developer site.

## Add OpenShift Hooks to your application

In the root of your application, create a .openshift/action_hooks folders. Please not that these are hidden folder and created on a linux box
using the command below

```
mkdir -p /home/hashcode/IdeaProjects/course/.openshift/action_hooks

```

Next up create three files deploy, start and stop inside the action_hooks folder

```
touch /home/hashcode/IdeaProjects/course/.openshift/action_hooks/deploy
touch /home/hashcode/IdeaProjects/course/.openshift/action_hooks/start
touch /home/hashcode/IdeaProjects/course/.openshift/action_hooks/stop

```

Here are the contents of each file

deploy

```
#!/bin/bash

set -x

if [ ! -d $OPENSHIFT_DATA_DIR/m2/repository ]
        then
                cd $OPENSHIFT_DATA_DIR
				mkdir m2/repository
fi

if [ ! -d $OPENSHIFT_DATA_DIR/logs ]
        then
                cd $OPENSHIFT_DATA_DIR
				mkdir logs
fi

if [ ! -d $OPENSHIFT_DATA_DIR/apache-maven-3.3.3 ]
        then
                cd $OPENSHIFT_DATA_DIR
                wget http://apache.is.co.za/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz
                tar xvf *.tar.gz
                rm -f *.tar.gz
fi


cd $OPENSHIFT_REPO_DIR
export M2=$OPENSHIFT_DATA_DIR/apache-maven-3.3.3/bin
export MAVEN_OPTS="-Xms384m -Xmx412m"

mvn -s settings.xml clean install

```

start

```

#!/bin/bash
cd $OPENSHIFT_REPO_DIR
nohup java -Xms384m -Xmx412m -jar target/*.jar --server.port=${OPENSHIFT_DIY_PORT} --server.address=${OPENSHIFT_DIY_IP} &

```


stop

```
#!/bin/bash

source $OPENSHIFT_CARTRIDGE_SDK_BASH

PID=$(ps -ef | grep java.*\.jar | grep -v grep | awk '{ print $2 }')

if [ -z "$PID" ]
then
    client_result "Application is already stopped"
else
    kill $PID
fi

```

Next create a settings.xml file in the root of your application

```
touch /home/hashcode/IdeaProjects/course/settings.xml

```

And here is the contents for settings.xml

```
<settings>
    <localRepository>${OPENSHIFT_DATA_DIR}/m2/repository</localRepository>
</settings>

```

Also update your applications.properties to look like

```

spring.datasource.url: jdbc:mysql://${OPENSHIFT_MYSQL_DB_HOST}:${OPENSHIFT_MYSQL_DB_PORT}/${OPENSHIFT_APP_NAME}
spring.datasource.username: ${OPENSHIFT_MYSQL_DB_USERNAME}
spring.datasource.password: ${OPENSHIFT_MYSQL_DB_PASSWORD}

# Specify the DBMS
spring.jpa.database = MYSQL

# Show or not log for each sql query
spring.jpa.show-sql = true

management.context-path=/manage

spring.jpa.generate-ddl = true

# Hibernate settings are prefixed with spring.jpa.hibernate.*
spring.jpa.hibernate.ddl-auto = update
spring.jpa.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
spring.jpa.hibernate.naming_strategy = org.hibernate.cfg.ImprovedNamingStrategy

```

With all these changes made to your application, push it to your repository either using command line or your IDE


## Create an application for Deployment

I created a folder called openshift on my machine where I will host Openshif apps

```
$ cd
$ mkdir openshift
$ cd openshift
$ rhc app create course diy-0.1
```

I got the following output

```
Application Options
-------------------
Domain:     kabaso
Cartridges: diy-0.1
Gear Size:  default
Scaling:    no

Creating application 'course' ... done

  Disclaimer: This is an experimental cartridge that provides a way to try unsupported languages, frameworks, and middleware on OpenShift.

Waiting for your DNS name to be available ... done

Cloning into 'course'...

Your application 'course' is now available.

  URL:        http://course-kabaso.rhcloud.com/
  SSH to:     55f98a7b89f5cf87520001a8@course-kabaso.rhcloud.com
  Git remote: ssh://55f98a7b89f5cf87520001a8@course-kabaso.rhcloud.com/~/git/course.git/
  Cloned to:  /home/hashcode/IdeaProjects/course/course

Run 'rhc show-app course' for more details about your app.

```


Next up is adding the Database cartridge

```
rhc cartridge add mysql-5.5 -a course

```

And got this output

```

Adding mysql-5.5 to application 'course' ... done

mysql-5.5 (MySQL 5.5)
---------------------
  Gears:          Located with diy-0.1
  Connection URL: mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
  Database Name:  course
  Password:       #########
  Username:       #########

MySQL 5.5 database added.  Please make note of these credentials:

       Root User: ##########
   Root Password: ##########
   Database Name: course

Connection URL: mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/

You can manage your new MySQL database by also embedding phpmyadmin.
The phpmyadmin username and password will be the same as the MySQL credentials above.

```

Next is move into the course directory to get the application from my git repository and push it to Openshift

```
$ cd course
$ git rm -rf .openshift README.md diy misc
$ git commit -am "Removed template application source code"
$ git remote add upstream https://github.com/boniface/course.git
$ git pull -s recursive -X theirs upstream master
$ chmod a+x .openshift/action_hooks/*
$ git commit -am "Make Hooks Executable"
$ git push

```

That should start deployment of your application to Openshift. Your last lines should looklike so

```
remote: 2015-09-17 15:32:07.817  INFO 108597 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
remote: 2015-09-17 15:32:07.820  INFO 108597 --- [           main] course.App                               : Started App in 29.831 seconds (JVM running for 31.629)
remote: -------------------------
remote: Git Post-Receive Result: success
remote: Activation status: success
remote: Deployment completed with status: success
To ssh://55fafe030c1e666edd000085@course-kabaso.rhcloud.com/~/git/course.git/
   fa288f1..025669d  master -> master

```

## Issues you may encounter

I did this at home where I have no CPUT firewall nonsense, so if you are on campus it could get out of the way.
Try this in your session to get round it

```
  export HTTPS_PROXY=http://username:passwword@10.18.8.9:8080
  export HTTP_PROXY=http://username:passwword@10.18.8.9:8080

  ```
  if after this you get ssh errors, go to CTS  or lab technicainas and ask them to open port ssh on port 22

  Other error could be

  ```
  fatal: The remote end hung up unexpectedly
  error: error in sideband demultiplexer
  ```

  log into your account on command line and create a config file as below

  ```
  vim ~/.openshift_ssh/config

  ```

  and paste these values into that file

  ```
  ServerAliveInterval 60
  ServerAliveCountMax 15
  ```


## Finally

you can browse my rest API on

http://course-kabaso.rhcloud.com/











