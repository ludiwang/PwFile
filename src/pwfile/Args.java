/*
 * All Rights reserved
 * Copyright(c) 2016 L.Wang Consultancy  All Rights Reserved.
 * This software is the proprietary information of L.Wang
Consultancy.
 */
package pwfile;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;

import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author WangL
 */
public class Args {

    private String[] args = null;
    private Options options = new Options();
     
    private OptionGroup optGroup = new OptionGroup();
    private Option.Builder optBuilder = Option.builder();
    public String conf = null;
    public String argName = null;
    public String argValue = null;

    public Args(String[] args) {
        this.args = args;
        //options.addOption("conf", true, "configuration file");
         optBuilder.argName("/tmp/testExport").longOpt("confFile").hasArg(true).required(true).desc("Export all key pairs to a file.");
        Option confFile = optBuilder.build();
        options.addOption(confFile);
         

        optBuilder.longOpt("create_keystore").hasArg(false).desc("create a keystore as named in the config file.");
        Option create_keystore = optBuilder.build();

        optBuilder.argName("name=value").longOpt("add_key").hasArg(true).desc("Add a new key pair to the keystore.");
        Option add_key = optBuilder.build();

        optBuilder.argName("name=value").longOpt("update_key").hasArg(true).desc("Update the password of a key.");
        Option update_key = optBuilder.build();

        optBuilder.argName("name").longOpt("delete_key").hasArg(true).desc("Delete a key pair.");
        Option delete_key = optBuilder.build();

        optBuilder.argName("/tmp/files_with_keypairs.txt").longOpt("import_keys").hasArg(true).desc("import key pairs from file, content: name=value");
        Option import_keys = optBuilder.build();

        optBuilder.argName("/tmp/export_to_file.txt").longOpt("export_keys").hasArg(true).desc("export key pairs to file.");
        Option export_keys = optBuilder.build();

        optBuilder.argName("name").longOpt("get_pw").hasArg(true).desc("return password of a key.");
        Option get_pw = optBuilder.build();

        optGroup.addOption(create_keystore);
        optGroup.addOption(add_key);
        optGroup.addOption(update_key);
        optGroup.addOption(delete_key);
        optGroup.addOption(import_keys);
        optGroup.addOption(export_keys);
        optGroup.addOption(get_pw);
        optGroup.setRequired(true);
        options.addOptionGroup(optGroup);

    }

    public void parse() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("confFile")) {
                this.conf = cmd.getOptionValue("confFile");
            }

            if (cmd.hasOption("create_keystore")) {
                //System.out.println("create keystore");
                this.argName = "create_keystore";
            }

            if (cmd.hasOption("add_key")) {
                //System.out.println("create keystore");
                this.argName = "add_key";
                this.argValue = cmd.getOptionValue("add_key");
            }

            if (cmd.hasOption("update_key")) {
                //System.out.println("create keystore");
                this.argName = "update_key";
                this.argValue = cmd.getOptionValue("update_key");
            }

            if (cmd.hasOption("delete_key")) {
                //System.out.println("create keystore");
                this.argName = "delete_key";
            }

            if (cmd.hasOption("import_keys")) {
                //System.out.println("create keystore");
                this.argName = "import_keys";
                this.argValue = cmd.getOptionValue("import_keys");
            }

            if (cmd.hasOption("export_keys")) {
                //System.out.println("create keystore");
                this.argName = "export_keys";
                this.argValue = cmd.getOptionValue("export_keys");
            }

            if (cmd.hasOption("get_pw")) {
                //System.out.println("create keystore");
                this.argName = "get_pw";
                this.argValue = cmd.getOptionValue("get_pw");
            }

        } catch (ParseException ex) {
           // ex.printStackTrace();
            System.out.println("Failed to parse command line optoins.");
            help();
        }

    }

    private void help() {

        // This prints out some help
        HelpFormatter formater = new HelpFormatter();
         
        formater.printHelp("Main", options);

        System.exit(0);

    }

}
