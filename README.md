# PwFile

This program is used to create keystores to keep passwords.

#Prerequisite
At the location of the jar file, make a directory called lib, then put following apache commons library files in there, these files can be found in source dir in dist directory as well.

commons-cli-1.3.1.jar 
commons-codec-1.10.jar

# Usage

    --conffile </tmp/conffile.conf> (always required)

       --create_conffile </tmp/keystorepath,mykeystorepassword>
         create a configuration file.

       --create_keystore
         create a keystore as defined in the conf file.

       --add_key <name=value>
         Add a new keypair in the keystore.

       --update_key <name=value>
         Update the password of a key in the keystore.

       --delete_key <name>
         Delete a key in the keystore.

       --export_keys </tmp/export_keys.lst>
         Export all keys to a file.

       --import_keys </tmp/keys.lst>
         Import key pairs into the keystore.

       --get_pw <name>
         Get the password of a key.

# Example
        -- First, create a conf file, provide conf file location, keystore location, keystore password. The conf file is created, the keystore not yet.
        java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar" -conffile /tmp/test.conf -create_conffile /tmp/testkeystore.jks,testpw
        
        [oracle@soa-training ~]$ cat /tmp/test.conf
        keyStorePath=/tmp/testkeystore.jks
        keyStorePass=rxfHugROdTuJNXfXnofPtg==
        
        [oracle@soa-training ~]$ ls /tmp/testkeystore.jks
        ls: cannot access /tmp/testkeystore.jks: No such file or directory
        
        -- Create a keystore
        java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar" --create_keystore -conffile /tmp/test.conf

        [oracle@soa-training ~]$ ls /tmp/testkeystore.jks
        /tmp/testkeystore.jks
        
        --add two username/password pairs
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --add_key user1=pw1
        add key user1 done.
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --add_key user2=pw2
        add key user2 done.
        
        --read user1 password, change user1 password, then read user1 password again
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --get_pw user1
        pw1
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --update_key user1=pw3
        update key user1 done.
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --get_pw user1
        pw3
        
        --delete user1 , then check again
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --delete_key user1
        Key user1 deleted.
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --get_pw user1
        Key user1not found.
        null
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --get_pw user1
        Key user1 not found.
        
        --export keys
        [oracle@soa-training ~]$ java -jar "/home/oracle/NetBeansProjects/PwFile/dist/PwFile.jar"  -conffile /tmp/test.conf --export_keys /tmp/export1
        [oracle@soa-training ~]$ cat /tmp/export1
        user2=pw2



        
        


        

