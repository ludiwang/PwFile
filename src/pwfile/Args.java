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
 * Args class processes command line arguments and provides usage text.
 * 
 *
 * @author WangL
 * @version 1.0
 * @since 01-June-2016
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
         optBuilder.argName("/tmp/testExport").longOpt("conffile").hasArg(true).required(true).desc("Export all key pairs to a file.");
        Option conffile = optBuilder.build();
        options.addOption(conffile);
         
        optBuilder.longOpt("create_conffile").hasArg(true).desc("create conffile");
        Option create_conffile = optBuilder.build();
        
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
        optGroup.addOption(create_conffile);
        optGroup.addOption(add_key);
        optGroup.addOption(update_key);
        optGroup.addOption(delete_key);
        optGroup.addOption(import_keys);
        optGroup.addOption(export_keys);
        optGroup.addOption(get_pw);
        optGroup.setRequired(true);
        options.addOptionGroup(optGroup);

    }

    /**
     * This method parses the command line arguments.
     */
    public void parse() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("conffile")) {
                this.conf = cmd.getOptionValue("conffile");
            }
            
            if (cmd.hasOption("create_conffile")) {
                //System.out.println("create keystore");
                this.argName = "create_conffile";
                this.argValue = cmd.getOptionValue("create_conffile");
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
                this.argValue = cmd.getOptionValue("delete_key");
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
/**
 * This method provides usage text.
 */
    protected void help() {

        // This prints out some help
        HelpFormatter formatter = new HelpFormatter();
         
        //formatter.printHelp("Main", options);
        String padding1 ="     ";
        String padding2 ="       ";
        String padding3 ="         ";
        System.out.println("Usage: ");
        //System.out.println(padding + "Required with all commands:");
        System.out.println(padding1 + "--conffile </tmp/conffile.conf> (always required)"); 
        
        System.err.println("");
        System.out.println(padding2 + "--create_conffile </tmp/keystorepath,mykeystorepassword>");
        System.out.println(padding3 + "create a configuration file.");
        
        System.err.println("");
        System.out.println(padding2 + "--create_keystore");
        System.out.println(padding3 + "create a keystore as defined in the conf file.");
        
        System.err.println("");
        System.out.println(padding2 + "--add_key <name=value>");
        System.out.println(padding3 + "Add a new keypair in the keystore.");

        System.err.println("");
        System.out.println(padding2 + "--update_key <name=value>");
        System.out.println(padding3 + "Update the password of a key in the keystore.");
        
        System.err.println("");
        System.out.println(padding2 + "--delete_key <name>");
        System.out.println(padding3 + "Delete a key in the keystore.");
        
        System.err.println("");
        System.out.println(padding2 + "--export_keys </tmp/export_keys.lst>");
        System.out.println(padding3 + "Export all keys to a file.");
        
        System.err.println("");
        System.out.println(padding2 + "--import_keys </tmp/keys.lst>");
        System.out.println(padding3 + "Import key pairs into the keystore.");
        
        System.err.println("");
        System.out.println(padding2 + "--get_pw <name>");
        System.out.println(padding3 + "Get the password of a key.");
        
        
        
        System.exit(0);

    }

}
