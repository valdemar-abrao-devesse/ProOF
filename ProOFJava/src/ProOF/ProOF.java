/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProOF;

import ProOF.apl.factorys.fRun;
import ProOF.com.model.Model;
import ProOF.com.runner.Runner;
import ProOF.opt.abst.problem.meta.Best;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;


/**
 *
 * @author marcio
 */
public class ProOF { 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, Exception {
        boolean local = false;
        if (args == null || args.length == 0) {
            local = true;
            args = new String[]{"run", "../work_space/job_local/waiting/task", "../work_space/input/"};
        }
        try{
            starting(args, local);
        }catch(Throwable ex){
            ex.printStackTrace(System.err);
            PrintStream log = new PrintStream(new File("log_error.txt"));
            ex.printStackTrace(log);
            log.close();
        }
        if(!local){
            System.exit(0);
        }
    }
    
    private static void starting(String[] args, boolean local) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        if (args == null || args.length < 1) {
            throw new Exception("don't have arguments");
        } else if (args[0].equals("model")) {
            Model.PRINT = true;
            Model model = new Model();
            model.create(fRun.obj);
            model.savePof("model.pof");
            model.saveSgl("model.sgl");
        } else if (args[0].equals("run")) {
            Runner.PRINT = false;
            Runner.LOCAL = local;
            Best.force_finish(true);
            Runner runner = new Runner(new File(args[1]), new File(args[2]), fRun.obj);
            runner.run();
        } else {
            throw new Exception(String.format("arg[0]='%s' is not recognized.", args[0]));
        }
    }
}