# PwFile

This program is used to create keystores to keep passwords.

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


